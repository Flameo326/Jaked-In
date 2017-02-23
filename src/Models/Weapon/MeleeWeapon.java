package Models.Weapon;

import java.util.ArrayList;

import Models.Entity;
import javafx.scene.image.Image;

public class MeleeWeapon extends Weapon{

	public MeleeWeapon(Image i, int x, int y) {
		super(i, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public HitBox attack() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

}
