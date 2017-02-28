package Models.NPCs;

import java.util.ArrayList;

import Interfaces.Collideable;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class PowerUpNPC extends Entity implements Collideable, Interactable {

	public PowerUpNPC(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interact(PlayableCharacter c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasCollided(Collision c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub
		
	}

}
