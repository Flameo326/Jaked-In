package Models.Weapon;

import Enums.BulletType;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Models.Weapon.Attack.Projectile;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ProjectileWeapon extends Weapon{
	private final BulletType type;
	private final int bulletLifeTime;
	
	public ProjectileWeapon(PlayableCharacter e, Image i, int reloadTime, int bulletLifeTime, BulletType type){
		super(e, i);
		setAttackTime(reloadTime);
		setTimer(getAttackTime());
		this.type = type;
		this.bulletLifeTime = bulletLifeTime;
	}

	@Override
	public Attack attack() {
		Projectile p = null;
		if(getTimer() > getAttackTime()){
			setTimer(0);
			switch (type) {
			case NORMAL:
				Image img = SpriteSheet.getBlock(5, 5, Color.BLACK);
				p = new Projectile(getOwnedEntity(), img, type);
				break;
			case EXPLOSIVE:
				Image img1 = SpriteSheet.getBlock(10, 10, Color.BLACK);
				p = new Projectile(getOwnedEntity(), img1, type);
				break;
			case BOUNCE:
				Image img2 = SpriteSheet.getBlock(5, 5, Color.BLACK);
				p = new Projectile(getOwnedEntity(), img2, type);
				break;
			default:
				Image img3 = SpriteSheet.getBlock(5, 5, Color.BLACK);
				p = new Projectile(getOwnedEntity(), img3, type);
				break;
			}
			p.setLifeTime((int)(bulletLifeTime * 3.33));
		}
		return p;
	}
}