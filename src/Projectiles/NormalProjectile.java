package Projectiles;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class NormalProjectile extends Projectile {

	public NormalProjectile(PlayableCharacter e) {
		super(e, SpriteSheet.getNormalProjectile(), 5);
	}

}
