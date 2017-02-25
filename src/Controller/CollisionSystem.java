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
		if(hasCollided = isIntersectingXAxis(a, b)) {}
		else if(hasCollided = isIntersectingXAxis(a, b)) {}
		return new Collision(a, b, hasCollided);
	}
	
	/**
	 * This method tests if the two entities are colliding along the X Axis.
	 * That is to say, if we were to translate the Entity onto a 1 Dimensional line by it's X Axis, would they be intersecting.
	 * This method uses the typical collision testing system of AABB meaning,
	 * 	It tests if A's maximum X value is less than B's minimum X value or 
	 * 	if A's minimum X value is greater than B's maximum X value.
	 * This method returns false if either of the previous cases is true
	 * @param a - an Entity in AABB form
	 * @param b - an Entity in AABB form
	 * @return
	 */
	public static boolean isIntersectingXAxis(Entity a, Entity b){
		return !(a.getXPos() + a.getWidth() < b.getXPos() || b.getXPos() + b.getWidth() < a.getXPos());
	}
	
	/**
	 * This method tests if the two entities are colliding along the Y Axis.
	 * That is to say, if we were to translate the Entity onto a 1 Dimensional line by it's Y Axis, would they be intersecting.
	 * This method uses the typical collision testing system of AABB meaning,
	 * 	It tests if A's maximum Y value is less than B's minimum Y value or 
	 * 	if A's minimum Y value is greater than B's maximum Y value.
	 * This method returns false if either of the previous cases is true
	 * @param a - an Entity in AABB form
	 * @param b - an Entity in AABB form
	 * @return
	 */
	public static boolean isIntersectingYAxis(Entity a, Entity b){
		return !(a.getYPos() + b.getHeight() < b.getYPos() || b.getYPos()  < a.getYPos()  + a.getHeight() ); 
	}
	
	/**
	 * This equation takes in 4 values and represents the values as two lines on a single number line.
	 * This equation returns true if the two lines intersect on the number line.
	 * @param entityAMin - Minimum value of Line A
	 * @param entityAMax - Maximum value of Line A
	 * @param entityBMin - Minimum value of Line B
	 * @param entityBMax - Maximum value of Line B
	 * @return 
	 */
	public static boolean isIntersectingAlongLine(int lineAMin, int lineAMax, int lineBMin, int lineBMax){
		return !(lineAMax < lineBMin || lineBMax < lineAMin); 
	}

}
