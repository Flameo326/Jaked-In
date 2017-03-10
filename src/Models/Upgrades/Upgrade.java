package Models.Upgrades;

import java.util.ArrayList;

import Controller.GameController;
import Interfaces.Collectable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public abstract class Upgrade extends Entity implements Collectable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlayableCharacter playerAffected;
	protected boolean isCollected, permanent;
	private long boostExpiration;
	private int bonus, length;
	

	public Upgrade(Image i, int x, int y, boolean permanent) {
		super(i, x, y);
		setDisplayLayer(4);
		setTag("Upgrade");
		this.permanent = permanent;
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		if(isCollected){
			if(permanent){ entities.remove(this); }
			else {
				if(GameController.getTimer() >= boostExpiration){
					reverseEffect();
					entities.remove(this);
				}
			}
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
	
	public abstract void reverseEffect();
	
	public void setBoostExpiration(){
		boostExpiration = GameController.getTimer() + 1000000000l * length;
	}
	
	public long getBoostExpiration(){
		return boostExpiration;
	}
	
	public void setBonus(int v){
		bonus = v;
	}
	
	public void setLength(int v){
		length = v;
	}
	
	public boolean isPermanent(){
		return permanent;
	}
	
	public void setPlayerAffected(PlayableCharacter c){
		playerAffected = c;
	}
	
	public int getBonus(){
		return bonus;
	}
	
	public int getLength(){
		return length;
	}
	
	public PlayableCharacter getPlayerAffected(){
		return playerAffected;
	}

}
