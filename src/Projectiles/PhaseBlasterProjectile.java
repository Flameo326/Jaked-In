package Projectiles;

import java.util.ArrayList;

import Controller.GameController;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import SpriteSheet.SpriteSheet;

public class PhaseBlasterProjectile extends Attack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhaseBlasterProjectile(PlayableCharacter e) {
		super(e, SpriteSheet.getPhaseBlasterProjectile());
		setCurrDir(getOwnedEntity().getCurrDir());
		setTag(getTag() + "-Projectile");
		setSpeed(3);
		setDamage(getDamage() + 15);
		setLifeTime(3000);
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		if(GameController.getTimer() >= lifeTime){
			entities.remove(this);
		}
		move(entities);
	}

}
