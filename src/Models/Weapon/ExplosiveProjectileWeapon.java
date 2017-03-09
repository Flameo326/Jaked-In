package Models.Weapon;

import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Projectiles.ExplosiveProjectile;
import SpriteSheet.SpriteSheet;

public class ExplosiveProjectileWeapon extends ProjectileWeapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExplosiveProjectileWeapon(PlayableCharacter e, int bullets) {
		super(e, SpriteSheet.getExplosiveProjectile(), bullets, 100, 30);
	}

	@Override
	public Attack attack() {
		ExplosiveProjectile p = null;
		if(getTimer() >= getAttackTime()){
			setTimer(0);
			p = new ExplosiveProjectile(getOwnedEntity());
			p.setLifeTime((int)(bulletLifeTime * 3.33));
			if(--bullets <= 0){
				getOwnedEntity().removeWeapon();
			}
		}
		return p;
	}
	
	
}
