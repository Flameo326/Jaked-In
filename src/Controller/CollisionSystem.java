package Controller;

import java.util.ArrayList;

import Enums.Direction;
import Models.Collision;
import Models.Entity;
import Models.Shape.Circle;
import Models.Shape.Shape;

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
		Collision c;
		if(a.getShape() instanceof Circle){
			if(b.getShape() instanceof Circle){
				c = CirclevsCircle(a, b);
			} else {
				c = CirclevsAABB(a, b);
			}
		} else if(b.getShape() instanceof Circle){
			c = CirclevsAABB(a, b);
		} else {
			c = AABBvsAABB(a, b);
		}
		return c;
	}
	
	/**
	 * This method checks to see if the two entities have collided. 
	 *  The two Entities MUST support AABB Collision through the Rectangle Shape
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
	
	public static Collision CirclevsCircle(Entity a, Entity b){
		Circle shapeA, shapeB;
		if( a.getShape() instanceof Circle){
			shapeA = (Circle)a.getShape();
		} else { throw new IllegalArgumentException("Entity a's Shape is not a circle."); }
		if( b.getShape() instanceof Circle){
			shapeB = (Circle)b.getShape();
		} else { throw new IllegalArgumentException("Entity b's Shape is not a circle."); }

		int radiusSum = shapeA.getRadius() + shapeB.getRadius();
		int xDir = 0, yDir = 0;
		int penXDepth = 0, penYDepth = 0;
		if(shapeA.getCenterX() < shapeB.getCenterX()){
			penXDepth = shapeB.getCenterX() - shapeA.getCenterX();
			xDir = -1;
		} else {
			penXDepth = shapeA.getCenterX() - shapeB.getCenterX();
			xDir = 1;
		}
		if(shapeA.getCenterY() < shapeB.getCenterY()){
			penYDepth = shapeB.getCenterY() - shapeA.getCenterY();
			yDir = -1;
		} else {
			penYDepth = shapeA.getCenterY() - shapeB.getCenterY();
			yDir = 1;
		}
		boolean colliding = radiusSum*radiusSum < penXDepth*penXDepth + penYDepth*penYDepth;
		return new Collision(a, b, colliding, Direction.getDir(xDir, yDir), penXDepth, penYDepth);
	}
	
	public static Collision CirclevsAABB(Entity a, Entity b){
		Circle shapeA;
		Shape shapeB;
		if( a.getShape() instanceof Circle){
			shapeA = (Circle)a.getShape();
			shapeB = b.getShape();
		}else if( b.getShape() instanceof Circle){
			shapeA = (Circle)b.getShape();
			shapeB = a.getShape();
		} else { throw new IllegalArgumentException("Neither Entity is a circle Shape is not a circle."); }
		
		boolean collided = false;
		int penXDepth = 0, penYDepth = 0;
		int xDir = 0, yDir = 0;
		
		// Figure how to collide Circle and AABB
		
		return new Collision(a, b, collided, Direction.getDir(xDir, yDir), penXDepth, penYDepth);
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
		Shape shapeA = a.getShape();
		Shape shapeB = b.getShape();
		Collision c = getCollisionFromLine(shapeA.getMinX(), shapeA.getMaxX(), shapeB.getMinX(), shapeB.getMaxX());
		return new Collision(a, b, c.hasCollided, Direction.getDir(c.collisionNormal.getX(), 0), c.xPenDepth, 0);
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
		Shape shapeA = a.getShape();
		Shape shapeB = b.getShape();
		Collision c = getCollisionFromLine(shapeA.getMinY(), shapeA.getMaxY(), shapeB.getMinY(), shapeB.getMaxY());
		return new Collision(a, b, c.hasCollided, Direction.getDir(0, c.collisionNormal.getX()), 0, c.xPenDepth);
	}
	
	public static Collision getCollisionFromLine(int lineAMin, int lineAMax, int lineBMin, int lineBMax){
		boolean colliding = isIntersectingAlongLine(lineAMin, lineAMax, lineBMin, lineBMax);
		int xDir = 0;
		int penDepth = 0;
		int penLeft = lineAMax - lineBMin;
		int penRight = lineBMax - lineAMin;
		if(penLeft < penRight){
			xDir = -1;
			penDepth = penLeft;
		} else if(penLeft > penRight){
			xDir = 1;
			penDepth = penRight;
		} else {
			xDir = 0;
			penDepth = penRight;
		}
		return new Collision(null, null, colliding, Direction.getDir(xDir, 0), penDepth, 0);
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
		return!(lineAMax < lineBMin || lineBMax < lineAMin); 
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
