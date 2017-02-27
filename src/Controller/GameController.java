package Controller;

import java.util.ArrayList;
import java.util.Collections;

import Models.Entity;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameController extends AnimationTimer {

	private ArrayList<Entity> entities;
	private Canvas myCanvas;
	private GraphicsContext g;
	private Entity focusedEntity;
	
	
	public GameController(Canvas myCanvas) {
		entities = new ArrayList<>();
		this.myCanvas = myCanvas;
		g = myCanvas.getGraphicsContext2D();
	}
	
	@Override
	public void handle(long now) {
		for(Entity e : entities.toArray(new Entity[0])){
			// All Entities are updated even if they don't move
			e.update(entities);
			// Print out Entity Information
			//System.out.println(e.getClass().getSimpleName() + " X: " + e.getXPos() + " Y: " + e.getYPos());
		}
		// Handles the graphical Rendering 
		updateImage();
	}

	private void updateImage(){
		g.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
		Collections.sort(entities);
		int offsetX = 0, offsetY = 0;
		if(focusedEntity != null){
			offsetX = focusedEntity.getXPos();
			offsetY = focusedEntity.getYPos();
		}
		for(Entity e : entities){
			g.drawImage(e.getImage(), e.getXPos() - offsetX + (myCanvas.getWidth()/2), e.getYPos() - offsetY + (myCanvas.getHeight()/2));
		}	
	}

	public void add(Entity... items) {
		for(Entity i : items){
			if(!entities.contains(i)){
				entities.add(i);
			}
		}
	}
	
	public void setFocus(Entity focusedEntity){
		this.focusedEntity = focusedEntity;
	}
	
}
