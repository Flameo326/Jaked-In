package Models.NPCs;

import java.util.ArrayList;

import Interfaces.Collideable;
import Interfaces.Interactable;
import Models.Bounds;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class SecurityWorker extends Entity implements Collideable, Interactable {

	public SecurityWorker(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interact(PlayableCharacter c) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isColliding(Collideable c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bounds getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub
		
	}

}
