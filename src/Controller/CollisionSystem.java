package Controller;

import Models.Collision;
import Models.Entity;

public class CollisionSystem {
	
	// Simplify it to only AABB for now
	public static Collision hasCollided(Entity a, Entity b){
		return AABBvsAABB(a, b);
	}
	
	// This would need to be changed to take in rectangles instead... probably
	public static Collision AABBvsAABB(Entity a, Entity b){
		boolean hasCollided = true;
		if(a.getXPos() + a.getWidth() < b.getXPos() || b.getXPos() + b.getWidth() < a.getXPos()) { hasCollided = false; }
		else if(a.getYPos() + a.getHeight() < b.getYPos() || b.getYPos() + b.getHeight() < a.getYPos()) { hasCollided = false; }
		return new Collision(a, b, hasCollided);
	}

}
