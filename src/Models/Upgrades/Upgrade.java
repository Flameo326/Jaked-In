package Models.Upgrades;

import java.util.ArrayList;

import Interfaces.Collectable;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public abstract class Upgrade extends Entity implements Interactable, Collectable{
	
	protected boolean isCollected;

	public Upgrade(Image i, int x, int y) {
		super(i, x, y, (int)i.getWidth(), (int)i.getHeight());
		setDisplayLayer(4);
		setTag("Upgrade");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		if(isCollected){
			entities.remove(this);
		}
	}
	
	@Override
	public void hasCollided(Collision c) {
		Entity collider;
		if(c.collidingEntity == this){
			collider = c.collidedEntity;
		} else { 
			collider = c.collidingEntity;
		}
		String[] tagElements = collider.getTag().split("-");
		switch(tagElements[0]){
		case "Human":
			collect((PlayableCharacter)collider);
			break;
		}
	}

}
