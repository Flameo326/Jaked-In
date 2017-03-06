package Models.Players;

import java.util.ArrayList;

import Controller.InputHandler;
import Interfaces.Attackable;
import Interfaces.Damageable;
import Interfaces.Dodgeable;
import Models.Collision;
import Models.Entity;
import Models.Weapon.ExplodingProjectileWeapon;
import Models.Weapon.ProjectileWeapon;
import Models.Weapon.Weapon;
import Models.Weapon.Attack.Attack;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public abstract class PlayableCharacter extends Entity implements Attackable, Dodgeable, Damageable {
	
	private Weapon weapon;
	private Entity healthBar;
	private int maxHealth, currentHealth;
	private boolean isDodging;

	public PlayableCharacter(Image i, int x, int y) {
		super(i, x, y, (int)i.getWidth(), (int)i.getHeight());
		healthBar = new HealthBar(this);
		setSpeed(3);
		setDisplayLayer(7);
		// Just Default it to a Standard Projectile Weapon for now
		setWeapon(new ExplodingProjectileWeapon(this, SpriteSheet.getBorderedBlock(5, 5, Color.WHITE, 3)));
		
		setMaxHealth(100);
		setCurrentHealth(100);
	}
	
	@Override
	public void update(ArrayList<Entity> entities){
		if(!isAlive()){
			for(Entity e : getDisplayableEntities()){
				entities.remove(e);
			}
		}
	}

	@Override
	public void hasCollided(Collision c) {
		Entity collider;
		if(c.collidingEntity == this){
			collider = c.collidedEntity;
		} else { return; }
		if(InputHandler.keyInputContains(KeyCode.F)) { return; }
		
		String[] tagElements = collider.getTag().split("-");
		
		switch(tagElements[0]){
		case "Human":
		case "Computer":
		case "NPC":
		case "Wall":
			if(c.xPenDepth < c.yPenDepth){
				setXPos(getXPos() + c.collisionNormal.getX() * c.xPenDepth);
			} else {
				setYPos(getYPos() + c.collisionNormal.getY() * c.yPenDepth);
			}
			break;
		case "Room":
			// Maybe display the room name in screen...
			break;
		}
	}

	@Override
	public void dodge() {
		isDodging = true;
		// setTimer for dodging
	}

	@Override
	public void takeDamage(int val) {
		if(!isDodging){
			currentHealth -= val;
			System.out.println(getTag() + " has " + getCurrentHealth() + "/" + getMaxHealth());
		}
	}
	
	@Override
	public Attack attack() {
		if(getWeapon() != null){
			return getWeapon().attack();
		} else {
			System.out.println(getTag() + "-Weapon is Null"); 
			return null;
		}	
	}
	
	public void heal(int i){
		currentHealth += i;
		if(currentHealth > maxHealth){
			currentHealth = maxHealth;
		}
	}
	
	public boolean isAlive(){
		return currentHealth > 0;
	}
	
	public void setMaxHealth(int i){
		maxHealth = i;
	}
	
	public void setCurrentHealth(int i){
		currentHealth = i;
	}
	
	public void setWeapon(Weapon w){
		if(w != null){	
			weapon = w;
		}
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getCurrentHealth(){
		return currentHealth;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}

	public Entity[] getDisplayableEntities() {
		return weapon != null ? new Entity[] {this, weapon, healthBar} : new Entity[] {this, healthBar};
	}
}
