package Models.Weapon;

import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Projectiles.BounceProjectile;
import SpriteSheet.SpriteSheet;

public class BounceProjectileWeapon extends ProjectileWeapon {

	public BounceProjectileWeapon(PlayableCharacter e, int bullets) {
		super(e, SpriteSheet.getNormalProjectile(), bullets, 30, 2000);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Attack attack() {
		BounceProjectile p = null;
		if(getTimer() >= getAttackTime()){
			setTimer(0);
			p = new BounceProjectile(getOwnedEntity(), 4);
			p.setLifeTime((int)(bulletLifeTime * 3.33));
			if(--bullets <= 0){
				getOwnedEntity().removeWeapon(this);
			}
		}
		return p;
	}
}
