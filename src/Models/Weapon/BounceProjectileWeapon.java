package Models.Weapon;

import Controller.GameController;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Projectiles.BounceProjectile;
import SpriteSheet.SpriteSheet;

public class BounceProjectileWeapon extends ProjectileWeapon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BounceProjectileWeapon(PlayableCharacter e, int bullets) {
		super(e, SpriteSheet.getNormalProjectileWeapon(), bullets, 30, 2000);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Attack attack() {
		BounceProjectile p = null;
		if(GameController.getTimer() >= getNextAttackTime()){
			setNextAttackTime();
			p = new BounceProjectile(getOwnedEntity(), 4);
			p.setLifeTime((int)(bulletLifeTime * 3.33));
			if(--bullets <= 0){
				getOwnedEntity().removeWeapon();
			}
		}
		return p;
	}
	
	public int getDamage(){
		return 45;
	}
}
