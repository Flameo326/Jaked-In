package Models.Upgrades;

import java.util.ArrayList;

import Interfaces.Collectable;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class Upgrade extends Entity implements Interactable, Collectable{

	public Upgrade(Image i, int x, int y) {
		super(i, x, y, (int)i.getWidth(), (int)i.getHeight());
		setDisplayLayer(4);
		setTag("Upgrade");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hasCollided(Collision c) {
		// other methods should override this
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
