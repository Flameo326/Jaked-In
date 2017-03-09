package Models.Upgrades;

import java.util.Random;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class BonusDamage extends Upgrade{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bonusDamage;

	public BonusDamage(int x, int y) {
		super(SpriteSheet.getBonusDamage(), x, y);
		bonusDamage = new Random().nextInt(5)+1;
	}

	@Override
	public void collect(PlayableCharacter c) {
		c.setBonusDamage(bonusDamage);
		isCollected = true;
	}

}
