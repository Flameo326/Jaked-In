package Models.Upgrades;

import java.util.Random;

import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class DamageReduction extends Upgrade{
	
	private int damageReduction = 0;

	public DamageReduction(Image i, int x, int y) {
		super(i, x, y);
		Random rand = new Random();
		damageReduction = rand.nextInt(11)+20;
	}

	@Override
	public void collect(PlayableCharacter c) {
		c.setDamageReduction(damageReduction);
	}

}
