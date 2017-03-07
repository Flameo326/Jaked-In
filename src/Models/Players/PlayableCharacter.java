package Models.Players;

import java.util.ArrayList;

import Controller.GameController;
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
	
	private ArrayList<Weapon> weapons;
	private Weapon equippedWeapon, previousWeapon;
	private Entity healthBar;
	private int maxHealth, currentHealth;
	private int currentWeapon;
	private boolean isDodging, weaponHasChanged;

	public PlayableCharacter(Image i, int x, int y) {
		super(i, x, y, (int)i.getWidth(), (int)i.getHeight());
		healthBar = new HealthBar(this);
		weapons = new ArrayList<>();
		setSpeed(3);
		setDisplayLayer(7);
		// Just Default it to a Standard Melee Weapon for now
		addWeapon(new MeleeWeapon(this));
		
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
		if(GameController.getStoryMode()){
			if(weapons.isEmpty()){
				setWeapon(w);
			}
			weapons.add(w);
		} else {
			setWeapon(w);
		}
	}
	
	public void removeWeapon(Weapon w){
		if(!GameController.getStoryMode()){
			setWeapon(new MeleeWeapon(this));
		} else {
			changeWeapon();
			weapons.remove(w);
		}
	}
	
	public void changeWeapon(){
		if(++currentWeapon >= weapons.size()){
			currentWeapon = 0;
		}
		setWeapon(weapons.get(currentWeapon));
	}
	
	public Weapon hasWeapon(Class val){
		Weapon weapon = null;
		for(Weapon w : weapons){
			if(val.isInstance(w)) {
				weapon = w;
				break; 
			}
		}
		return weapon;
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
			if(equippedWeapon != null){
				weaponHasChanged = true;
				previousWeapon = equippedWeapon;
			}
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
}
