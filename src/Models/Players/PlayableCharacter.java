package Models.Players;

import Enums.Direction;
import Interfaces.Attackable;
import Interfaces.Damageable;
import Interfaces.Dodgeable;
import Models.Collision;
import Models.Entity;
import Models.Weapon.HitBox;
import Models.Weapon.ProjectileWeapon;
import Models.Weapon.Weapon;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class PlayableCharacter extends Entity implements Attackable, Dodgeable, Damageable {
	
	private Weapon weapon;
	private int maxHealth, currentHealth;
	private Direction direction;

	public PlayableCharacter(Image i, int x, int y) {
		super(i, x, y, (int)i.getWidth(), (int)i.getHeight());
		setSpeed(3);
		setDisplayLayer(7);
		// Just Default it to a Standard Projectile Weapon for now
		setWeapon(new ProjectileWeapon(this, SpriteSheet.getBorderedBlock(5, 5, Color.WHITE, 3)));
	}

	@Override
	public void hasCollided(Collision c) {
		Entity collider;
		boolean colliding = false;
		if(c.collidedEntity == this){
			collider = c.collidingEntity;
		} else { collider = c.collidedEntity; colliding  = true; }
		String[] tagElements = collider.getTag().split("-");
		// We have exactly 2 elements, type and ID
		String[] ourElements = getTag().split("-");
		System.out.println("Colliding: " + getTag());
		System.out.println("Collider: " + collider.getTag());
		
		switch(tagElements[0]){
		// If I collide against these then just move away
		case "Human":
		case "Computer":
		case "NPC":
		case "Wall":
			if(colliding){
				if(c.xPenDepth < c.yPenDepth){
					setXPos(getXPos() + c.collisionNormal.getX() * c.xPenDepth);
				} else {
					setYPos(getYPos() + c.collisionNormal.getY() * c.yPenDepth);
				}
			} else {
				if(c.xPenDepth < c.yPenDepth){
					setXPos(getXPos() - c.collisionNormal.getX() * c.xPenDepth);
				} else {
					setYPos(getYPos() - c.collisionNormal.getY() * c.yPenDepth);
				}
			}
			break;
		case "Room":
			// Maybe display the room name in screen...
			break;
		case "Upgrade":
			// If we interact with an upgrade then upgrade should probaly do something, not us
			// *Note, "Interact" not "Collide"
			break;
		// The object is something that damages.
		// If it's owner is not us then we take damage
		case "Attack":
			// 1 for Attack, 1 for Type, 2 for Owner
			if(tagElements.length == 4 && tagElements[2] == ourElements[0] && tagElements[3] == ourElements[1]){
				// our own attack
			} else {
				// take damage
			}
			break;
		}
	}

	@Override
	public void dodge() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public void takeDamage() {
		currentHealth--;
	}
	
	@Override
	public HitBox attack() {
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

	public void setCurrDir(Direction direction) {
		this.direction = direction;
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
	
	public Direction getCurrDir(){
		return direction;
	}

	public Entity[] getDisplayableEntities() {
		return weapon != null ? new Entity[] {this, weapon} : new Entity[] {this};
	}
}
