package Models.Upgrades;

import Models.Players.PlayableCharacter;
import Models.Weapon.NormalProjectileWeapon;
import javafx.scene.image.Image;

public class NormalWeaponBullets extends Upgrade{

	public NormalWeaponBullets(Image i, int x, int y) {
		super(i, x, y);
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

}
