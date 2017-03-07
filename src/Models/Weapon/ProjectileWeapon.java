package Models.Weapon;

import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Projectiles.NormalProjectile;
import javafx.scene.image.Image;

public abstract class ProjectileWeapon extends Weapon{
	private final int bulletLifeTime;
	private int bullets;
	
	public ProjectileWeapon(PlayableCharacter e, Image i, int bullets, int reloadTime, int bulletLifeTime){
		super(e, i);
		setAttackTime(reloadTime);
		setTimer(getAttackTime());
		this.bulletLifeTime = bulletLifeTime;
		this.bullets = bullets;
	}

	@Override
	public Attack attack() {
		NormalProjectile p = null;
		if(getTimer() >= getAttackTime()){
			setTimer(0);
			p = new NormalProjectile(getOwnedEntity());
			p.setLifeTime((int)(bulletLifeTime * 3.33));
			if(--bullets <= 0){
				getOwnedEntity().removeWeapon(this);
			}
		}
		return p;
	}
	
	public void addBullets(int val){
		bullets += val;
	}
}