package Controller;

import java.util.ArrayList;

import Enums.Direction;
import Models.Collision;
import Models.Entity;

public class CollisionSystem {
	
	public static ArrayList<Collision> getCollision(ArrayList<Entity> entities){
		ArrayList<Collision> collisions = new ArrayList<>();
		for(int i = 0; i < entities.size(); i++){
			collisions.addAll(getCollision(entities.get(i), entities.toArray(new Entity[i+1])));
		}
		return collisions;
	}
	
	/**
	 * This method checks to see if the first entity passed in is colliding against the remaining. 
	 *  It returns an arraylist that contains Collision objects representing all of the collisions that happened
	 * @param a
	 * @param b
	 * @return
	 */
	public static ArrayList<Collision> getCollision(Entity a, Entity... entities){
		ArrayList<Collision> collisions = new ArrayList<>();
		for(Entity e : entities){
			collisions.add(getCollision(a, e));
		}
		return collisions;
	}
	
	// Simplify it to only AABB for now
	/**
	 * This method checks to see if the two entities have collided. 
	 *  It determines the appropriate way to detect how they collided.
	 *  This can be AABB vs. AABB, Circle vs. Circle, or AABB vs. Circle
	 * @param a
	 * @param b
	 * @return
	 */
	public static Collision getCollision(Entity a, Entity b){
		return AABBvsAABB(a, b);
	}
	
	// This would need to be changed to take in rectangles instead... probably
	/**
	 * This method checks to see if the two entities have collided. 
	 *  It uses the AABB collision method for simplification
	 * @param a
	 * @param b
	 * @return
	 */
	public static Collision AABBvsAABB(Entity a, Entity b){
		Collision hori = isIntersectingXAxis(a, b);
		Collision vert = isIntersectingYAxis(a, b);
		
		// create a new collision based on the previous two...
		boolean hasCollided = hori.hasCollided && vert.hasCollided;
		Direction d = Direction.getDir(hori.collisionNormal.getX(), vert.collisionNormal.getY());
		return new Collision(a, b, hasCollided, d, hori.xPenDepth, vert.yPenDepth);
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
	public static Collision isIntersectingXAxis(Entity a, Entity b){
		// detect collision
		boolean colliding = isIntersectingAlongLine(a.getXPos(), a.getXPos() + a.getWidth(), b.getXPos(), b.getXPos() + b.getWidth());
		
		// collision Normal, which is the opposite direction we are colliding in
		// for example, if we are collding on the right side then we need to move left
		int xDir = 0;
		int penDepth = 0;
		
		// our collision is going to be which side we are collisiong against the smallest
		int penRight = a.getXPos() + a.getWidth() - b.getXPos();
		int penLeft = b.getXPos() + b.getWidth() - a.getXPos();
		if(penRight < penLeft){
			xDir = -1;
			penDepth = penRight;
		} else if(penRight > penLeft){
			xDir = 1;
			penDepth = penLeft;
		}
		// Otherwise the distance is the exact same and there is no particular direction it should go in
		else {
			xDir = 0;
			penDepth = penLeft;
		}
		return new Collision(a, b, colliding, Direction.getDir(xDir, 0), penDepth, 0);
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
	public static Collision isIntersectingYAxis(Entity a, Entity b){
		// detect collision
		boolean colliding = isIntersectingAlongLine(a.getYPos(), a.getYPos() + a.getHeight(), b.getYPos(), b.getYPos() + b.getHeight());
		
		// collision Normal, which is the opposite direction we are colliding in
		// for example, if we are collding on the top side then we need to move down
		int yDir = 0;
		int penDepth = 0;
		
		// our collision is going to be which side we are collisiong against the smallest
		int penUp = a.getYPos() + a.getHeight() - b.getYPos();
		int penDown = b.getYPos() + b.getHeight() - a.getYPos();
		if(penUp < penDown){
			yDir = -1;
			penDepth = penUp;
		} else if(penUp > penDown){
			yDir = 1;
			penDepth = penDown;
		}
		// Otherwise the distance is the exact same and there is no particular direction it should go in
		else {
			yDir = 0;
			penDepth = penDown;
		}
		return new Collision(a, b, colliding, Direction.getDir(0, yDir), 0, penDepth);
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
	
	/**
	 * This method tests for intersection along a linear number line.
	 * This method returns true if and only if Line A fully intersects, or encapsulates, Line B.
	 * @param lineAMin
	 * @param lineAMax
	 * @param lineBMin
	 * @param lineBMax
	 * @return
	 */
	public static boolean isFullyEncapsulating(int lineAMin, int lineAMax, int lineBMin, int lineBMax){
		return !(lineAMin > lineBMin || lineAMax < lineBMax); 
	}

}
