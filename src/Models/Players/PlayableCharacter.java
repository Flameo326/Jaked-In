package Models.Players;

import java.util.ArrayList;

import Controller.CollisionSystem;
import Interfaces.Attackable;
import Interfaces.Damageable;
import Interfaces.Dodgeable;
import Models.Collision;
import Models.Entity;
import Models.Weapon.MeleeWeapon;
import Models.Weapon.Weapon;
import Models.Weapon.Attack.Attack;
import javafx.scene.image.Image;

public abstract class PlayableCharacter extends Entity implements Attackable, Dodgeable, Damageable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ArrayList<PlayableCharacter> Enemys = new ArrayList<>();
	private Weapon equippedWeapon, previousWeapon, mainWeapon, secondWeapon;
	private HealthBar healthBar;
	private int maxHealth, currentHealth;
<<<<<<< HEAD
	private boolean isDodging, weaponHasChanged;
	protected ArrayList<PlayableCharacter> Enemys = new ArrayList<>();
	private int damageReduction, bonusDamage;
	private static ArrayList<PlayableCharacter> friendlies = new ArrayList<>();
	private static ArrayList<PlayableCharacter> enemies = new ArrayList<>();
	
=======
	private boolean isDodging, weaponHasChanged;
	private int bonusSpeed;
	private int damageReduction, bonusReduction;
	private int baseDamage, bonusDamage;
	private long bonusDamageLength, bonusSpeedLength, bonusReductionLength, ForceFieldLength;
	


>>>>>>> branch 'master' of https://github.com/Flameo326/Jaked-In.git
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
		for(int i = 0; i < getSpeed()+getBonusSpeed(); i++){
			setPrevXPos(getXPos());
			setPrevYPos(getYPos());
			setXPos(getXPos() + getCurrDir().getX());
			setYPos(getYPos() + getCurrDir().getY());
			CollisionSystem.checkMovementCollisions(this, entities);
		}
		healthBar.update(entities);
		equippedWeapon.update(entities);
	}

	@Override
	public void hasCollided(Collision c) {
		Entity collider;
		if(c.collidingEntity == this){
			collider = c.collidedEntity;
		} else { return; }
		
		String[] tagElements = collider.getTag().split("-");
		
		switch(tagElements[0]){
		case "Wall":
			if(tagElements.length > 1 && tagElements[1].equals("ForceField")){break;}
		case "Human":
		case "Computer":
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
			if(val > bonusReduction){
				val -= bonusReduction;
				bonusReduction = 0;
			} else {
				bonusReduction -= val;
				val = 0;
			}
			val -= damageReduction;
			if(val < 0) { val = 0; }
			
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
	
	public void setBaseDamage(int i){
		baseDamage = i;
	}
<<<<<<< HEAD

=======
	
	public int getBaseDamage(){
		return baseDamage;
	}
	
>>>>>>> branch 'master' of https://github.com/Flameo326/Jaked-In.git
	public ArrayList<PlayableCharacter> getEnemys() {
		return Enemys;
	}
	
	public void setEnemys(ArrayList<PlayableCharacter>Enemys) {
		this.Enemys = Enemys;
	}
	
	public int getBonusDamage(){
		return bonusDamage;
	}
	
	public void setBonusDamage(int val){
		bonusDamage = val;
	}
	
<<<<<<< HEAD
	public static ArrayList<PlayableCharacter> getFriendlies() {
		return friendlies;
	}

	public static void setFriendlies(ArrayList<PlayableCharacter> friendlies) {
		PlayableCharacter.friendlies = friendlies;
	}

	public static ArrayList<PlayableCharacter> getEnemies() {
		return enemies;
	}

	public static void setEnemies(ArrayList<PlayableCharacter> enemies) {
		PlayableCharacter.enemies = enemies;
	}

=======
	public int getBonusReduction(){
		return bonusReduction;
	}
	
	public void setBonusReduction(int i){
		bonusReduction = i;
	}
	
	public void setBonusSpeed(int v){
		bonusSpeed = v;
	}
	
	public int getBonusSpeed(){
		return bonusSpeed;
	}
	
	public void setBonusDamageLength(long l){
		bonusDamageLength = l;
	}
	
	public void setBonusSpeedLength(long l){
		bonusSpeedLength = l;
	}
	
	public void setBonusReductionLength(long l){
		bonusReductionLength = l;
	}
	
	public void setForceFieldLength(long l){
		ForceFieldLength = l;
	}
	
	public long getBonusDamageLength(){
		return bonusDamageLength;
	}
	
	public long getBonusSpeedLength(){
		return bonusSpeedLength;
	}
	
	public long getBonusReductionLength(){
		return bonusReductionLength;
	}
	
	public long getForceFieldLength(){
		return ForceFieldLength;
	}
>>>>>>> branch 'master' of https://github.com/Flameo326/Jaked-In.git
}
