package Models.Weapon;

import Controller.GameController;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Projectiles.NormalProjectile;
import javafx.scene.image.Image;

public abstract class ProjectileWeapon extends Weapon{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final int bulletLifeTime;
	protected int bullets;
	
	public ProjectileWeapon(PlayableCharacter e, Image i, int bullets, int reloadTime, int bulletLifeTime){
		super(e, i);
		setAttackTime(reloadTime);
		this.bulletLifeTime = bulletLifeTime;
		this.bullets = bullets;
	}

	@Override
	public Attack attack() {
		NormalProjectile p = null;
		if(GameController.getTimer() >= getNextAttackTime()){
			setNextAttackTime();
			p = new NormalProjectile(getOwnedEntity());
			p.setLifeTime((int)(bulletLifeTime * 3.33));
			if(--bullets <= 0){
				getOwnedEntity().removeWeapon();
			}
		}
		return p;
	}
	
	public void addBullets(int val){
		bullets += val;
	}

	public int getBullets() {
		return bullets;
	}
}