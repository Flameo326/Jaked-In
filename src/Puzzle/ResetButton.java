package Puzzle;

import Interfaces.Interactable;
import Models.Players.PlayableCharacter;

public class ResetButton implements Interactable{
	
	public ResetButton(){
		
	}

	@Override
	public void interact(PlayableCharacter c) {
		CombinedColor.resetColor();
		
	}

}
