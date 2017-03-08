package Models.Upgrades;

import Models.Players.PlayableCharacter;
import Models.Weapon.NormalProjectileWeapon;
import javafx.scene.image.Image;

public class ProjectileWeaponBullets extends Upgrade{

	public ProjectileWeaponBullets(Image i, int x, int y) {
		super(i, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collect(PlayableCharacter c) {
		NormalProjectileWeapon currWeapon =  (NormalProjectileWeapon) c.hasWeapon(NormalProjectileWeapon.class);
		if(currWeapon != null){
			currWeapon.addBullets(30);
		} else {
			c.addWeapon(new NormalProjectileWeapon(c, 25));
		}
		isCollected = true;
	}

}
