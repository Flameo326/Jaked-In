package Models.Upgrades;

import Models.Players.PlayableCharacter;
import Models.Weapon.ExplosiveProjectileWeapon;
import SpriteSheet.SpriteSheet;

public class ExplosiveWeaponPickup extends Upgrade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExplosiveWeaponPickup(int x, int y) {
		super(SpriteSheet.getExplosiveProjectilePickup(), x, y, true);
	}

	@Override
	public void collect(PlayableCharacter c) {
		if(c.getWeapon() instanceof ExplosiveProjectileWeapon){
			((ExplosiveProjectileWeapon)c.getWeapon()).addBullets(30);
		} else {
			c.addWeapon(new ExplosiveProjectileWeapon(c, 25));
		}
		isCollected = true;
	}
//
//	@Override
//	public void reverseEffect() {
//		// TODO Auto-generated method stub
//		
//	}

}
