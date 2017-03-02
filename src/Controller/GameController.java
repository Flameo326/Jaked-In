package Controller;

import java.util.ArrayList;
import java.util.Collections;

import Models.Collision;
import Models.Entity;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameController extends AnimationTimer {

	private ArrayList<Entity> entities;
	private Canvas myCanvas;
	private GraphicsContext g;
	private Entity focusedEntity;
	private Stage error;
	private Label playPos;
	
	public GameController(Canvas myCanvas) {
		this.myCanvas = myCanvas;
		g = myCanvas.getGraphicsContext2D();
		entities = new ArrayList<>();
		
		playPos = new Label();
		
		StackPane root = new StackPane(playPos);
		Scene scene = new Scene(root, 300, 50);
		
		error = new Stage();
		error.setScene(scene);
		error.show();
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
//			System.out.println(e.getClass().getSimpleName() + " X: " + e.getXPos() + " Y: " + e.getYPos());
		}
		playPos.setText("Player Center X: " + focusedEntity.getXPos() + " Y: " + focusedEntity.getYPos());
		// Handles the graphical Rendering 
		updateImage();
	}

	private void updateImage(){
		g.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
		int offsetX = 0, offsetY = 0;
		if(focusedEntity != null){
			offsetX = focusedEntity.getDisplayableXPos();
			offsetY = focusedEntity.getDisplayableYPos();
		}
		for(Entity e : entities){
			g.drawImage(e.getImage(), e.getDisplayableXPos() - offsetX + (myCanvas.getWidth()/2),
					e.getDisplayableYPos() - offsetY + (myCanvas.getHeight()/2), e.getWidth(), e.getHeight());
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
