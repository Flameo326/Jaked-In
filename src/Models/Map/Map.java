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
		generateMap(20, 20, 100, 100, 2);
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
			// Sometime parths work and other times they don't
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
		
		Entity e = new Entity(SpriteSheet.getBorderedBlock(width, height, Color.AQUA), 0, 0, width, height){
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
	
	// What if I get the X and Y value the path has to traverse
	
	// Smallest character dimensions are 20 x 20
	// Introduce clipping so its a value of 5
	public ArrayList<Entity> generatePathsBetween(Entity e1, Entity e2){
		System.out.println("Making paths from Room: ");
		System.out.println("\tX: " + e1.getXPos() + " Y: " + e1.getYPos() + " Width: " + e1.getWidth() + " Height: " + e1.getHeight());
		System.out.println("To Room: ");
		System.out.println("\tX: " + e2.getXPos() + " Y: " + e2.getYPos() + " Width: " + e2.getWidth() + " Height: " + e2.getHeight());
		ArrayList<Entity> paths = new ArrayList<>();
		// Width and Height of anti Axis path is 5 + playerSize to 2 * playerSize
		// Incrementing by 5
		int playerW = 30 + 5, playerH = 30 + 5;
		int width = Math.min(e1.getWidth(), rand.nextInt(playerW/5) * 5 + playerW);
		int height = Math.min(e1.getHeight(), rand.nextInt(playerH/5) * 5 + playerH);
		System.out.println("Default Width: " + width + " Default Height: " + height);
		
		Entity currentRoom = e1;
		Direction previousD = null;
		// false??
		boolean xConnected = false;
		boolean yConnected = false;
		boolean xPath = (xConnected || yConnected ? (xConnected ? false : true): rand.nextBoolean());
		
		int i = 0;
		while(!xConnected || !yConnected){
			int xDiff = e2.getCenterXPos() > currentRoom.getCenterXPos() ? 1 : -1;
			int yDiff = e2.getCenterYPos() > currentRoom.getCenterYPos() ? 1 : -1;
			Direction d = Direction.getDir(xDiff, yDiff);
			if(xPath){
				// The center Y value of path that will stretch along x Axis
				int centerY;
				
				// If we are at the start then choose a random position
				// Otherwise choose the farthest along edge towards e2
				if(currentRoom == e1){
					centerY = rand.nextInt(Math.max(1, currentRoom.getHeight() - height)) + currentRoom.getYPos();
				} else {
					centerY = currentRoom.getYPos();
					if(previousD.getY() == 1){
						centerY += currentRoom.getHeight() - height;
					} 
//					else if(currentRoom.getHeight() < height){
//						centerY += height - currentRoom.getHeight();
//					}
				}
				
				// The initial center X position of the room which will be generated
				int initialX;
				// Width Range
				int widthMin;
				int widthMax;
				// change so they can extend further...
				if(d.getX() == 1){
					widthMax = e2.getXPos() + (yConnected ? 0 : e2.getWidth()) - (currentRoom.getXPos() + currentRoom.getWidth());
					widthMin = Math.min(width, widthMax);
					initialX = currentRoom.getXPos() + currentRoom.getWidth();
				} else {
					widthMax = currentRoom.getXPos() - (e2.getXPos() + (yConnected ? e2.getWidth() : 0));
					widthMin = Math.min(width, widthMax);
					initialX = currentRoom.getXPos();
				}
				
				// Create the room and set it's values
				System.out.println(height + " " + widthMin + " " + widthMax);
				Entity e = createNewRoom(widthMin, height, widthMax, height);
				e.setImage(SpriteSheet.getBlock(e.getWidth(), e.getHeight()));
				e.setXPos(initialX - (d.getX() == 1 ? 0 : e.getWidth()));
				e.setYPos(centerY);
				paths.add(e);
				currentRoom = e;
				if(!yConnected) { yConnected = CollisionSystem.isIntersectingYAxis(e, e2); }
				xConnected = CollisionSystem.isIntersectingAlongLine(e.getXPos(), e.getXPos() + e.getWidth(),
						e2.getXPos() + (d.getX() == 1 && !yConnected ? width : 0), e2.getXPos() + e2.getWidth() - (d.getX() == -1 && !yConnected ? width : 0));
			} else {
				// The center Y value of path that will stretch along x Axis
				int centerX;
				if(currentRoom == e1){
					centerX = rand.nextInt(Math.max(currentRoom.getWidth() - width, 1)) + currentRoom.getXPos();
				} else {
					//We want to ensure that our path is always connected exactl which means it must be at one end of the
					// previous path. By default it needs to be at xPos
					centerX = currentRoom.getXPos();
					// If we are going in the right direction then we should make it on the right side by adding width
					if(previousD.getX() == 1){
						// we add the width or previousRoom and then subtract the width of this path
						// This also helps if our previous path was forced at a smaller width from the right side
						centerX += currentRoom.getWidth() - width;
					}
//					else if(currentRoom.getWidth() < width ){
//						// the alternative is that our path was forced at a smaller width from the left side
//						// in that case we must subtract the opposite value of the previous or just swap the values.
//						centerX += width - currentRoom.getWidth();
//					}
				}
				// The initial center X position of the room which will be generated
				int initialY;
				// Height Range
				int heightMin;
				int heightMax;
				// Aka it's below
				if(d.getY() == 1){
					// If x is connected then we only want to meet with the object which would be the minimum point
					// otherwise we can go along the entire height to try and get connected
					heightMax = e2.getYPos() + (xConnected ? 0 : e2.getHeight()) - (currentRoom.getYPos() + currentRoom.getHeight());
					heightMin = Math.min(height, heightMax);
					initialY = currentRoom.getYPos() + currentRoom.getHeight();
				} else {
					heightMax = currentRoom.getYPos() - (e2.getYPos() + (xConnected ? e2.getHeight() : 0));
					heightMin = Math.min(height, heightMax);
					initialY = currentRoom.getYPos();
				}
				
				// Create the room and set it's values
				System.out.println(width + " " + heightMin + " " + heightMax);
				Entity e = createNewRoom(width, heightMin, width, heightMax);
				e.setImage(SpriteSheet.getBlock(e.getWidth(), e.getHeight()));
				e.setYPos(initialY - (d.getY() == 1 ? 0 : e.getHeight()));
				e.setXPos(centerX);
				paths.add(e);
				currentRoom = e;
				if(!xConnected) { xConnected = CollisionSystem.isIntersectingXAxis(e, e2); }
				yConnected = CollisionSystem.isIntersectingAlongLine(e.getYPos(), e.getYPos() + e.getHeight(),
						e2.getYPos() + (d.getY() == 1 && !xConnected ? height : 0), e2.getYPos() + e2.getHeight() - (d.getY() == -1 && !xConnected ? height : 0));
			} 
			System.out.println("Created " + (xPath ? "x" : "y") + "Path X: " + currentRoom.getXPos() + " Y: " + currentRoom.getYPos()
					+ " Width: " + currentRoom.getWidth() + " Height: " + currentRoom.getHeight());
			System.out.println("X: " + xConnected + " Y: " + yConnected);
			System.out.println("xPath is now " + xPath);
			xPath = !xPath;
			previousD = d;
			if(++i > 500){ break; }
		}
		return paths;
	}
	
	public ArrayList<Entity> getMapObjects(){
		return mapObjects;
	}

}
