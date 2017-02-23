package Models.Weapon;

import java.util.ArrayList;

import Interfaces.Collideable;
import Models.Bounds;
import Models.Entity;
import javafx.scene.image.Image;

public class HitBox extends Entity{

	// This will be for Like Melee Weapons...
	public HitBox(Image i, int x, int y) {
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
	public void update(ArrayList<Entity> entities) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

}
