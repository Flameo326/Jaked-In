package Models.Upgrades;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class ForceFieldReflection extends Upgrade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForceFieldReflection(int x, int y) {
		super(SpriteSheet.getForceField(), x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collect(PlayableCharacter c) {
		// TODO Auto-generated method stub
		isCollected = true;
	}

}
