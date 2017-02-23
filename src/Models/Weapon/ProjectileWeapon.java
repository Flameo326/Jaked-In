package Models.Weapon;

import java.util.ArrayList;

import Interfaces.Attackable;
import Interfaces.Collideable;
import Models.Bounds;
import Models.Entity;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;

public class ProjectileWeapon extends Weapon implements Attackable{
	
	private Entity ownedEntity;

	public ProjectileWeapon(Entity e, Image i){
		super(i, e.getXPos(), e.getYPos());
		ownedEntity = e;
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
	public void update(ArrayList<Entity> entities) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public HitBox attack() {
		return new Projectile(ownedEntity, SpriteSheet.getBlock(5, 5));
	}
}
