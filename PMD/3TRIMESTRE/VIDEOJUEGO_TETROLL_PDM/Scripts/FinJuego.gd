extends Control

func _ready():
	$VBoxContainer/ScoreLabel.text = "Puntuación Final: " + str(Global.get_score())

func _on_button_reiniciar_pressed():
	Global.reset_game()
	Global.change_scene("res://Scenes/MenuPrincipal.tscn")
