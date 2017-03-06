package Models.NPCs;

import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public abstract class NPC extends Entity implements Interactable{

	public NPC(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
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
		System.out.println(collider);
		System.out.println(collider.getTag());
		String[] tagElements = collider.getTag().split("-");
		switch(tagElements[0]){
		case "Human":
			interact((PlayableCharacter)collider);
			break;
		}
	}

}
