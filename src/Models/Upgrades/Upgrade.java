package Models.Upgrades;

import java.util.ArrayList;

import Interfaces.Collectable;
import Interfaces.Collideable;
import Interfaces.Interactable;
import Models.Bounds;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class Upgrade extends Entity implements Interactable, Collectable{

	public Upgrade(Image i, int x, int y) {
		super(i, x, y, (int)i.getWidth(), (int)i.getHeight());
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isColliding(Collideable c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public Bounds getBounds() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// Don't move
	}

	@Override
	public void collect(PlayableCharacter c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public void interact(PlayableCharacter c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

}
