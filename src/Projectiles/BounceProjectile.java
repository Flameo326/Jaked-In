package Projectiles;

import java.util.ArrayList;
import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class BounceProjectile extends Projectile {
	
	public BounceProjectile(PlayableCharacter e) {
		super(e, SpriteSheet.getBouceProjectile(), 8);
		// TODO Auto-generated constructor stub
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
	}

}
