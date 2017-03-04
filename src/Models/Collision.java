package Models;

import Enums.Direction;

public class Collision {
	
	public final boolean hasCollided;
	public final Entity collidingEntity;
	public final Entity collidedEntity;
	// The direction we should go in to avoid collision
	public final Direction collisionNormal;
	// The depth of collision
	// If this value is negative then it indicates how far we need to move to collide
	public final int xPenDepth;
	public final int yPenDepth;
	
	public Collision(Entity collidingEntity, Entity collidedEntity, boolean hasCollided){
		this(collidingEntity, collidedEntity, hasCollided, Direction.NULL, 0, 0);
	}
	
	public Collision(Entity collidingEntity, Entity collidedEntity, boolean hasCollided, Direction collisionNormal, int xPenDepth, int yPenDepth){
		this.collidingEntity = collidingEntity;
		this.collidedEntity = collidedEntity;
		this.hasCollided = hasCollided;
		this.collisionNormal = collisionNormal;
		this.xPenDepth = xPenDepth;
		this.yPenDepth = yPenDepth;
	}

}

/**
 * int width = (e.getPreviousXPos() < e.getXPos() ? e.getXPos() - e.getPreviousXPos() :
					e.getPreviousXPos() - e.getXPos()) + e.getWidth();
				int height = (e.getPreviousYPos() < e.getYPos() ? e.getYPos() - e.getPreviousYPos() :
					e.getPreviousYPos() - e.getYPos()) + e.getHeight();
				int xPos = (e.getPreviousXPos() < e.getXPos() ? e.getPreviousXPos() + width/2 : 
						e.getXPos() + width/2);
				int yPos = (e.getPreviousYPos() < e.getYPos() ? e.getPreviousYPos() + height/2 : 
					e.getYPos() + height/2);
				Shape newShape = new Shape(xPos, yPos, width, height);
				// Fix...
 * 
 * public static Collision AABBvsAABBShape(Shape a, Shape b){
		Collision hori = getCollisionFromLine(a.getMinX(), a.getMaxX(), b.getMinX(), b.getMaxX());
		Collision vert = getCollisionFromLine(a.getMinY(), a.getMaxY(), b.getMinY(), b.getMax());
		
		// create a new collision based on the previous two...
		boolean hasCollided = hori.hasCollided && vert.hasCollided;
		Direction d = Direction.getDir(hori.collisionNormal.getX(), vert.collisionNormal.getY());
		return new Collision(null, null, hasCollided, d, hori.xPenDepth, vert.yPenDepth);
	}
 * 
 */
