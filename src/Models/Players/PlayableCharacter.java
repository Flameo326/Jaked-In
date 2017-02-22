package Models.Players;

import Interfaces.Attackable;
import Interfaces.Collideable;
import Interfaces.Damageable;
import Interfaces.Dodgeable;
import Models.Bounds;
import Models.Entity;
import javafx.scene.image.Image;

public abstract class PlayableCharacter extends Entity implements Attackable, Dodgeable, Damageable {

	public PlayableCharacter(Image i, int x, int y) {
		super(i, x, y);
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
	public void dodge() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public void takeDamage() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}
}
