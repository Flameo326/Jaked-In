package Models.Weapon;

import java.util.ArrayList;

import Interfaces.Collideable;
import Models.Bounds;
import Models.Entity;
import javafx.scene.image.Image;

public class Projectile extends HitBox{
	
	private Entity entity;

	public Projectile(Entity e, Image i) {
		super(i, e.getXPos(), e.getYPos());
		entity = e;
		// direction = e.getRotation()
		// or something
		setTag(entity.getTag() + ".Projectile");
		
	}

	@Override
	public boolean isColliding(Collideable c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public Bounds getBounds() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	// We can add a lifetime counter or something
	@Override
	public void update(ArrayList<Entity> entities) {
		move(0, 1);
	}

}
