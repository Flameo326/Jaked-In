package Models.Map;

import java.util.ArrayList;
import java.util.Random;

import Controller.CollisionSystem;
import Enums.Direction;
import Models.Collision;
import Models.Entity;
import Models.Shape.Shape;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Map {
	
	private ArrayList<Entity> mapObjects;
	protected Random rand;
	private int mapWidth, mapHeight;
	private int border = 10;
	private boolean mapIsLinear;
	
	public Map(int width, int height){
		mapWidth = width;
		mapHeight = height;
		mapObjects = new ArrayList<>();
		rand = new Random();
	}
	
	// This method can be overrode for different functionality
	public void generateMap(){
		mapObjects.clear();
		ArrayList<Entity> rooms = generateRooms(150, mapWidth, .8, 1.2, 10);
		generatePaths(rooms);
		populateMap(rooms);
	}
	
	public ArrayList<Entity> generateRooms(int minWidth, int maxWidth, double minHeightMultiplier, double maxHeightMultiplier, int roomAmo){
		ArrayList<Entity> rooms = new ArrayList<>(roomAmo);
		
		// Create first starting room
		Entity currentRoom = createNewRoom(minWidth, maxWidth, minHeightMultiplier, maxHeightMultiplier);
		rooms.add(currentRoom);
		int radius = 0;
		if(!mapIsLinear){
			radius = (int)(((maxWidth * maxHeightMultiplier - minWidth * minHeightMultiplier)) * roomAmo*.1);
		}
		room: for(int i = 1; i < roomAmo; i++){
			Entity previousRoom = rooms.get(rooms.size()-1);
			currentRoom = createNewRoom(minWidth, maxWidth, minHeightMultiplier, maxHeightMultiplier);
			
			int maxDist = Math.max(currentRoom.getWidth(), currentRoom.getHeight());
			if(mapIsLinear){
				radius = (int) ((rand.nextDouble() + 1) * maxDist);
			} 
			// in radians
			int degree = rand.nextInt(360);
			int initialDegree = degree;
			
			boolean hasNotCollided = false;
			while(!hasNotCollided){
				int x = (int)(Math.cos(degree * Math.PI / 180) * radius);
				int y = (int)(Math.sin(degree * Math.PI / 180) * radius);
				
				if(mapIsLinear){
					// Linear
					currentRoom.setXPos(previousRoom.getXPos() + x);
					currentRoom.setYPos(previousRoom.getYPos() + y);
				} else {
					// Scatterplot
					currentRoom.setXPos(x);
					currentRoom.setYPos(y);
				}
				
				hasNotCollided = true;
				// do a collision check against the rooms 
				for(Collision c : CollisionSystem.getCollision(currentRoom, rooms.toArray(new Entity[0]))){
					if(Math.min(c.xPenDepth, c.yPenDepth) > -20){
						hasNotCollided = false;
					}
				}
				degree += 99;
				if(degree >= 360) { degree -= 360; }
				if(degree == initialDegree){
					continue room;
				}
			}
			rooms.add(currentRoom);
		}
		return rooms;
	}
	
	public void generatePaths(ArrayList<Entity> rooms){
		ArrayList<Entity> walls = new ArrayList<>();
		for(int i = 0; i < rooms.size(); i++){
			Entity currentRoom = rooms.get(i);
			System.out.println("Generating Room");
			walls.addAll(generateWalls(currentRoom, walls));
			
			// If this is zero then 
			ArrayList<Collision> c = CollisionSystem.getCollision(currentRoom, mapObjects.toArray(new Entity[0]));
			Entity closest = null;
			int smallestPenetration = Integer.MIN_VALUE;
			boolean notColliding = c.size() > 0;
			
			for(int y = 0; y < c.size(); y++){
				Collision curr = c.get(y);
				// The first statement probably wont ever be true
				if(curr.collidedEntity == currentRoom || 
						curr.collidedEntity.getTag().equals("Wall")) { 
					continue; 
				}

				if((curr.xPenDepth >= 40 && curr.yPenDepth >= 0) || (curr.yPenDepth >= 40 && curr.xPenDepth >= 0)){
					closest = curr.collidedEntity;
					notColliding = false;
					break;
				}
				
				int temp; 
				if((temp = curr.xPenDepth + curr.yPenDepth) > smallestPenetration) { 
					smallestPenetration = temp;
					closest = curr.collidedEntity;
				}
			}
			
			mapObjects.add(currentRoom);
			if(notColliding){
				ArrayList<Entity> paths = generatePathsBetween(closest, currentRoom);
//				generatePathsBetween(currentRoom, closest)
				for(Entity path : paths){
					walls.addAll(generateWalls(path, walls));
					mapObjects.add(path);
				}
			}
		}
		mapObjects.addAll(walls);
	}
	
	public void populateMap(ArrayList<Entity> rooms){
		
	}
	
	public Entity createNewWall(int x, int y, int width, int height){
		Image img = SpriteSheet.getBlock(width, height, Color.BLACK);
		Entity e = new Entity(img, x, y, (int)img.getWidth(), (int)img.getHeight()){
			@Override
			public void update(ArrayList<Entity> entities) {
				// Do Nothing
			}
			@Override
			public void hasCollided(Collision c) {
				// do nothing
			}
		};
		e.setTag("Wall");
		e.setDisplayLayer(3);
		return e;
	}
	
	public Entity createNewRoom(int minWidth, int maxWidth, double minHeightMultiplier, double maxHeightMultiplier){
		int width = rand.nextInt(maxWidth - minWidth + 1) + minWidth;
		int height = (int)(rand.nextDouble() * (maxHeightMultiplier - minHeightMultiplier) + minHeightMultiplier * width);
		Entity e = new Entity(SpriteSheet.getBlock(width, height, Color.AQUA), 0, 0, width, height){
			@Override
			public void hasCollided(Collision c) {
				// do nothing
			}
			@Override
			public void update(ArrayList<Entity> entities) {
				// Do Nothing
			}
		};
		e.setTag("Room");
		e.setDisplayLayer(2);
		return e;
	}
	
	public Entity createNewPath(int minWidth, int minHeight, int maxWidth, int maxHeight){
		int width = rand.nextInt(maxWidth - minWidth + 1) + minWidth;
		int height = rand.nextInt(maxHeight - minHeight + 1) + minHeight;
		Entity e = new Entity(SpriteSheet.getBlock(width, height, Color.AQUAMARINE), 0, 0, width, height){
			@Override
			public void hasCollided(Collision c) {
				// do nothing
			}
			@Override
			public void update(ArrayList<Entity> entities) {
				// Do Nothing
			}
		};
		e.setTag("Path");
		e.setDisplayLayer(1);
		return e;
	}
	
	public ArrayList<Entity> generateWalls(Entity e, ArrayList<Entity> mapWalls){
		// Check for walls that the object is colliding with
		checkWalls(e, mapWalls);
		
		ArrayList<Entity> walls = new ArrayList<>();
		Shape shape = e.getShape();
		int height = shape.getRoundedHeight();
		int width = shape.getRoundedWidth() + border*2;
		
		// Top
		System.out.println("Top");
		Entity wall = createNewWall(shape.getCenterX(), shape.getMinY()-border/2, width, border);
		walls.addAll(checkWallCollision(wall));
		
		// Bot
		System.out.println("Bot");
		wall = createNewWall(shape.getCenterX(), shape.getMaxY()+border/2, width, border);
		walls.addAll(checkWallCollision(wall));
		
		// Left
		System.out.println("Left");
		wall = createNewWall(shape.getMinX()-border/2, shape.getCenterY(), border, height);
		walls.addAll(checkWallCollision(wall));
		
		// Right 
		System.out.println("Right");
		wall = createNewWall(shape.getMaxX()+border/2, shape.getCenterY(), border, height);
		walls.addAll(checkWallCollision(wall));
		return walls;
	}
	
	/**
	 * This method will check if the given entity has collided against any walls.
	 * <p>
	 * If it has collided against a wallit will split the wall up depending on its direction.
	 * The new wall(s) will now have a gap that does not collide with the entity
	 * @param e - The Entity that may be colliding against walls
	 */
	public void checkWalls(Entity e, ArrayList<Entity> walls){
		for(int i = 0; i < walls.size(); i++){
			Entity wall = walls.get(i);
			
			// If wall is not actually wall then continue on.
			if(!wall.getTag().equals("Wall")) { continue; }
			
			Collision c = CollisionSystem.getCollision(e, wall);
			
			walls.remove(wall);
			// Trunctate the wall
			System.out.println("Checking for Entity being placed on Wall");
			walls.addAll(0, truncateWallFromCollision(c));
		}
	}
	
	/**
	 * This methods takes in a wall to be tested for collision among the map.
	 * <p>
	 * The wall will be tested against all other entities in the map for collision.
	 * <p>
	 * If a collision is found, the wall will be truncated until it can fit the space without colliding
	 * This is done by ignoring the wall and creating new walls in it's place.
	 * <p>
	 * If the wall can not possibly fit then no walls will be returned
	 * @param wall - A wall
	 * @return An ArrayList containing the walls that do not collide against entities in the map
	 */
	public ArrayList<Entity> checkWallCollision(Entity wall){
		ArrayList<Entity> walls = new ArrayList<Entity>();
		walls.add(wall);
		for(int i = 0; i < mapObjects.size(); i++){
			Entity e = mapObjects.get(i);
			
			// Don't care about other walls
//			if(e.getTag().equals("Wall")) { continue; }
			
			ArrayList<Collision> collisions = CollisionSystem.getCollision(e, walls.toArray(new Entity[0]));
			walls.clear();
			for(Collision c : collisions){
				walls.addAll(truncateWallFromCollision(c));
			}
			if(walls.isEmpty()) { break; }
		}
		return walls;
	}
	
	/**
	 * This method takes in a collision against a wall and some other Entity
	 * <p>
	 * If the wall collides against the Entity then it will be truncated until it no longer collides
	 * <p>
	 * If the returned ArrayList is empty then no walls can exist from the collision
	 * @param c
	 * @return - An ArrayList containing the walls that come from the collision.
	 */
	public ArrayList<Entity> truncateWallFromCollision(Collision c){
		ArrayList<Entity> walls = new ArrayList<>();
		Entity wall, e;
		Shape shapeW, shapeE;
		// We assume the Wall is the colliding below
		int yDir = c.collisionNormal.getY(), xDir = c.collisionNormal.getX();
		if(c.collidedEntity.getTag().equals("Wall")){
			wall = c.collidedEntity;
			e = c.collidingEntity;
			xDir = -xDir;
			yDir = -yDir;
		} else if(c.collidingEntity.getTag().equals("Wall")){
			wall = c.collidingEntity;
			e = c.collidedEntity;
		} else {throw new IllegalArgumentException("Collision does not contain a wall"); }
		walls.add(wall);
		shapeE = e.getShape();
		shapeW = wall.getShape();
		
		System.out.println("Detecting collision between: ");
		System.out.println("\tMin X: " + shapeW.getMinX() + 
				" Min Y: " + shapeW.getMinY() + 
				" Max X: " + shapeW.getMaxX() + 
				" Max Y: " + shapeW.getMaxY());
		System.out.println("And: ");
		System.out.println("\tMin X: " + shapeE.getMinX() + 
				" Min Y: " + shapeE.getMinY() + 
				" Max X: " + shapeE.getMaxX() + 
				" Max Y:" + shapeE.getMaxY());
			
		// Check if it's completely within the object and remove
		if(c.xPenDepth >= shapeW.getWidth() && c.yPenDepth >= shapeW.getHeight()){
			// the Wall is entirely inside the object.
			walls.remove(wall);
			System.out.println("Wall was removed");
//		if(shapeE.getMinY() <= shapeW.getMinY() && shapeE.getMaxY() >= shapeW.getMaxY()
//				&& shapeE.getMinX() <= shapeW.getMinX() && shapeE.getMaxX() >= shapeW.getMaxX()){
//			// the Wall is entirely inside the object.
//			walls.remove(wall);
//			System.out.println("Wall was removed");
		} else if(c.xPenDepth > 0 && c.yPenDepth > 0){
			walls.remove(wall);
			boolean split = false, hasWidth = false, hasHeight =  false;
			if(wall.getWidth() <= 0 || wall.getHeight() <= 0) {
				System.out.println("Wall did not have valid width or height");
				walls.clear(); 
				return walls; 
			}
			Entity wall1 = createNewWall(wall.getXPos(), wall.getYPos(),
					wall.getWidth(), wall.getHeight());
			Entity wall2 = createNewWall(wall.getXPos(), wall.getYPos(), 
					wall.getWidth(), wall.getHeight());
			Entity wall3 = createNewWall(wall.getXPos(), wall.getYPos(), 
					wall.getWidth(), wall.getHeight());
			Entity wall4 = createNewWall(wall.getXPos(), wall.getYPos(), 
					wall.getWidth(), wall.getHeight());
			
			// Check if split between x Min and MAx
			if(shapeW.getMinX() < shapeE.getMinX() &&
					shapeW.getMaxX() > shapeE.getMaxX()){
				// wall is cut in half
				split = true;
				resizeWallWidth(wall3, shapeE.getMinX(), -1);
				resizeWallWidth(wall4, shapeE.getMaxX(), 1);
			} 
			// Check if Wall extends outside Entity
			else if(c.xPenDepth < shapeW.getRoundedWidth()){
				hasWidth = true;
				resizeWallWidth(wall1, xDir == 1 ? shapeE.getMaxX() : shapeE.getMinX(), xDir);
			} 
			
			// Check if split between y Min and MAx
			if(shapeW.getMinY() < shapeE.getMinY() &&
					shapeW.getMaxY() > shapeE.getMaxY()){
				// wall is cut in half
				split = true;
				resizeWallHeight(wall3, shapeE.getMinY(), -1);
				resizeWallHeight(wall4, shapeE.getMaxY(), 1);
			} 
			// Check if Wall extends outside Entity
			else if(c.yPenDepth < shapeW.getRoundedHeight()) {
				hasHeight = true;
				resizeWallHeight(wall2, yDir == 1 ? shapeE.getMaxY() : shapeE.getMinY() , yDir);
			} 
			
			System.out.println("Result is: ");
			if(split){
				// If split, add the new 2 walls
				walls.add(wall3);
				walls.add(wall4);
				System.out.println("Split");
				System.out.println("\tMin X: " + wall3.getShape().getMinX() +
						" Min Y: " + wall3.getShape().getMinY() + 
						" Max X: " + wall3.getShape().getMaxX() +
						" Max Y:" + wall3.getShape().getMaxY());
				System.out.println("\tMin X: " + wall4.getShape().getMinX() + 
						" Min Y: " + wall4.getShape().getMinY() + 
						" Max X: " + wall4.getShape().getMaxX() +
						" Max Y:" + wall4.getShape().getMaxY());
			} 
			if(hasHeight){
				walls.add(wall2);
				System.out.println("Height Wall: ");
				System.out.println("\tMin X: " + wall2.getShape().getMinX() + 
						" Min Y: " + wall2.getShape().getMinY() + 
						" Max X: " + wall2.getShape().getMaxX() +
						" Max Y:" + wall2.getShape().getMaxY());
			} 
			if(hasWidth){
				walls.add(wall1);
				System.out.println("Width Wall: ");
				System.out.println("\tMin X: " + wall1.getShape().getMinX() + 
						" Min Y: " + wall1.getShape().getMinY() + 
						" Max X: " + wall1.getShape().getMaxX() +
						" Max Y:" + wall1.getShape().getMaxY());
			}
		}
		System.out.println();
		return walls;
	}
	
	private void resizeWallWidth(Entity wall, int pointOfIntersect, int dir){
		Shape shapeW = wall.getShape();
		int width = shapeW.getWidth(), xPos = shapeW.getCenterX();
		if(dir == 1){
			width = shapeW.getMaxX() - pointOfIntersect;
			xPos = shapeW.getMaxX() - width/2;
		} else if(dir == -1){
			width =  pointOfIntersect - shapeW.getMinX();
			xPos = shapeW.getMinX() + width/2;
		} else { /* not Valid */ }
		wall.setXPos(xPos);
		wall.setWidth(width);
	}
	
	private void resizeWallHeight(Entity wall, int pointOfIntersect, int dir){
		Shape shapeW = wall.getShape();
		int height = shapeW.getWidth(), yPos = shapeW.getCenterX();
		if(dir == 1){
			height = shapeW.getMaxY() - pointOfIntersect;
			yPos = shapeW.getMaxY() - height/2;
		} else if(dir == -1){
			height =  pointOfIntersect - shapeW.getMinY();
			yPos = shapeW.getMinY() + height/2;
		} else { /* not Valid */ }
		wall.setYPos(yPos);
		wall.setHeight(height);
	}
	
	public ArrayList<Entity> generatePathsBetween(Entity e1, Entity e2){
		ArrayList<Entity> paths = new ArrayList<>();
//		System.out.println("Making paths from Room: ");
//		System.out.println("\tX: " + e1.getShape().getMinX() + " Y: " + e1.getShape().getMinY() + " Width: " + e1.getShape().getMaxX() + " Height: " + e1.getShape().getMaxY());
//		System.out.println("To Room: ");
//		System.out.println("\tX: " + e2.getShape().getMinX() + " Y: " + e2.getShape().getMinY() + " Width: " + e2.getShape().getMaxX() + " Height: " + e2.getShape().getMaxY());
		
		int playerW = 30 + 5, playerH = 30 + 5;
		int width = rand.nextInt((int)(playerW*1.5/5)) * 5 + (int)(playerW*1.5);
		int height = rand.nextInt((int)(playerH*1.5/5)) * 5 + (int)(playerH*1.5);
//		System.out.println("Default Width: " + width + " Default Height: " + height + "\n");
		
		Entity previousPath = e1;
		Shape shapeE2 = e2.getShape();
		Shape previousShape = previousPath.getShape();
		
		Collision c = CollisionSystem.getCollision(previousPath, e2);
		Direction d = Direction.getDir(-c.collisionNormal.getX(), -c.collisionNormal.getY());
		Direction previousD = Direction.NULL;
		
		// If e2 fully encapsulates us then we are already connected no matter what.
		boolean xConnected = CollisionSystem.isFullyEncapsulating(shapeE2.getMinX(),
				shapeE2.getMaxX(), previousShape.getMinX(), previousShape.getMaxX());
		boolean yConnected = CollisionSystem.isFullyEncapsulating(shapeE2.getMinY(),
				shapeE2.getMaxY(), previousShape.getMinY(), previousShape.getMaxY());
		
		// If an object is already sufficiently colliding  then choose the other path
		boolean xPath = rand.nextBoolean();
		if(xConnected || CollisionSystem.isIntersectingAlongLine(previousShape.getMinX(), previousShape.getMaxX(),
				shapeE2.getMinX() + (d.getX() == 1 && !yConnected ? playerW : 0),
				shapeE2.getMaxX() - (d.getX() == -1 && !yConnected ? playerW : 0))){
			xPath = false;
		} else if(yConnected || CollisionSystem.isIntersectingAlongLine(previousShape.getMinY(), previousShape.getMaxY(),
				shapeE2.getMinY() + (d.getY() == 1 && !xConnected ? playerH : 0),
				shapeE2.getMaxY() - (d.getY() == -1 && !xConnected ? playerH : 0))){
			xPath = true;
		}
//		int i = 0;
		while(!xConnected || !yConnected){
			// Negate it get the direction towards the object
			c = CollisionSystem.getCollision(previousPath, e2);
			d = Direction.getDir(-c.collisionNormal.getX(), -c.collisionNormal.getY());
			Entity path = null;
			
			if(xPath){
				int widthMax;
				if(d.getX() == 1){
					widthMax = (yConnected ? shapeE2.getMinX() : shapeE2.getMaxX()) - (previousShape.getMaxX());
				} else {
					widthMax = previousShape.getMinX() - ((yConnected ? shapeE2.getMaxX() : shapeE2.getMinX()));
				}  
				int widthMin = Math.min(width+5, widthMax);
				// If width is odd and fixed then make it so that it goes even
				if(widthMax == widthMin && widthMax%2 == 1){
					widthMax += 1;
					widthMin = widthMax;
				}
//				System.out.println("Width Min and Max: " + widthMin + " " + widthMax);
				
				path = createNewPath(widthMin, height, widthMax, height);
				
				int initialY;
				if(previousPath == e1){
					initialY = rand.nextInt(Math.max(previousPath.getHeight() - height + 1, 1)) + previousShape.getMinY() + height/2;
				} else {
					initialY = (previousD.getY() == 1 ? previousShape.getMaxY() - height/2 : previousShape.getMinY() + height/2);
				}
				int initialX = (d.getX() == 1 ? previousShape.getMaxX() + path.getWidth()/2 : 
						previousShape.getMinX() - path.getWidth()/2);
				path.setXPos(initialX);
				path.setYPos(initialY);
			} else {
				int heightMax;
				if(d.getY() == 1){
					heightMax = (xConnected ? shapeE2.getMinY() : shapeE2.getMaxY()) - previousShape.getMaxY();
				} else {
					heightMax = previousShape.getMinY() - (xConnected ? shapeE2.getMaxY() : shapeE2.getMinY());
				}
				int heightMin = Math.min(height+5, heightMax);
				// If height is odd and fixed then make it so that it goes even
				if(heightMax == heightMin && heightMax%2 == 1){
					heightMax += 1;
					heightMin = heightMax;
				}
//				System.out.println("Height Min and Max: " + heightMin + " " + heightMax);
				
				path = createNewPath(width, heightMin, width, heightMax);
				int initialX;
				if(previousPath == e1){
					initialX = rand.nextInt(Math.max(previousPath.getWidth() - width + 1, 1)) + previousShape.getMinX() + width/2;
				} else {
					initialX = (previousD.getX() == 1 ? previousShape.getMaxX() - width/2 : previousShape.getMinX() + width/2);
				}
				int initialY = (d.getY() == 1 ? previousShape.getMaxY() + path.getHeight()/2 : 
						previousShape.getMinY() - path.getHeight()/2);
				path.setXPos(initialX);
				path.setYPos(initialY);
			} 
			if(path != null){
				// Check Collisions
				if(xConnected || CollisionSystem.isIntersectingAlongLine(path.getShape().getMinX(), path.getShape().getMaxX(),
						shapeE2.getMinX() + (d.getX() == 1 && !yConnected ? playerW : 0),
						shapeE2.getMaxX() - (d.getX() == -1 && !yConnected ? playerW : 0))){
					xConnected = true;
					yConnected = CollisionSystem.isIntersectingYAxis(path, e2).hasCollided;
				} else if(yConnected || CollisionSystem.isIntersectingAlongLine(path.getShape().getMinY(), path.getShape().getMaxY(),
						shapeE2.getMinY() + (d.getY() == 1 && !xConnected ? playerH : 0),
						shapeE2.getMaxY() - (d.getY() == -1 && !xConnected ? playerH : 0))){
					yConnected = true;
					xConnected = CollisionSystem.isIntersectingXAxis(path, e2).hasCollided;
				}
				
//				System.out.println("Created " + (xPath ? "x" : "y") + "Path X: " + path.getShape().getMinX() + 
//						" Y: " + path.getShape().getMinY() + " Width: " + path.getShape().getMaxX() + " Height: " + path.getShape().getMaxY());
//				System.out.println("X: " + xConnected + " Y: " + yConnected + "\n");
				
				paths.add(path);
				previousPath = path;
				previousShape = path.getShape();
			}
			xPath = !xPath;
			previousD = d;
		}
		return paths;
	}
	
	public void setMapIsLinear(boolean b){
		mapIsLinear = b;
	}
	
	public void setBorder(int i){
		border = i;
	}
	
	public ArrayList<Entity> getMapObjects(){
		return mapObjects;
	}
	
	public int getBorder(){
		return border;
	}
	
	public int getMapWidth(){
		return mapWidth;
	}
	
	public int getMapHeight(){
		return mapHeight;
	}

}
