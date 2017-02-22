package Models.Weapon;

import Interfaces.Collideable;
import Models.Bounds;
import Models.Entity;
import javafx.scene.image.Image;

public class Projectile extends Entity {

	public Projectile(Image i, int x, int y) {
		super(i, x, y);
		setSpeed(5);
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
	public void update() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}
}
