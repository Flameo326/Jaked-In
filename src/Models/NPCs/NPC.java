package Models.NPCs;

import Controller.StoryController;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public abstract class NPC extends Entity implements Interactable{
	
	private StoryController controller;

	public NPC(Image i, StoryController st, int x, int y) {
		super(i, x, y, (int)i.getWidth(), (int)i.getHeight());
		controller = st;
		setTag("NPC");
		setDisplayLayer(6);
	}
	
	@Override
	public void hasCollided(Collision c){
		Entity collider;
		if(c.collidingEntity == this){
			collider = c.collidedEntity;
		} else { 
			collider = c.collidingEntity;
		}
		String[] tagElements = collider.getTag().split("-");
		switch(tagElements[0]){
		case "Human":
			interact((PlayableCharacter)collider);
			break;
		}
	}

	public StoryController getController(){
		return controller;
	}
	
}
