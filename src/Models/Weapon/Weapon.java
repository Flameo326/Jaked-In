package Models.Weapon;

import java.util.ArrayList;
import java.util.Collections;

import Controller.GameController;
import Interfaces.Attackable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public abstract class Weapon extends Entity implements Attackable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PlayableCharacter ownedEntity;
	// In frames per Seconds
	private int attackTime;
	private long nextAttackTime;

	// Weapons are no longer going to have a display???
	public Weapon(PlayableCharacter e, Image i){
		super(i, e.getXPos(), e.getYPos());
		ownedEntity = e;
		setDisplayLayer(6);
	}

	@Override
	public void hasCollided(Collision c) {
		// do nothing
	}
	
	@Override
	public void update(ArrayList<Entity> entities){
//		if(nextAttackTime > GameController.getTimer()){
//			setDisplayLayer(0);
//		} else {
//			
//		}
		int xDir = getOwnedEntity().getCurrDir().getX();
		int yDir = getOwnedEntity().getCurrDir().getY();
		if(xDir > 0){
			setXPos(getOwnedEntity().getShape().getMaxX());
		} else if(xDir < 0){
			setXPos(getOwnedEntity().getShape().getMinX());
		} else {
			setXPos(getOwnedEntity().getXPos());
		}
		if(yDir > 0){
			setYPos(getOwnedEntity().getShape().getMaxY());
		} else if(yDir < 0){
			setYPos(getOwnedEntity().getShape().getMinY());
		} else {
			setYPos(getOwnedEntity().getYPos());
		}
	}
	
	public void setAttackTime(int i){
		attackTime = i;
	}
	
	public void setNextAttackTime(){
		nextAttackTime = GameController.getTimer() + 1000000000l/60 * attackTime;
	}
	
	public PlayableCharacter getOwnedEntity(){
		return ownedEntity;
	}
	
	public int getAttackTime(){
		return attackTime;
	}
	
	public long getNextAttackTime(){
		return nextAttackTime;
	}
	
	public abstract int getDamage();

}
