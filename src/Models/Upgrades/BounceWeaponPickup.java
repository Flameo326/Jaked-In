package Models.Upgrades;

import Models.Players.PlayableCharacter;
import Models.Weapon.BounceProjectileWeapon;
import SpriteSheet.SpriteSheet;

public class BounceWeaponPickup extends Upgrade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BounceWeaponPickup(int x, int y) {
		super(SpriteSheet.getBounceProjectilePickup(), x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collect(PlayableCharacter c) {
		if(c.getWeapon() instanceof BounceProjectileWeapon){
			((BounceProjectileWeapon)c.getWeapon()).addBullets(30);
		} else {
			c.addWeapon(new BounceProjectileWeapon(c, 25));
		}
		isCollected = true;
	}

}
