package Models.Weapon;

import java.util.ArrayList;

import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class HitBox extends Entity{

	private PlayableCharacter ownedEntity;
	private int lifeTime, timer;

	// This will be for Like Melee Weapons...
	public HitBox(PlayableCharacter e, Image i) {
		super(i, e.getCenterXPos()-(int)i.getWidth()/2, e.getCenterYPos()-(int)i.getHeight()/2, (int)i.getWidth(), (int)i.getHeight());
		ownedEntity = e;
		setTag("Attack-Melee-" + ownedEntity.getTag());
		setDisplayLayer(5);
	}

	@Override
	public void hasCollided(Collision c) {
		Entity collider;
		if(c.collidedEntity == this){
			collider = c.collidingEntity;
		} else { collider = c.collidedEntity; }
		String[] tagElements = collider.getTag().split("-");
		// We have exactly 2 elements, type and ID
		String[] ourElements = getTag().split("-");
		System.out.println("Colliding: " + getTag());
		System.out.println("Collider: " + collider.getTag());
		
		switch(tagElements[0]){
		case "Human":
		case "Computer":
		case "NPC":
			// do damage if possible
			break;
		}
	}

	// Position the hitBox in the direction that the object was moving
	@Override
	public void update(ArrayList<Entity> entities) {
		if(++timer >= lifeTime){
			entities.remove(this);
		}
		int xDir = getOwnedEntity().getCurrDir().getX();
		int yDir = getOwnedEntity().getCurrDir().getY();
		if(xDir > 0){
			setXPos(ownedEntity.getCenterXPos() + ownedEntity.getWidth()/2);
		} else if(xDir < 0){
			setXPos(ownedEntity.getCenterXPos() - ownedEntity.getWidth()/2 - getWidth());
		} else {
			setXPos(ownedEntity.getCenterXPos()-getWidth()/2);
		}
		if(yDir > 0){
			setYPos(ownedEntity.getCenterYPos() + ownedEntity.getHeight()/2);
		} else if(yDir < 0){
			setYPos(ownedEntity.getCenterYPos() - ownedEntity.getHeight()/2 - getHeight());
		} else {
			setYPos(ownedEntity.getCenterYPos() - getHeight()/2);
		}
	}
	
	public void setLifeTime(int i){
		lifeTime = i;
	}
	
	public void incrementTimer(){
		++timer;
	}
	
	public PlayableCharacter getOwnedEntity(){
		return ownedEntity;
	}
	
	public int getLifeTime(){
		return lifeTime;
	}
	
	public int getTimer(){
		return timer;
	}
}
