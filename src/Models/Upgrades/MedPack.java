package Models.Upgrades;

import java.util.Random;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class MedPack extends Upgrade {
	
	private final int HPIncrease;

	public MedPack(Image i, int x, int y) {
		super(i, x, y);
		Random randy = new Random();
		HPIncrease = randy.nextInt(21) + 20;
	}
	
	
	
	@Override 
	public void collect(PlayableCharacter c){
		c.heal(getHPIncrease());
		isCollected = true;
	}

	@Override
	public void interact(PlayableCharacter c) {
		
	}

	public int getHPIncrease() {
		return HPIncrease;
	}


}
