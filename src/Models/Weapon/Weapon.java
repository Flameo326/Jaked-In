package Models.Weapon;

import java.util.ArrayList;

import Interfaces.Attackable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public abstract class Weapon extends Entity implements Attackable{

	private final PlayableCharacter ownedEntity;
	private int attackTime, timer;

	public Weapon(PlayableCharacter e, Image i){
		super(i, e.getCenterXPos(), e.getCenterYPos(), (int)i.getWidth(), (int)i.getHeight());
		ownedEntity = e;
		setDisplayLayer(8);
	}

	@Override
	public void hasCollided(Collision c) {
		// do nothing im pretty sure
		// we dont care about collision among weapon... potentially//
		// only there for display
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
