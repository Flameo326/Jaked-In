package Models.NPCs;

import java.util.ArrayList;

import Interfaces.Interactable;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class AllyNPC extends PlayableCharacter implements Interactable {

	// could have an Awakend statuc to tell wether they are stationary or following player...
	
	public AllyNPC(Image i, int x, int y) {
		super(i, x, y);
		setTag(getTag() + "-AllyNPC");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub
	}

	@Override
	public void interact(PlayableCharacter c) {
		
	}

}
