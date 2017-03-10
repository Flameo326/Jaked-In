package Models.Upgrades;

import Models.Players.PlayableCharacter;
import Models.Weapon.NormalProjectileWeapon;
import SpriteSheet.SpriteSheet;

public class ProjectileWeaponPickup extends Upgrade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProjectileWeaponPickup(int x, int y) {
		super(SpriteSheet.getNormalProjectilePickup(), x, y, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collect(PlayableCharacter c) {
		if(c.getWeapon() instanceof NormalProjectileWeapon){
			((NormalProjectileWeapon)c.getWeapon()).addBullets(30);
		} else {
			c.addWeapon(new NormalProjectileWeapon(c, 25));
		}
		isCollected = true;
	}

//	@Override
//	public void reverseEffect() {
//		// do nothing
//	}

}
