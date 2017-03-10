package Models.Weapon.Attack;

import java.util.ArrayList;
import java.util.HashSet;

import Controller.GameController;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public abstract class Attack extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected HashSet<Entity> hasHit;
	private final PlayableCharacter ownedEntity;
	protected long lifeTime;
	private int damage;
	
	// This will be for Like Melee Weapons...
	public Attack(PlayableCharacter e, Image i) {
		super(i, e.getXPos(), e.getYPos());
		ownedEntity = e;
		hasHit = new HashSet<Entity>();
		setTag("Attack-" + ownedEntity.getTag());
		setDisplayLayer(5);
		setDamage(e.getBonusDamage() + e.getBaseDamage());
	}
	
	// Position the hitBox in the direction that the object was moving
	@Override
	public void update(ArrayList<Entity> entities) {
		if(GameController.getTimer() >= lifeTime || !hasHit.isEmpty()){
			entities.remove(this);
		}
	}
	
	@Override
	public void hasCollided(Collision c) {
		Entity collider;
		PlayableCharacter collidedChar;
		
		if(c.collidedEntity == this){
			collider = c.collidingEntity;
		} else { collider = c.collidedEntity; }
		
		// Test for collision against the same enemy...
		if(hasHit.contains(collider)) { return; }
		
		if(collider instanceof PlayableCharacter){
			collidedChar = (PlayableCharacter)collider;
		} else {
			//Attack can not do damage
			return;
		}
		
		// In ArenaMode
		if(!GameController.getStoryMode()){
			if(!collidedChar.getTag().equals(getOwnedEntity().getTag())){
				collidedChar.takeDamage(getDamage());
				hasHit.add(collider);
				return;
			}
		} 
		
		String[] tagElements = collidedChar.getTag().split("-");
		String[] ourElements = getTag().split("-");
		switch(tagElements[0]){
		case "Human":
			 if(!ourElements[1].equals("AllyNPC") && !ourElements[1].equals("Human")){
				collidedChar.takeDamage(getDamage());
				hasHit.add(collider);
			} 
			break;
		case "Computer":
			if(ourElements[1].equals("AllyNPC") || ourElements[1].equals("Human")){
				collidedChar.takeDamage(getDamage());
				hasHit.add(collider);
			} 
			break;
		case "AllyNPC":
			if(!ourElements[1].equals("AllyNPC") && !ourElements[1].equals("Human")){
				collidedChar.takeDamage(getDamage());
				hasHit.add(collider);
			} 
			break;
		case "EnemyNPC":
			if(ourElements[1].equals("AllyNPC") || ourElements[1].equals("Human")){
				collidedChar.takeDamage(getDamage());
				hasHit.add(collider);
			} 
			break;
		}
	}
	
	public void setLifeTime(int i){
		lifeTime = GameController.getTimer() + 1000000000l/60 * i;
	}
	
//	public void incrementTimer(){
//		++timer;
//	}
	
	public void setDamage(int val){
		damage = val;
	}
	
	public PlayableCharacter getOwnedEntity(){
		return ownedEntity;
	}
	
	public long getLifeTime(){
		return lifeTime;
	}
	
//	public int getTimer(){
//		return timer;
//	}
	
	public int getDamage(){
		return damage;
	}

}
