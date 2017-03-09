package Models.Upgrades;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class SpeedBoost extends Upgrade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpeedBoost(int x, int y) {
		super(SpriteSheet.getSpeedBoost(), x, y);
	}
	
	// instead we could check if the boost is permanent, 
	// if it is then isCollected is true,
	// otherwise we call setTimer() and check to see when it has expired...
	@Override 
	public void collect(PlayableCharacter c){
		c.setSpeed(c.getSpeed() + 1);
		isCollected = true;
	}

}
