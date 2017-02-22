package Controller;

import java.util.ArrayList;

import Models.Players.PlayableCharacter;

public class HumanController {
	
	private PlayableCharacter player;
	private ArrayList<String> inputs;
	
	public HumanController(ArrayList<String> input, PlayableCharacter person){
		inputs = input;
		player = person;
	}
	
	public void checkForInput(){
		System.out.print("[");
		for(String s : inputs){
			System.out.print(s + ", ");
		}
		System.out.println("]");
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
