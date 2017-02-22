package Controller;

import java.util.HashMap;

import Models.Players.PlayableCharacter;

public class HumanController {
	
	private PlayableCharacter player;
	private HashMap<String, Boolean> inputs;
	
	public HumanController(HashMap<String, Boolean> input, PlayableCharacter person){
		inputs = input;
		player = person;
	}
	
	public void checkForInput(){
		System.out.print("[");
		for(String s : inputs.keySet()){
			System.out.print(s + ", ");
		}
		System.out.println("]");
		if(inputs.containsKey("w")){
			player.move(0, -1);
		} 
		if(inputs.containsKey("s")){
			player.move(0, 1);
		}
		if(inputs.containsKey("a")){
			player.move(-1, 0);
		}
		if(inputs.containsKey("d")){
			player.move(1,  0);
		}
	}

}
