package Models;

public class Collision {
	
	public final boolean hasCollided;
	public final Entity collidingEntity;
	public final Entity collidedEntity;
	//public final Direction collisionNormal;
	//public final int collisionDepth;
	
	public Collision(Entity a, Entity b, boolean collided){
		collidingEntity = a;
		collidedEntity = b;
		hasCollided = collided;
	}

}
