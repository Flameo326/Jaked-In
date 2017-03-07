package Projectiles;

import java.util.ArrayList;
import Enums.Direction;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import javafx.scene.image.Image;

public abstract class Projectile extends Attack{
	protected int bounces = 0;
	protected int bounceAmount = 0;
	
	public Projectile(PlayableCharacter e, Image img, int speed) {
		super(e, img);
		setCurrDir(getOwnedEntity().getCurrDir());
		setSpeed(speed);
		setTag(getTag() + "-Projectile");
	}
	
	public void update(ArrayList<Entity> entities) {
		if(++timer >= lifeTime || !hasHit.isEmpty()){
			entities.remove(this);
		}
		move(entities);
	}
	
	@Override 
	public void hasCollided(Collision c){
		// Test for damage in parent Class
		super.hasCollided(c);
		
		Entity collider;
		if(c.collidingEntity == this /*&& (c.yPenDepth > 0 && c.xPenDepth > 0)*/){
			collider = c.collidedEntity;
		} else { return; }
		// This is wrong because Players can collide with it
		// ???
		
		String[] tagElements = collider.getTag().split("-");
//		String[] ourElements = getTag().split("-");
		
		switch(tagElements[0]){
//		case "Attack":
		case "Wall":
			bounces++;
			if(c.xPenDepth < c.yPenDepth){
				setXPos(getXPos() + (-getCurrDir().getX() * c.xPenDepth) * 2);
				setCurrDir(Direction.getDir(-getCurrDir().getX(), getCurrDir().getY()));
			} else {
				setYPos(getYPos() + (-getCurrDir().getY() * c.yPenDepth) * 2);
				setCurrDir(Direction.getDir(getCurrDir().getX(), -getCurrDir().getY()));
			}
			
//			int xDir = getCurrDir().getX(), yDir = getCurrDir().getY();

//			//Determine which face we bounced off, i.e. the collision normal
//			Collision prevC = CollisionSystem.AABBvsAABBShape(
//					new Shape(getPrevXPos(), getPrevYPos(), getWidth(), getHeight()), collider.getShape());
//
//			if(prevC.xPenDepth > 0){
//				setYPos(getYPos() + ( yDir == 1 ?
//						-(getShape().getMaxY() - collider.getShape().getMinY()) :
//						collider.getShape().getMaxY() - getShape().getMinY()) * 2);
//				yDir = -yDir;
//				
//			} else if(prevC.yPenDepth > 0){
//				setXPos(getXPos() + ( xDir == 1 ?
//						-(getShape().getMaxX() - collider.getShape().getMinX()) :
//						collider.getShape().getMaxX() - getShape().getMinX()) * 2);
//				xDir = -xDir;
//			}
//			// otherwise we bounce off the axis we are farther from
//			else if(prevC.xPenDepth < prevC.yPenDepth){
//				setYPos(getYPos() + ( yDir == 1 ?
//						-(getShape().getMaxY() - collider.getShape().getMinY()) :
//						collider.getShape().getMaxY() - getShape().getMinY()) * 2);
//				yDir = -yDir;
//			} else {
//				setXPos(getXPos() + ( xDir == 1 ?
//						-(getShape().getMaxX() - collider.getShape().getMinX()) :
//						collider.getShape().getMaxX() - getShape().getMinX()) * 2);
//				xDir = -xDir;
//			}
//			setCurrDir(Direction.getDir(xDir, yDir));
			break;
		}
	}

}
