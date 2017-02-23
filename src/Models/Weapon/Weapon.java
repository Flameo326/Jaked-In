package Models.Weapon;

import Interfaces.Attackable;
import Interfaces.Collideable;
import Models.Bounds;
import Models.Entity;
import javafx.scene.image.Image;

public abstract class Weapon extends Entity implements Attackable{

	// What If this class had a direction that it moves in that way, there is no reason to pass in x and y values.
	//  that can be in the Controller...
	public Weapon(Image i, int x, int y) {
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

}
