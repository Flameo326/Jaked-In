package Models.Map;

import java.util.ArrayList;
import java.util.Random;

import Interfaces.Collideable;
import Models.Bounds;
import Models.Entity;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Map {
	
	private ArrayList<Entity> mapObjects;
	private Random rand;
	private int mapWidth, mapHeight;
	
	public Map(int width, int height){
		mapWidth = width;
		mapHeight = height;
		mapObjects = new ArrayList<>();
		rand = new Random();
		generateMap();
	}
	
	// Map is generated Randomly and stored
	private void generateMap(){
		int wallAmo = rand.nextInt(11) + 5;
		for(int i = 0; i < wallAmo; i++){
			// Get Stage width and height
			int xPos = rand.nextInt(mapWidth);
			int yPos = rand.nextInt(mapHeight);
			// Maybe standardize Wall Width
			int max;
			int widthRange = ((max = mapWidth - xPos) > 20 ? 20 : max > 0 ? max : 1);
			int heightRange = ((max = mapHeight - yPos) > 20 ? 20 : max > 0 ? max : 1);
			int width = (rand.nextInt(widthRange) + 1) * 5;
			int height = (rand.nextInt(heightRange) + 1) * 5;
			mapObjects.add(createNewWall(xPos, yPos, width, height));
		}
	}
	
	private void populateMap(){
		
	}
	
	private Entity createNewWall(int x, int y, int width, int height){
		WritableImage img = new WritableImage(width, height);
		PixelWriter pw = img.getPixelWriter();
		int borderWidth = 3;
		for(int i = 0; i < img.getHeight(); i++){
			for(int e = 0; e < img.getWidth(); e++){
				if(i < borderWidth || e < borderWidth || i > img.getHeight()-borderWidth ||
						e > img.getWidth()-borderWidth){
					pw.setColor(e, i, Color.BLACK);
				} else {
					pw.setColor(e, i, Color.color(200.0/255, 200.0/255, 200.0/255));
				}
			}
		}
		Entity e = new Entity(img, x, y){
			@Override
			public boolean isColliding(Collideable c) {
				throw new UnsupportedOperationException("Not yet Implemented");
			}
			@Override
			public Bounds getBounds() {
				throw new UnsupportedOperationException("Not yet Implemented");
			}
			@Override
			public void update() {
				// Do Nothing
			}
		};
		return e;
	}
	
	public ArrayList<Entity> getMapObjects(){
		return mapObjects;
	}

}
