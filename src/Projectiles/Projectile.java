package Projectiles;

import java.util.ArrayList;

import Controller.GameController;
import Enums.Direction;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import javafx.scene.image.Image;

public abstract class Projectile extends Attack{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int bounces = 0;
	protected int bounceAmount = 0;
	
	public Projectile(PlayableCharacter e, Image img, int speed) {
		super(e, img);
		setCurrDir(getOwnedEntity().getCurrDir());
		setSpeed(speed);
		setTag(getTag() + "-Projectile");
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		if(GameController.getTimer() >= lifeTime || !hasHit.isEmpty()){
			entities.remove(this);
		}
		move(entities);
	}
	
	@Override 
	public void hasCollided(Collision c){
		// Test for damage in parent Class
		super.hasCollided(c);
		
		Entity collider;
		if(c.collidingEntity == this){
			collider = c.collidedEntity;
		} else { return; }
		
		String[] tagElements = collider.getTag().split("-");
		//Wall-ForceField-Human-1
		switch(tagElements[0]){
		case "Wall":
			if(tagElements.length > 3 && tagElements[1].equals("ForceField")){
				String id = tagElements[2] + "-" + tagElements[3];
				if(id.equals(getOwnedEntity().getTag())){
					break;
				}
			}
			bounces++;
			if(c.xPenDepth < c.yPenDepth){
				setXPos(getXPos() + (-getCurrDir().getX() * c.xPenDepth) * 2);
				setCurrDir(Direction.getDir(-getCurrDir().getX(), getCurrDir().getY()));
			} else {
				setYPos(getYPos() + (-getCurrDir().getY() * c.yPenDepth) * 2);
				setCurrDir(Direction.getDir(getCurrDir().getX(), -getCurrDir().getY()));
			}
			break;
		}
	}

	public int getBounceAmount() {
		return bounceAmount;
	}
	
	public void setBounceAmount(int bounceAmount) {
		this.bounceAmount = bounceAmount;
	}
}
