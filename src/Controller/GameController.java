package Controller;

import java.util.ArrayList;
import java.util.Collections;

import Models.Collision;
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
		this.myCanvas = myCanvas;
		g = myCanvas.getGraphicsContext2D();
		entities = new ArrayList<>();
	}
	
	// This entire thing will be our "Run" method. It gets called constantly and updates accordingly.
	// We need to figure out a way for this class to communicate between The ArenaController and everything the player does.
	// For example, If they create a new Projectile, it needs to be added to the colliders and things that must be
	// -- graphicaly updated every Frame
	@Override
	public void handle(long now) {
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			// All Entities are updated even if they don't move
			e.update(entities);
			// Test for collisions
			for(int y = 0; y < entities.size(); y++){
				if(y == i) { continue; }
				Entity collided = entities.get(y);
				Collision c = CollisionSystem.getCollision(e, collided);
				if(c.hasCollided){
					e.hasCollided(c);
					collided.hasCollided(c);
				}
			}
			// Print out Entity Information
			//System.out.println(e.getClass().getSimpleName() + " X: " + e.getXPos() + " Y: " + e.getYPos());
		}
		// Handles the graphical Rendering 
		updateImage();
	}

	private void updateImage(){
		g.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
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
		Collections.sort(entities);
	}
	
	public void setFocus(Entity focusedEntity){
		this.focusedEntity = focusedEntity;
	}
	
}
