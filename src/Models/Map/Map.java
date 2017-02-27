package Models.Map;

import java.util.ArrayList;
import java.util.Random;

import Controller.CollisionSystem;
import Enums.Direction;
import Interfaces.Collideable;
import Models.Bounds;
import Models.Collision;
import Models.Entity;
import Models.Upgrades.Upgrade;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Map {
	
	private ArrayList<Entity> mapObjects;
	private Random rand;
	private int mapWidth, mapHeight;
	
	// So just create a 2 Dimensional Array that represents the Map
	// WE fill those 
	
	public Map(int width, int height){
		mapWidth = width;
		mapHeight = height;
		mapObjects = new ArrayList<>();
		rand = new Random();
		// rand.nextInt(10)+1
		generateMap(70, mapWidth, .8, 1.2, 10);
		populateMap();
	}
	
	// Current Problems:
	// Paths are created and not reused...
	// Usually Linear unless paths intersect
	// the more rooms, the more blobbish
	
	// We should try to not generate rooms that are less than the player's width and height
	public void generateMap(int minWidth, int maxWidth, double minHeightMultiplier, double maxHeightMultiplier, int roomAmo){
		mapObjects.clear();
		
		Entity currentRoom = createNewRoom(minWidth, maxWidth, minHeightMultiplier, maxHeightMultiplier);
		mapObjects.add(currentRoom);
		room: for(int i = 1; i < roomAmo; i++){
			Entity previousRoom = mapObjects.get(mapObjects.size()-1);
			currentRoom = createNewRoom(minWidth, maxWidth, minHeightMultiplier, maxHeightMultiplier);
			
			int maxDist = Math.max(currentRoom.getWidth(), currentRoom.getHeight());
			int radius = (int) ((rand.nextDouble() + 1) * maxDist);
			// in radians
			int degree = rand.nextInt(360);
			int initialDegree = degree;
			
			boolean hasNotCollided = false;
			while(!hasNotCollided){
				int x = (int)(Math.cos(degree * Math.PI / 180) * radius);
				int y = (int)(Math.sin(degree * Math.PI / 180) * radius);
				currentRoom.setXPos(previousRoom.getXPos() + x);
				currentRoom.setYPos(previousRoom.getYPos() + y);
				hasNotCollided = true;
				// do a collision check against the rooms which will be at the end of size i
				for(Collision c : CollisionSystem.getCollision(currentRoom, 
						mapObjects.toArray(new Entity[mapObjects.size()-i]))){
					if(Math.min(c.xPenDepth, c.yPenDepth) > -20){
						hasNotCollided = false;
					}
//					hasNotCollided = hasNotCollided && !c.hasCollided;
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
			Entity closest = c.get(0).collidingEntity;
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
					closest = c.get(i).collidingEntity;
				} else if(temp == smallestPenetration){
					if(c.get(i).yPenDepth > smallestYPen){
						smallestYPen = c.get(i).yPenDepth;
						closest = c.get(i).collidingEntity;
					} else if(c.get(i).xPenDepth > smallestXPen){
						smallestXPen = c.get(i).xPenDepth;
						closest = c.get(i).collidingEntity;
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
			mapObjects.add(currentRoom);
		}
	}
	
	
	private void populateMap(){
		int upgradesAmo = rand.nextInt(10) + 1;
		for(int i = 0; i < upgradesAmo; i++){
			Entity room = mapObjects.get(rand.nextInt(mapObjects.size()-i));
			int temp;
			while((temp = (int) (Math.min(room.getWidth(), room.getHeight()) * .5)) <= 1){
				room = mapObjects.get(rand.nextInt(mapObjects.size()-i));
			}
			int size = (rand.nextInt(10) + 1) * 5;
			// anything less than 20 fails
			if(size > temp) { size = temp; }
			int xPos = rand.nextInt(room.getWidth() - size) + room.getXPos();
			int yPos = rand.nextInt(room.getHeight() - size) + room.getYPos();
			mapObjects.add(new Upgrade(SpriteSheet.getBlock(size, size, Color.BLUEVIOLET), xPos, yPos));
		}
	}
	
	// Should we define a new class as wall???
	// Or is it ok to just define the standard methods neccesary in Entity and not make it abstract?
	private Entity createNewWall(int x, int y, int width, int height){
		Image img = SpriteSheet.getBlock(width, height, Color.BLACK);
		Entity e = new Entity(img, x, y, (int)img.getWidth(), (int)img.getHeight()){
			@Override
			public boolean isColliding(Collideable c) {
				throw new UnsupportedOperationException("Not yet Implemented");
			}
			@Override
			public Bounds getBounds() {
				throw new UnsupportedOperationException("Not yet Implemented");
			}
			@Override
			public void update(ArrayList<Entity> entities) {
				// Do Nothing
			}
		};
		e.setTag("Wall");
		return e;
	}
	
	public Entity createNewRoom(int minWidth, int maxWidth, double minHeightMultiplier, double maxHeightMultiplier){
		int width = rand.nextInt(maxWidth - minWidth + 1) + minWidth;
		int height = (int)(rand.nextDouble() * (maxHeightMultiplier - minHeightMultiplier) + minHeightMultiplier * width);
		Entity e = new Entity(SpriteSheet.getBlock(width, height, Color.AQUA), 0, 0, width, height){
			@Override
			public boolean isColliding(Collideable c) {
				throw new UnsupportedOperationException("Not yet Implemented");
			}
			@Override
			public Bounds getBounds() {
				throw new UnsupportedOperationException("Not yet Implemented");
			}
			@Override
			public void update(ArrayList<Entity> entities) {
				// Do Nothing
			}
		};
		e.setTag("Room");
		return e;
	}
	
	public Entity createNewPath(int minWidth, int minHeight, int maxWidth, int maxHeight){
		int width = rand.nextInt(maxWidth - minWidth + 1) + minWidth;
		int height = rand.nextInt(maxHeight - minHeight + 1) + minHeight;
		Entity e = new Entity(SpriteSheet.getBlock(width, height, Color.AQUAMARINE), 0, 0, width, height){
			@Override
			public boolean isColliding(Collideable c) {
				throw new UnsupportedOperationException("Not yet Implemented");
			}
			@Override
			public Bounds getBounds() {
				throw new UnsupportedOperationException("Not yet Implemented");
			}
			@Override
			public void update(ArrayList<Entity> entities) {
				// Do Nothing
			}
		};
		e.setTag("Path");
		return e;
	}
	
	public ArrayList<Entity> generatePathsBetween(Entity e1, Entity e2){
		ArrayList<Entity> paths = new ArrayList<>();
//		System.out.println("Making paths from Room: ");
//		System.out.println("\tX: " + e1.getXPos() + " Y: " + e1.getYPos() + " Width: " + e1.getWidth() + " Height: " + e1.getHeight());
//		System.out.println("To Room: ");
//		System.out.println("\tX: " + e2.getXPos() + " Y: " + e2.getYPos() + " Width: " + e2.getWidth() + " Height: " + e2.getHeight());
		
		int playerW = 30 + 5, playerH = 30 + 5;
		int width = rand.nextInt((int)(playerW*1.5/5)) * 5 + (int)(playerW*1.5);
		int height = rand.nextInt((int)(playerH*1.5/5)) * 5 + (int)(playerH*1.5);
//		System.out.println("Default Width: " + width + " Default Height: " + height);
		
		Entity currentPath = e1;
		Direction previousD = Direction.NULL;
		boolean xConnected = false;
		boolean yConnected = false;
		
		// By default I dont want to move in the X direction if we are already intersecting. This doesnt mean it 
		// should be considered intersecting, just that we dont want to move in that direction first.
		boolean xPath = (CollisionSystem.isIntersectingXAxis(e1, e2).hasCollided ? false :
			CollisionSystem.isIntersectingYAxis(e1, e2).hasCollided ? true : rand.nextBoolean());
		while(!xConnected || !yConnected){
			
			// Negate it get the direction towards the object
			int xDiff = -CollisionSystem.isIntersectingXAxis(currentPath, e2).collisionNormal.getX();
			int yDiff = -CollisionSystem.isIntersectingYAxis(currentPath, e2).collisionNormal.getY();
			Direction d = Direction.getDir(xDiff, yDiff);
			Entity path = null;
			if(xPath && !CollisionSystem.isFullyEncapsulating(currentPath.getXPos(),
					currentPath.getXPos() + currentPath.getWidth(),
					e2.getXPos(), e2.getXPos() + e2.getWidth())){
				
				int widthMax;
				if(d.getX() == 1){
					widthMax = e2.getXPos() + (yConnected ? 0 : e2.getWidth()) - (currentPath.getXPos() + currentPath.getWidth());
				} else {
					widthMax = currentPath.getXPos() - (e2.getXPos() + (yConnected ? e2.getWidth() : 0));
				} 
				int widthMin = Math.min(width, widthMax);
//				System.out.println(widthMin + " " + widthMax);
				
				path = createNewPath(widthMin, height, widthMax, height);
				int initialY;
				if(currentPath == e1){
					initialY = rand.nextInt((currentPath.getHeight() > height ? currentPath.getHeight() - height + 1 : 1)) + currentPath.getYPos();
				} else {
					initialY = currentPath.getYPos() + (previousD.getY() == 1 ? currentPath.getHeight() - height : 0);
				}
				int initialX = currentPath.getXPos() + (d.getX() == 1 ? currentPath.getWidth() : -path.getWidth());
				path.setXPos(initialX);
				path.setYPos(initialY);
				
				// Check Collision
				xConnected = CollisionSystem.isIntersectingAlongLine(path.getXPos(), path.getXPos() + path.getWidth(),
						e2.getXPos() + (d.getX() == 1 && !yConnected ? width : 0), e2.getXPos() + e2.getWidth() - (d.getX() == -1 && !yConnected ? width : 0));
				if(!yConnected && xConnected) { yConnected = CollisionSystem.isIntersectingYAxis(path, e2).hasCollided; }
			} else if(!CollisionSystem.isFullyEncapsulating(currentPath.getYPos(),
					currentPath.getYPos() + currentPath.getHeight(),
					e2.getYPos(), e2.getYPos() + e2.getHeight())){
				int heightMax;
				if(d.getY() == 1){
					heightMax = e2.getYPos() + (xConnected ? 0 : e2.getHeight()) - (currentPath.getYPos() + currentPath.getHeight());
				} else {
					heightMax = currentPath.getYPos() - (e2.getYPos() + (xConnected ? e2.getHeight() : 0));
				}
				int heightMin = Math.min(height, heightMax);
//				System.out.println(heightMin + " " + heightMax);
				
				path = createNewPath(width, heightMin, width, heightMax);
				int initialX;
				if(currentPath == e1){
					initialX = rand.nextInt((currentPath.getWidth() > width ? currentPath.getWidth() - width + 1 : 1)) + currentPath.getXPos();
				} else {
					initialX = currentPath.getXPos() + (previousD.getX() == 1 ? currentPath.getWidth() - width : 0);
				}
				int initialY = currentPath.getYPos() + (d.getY() == 1 ? currentPath.getHeight() : -path.getHeight());
				path.setXPos(initialX);
				path.setYPos(initialY);
				
				// Check Collisions
				yConnected = CollisionSystem.isIntersectingAlongLine(path.getYPos(), path.getYPos() + path.getHeight(),
						e2.getYPos() + (d.getY() == 1 && !xConnected ? height : 0), e2.getYPos() + e2.getHeight() - (d.getY() == -1 && !xConnected ? height : 0));
				if(!xConnected && yConnected) { xConnected = CollisionSystem.isIntersectingXAxis(path, e2).hasCollided; }
			} 
			if(path != null){
//				System.out.println("Created " + (xPath ? "x" : "y") + "Path X: " + path.getXPos() + " Y: " + path.getYPos()
//				+ " Width: " + path.getWidth() + " Height: " + path.getHeight());
//				System.out.println("X: " + xConnected + " Y: " + yConnected + "\n");
	
				paths.add(path);
				currentPath = path;
			}
			xPath = !xPath;
			previousD = d;
		}
		return paths;
	}
	
	public ArrayList<Entity> getMapObjects(){
		return mapObjects;
	}

}