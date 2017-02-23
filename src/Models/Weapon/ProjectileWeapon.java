package Models.Weapon;

import java.util.ArrayList;

import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;

public class ProjectileWeapon extends Weapon{
	
	public ProjectileWeapon(PlayableCharacter e, Image i){
		super(e, i);
		setAttackTime(50);
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		super.update(entities);
	}

	@Override
	public HitBox attack() {
		Projectile p = null;
		if(getTimer() > getAttackTime()){
			setTimer(0);
			p = new Projectile(getOwnedEntity(), SpriteSheet.getBlock(5, 5));
			p.setLifeTime((int)(getAttackTime() * 3.33));
		}
		return p;
	}
}
