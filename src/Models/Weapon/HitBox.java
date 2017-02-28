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
		setTag(ownedEntity.getTag() + ".Melee");
	}

	@Override
	public void hasCollided(Collision c) {
		throw new UnsupportedOperationException("Not yet Implemented");
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
