package Controller;

import java.util.ArrayList;

import Models.Players.PlayableCharacter;

public class HumanMovementController {
	
	private PlayableCharacter player;
	private ArrayList<String> inputs;
	
	public HumanMovementController(ArrayList<String> input, PlayableCharacter person){
		inputs = input;
		player = person;
	}
	
	public void checkForInput(){
		if(inputs.contains("w")){
			player.move(0, -1);
		} 
		if(inputs.contains("s")){
			player.move(0, 1);
		}
		if(inputs.contains("a")){
			player.move(-1, 0);
		}
		if(inputs.contains("d")){
			player.move(1,  0);
		}
	}

}
