package Models.Weapon;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class NormalProjectileWeapon extends ProjectileWeapon{

	public NormalProjectileWeapon(PlayableCharacter e, int bullets) {
		super(e, SpriteSheet.getNormalProjectile(), bullets, 50, 100);
	}
	
}
