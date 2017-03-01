package Models.Weapon;

import java.util.ArrayList;

import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Models.Weapon.Attack.Projectile;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ProjectileWeapon extends Weapon{
	
	public ProjectileWeapon(PlayableCharacter e, Image i){
		super(e, i);
		setAttackTime(50);
		setTimer(getAttackTime());
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		super.update(entities);
	}

	@Override
	public Attack attack() {
		Projectile p = null;
		if(getTimer() > getAttackTime()){
			setTimer(0);
			p = new Projectile(getOwnedEntity(), SpriteSheet.getBlock(5, 5, Color.BLACK));
			p.setLifeTime((int)(getAttackTime() * 3.33));
		}
		return p;
	}
}
