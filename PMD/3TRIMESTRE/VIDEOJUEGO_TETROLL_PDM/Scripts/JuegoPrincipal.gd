extends Node2D

const COLS = 10
const ROWS = 20
const CELL_SIZE = 40

var grid = [] # Matriz 2D de ColorRects (null si está vacío)

# Formas de los Tetrominós
const SHAPES = [
	[Vector2(-1, 0), Vector2(0, 0), Vector2(1, 0), Vector2(2, 0)], # I
	[Vector2(-1, -1), Vector2(-1, 0), Vector2(0, 0), Vector2(1, 0)], # J
	[Vector2(1, -1), Vector2(-1, 0), Vector2(0, 0), Vector2(1, 0)], # L
	[Vector2(0, -1), Vector2(1, -1), Vector2(0, 0), Vector2(1, 0)], # O
	[Vector2(0, -1), Vector2(1, -1), Vector2(-1, 0), Vector2(0, 0)], # S
	[Vector2(0, -1), Vector2(-1, 0), Vector2(0, 0), Vector2(1, 0)], # T
	[Vector2(-1, -1), Vector2(0, -1), Vector2(0, 0), Vector2(1, 0)] # Z
]

# Colores para cada Tetrominó
const COLORS = [
	Color(0.0, 1.0, 1.0), # I (Cyan)
	Color(0.0, 0.0, 1.0), # J (Azul)
	Color(1.0, 0.5, 0.0), # L (Naranja)
	Color(1.0, 1.0, 0.0), # O (Amarillo)
	Color(0.0, 1.0, 0.0), # S (Verde)
	Color(0.5, 0.0, 1.0), # T (Morado)
	Color(1.0, 0.0, 0.0)  # Z (Rojo)
]

var active_piece_type: int
var active_piece_cells: Array
var active_pos: Vector2
var fast_drop: bool = false
var normal_wait_time: float = 0.5
var block_texture: Texture2D
var dam_texture: Texture2D


@onready var blocks_node = $GridContainer/Blocks
@onready var active_piece_node = $GridContainer/ActivePiece
@onready var score_label = $UI/ScoreLabel
@onready var fall_timer = $FallTimer
@onready var pause_overlay = $UI/PauseOverlay

var is_paused: bool = false

# Nodos de audio - INSTRUCCIÓN PARA EL ALUMNO:
# Arrastra tus archivos .wav o .ogg a la propiedad 'Stream' de estos nodos en el editor
@onready var rotate_sound = $Audio/RotateSound
@onready var lock_sound = $Audio/LockSound
@onready var line_clear_sound = $Audio/LineClearSound
@onready var game_over_sound = $Audio/GameOverSound

func _ready():
	# Cargar sonidos automáticamente desde la carpeta Assets
	rotate_sound.stream = load("res://Assets/RotateSound.mp3")
	line_clear_sound.stream = load("res://Assets/LineClearSound.mp3")
	game_over_sound.stream = load("res://Assets/GameOverSound.mp3")
	
	var lock_snd = load("res://Assets/LockSound.mp3")
	if lock_snd:
		lock_sound.stream = lock_snd
		
	# Las texturas ya están importadas, usamos load normal con formato .jpg correcto
	block_texture = load("res://Assets/block_sprite.jpg")
	dam_texture = load("res://Assets/dam_sprite.jpg")

	# Inicializar la cuadrícula vacía
	for y in range(ROWS):
		var row = []
		for x in range(COLS):
			row.append(null)
		grid.append(row)
		
	update_score_label()
	spawn_piece()

func _process(_delta):
	if is_paused:
		return
		
	# Soporte para teclado (PC)
	if Input.is_action_just_pressed("ui_left"):
		move_active_piece(Vector2(-1, 0))
	elif Input.is_action_just_pressed("ui_right"):
		move_active_piece(Vector2(1, 0))
	elif Input.is_action_just_pressed("ui_up") or Input.is_action_just_pressed("ui_accept"):
		rotate_active_piece()
	
	if Input.is_action_just_pressed("ui_down"):
		_on_btn_drop_down()
	elif Input.is_action_just_released("ui_down"):
		_on_btn_drop_up()

func spawn_piece():
	active_piece_type = randi() % SHAPES.size()
	active_piece_cells = SHAPES[active_piece_type].duplicate(true)
	active_pos = Vector2(COLS / 2, 1)
	
	if check_collision(active_pos, active_piece_cells):
		game_over()
	else:
		draw_active_piece()

func move_active_piece(dir: Vector2) -> bool:
	var new_pos = active_pos + dir
	if not check_collision(new_pos, active_piece_cells):
		active_pos = new_pos
		draw_active_piece()
		return true
	return false

func rotate_active_piece():
	if active_piece_type == 3: # La pieza 'O' (cuadrado) no necesita rotar
		return
		
	var new_cells = []
	for cell in active_piece_cells:
		# Rotación de 90 grados: (x, y) -> (-y, x)
		new_cells.append(Vector2(-cell.y, cell.x))
		
	if not check_collision(active_pos, new_cells):
		active_piece_cells = new_cells
		if rotate_sound.stream != null:
			rotate_sound.play()
		draw_active_piece()

func check_collision(pos: Vector2, cells: Array) -> bool:
	for cell in cells:
		var grid_x = int(pos.x + cell.x)
		var grid_y = int(pos.y + cell.y)
		
		if grid_x < 0 or grid_x >= COLS or grid_y >= ROWS:
			return true
		if grid_y >= 0 and grid[grid_y][grid_x] != null:
			return true
	return false

func lock_piece():
	var idx = 0
	for cell in active_piece_cells:
		var grid_x = int(active_pos.x + cell.x)
		var grid_y = int(active_pos.y + cell.y)
		if grid_y >= 0 and grid_y < ROWS and grid_x >= 0 and grid_x < COLS:
			var tex = block_texture
			var is_dam = false
			if active_piece_type == 3 and dam_texture != null:
				is_dam = true
				tex = AtlasTexture.new()
				tex.atlas = dam_texture
				var rx = (idx % 2) * (dam_texture.get_width() / 2)
				var ry = (idx / 2) * (dam_texture.get_height() / 2)
				tex.region = Rect2(rx, ry, dam_texture.get_width() / 2, dam_texture.get_height() / 2)
				
			grid[grid_y][grid_x] = {
				"color": COLORS[active_piece_type],
				"texture": tex,
				"is_dam": is_dam
			}
		idx += 1
	
	if lock_sound.stream != null:
		lock_sound.play()
	
	check_lines()
	spawn_piece()
	draw_grid()

func check_lines():
	var lines_to_clear = []
	for y in range(ROWS):
		var full = true
		for x in range(COLS):
			if grid[y][x] == null:
				full = false
				break
		if full:
			lines_to_clear.append(y)
			
	if lines_to_clear.size() > 0:
		if line_clear_sound.stream != null:
			line_clear_sound.play()
			
		for y in lines_to_clear:
			grid.remove_at(y)
			var new_row = []
			for x in range(COLS):
				new_row.append(null)
			grid.push_front(new_row)
			
		var points = lines_to_clear.size() * 100
		Global.add_score(points)
		update_score_label()
		
		# Aumentar dificultad
		if Global.get_score() >= Global.level * 500:
			Global.level += 1
			normal_wait_time = max(0.1, normal_wait_time - 0.05)
			if not fast_drop:
				fall_timer.wait_time = normal_wait_time

func draw_active_piece():
	# Limpiar dibujos anteriores de la pieza activa
	for child in active_piece_node.get_children():
		child.queue_free()
		
	var color = COLORS[active_piece_type]
	var idx = 0
	for cell in active_piece_cells:
		var tex = block_texture
		var is_dam = false
		if active_piece_type == 3 and dam_texture != null:
			is_dam = true
			tex = AtlasTexture.new()
			tex.atlas = dam_texture
			var rx = (idx % 2) * (dam_texture.get_width() / 2)
			var ry = (idx / 2) * (dam_texture.get_height() / 2)
			tex.region = Rect2(rx, ry, dam_texture.get_width() / 2, dam_texture.get_height() / 2)
			
		if tex != null:
			var rect = TextureRect.new()
			rect.texture = tex
			if is_dam:
				rect.modulate = Color.WHITE
			else:
				rect.modulate = color
			rect.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
			rect.size = Vector2(CELL_SIZE - 2, CELL_SIZE - 2)
			rect.position = Vector2((active_pos.x + cell.x) * CELL_SIZE, (active_pos.y + cell.y) * CELL_SIZE)
			active_piece_node.add_child(rect)
		else:
			var rect = ColorRect.new()
			rect.color = color
			rect.size = Vector2(CELL_SIZE - 2, CELL_SIZE - 2)
			rect.position = Vector2((active_pos.x + cell.x) * CELL_SIZE, (active_pos.y + cell.y) * CELL_SIZE)
			active_piece_node.add_child(rect)
		idx += 1

func draw_grid():
	# Limpiar tablero dibujado anteriormente
	for child in blocks_node.get_children():
		child.queue_free()
		
	for y in range(ROWS):
		for x in range(COLS):
			if grid[y][x] != null:
				var cell_data = grid[y][x]
				if typeof(cell_data) == TYPE_COLOR:
					cell_data = {"color": cell_data, "texture": block_texture, "is_dam": false}
					
				if cell_data.has("texture") and cell_data["texture"] != null:
					var rect = TextureRect.new()
					rect.texture = cell_data["texture"]
					if cell_data.get("is_dam", false):
						rect.modulate = Color.WHITE
					else:
						rect.modulate = cell_data["color"]
					rect.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
					rect.size = Vector2(CELL_SIZE - 2, CELL_SIZE - 2)
					rect.position = Vector2(x * CELL_SIZE, y * CELL_SIZE)
					blocks_node.add_child(rect)
				else:
					var rect = ColorRect.new()
					rect.color = cell_data.get("color", Color.WHITE)
					rect.size = Vector2(CELL_SIZE - 2, CELL_SIZE - 2)
					rect.position = Vector2(x * CELL_SIZE, y * CELL_SIZE)
					blocks_node.add_child(rect)

func update_score_label():
	score_label.text = "Puntuación: " + str(Global.get_score())

func game_over():
	fall_timer.stop()
	if game_over_sound.stream != null:
		game_over_sound.play()
	
	# Pausa para que se escuche el sonido antes del cambio de escena
	await get_tree().create_timer(1.0).timeout
	Global.change_scene("res://Scenes/FinJuego.tscn")

func _on_fall_timer_timeout():
	if not move_active_piece(Vector2(0, 1)):
		lock_piece()

# Señales de los controles móviles
func _on_btn_left_pressed():
	if not is_paused: move_active_piece(Vector2(-1, 0))

func _on_btn_right_pressed():
	if not is_paused: move_active_piece(Vector2(1, 0))

func _on_btn_rotate_pressed():
	if not is_paused: rotate_active_piece()

func _on_btn_drop_down():
	if not is_paused:
		fast_drop = true
		fall_timer.wait_time = 0.05

func _on_btn_drop_up():
	if not is_paused:
		fast_drop = false
		fall_timer.wait_time = normal_wait_time

func toggle_pause():
	is_paused = !is_paused
	pause_overlay.visible = is_paused
	if is_paused:
		fall_timer.stop()
	else:
		fall_timer.start()

func _on_btn_pause_pressed():
	toggle_pause()

func _input(event):
	if event is InputEventKey and event.pressed and not event.echo:
		if event.keycode == KEY_P:
			toggle_pause()
