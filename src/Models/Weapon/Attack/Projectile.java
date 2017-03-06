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
		if(c.collidingEntity == this){
			collider = c.collidedEntity;
		} else { return; }
		
		String[] tagElements = collider.getTag().split("-");
//		String[] ourElements = getTag().split("-");
		
		switch(tagElements[0]){
		// If I collide against these then just move away
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
//			if(c.xPenDepth < c.yPenDepth && c.xPenDepth > 0){
//				if(getCurrDir().getX() == 1){
//					xDir = -1;
//					setXPos(getXPos() - (getShape().getMaxX() - collider.getShape().getMinX()) * 2);
//				} else if(getCurrDir().getX() == -1) {
//					xDir = 1;
//					setXPos(getXPos() + (collider.getShape().getMaxX() - getShape().getMinX()) * 2);
//				} else {
//					xDir = c.collisionNormal.getX();
//					setXPos(getXPos() + xDir == 1 ? c.xPenDepth : -c.xPenDepth);
//				}
//				if(getYPos() < collider.getShape().getMinY()){
//					yDir = -1;
//				} else if(getYPos() > collider.getShape().getMaxY()){
//					yDir = 1;
//				}
//			} else if(c.yPenDepth > 0) {
//				if(getCurrDir().getY() == 1){
//					yDir = -1;
//					setYPos(getYPos() - (getShape().getMaxY() - collider.getShape().getMinY()) * 2);
//				} else if(getCurrDir().getY() == -1){
//					yDir = 1;
//					setYPos(getYPos() + (collider.getShape().getMaxY() - getShape().getMinY()) * 2);
//				} else {
//					yDir = c.collisionNormal.getY();
//					setYPos(getYPos() + yDir == 1 ? c.yPenDepth : -c.yPenDepth);
//				}
//				if(getXPos() < collider.getShape().getMinX()){
//					xDir = -1;
//				} else if(getXPos() > collider.getShape().getMaxX()){
//					xDir = 1;
//				}
//			}
//			setCurrDir(Direction.getDir(xDir, yDir));
			break;
		}
	}

}
