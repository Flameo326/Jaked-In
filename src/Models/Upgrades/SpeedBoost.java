package Models.Upgrades;

import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class SpeedBoost extends Upgrade{

	public SpeedBoost(Image i, int x, int y) {
		super(i, x, y);
	}
	
	@Override 
	public void collect(PlayableCharacter c){//need a player method for temp speed boost.
		c.setSpeed(c.getSpeed() + 1);
		isCollected = true;
	}

	@Override
	public void interact(PlayableCharacter c) {
		collect(c);
	}


}
