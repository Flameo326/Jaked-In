package Projectiles;

import java.util.ArrayList;
import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class BounceProjectile extends Projectile {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BounceProjectile(PlayableCharacter e, int bounceAmount) {
		super(e, SpriteSheet.getBouceProjectile(), bounceAmount);
		setBounceAmount(bounceAmount);
	}

	@Override
	public void update(ArrayList<Entity> entities){
		if( bounces > bounceAmount ){
			Projectile p = new ExplosiveProjectile(getOwnedEntity());
			p.setXPos(this.getXPos());
			p.setYPos(this.getYPos());
			p.setCurrDir(getCurrDir());
			p.setLifeTime((int)(50));
			p.setSpeed(1);
			entities.add(p);
			entities.remove(this);
		}
		move(entities);
	}

}
