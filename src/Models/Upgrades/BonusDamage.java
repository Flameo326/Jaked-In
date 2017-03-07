package Models.Upgrades;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class BonusDamage extends Upgrade{

	// This class would have to access the weapon to modify the damage...
	public BonusDamage(int x, int y) {
		super(SpriteSheet.getBonusDamage(), x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collect(PlayableCharacter c) {
		// TODO Auto-generated method stub
		
	}

}
