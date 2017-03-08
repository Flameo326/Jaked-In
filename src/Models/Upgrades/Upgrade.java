package Models.Upgrades;

import java.util.ArrayList;

import Controller.GameController;
import Interfaces.Collectable;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public abstract class Upgrade extends Entity implements Collectable{
	
	protected boolean isCollected;
	private long timer;

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
		if(isCollected) { return; }
		Entity collider;
		PlayableCharacter collidedChar;
		
		if(c.collidedEntity == this){
			collider = c.collidingEntity;
		} else { collider = c.collidedEntity; }
		
		if(collider instanceof PlayableCharacter){
			collidedChar = (PlayableCharacter)collider;
		} else {
			//Upgrade can not do anything
			return;
		}
		
		String[] tagElements = collider.getTag().split("-");
		switch(tagElements[0]){
		case "Human":
			collect(collidedChar);
			break;
		case "Computer":
			if(!GameController.getStoryMode()){
				collect(collidedChar);
			}
			break;
		}
	}
	
	private void setTimer(int val){
		timer = GameController.getTimer() + val * 1000000000l;
	}

}
