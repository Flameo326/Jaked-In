package Models.Weapon;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class NormalProjectileWeapon extends ProjectileWeapon{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NormalProjectileWeapon(PlayableCharacter e, int bullets) {
		super(e, SpriteSheet.getNormalProjectileWeapon(), bullets, 50, 100);
	}
	
	public int getDamage(){
		return 5;
	}
	
}
