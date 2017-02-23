package Models.Players;

import Interfaces.Attackable;
import Interfaces.Collideable;
import Interfaces.Damageable;
import Interfaces.Dodgeable;
import Models.Bounds;
import Models.Entity;
import Models.Weapon.ProjectileWeapon;
import Models.Weapon.Weapon;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;

public abstract class PlayableCharacter extends Entity implements Attackable, Dodgeable, Damageable {
	
	private Weapon weapon;

	public PlayableCharacter(Image i, int x, int y) {
		super(i, x, y);
		// Just Default it to a Standard Projectile Weapon for now
		setWeapon(new ProjectileWeapon(this, SpriteSheet.getBlock(5, 5)));
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
	
	public void setWeapon(Weapon w){
		if(w != null){
			
			weapon = w;
		}
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
}
