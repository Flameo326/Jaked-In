package Models.Map;

import java.util.ArrayList;
import java.util.Random;

import Controller.CollisionSystem;
import Enums.Direction;
import Interfaces.Collideable;
import Models.Bounds;
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
		generateMap(70, 70, 210, 210, 2);
		//populateMap();
	}
	
	// We should try to not generate maps that are less than the player's width and height
	public void generateMap(int minWidth, int minHeight, int maxWidth, int maxHeight, int roomAmo){
		mapObjects.clear();
		
		ArrayList<Entity> room = new ArrayList<>();
		room.add(createNewRoom(minWidth, minHeight, maxWidth, maxHeight));
		
		//
		for(int i = 1; i < roomAmo; i++){
			Entity previousRoom = room.get(i-1);
			Entity currentRoom = createNewRoom(minWidth, minHeight, maxWidth, maxHeight);
			
			int degree = rand.nextInt(360);
			//int maxDist = Math.max(previousRoom.getCenterXPos()-currentRoom.getCenterXPos(), previousRoom.getCenterYPos()-currentRoom.getCenterYPos());
			//int maxDist = Math.max(maxWidth, maxHeight);
			int maxDist = Math.max(currentRoom.getWidth(), currentRoom.getHeight());
			int radius = (int) ((rand.nextDouble() + rand.nextDouble() + rand.nextDouble() + 1) * maxDist);
			// in radians
			int initialDegree = degree;
			int x = (int)(Math.cos(degree * Math.PI / 180) * radius);
			int y = (int)(Math.sin(degree * Math.PI / 180) * radius);
			currentRoom.setXPos(x);
			currentRoom.setYPos(y);
			// Add the colision detection method
//			while(false){
//				if(++degree == initialDegree){
//					// Can't place room 
//				}
//				x = (int)(Math.cos(degree * Math.PI / 180) * radius);
//				y = (int)(Math.sin(degree * Math.PI / 180) * radius);
//				room[i].setXPos(x);
//				room[i].setYPos(y);
//			}
			
			// Will  not fully work because we are placing the corner there,
			// We should place the center there.
			room.add(currentRoom);
			mapObjects.addAll(generatePathsBetween(previousRoom, currentRoom));
		}
		mapObjects.addAll(room);
	}
	
	
	private void populateMap(){
		int upgradesAmo = rand.nextInt(5);
		for(int i = 0; i < upgradesAmo; i++){
			int xPos = rand.nextInt(mapWidth);
			int yPos = rand.nextInt(mapHeight);
			int max;
			int widthRange = ((max = mapWidth - xPos) > 10 ? 10 : max > 0 ? max : 1);
			int heightRange = ((max = mapHeight - yPos) > 10 ? 10 : max > 0 ? max : 1);
			int width = (rand.nextInt(widthRange) + 1) * 5;
			int height = (rand.nextInt(heightRange) + 1) * 5;
			mapObjects.add(new Upgrade(SpriteSheet.getBorderedBlock(width, height, Color.MEDIUMTURQUOISE), xPos, yPos));
		}
	}
	
	// Should we define a new class as wall???
	// Or is it ok to just define the standard methods neccesary in Entity and not make it abstract?
	private Entity createNewWall(int x, int y, int width, int height){
		Image img = SpriteSheet.getBorderedBlock(width, height, Color.color(200.0/255, 200.0/255, 200.0/255));
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
	
	public Entity createNewRoom(int minWidth, int minHeight, int maxWidth, int maxHeight){
		int width = rand.nextInt(maxWidth - minWidth + 1) + minWidth;
		int height = rand.nextInt(maxHeight - minHeight + 1) + minHeight;
		
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
	
	public ArrayList<Entity> generatePathsBetween(Entity e1, Entity e2){
		ArrayList<Entity> paths = new ArrayList<>();
		System.out.println("Making paths from Room: ");
		System.out.println("\tX: " + e1.getXPos() + " Y: " + e1.getYPos() + " Width: " + e1.getWidth() + " Height: " + e1.getHeight());
		System.out.println("To Room: ");
		System.out.println("\tX: " + e2.getXPos() + " Y: " + e2.getYPos() + " Width: " + e2.getWidth() + " Height: " + e2.getHeight());
		
		// Width and Height of anti Axis path is 5 + playerSize to 2 * playerSize
		// Incrementing by 5
		int playerW = 30 + 5, playerH = 30 + 5;
		// Just in case rooms are smaller than player... which they shouldt be
		int width = Math.min(Math.min(e1.getWidth(), e2.getWidth()), rand.nextInt((int)(playerW*1.5/5)) * 5 + (int)(playerW*1.5));
		int height = Math.min(Math.min(e1.getHeight(), e2.getHeight()), rand.nextInt((int)(playerH*1.5/5)) * 5 + (int)(playerH*1.5));
		System.out.println("Default Width: " + width + " Default Height: " + height);
		
		boolean xConnected = false;
		boolean yConnected = false;
		// choose the type of path to start with
		boolean xPath = rand.nextBoolean();
		
		// Current Problems:
		// Paths or rooms may full encapsulate e2 which means that a certain path may not be used
		
		Entity currentPath = e1;
		Direction previousD = null;
		while(!xConnected || !yConnected){
			// Negate it get the direction towards the object
			int xDiff = -CollisionSystem.isIntersectingXAxis(currentPath, e2).collisionNormal.getX();
			int yDiff = -CollisionSystem.isIntersectingYAxis(currentPath, e2).collisionNormal.getY();
			Direction d = Direction.getDir(xDiff, yDiff);
			Entity path = null;
			if(xPath && !CollisionSystem.isFullyEncapsulating(currentPath.getXPos(),
							currentPath.getXPos() + currentPath.getWidth(),
							e2.getXPos(), e2.getXPos() + e2.getWidth())){
				// Initial values for where the path will be located
				int initialY;
				int initialX;
				
				// If we are at the start then choose a random position on the room
				if(currentPath == e1){
					initialY = rand.nextInt(Math.max(1, currentPath.getHeight() - height)) + currentPath.getYPos();
				} 
				// Otherwise choose the farthest along edge towards e2
				else {
					initialY = currentPath.getYPos();
					if(previousD.getY() == 1){
						initialY += currentPath.getHeight() - height;
					} 
				}
				
				// determine maximum and minimum width of path
				int widthMin;
				int widthMax;
				// Can I test to see if current Room full encapsulates e2 by the X axis
				if(d.getX() == 1){
					widthMax = e2.getXPos() + (yConnected ? 0 : e2.getWidth()) - (currentPath.getXPos() + currentPath.getWidth());
					widthMin = Math.min(width, widthMax);
					initialX = currentPath.getXPos() + currentPath.getWidth();
				} else {
					widthMax = currentPath.getXPos() - (e2.getXPos() + (yConnected ? e2.getWidth() : 0));
					widthMin = Math.min(width, widthMax);
					initialX = currentPath.getXPos();
				} 
				System.out.println(widthMin + " " + widthMax);
				
				// Create the room and set it's values
				path = createNewRoom(widthMin, height, widthMax, height);
				path.setXPos(initialX - (d.getX() == 1 ? 0 : path.getWidth()));
				path.setYPos(initialY);
				
				// Check Collision
				xConnected = CollisionSystem.isIntersectingAlongLine(path.getXPos(), path.getXPos() + path.getWidth(),
						e2.getXPos() + (d.getX() == 1 && !yConnected ? width : 0), e2.getXPos() + e2.getWidth() - (d.getX() == -1 && !yConnected ? width : 0));
				if(!yConnected && xConnected) { yConnected = CollisionSystem.isIntersectingYAxis(path, e2).hasCollided; }
			} else if(!CollisionSystem.isFullyEncapsulating(currentPath.getYPos(),
					currentPath.getYPos() + currentPath.getHeight(),
					e2.getYPos(), e2.getYPos() + e2.getHeight())){
				// Initial starting position of Path
				int initialX;
				int initialY;
				if(currentPath == e1){
					initialX = rand.nextInt(Math.max(currentPath.getWidth() - width, 1)) + currentPath.getXPos();
				} else {
					initialX = currentPath.getXPos();
					if(previousD.getX() == 1){
						initialX += currentPath.getWidth() - width;
					}
				}
				
				// Determine range of height
				int heightMin;
				int heightMax;
				if(d.getY() == 1){
					heightMax = e2.getYPos() + (xConnected ? 0 : e2.getHeight()) - (currentPath.getYPos() + currentPath.getHeight());
					heightMin = Math.min(height, heightMax);
					initialY = currentPath.getYPos() + currentPath.getHeight();
				} else {
					heightMax = currentPath.getYPos() - (e2.getYPos() + (xConnected ? e2.getHeight() : 0));
					heightMin = Math.min(height, heightMax);
					initialY = currentPath.getYPos();
				} 
				System.out.println(heightMin + " " + heightMax);
				
				// Create the room and set it's values
				path = createNewRoom(width, heightMin, width, heightMax);
				path.setYPos(initialY - (d.getY() == 1 ? 0 : path.getHeight()));
				path.setXPos(initialX);
				
				// Check Collisions
				yConnected = CollisionSystem.isIntersectingAlongLine(path.getYPos(), path.getYPos() + path.getHeight(),
						e2.getYPos() + (d.getY() == 1 && !xConnected ? height : 0), e2.getYPos() + e2.getHeight() - (d.getY() == -1 && !xConnected ? height : 0));
				if(!xConnected && yConnected) { xConnected = CollisionSystem.isIntersectingXAxis(path, e2).hasCollided; }
			} 
			if(path != null){
				System.out.println("Created " + (xPath ? "x" : "y") + "Path X: " + path.getXPos() + " Y: " + path.getYPos()
				+ " Width: " + path.getWidth() + " Height: " + path.getHeight());
				System.out.println("X: " + xConnected + " Y: " + yConnected + "\n");
				
				path.setImage(SpriteSheet.getBlock(path.getWidth(), path.getHeight(), Color.AQUAMARINE));
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
