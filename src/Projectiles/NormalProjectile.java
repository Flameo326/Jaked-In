package Projectiles;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class NormalProjectile extends Projectile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NormalProjectile(PlayableCharacter e) {
		super(e, SpriteSheet.getNormalProjectile(), 5);
		setDamage(5);
	}
	
}
