package Models.Players;

import java.util.ArrayList;

import Controller.InputHandler;
import Interfaces.Attackable;
import Interfaces.Damageable;
import Interfaces.Dodgeable;
import Models.Collision;
import Models.Entity;
import Models.Weapon.MeleeWeapon;
import Models.Weapon.Weapon;
import Models.Weapon.Attack.Attack;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public abstract class PlayableCharacter extends Entity implements Attackable, Dodgeable, Damageable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Weapon equippedWeapon, previousWeapon, mainWeapon, secondWeapon;
	private Entity healthBar;
	private int maxHealth, currentHealth;
	private boolean isDodging, weaponHasChanged;
	private int damageReduction, bonusDamage;

	public PlayableCharacter(Image i, int x, int y) {
		super(i, x, y);
		healthBar = new HealthBar(this);
		setSpeed(3);
		setDisplayLayer(7);
		// Just Default it to a Standard Melee Weapon for now
		secondWeapon = new MeleeWeapon(this);
		equippedWeapon = secondWeapon;
		
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
		if(weaponHasChanged){
			entities.remove(previousWeapon);
			if(!entities.contains(equippedWeapon)) { entities.add(equippedWeapon); }
			weaponHasChanged = false;
		}
	}
	
	@Override
	public void move(ArrayList<Entity> entities) {
		super.move(entities);
		healthBar.update(entities);
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
			if(val > damageReduction){
				val -= damageReduction;
				damageReduction = 0;
			}else{
				damageReduction -= val;
				val = 0;
			}
			
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
	
	public void addWeapon(Weapon w){
		mainWeapon = w;
		setWeapon(w);
	}
	
	public void removeWeapon(){
		setWeapon(secondWeapon);
	}
	
	public void changeWeapon(){
		if(equippedWeapon == secondWeapon && mainWeapon != null){
			setWeapon(mainWeapon);
		} else {
			setWeapon(secondWeapon);
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
			weaponHasChanged = true;
			previousWeapon = equippedWeapon;
			equippedWeapon = w;
		}
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getCurrentHealth(){
		return currentHealth;
	}
	
	public Weapon getWeapon(){
		return equippedWeapon;
	}

	public Entity[] getDisplayableEntities() {
		return equippedWeapon != null ? new Entity[] {this, equippedWeapon, healthBar} : new Entity[] {this, healthBar};
	}

	public int getDamageReduction() {
		return damageReduction;
	}

	public void setDamageReduction(int damageReduction) {
		this.damageReduction = damageReduction;
	}
	
	public int getBonusDamage(){
		return bonusDamage;
	}
	
	public void setBonusDamage(int val){
		bonusDamage = val;
	}
}
