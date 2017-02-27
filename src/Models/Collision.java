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
	public final int penDepth;
	
	public Collision(Entity collidingEntity, Entity collidedEntity, boolean hasCollided){
		this(collidingEntity, collidedEntity, hasCollided, Direction.NULL, 0);
	}
	
	public Collision(Entity collidingEntity, Entity collidedEntity, boolean hasCollided, Direction collisionNormal, int penDepth){
		this.collidedEntity = collidingEntity;
		this.collidingEntity = collidedEntity;
		this.hasCollided = hasCollided;
		this.collisionNormal = collisionNormal;
		this.penDepth = penDepth;
	}

}
