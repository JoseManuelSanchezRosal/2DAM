extends Node

var score: int = 0
var lines_cleared: int = 0
var level: int = 1

func reset_game():
	score = 0
	lines_cleared = 0
	level = 1

func add_score(points: int):
	score += points
	
func get_score() -> int:
	return score
	
func change_scene(scene_path: String):
	get_tree().change_scene_to_file(scene_path)
