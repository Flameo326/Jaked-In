package Models.Upgrades;

import java.util.Random;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class DamageReduction extends Upgrade{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int damageReduction = 0;

	public DamageReduction(int x, int y) {
		super(SpriteSheet.getDamageReduction(), x, y);
		Random rand = new Random();
		damageReduction = rand.nextInt(11)+20;
	}

	@Override
	public void collect(PlayableCharacter c) {
		c.setDamageReduction(damageReduction);
		isCollected = true;
	}

}
