package Models.Weapon.Attack;

import java.util.ArrayList;

import Enums.BulletType;
import Enums.Direction;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Projectile extends Attack{
	private final BulletType type;
	private int bounces = 0;
	private int bounceAmount = 4;
	
	public Projectile(PlayableCharacter e, Image i, BulletType type) {
		super(e, i);
		setCurrDir(getOwnedEntity().getCurrDir());
		setSpeed(type.getSpeed());
		setTag(getTag() + "-Projectile");
		this.type = type; 
	}

	// We can add a lifetime counter or something
	@Override
	public void update(ArrayList<Entity> entities) {
		if(++timer >= lifeTime || !hasHit.isEmpty()){
			switch (type) {
			case NORMAL:
				entities.remove(this);
				break;
			case EXPLOSIVE:
				for(int i = 0; i < 8; i++){
					Projectile p = new Projectile(getOwnedEntity(), SpriteSheet.getBlock(5, 5, Color.BLACK), BulletType.NORMAL);
					p.setXPos(this.getXPos());
					p.setYPos(this.getYPos());
					p.setCurrDir(Direction.values()[i < 4 ? i : i + 1]);
					p.setLifeTime((int)(20));
					p.setSpeed(p.getSpeed() + 3);
					entities.add(p);
				}
				entities.remove(this);
				break;
			case BOUNCE:
				entities.remove(this);
				break;
			default:
				entities.remove(this);
				break;
			}
		}
		if(type == BulletType.BOUNCE && bounces > bounceAmount ){
			Projectile p = new Projectile(getOwnedEntity(), SpriteSheet.getBlock(10, 10, Color.BLACK), BulletType.EXPLOSIVE);
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
		case "Attack":
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
