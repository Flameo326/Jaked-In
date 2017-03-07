package Models.Upgrades;

import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class SpeedBoost extends Upgrade{

	public SpeedBoost(Image i, int x, int y) {
		super(i, x, y);
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
