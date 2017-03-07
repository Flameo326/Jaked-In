package Models.Upgrades;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class ForceFieldReflection extends Upgrade{

	public ForceFieldReflection(int x, int y) {
		super(SpriteSheet.getForceField(), x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collect(PlayableCharacter c) {
		// TODO Auto-generated method stub
		
	}

}
