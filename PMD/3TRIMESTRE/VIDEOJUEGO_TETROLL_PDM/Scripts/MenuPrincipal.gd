extends Control

func _ready():
	pass

func _on_button_jugar_pressed():
	Global.reset_game()
	Global.change_scene("res://Scenes/JuegoPrincipal.tscn")
