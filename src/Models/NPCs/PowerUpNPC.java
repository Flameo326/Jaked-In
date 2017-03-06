package Models.NPCs;

import java.util.ArrayList;

import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Upgrades.MedPack;
import Models.Upgrades.Upgrade;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PowerUpNPC extends NPC {

	private boolean hasSpoken = false;

	public PowerUpNPC(Image i, int x, int y) {
		super(i, x, y,(int)i.getWidth(), (int)i.getHeight());
		
	}

	public String conversation(PlayableCharacter c) {
		if (!hasSpoken) {//needs to be random powerup
			Upgrade u = new MedPack(null, 0, 0);
			u.collect(c);
			hasSpoken = true;
			return "Here, take this. It will help you fight Watson";
		}else{
			return "I have nothing more to help you!";
		}
	}

	public String callPlayer() {
		return "OVER HERE!!";
	}

	@Override
	public void interact(PlayableCharacter c) {
		conversation(c);
		
		

	}

//	@Override
//	public void hasCollided(Collision c) {
//		throw new UnsupportedOperationException("Not yet Implemented");
//	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub

	}

}
