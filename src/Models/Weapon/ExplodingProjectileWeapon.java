package Models.Weapon;

import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Models.Weapon.Attack.ExplodingProjectile;
import Models.Weapon.Attack.Projectile;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ExplodingProjectileWeapon extends ProjectileWeapon{

	public ExplodingProjectileWeapon(PlayableCharacter e, Image i) {
		super(e, i);
		setAttackTime(150);
		setTimer(getAttackTime());
	}

	@Override
	public Attack attack() {
		Projectile p = null;
		if(getTimer() > getAttackTime()){
			setTimer(0);
			p = new ExplodingProjectile(getOwnedEntity(), SpriteSheet.getBlock(10, 10, Color.BLACK));
			p.setLifeTime((int)(getAttackTime() * 3.33));
		}
		return p;
	}
}
