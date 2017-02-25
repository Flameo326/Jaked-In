package Models.Map;

import java.util.ArrayList;
import java.util.Random;

import Controller.CollisionSystem;
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
	private void generateMap(int minWidth, int minHeight, int maxWidth, int maxHeight, int roomAmo){
		
	
		
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
		int width = Math.max(e1.getWidth(), rand.nextInt(playerW/5) * 5 + playerW);
		int height = Math.max(e1.getHeight(), rand.nextInt(playerH/5) * 5 + playerH);
		
		Entity currentRoom = e1;
		boolean xPath = rand.nextBoolean();
		boolean xConnected = false, yConnected = false;
		while(!xConnected || !yConnected){
			// choose the x or y direction of path
			if(xPath && !xConnected){
				// The center Y value of path that will stretch along x Axis
				int centerY;
				//if(currentRoom == e1){
					centerY = rand.nextInt(Math.max(1, currentRoom.getHeight() - height)) + currentRoom.getYPos() + height/2;
//				} else {
//					if(e2.getCenterXPos() > currentRoom.getCenterXPos()){
//						centerY = currentRoom.getXPos() + currentRoom.getWidth() - 
//					} else {
//						centerY = 
//					}
//				}
				
				// The initial center X position of the room which will be generated
				int initialX;
				// Width Range
				int widthMin;
				int widthMax;
				if(e2.getCenterXPos() > currentRoom.getCenterXPos()){
					widthMax = Math.max(1, e2.getXPos() - currentRoom.getXPos() + currentRoom.getWidth());
					widthMin = Math.min(playerW, widthMax);
					initialX = currentRoom.getXPos() + currentRoom.getWidth();
				} else {
					widthMax = Math.max(1, currentRoom.getXPos() - e2.getXPos() + e2.getWidth());
					widthMin = Math.min(playerW, widthMax);
					initialX = currentRoom.getXPos();
				}
				
				// Create the room and set it's values
				Entity e = createNewRoom(widthMin, height, widthMax, height);
				e.setImage(SpriteSheet.getBlock(e.getWidth(), e.getHeight()));
				if(e2.getCenterXPos() > currentRoom.getCenterXPos()){
					e.setXPos(initialX);
				} else {
					e.setXPos(initialX-e.getWidth());
				}
				e.setYPos(centerY-e.getHeight()/2);
				paths.add(e);
				currentRoom = e;
				xConnected = CollisionSystem.AABBvsAABB(e, e2).hasCollided;
				// Maybe find a way of storing direction like Adam made.
//				if(e2.getCenterXPos() > currentRoom.getCenterXPos()){
//					if(e.getXPos() + e.getWidth() >= e2.getXPos()){
//						xConnected = true;
//					}
//				} else {
//					if(e.getXPos()>= e2.getXPos() + e2.getWidth()){
//						xConnected = true;
//					}
//				}
				
			} else if(!yConnected){
				// The center Y value of path that will stretch along x Axis
				int centerX = rand.nextInt(Math.max(currentRoom.getWidth() - width, 1)) + currentRoom.getXPos() + width/2;
				// The initial center X position of the room which will be generated
				int initialY;
				// Height Range
				int heightMin;
				int heightMax;
				// Aka it's below
				if(e2.getCenterYPos() > currentRoom.getCenterYPos()){
					heightMax = Math.max(1, e2.getYPos() - currentRoom.getYPos() + currentRoom.getHeight());
					heightMin = Math.min(playerH, heightMax);
					initialY = currentRoom.getYPos() + currentRoom.getHeight();
				} else {
					heightMax = Math.max(1, currentRoom.getYPos() - e2.getYPos() + e2.getHeight());
					heightMin = Math.min(playerH, heightMax);
					initialY = currentRoom.getYPos();
				}
				
				// Create the room and set it's values
				Entity e = createNewRoom(width, heightMin, width, heightMax);
				e.setImage(SpriteSheet.getBlock(e.getWidth(), e.getHeight()));
				if(e2.getCenterYPos() > currentRoom.getCenterYPos()){
					e.setYPos(initialY);
				} else {
					e.setYPos(initialY-e.getHeight());
				}
				e.setXPos(centerX-e.getWidth()/2);
				paths.add(e);
				currentRoom = e;
				yConnected = CollisionSystem.AABBvsAABB(e, e2).hasCollided;
//				if(e2.getCenterYPos() > currentRoom.getCenterYPos()){
//					if(e.getYPos() + e.getHeight() >= e2.getYPos()){
//						yConnected = true;
//					} 
//				} else {
//					if(e.getYPos() >= e2.getYPos() + e2.getHeight()){
//						yConnected = true;
//					} 
//				}
			} 
			System.out.println("Created " + (xPath ? "x" : "y") + "Path X: " + currentRoom.getXPos() + " Y: " + currentRoom.getYPos()
					+ " Width: " + currentRoom.getWidth() + " Height: " + currentRoom.getHeight());
			xPath = !xPath;
		}
		
		
		// So I choose y or x value first
		// I choose the top/Bottom or Left/ Right value of e1
		// I make a new "room" with a size variant of the corresponding value
		
		// I keep track of how far it needs to go by
		return paths;
	}
	
	public ArrayList<Entity> getMapObjects(){
		return mapObjects;
	}

}
