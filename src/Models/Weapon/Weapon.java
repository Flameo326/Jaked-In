package Models.Weapon;

import java.util.ArrayList;

import Interfaces.Attackable;
import Interfaces.Collideable;
import Models.Bounds;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public abstract class Weapon extends Entity implements Attackable{

	private final PlayableCharacter ownedEntity;
	private int attackTime, timer;

	public Weapon(PlayableCharacter e, Image i){
		super(i, e.getCenterXPos(), e.getCenterYPos());
		ownedEntity = e;
	}

	@Override
	public boolean isColliding(Collideable c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public Bounds getBounds() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}
	
	@Override
	public void update(ArrayList<Entity> entities){
		timer++;
		setXPos(getOwnedEntity().getCenterXPos()-getWidth()/2);
		setYPos(getOwnedEntity().getCenterYPos()-getHeight()/2);
	}
	
	public void setAttackTime(int i){
		attackTime = i;
	}
	
	public void setTimer(int i){
		timer = i;
	}
	
	public PlayableCharacter getOwnedEntity(){
		return ownedEntity;
	}
	
	public int getAttackTime(){
		return attackTime;
	}
	
	public int getTimer(){
		return timer;
	}

}
