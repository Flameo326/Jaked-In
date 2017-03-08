package Models.Upgrades;

import Enums.BulletType;
import Models.Players.PlayableCharacter;
import Models.Weapon.ProjectileWeapon;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ProjectileWeaponBullets extends Upgrade{

	public ProjectileWeaponBullets(Image i, int x, int y) {
		super(i, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collect(PlayableCharacter c) {
		ProjectileWeapon currWeapon =  ((ProjectileWeapon)c.hasWeapon(ProjectileWeapon.class));
		if(currWeapon != null){
			currWeapon.addBullets(30);
		} else {
			Image img = SpriteSheet.getBlock(5, 5, Color.BLACK);
			c.addWeapon(new ProjectileWeapon(c, img, 25, 60, 300, BulletType.NORMAL));
		}
		isCollected = true;
	}

}
