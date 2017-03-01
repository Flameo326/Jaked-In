package Models.Map;

import java.util.ArrayList;
import java.util.Random;

import Controller.CollisionSystem;
import Enums.Direction;
import Models.Collision;
import Models.Entity;
import Models.Shape.Shape;
import Models.Upgrades.MedPack;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Map {
	
	private ArrayList<Entity> mapObjects;
	private Random rand;
	private int mapWidth, mapHeight;
	private boolean mapIsLinear = true;
	
	public Map(int width, int height){
		mapWidth = width;
		mapHeight = height;
		mapObjects = new ArrayList<>();
		rand = new Random();
		// 
		ArrayList<Entity> rooms = generateRooms(150, mapWidth, .8, 1.2, 10);
		generatePaths(rooms);
		populateMap(rooms);
//		mapObjects.addAll(rooms);
	}
	
	// Current Problems:
	// Paths sometimes look ugly when they intersect but not cleanly, 
	// 	- i.e. they hang over on one side of the room making it lopsided
	//  -  - We can make it so that they are either generated on one side or the other, not in between
	//  -  - Find some way to make it so they generate long enought to be ok
	// Sometimes the path will not generate correctly and go into infinite while loop
	// - Fixed???
	// Rooms are sometimes generated smaller than 150 and default width/ height affect that giving out of bounds
	// Paths will techincally not intersect but will be displayed as if they do...
	// Rooms will connect with each other and not be connected to main room
	
	public ArrayList<Entity> generateRooms(int minWidth, int maxWidth, double minHeightMultiplier, double maxHeightMultiplier, int roomAmo){
		ArrayList<Entity> rooms = new ArrayList<>(roomAmo);
		mapObjects.clear();
		
		// Create first starting room
		Entity currentRoom = createNewRoom(minWidth, maxWidth, minHeightMultiplier, maxHeightMultiplier);
		rooms.add(currentRoom);
//		mapObjects.add(currentRoom);
		
		room: for(int i = 1; i < roomAmo; i++){
			Entity previousRoom = rooms.get(i-1);
			currentRoom = createNewRoom(minWidth, maxWidth, minHeightMultiplier, maxHeightMultiplier);
			
			int maxDist = Math.max(currentRoom.getWidth(), currentRoom.getHeight());
			int radius = (int) ((rand.nextDouble() + 1) * maxDist);
			if(!mapIsLinear){
				radius = (int)(maxWidth * maxHeightMultiplier * roomAmo/5);
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
				System.out.println("Here");
				hasNotCollided = true;
				// do a collision check against the rooms which will be at the end of size i
				for(Collision c : CollisionSystem.getCollision(currentRoom, rooms.toArray(new Entity[0]))){
					if(Math.min(c.xPenDepth, c.yPenDepth) > -20){
						hasNotCollided = false;
					}
				}
				degree += 99;
				if(degree >= 360) { degree -= 360; }
				if(degree == initialDegree){
					--i;
					continue room;
				}
			}
			
			// Collection of collisions between new Room and previous objects
			ArrayList<Collision> c =  CollisionSystem.getCollision(currentRoom, mapObjects.toArray(new Entity[0]));
			
			// Set default values to the first entity in collection
			Entity closest = c.get(0).collidedEntity;
			int smallestXPen = c.get(0).xPenDepth; 
			int smallestYPen = c.get(0).yPenDepth;
			int smallestPenetration = Math.max(smallestXPen, smallestYPen);
			boolean colliding = false;
			
			// Check for the Entity that is eiter colliding or has the least (maximum) penetrationDepth
			for(int y = 1; y < c.size(); y++){
				
				// Get the closest object to the currentRoom
				int temp;
				if((temp = Math.max(c.get(i).xPenDepth, c.get(i).yPenDepth)) > smallestPenetration) { 
					smallestYPen = c.get(i).yPenDepth;
					smallestXPen = c.get(i).xPenDepth;
					smallestPenetration = temp;
					closest = c.get(i).collidedEntity;
				} else if(temp == smallestPenetration){
					if(c.get(i).yPenDepth > smallestYPen){
						smallestYPen = c.get(i).yPenDepth;
						closest = c.get(i).collidedEntity;
					} else if(c.get(i).xPenDepth > smallestXPen){
						smallestXPen = c.get(i).xPenDepth;
						closest = c.get(i).collidedEntity;
					}
				}
				// If the player can move into the room from another object on the map
				// Then no need to create paths.
//				if((smallestXPen >= 40 && smallestYPen >= 0) || (smallestYPen >= 40 && smallestXPen >= 0)){
//					colliding = true; 
//					break; 
//				}
			}
			if(!colliding){
				mapObjects.addAll(0, generatePathsBetween(closest, currentRoom));
			}
//			mapObjects.add(currentRoom);
			rooms.add(currentRoom);
		}
		return rooms;
	}
	
	public void generatePaths(ArrayList<Entity> rooms){
		for(int i = 0; i < rooms.size(); i++){
			Entity currentRoom = rooms.get(i);
			
			// If this is zero then 
			ArrayList<Collision> c = CollisionSystem.getCollision(currentRoom, mapObjects.toArray(new Entity[0]));
			Entity closest = null;
			int smallestPenetration = Integer.MIN_VALUE;
			boolean notColliding = c.size() > 0;
			
			for(int y = 0; y < c.size(); y++){
				if(c.get(y).collidedEntity == currentRoom) { continue; }

				Collision curr = c.get(y);
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
			if(notColliding){
//				mapObjects.addAll(generatePathsBetween(currentRoom, closest));
				mapObjects.addAll(generatePathsBetween(closest, currentRoom));
			}
			mapObjects.add(currentRoom);
		}
	}
	
	private void populateMap(ArrayList<Entity> rooms){
		int upgradesAmo = rand.nextInt(10) + 1;
		for(int i = 0; i < upgradesAmo; i++){
			Entity room = rooms.get(rand.nextInt(rooms.size()));
			int size = (rand.nextInt(10) + 1) * 5;
			int xPos = rand.nextInt(room.getWidth() - size) + room.getShape().getMinX() + size/2;
			int yPos = rand.nextInt(room.getHeight() - size) + room.getShape().getMinY() + size/2;
			mapObjects.add(new MedPack(SpriteSheet.getBlock(size, size, Color.BLUEVIOLET), xPos, yPos));
		}
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
	
	public ArrayList<Entity> generatePathsBetween(Entity e1, Entity e2){
		ArrayList<Entity> paths = new ArrayList<>();
		System.out.println("Making paths from Room: ");
		System.out.println("\tX: " + e1.getShape().getMinX() + " Y: " + e1.getShape().getMinY() + " Width: " + e1.getShape().getMaxX() + " Height: " + e1.getShape().getMaxY());
		System.out.println("To Room: ");
		System.out.println("\tX: " + e2.getShape().getMinX() + " Y: " + e2.getShape().getMinY() + " Width: " + e2.getShape().getMaxX() + " Height: " + e2.getShape().getMaxY());
		
		int playerW = 30 + 5, playerH = 30 + 5;
		int width = rand.nextInt((int)(playerW*1.5/5)) * 5 + (int)(playerW*1.5);
		int height = rand.nextInt((int)(playerH*1.5/5)) * 5 + (int)(playerH*1.5);
		System.out.println("Default Width: " + width + " Default Height: " + height + "\n");
		
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
			
			if(xPath /*&& !(CollisionSystem.isFullyEncapsulating(currentPath.getShape().getMinX(),
					currentPath.getShape().getMaxX(), e2.getShape().getMinX(), e2.getShape().getMaxX()))*/){
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
				System.out.println("Width Min and Max: " + widthMin + " " + widthMax);
				
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
			} else /*if(!(CollisionSystem.isFullyEncapsulating(currentPath.getShape().getMinY(),
					currentPath.getShape().getMaxY(), e2.getShape().getMinY(), e2.getShape().getMaxY())))*/{
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
				System.out.println("Height Min and Max: " + heightMin + " " + heightMax);
				
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
				
				System.out.println("Created " + (xPath ? "x" : "y") + "Path X: " + path.getShape().getMinX() + 
						" Y: " + path.getShape().getMinY() + " Width: " + path.getShape().getMaxX() + " Height: " + path.getShape().getMaxY());
				System.out.println("X: " + xConnected + " Y: " + yConnected + "\n");
				
				paths.add(path);
				previousPath = path;
				previousShape = path.getShape();
			}
			xPath = !xPath;
			previousD = d;
//			if(++i >= 100) { break; }
		}
		return paths;
	}
	
	public ArrayList<Entity> getMapObjects(){
		return mapObjects;
	}
}
