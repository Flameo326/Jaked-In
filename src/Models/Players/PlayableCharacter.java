package Models.Players;

import Enums.Direction;
import Interfaces.Attackable;
import Interfaces.Collideable;
import Interfaces.Damageable;
import Interfaces.Dodgeable;
import Models.Bounds;
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
		this.setDisplayLayer(5);
		// Just Default it to a Standard Projectile Weapon for now
		setWeapon(new ProjectileWeapon(this, SpriteSheet.getBorderedBlock(5, 5, Color.WHITE, 3)));
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
			System.out.println(getTag() + ".Weapon is Null"); 
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
