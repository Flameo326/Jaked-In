package Controller;

import java.util.ArrayList;
import java.util.Collections;

import Interfaces.Publishable;
import Interfaces.Subscribable;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameController extends AnimationTimer implements Publishable<PlayableCharacter>{
	
	// This boolean will indicate wether or not we are in story mode right now
	// controls are different in story or arena
	private static long timer;
	private static boolean StoryMode;

	private ArrayList<Entity> entities;
	private ArrayList<PlayableCharacter> players;
	private ArrayList<Subscribable<PlayableCharacter>> subscribers;
	private ArrayList<Canvas> windows;
	private Entity focusedEntity;
	
	private Stage error;
	private Label playPos;
	
	public GameController(Canvas myCanvas, boolean storyMode) {
		GameController.StoryMode = storyMode;
		windows = new ArrayList<>();
		entities = new ArrayList<>();
		players = new ArrayList<>();
		subscribers = new ArrayList<>();
		
		addWindow(myCanvas);
		
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
		timer = now;
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			// All Entities are updated 
			e.update(entities);
		}
		if(focusedEntity != null){
			playPos.setText("Player Center X: " + focusedEntity.getXPos() + " Y: " + focusedEntity.getYPos());
		}
		
		// Handles the graphical Rendering 
		for(Canvas c : windows){
			updateImage(c);
		}
		notifySubscribers();
	}

	public void updateImage(Canvas c){
		GraphicsContext g = c.getGraphicsContext2D();
		g.clearRect(0, 0, c.getWidth(), c.getHeight());
		int offsetX = 0, offsetY = 0;
		if(focusedEntity != null){
			offsetX = focusedEntity.getDisplayableXPos();
			offsetY = focusedEntity.getDisplayableYPos();
		}
		for(Entity e : entities){
			if(InputHandler.keyInputContains(KeyCode.F) && e.getTag().equals("Wall")) { continue; }
			g.drawImage(e.getImage(), e.getDisplayableXPos() - offsetX + (c.getWidth()/2),
					e.getDisplayableYPos() - offsetY + (c.getHeight()/2), e.getWidth(), e.getHeight());
		}	
	}

	public void addEntity(Entity... items) {
		for(Entity i : items){
			if(!entities.contains(i)){
				entities.add(i);
			}
		}
		Collections.sort(entities);
	}
	
	public void removeEntity(Entity... items){
		for(Entity i : items){
			entities.remove(i);
		}
	}
	
	public void addPlayer(PlayableCharacter p){
		if(!players.contains(p)){
			players.add(p);
		}
	}
	
	public void removePlayer(PlayableCharacter p){
		players.remove(p);
	}
	
	public void addWindow(Canvas c){
		if(!windows.contains(c)){
			windows.add(c);
		}
	}
	
	public void setFocus(Entity focusedEntity){
		this.focusedEntity = focusedEntity;
	}
	
	public static void setStoryMode(boolean b){
		StoryMode = b;
	}
	
	public static boolean getStoryMode(){
		return StoryMode;
	}
	
	public static long getTimer(){
		return timer;
	}

	@Override
	public void attach(Subscribable<PlayableCharacter> sub) {
		if(!subscribers.contains(sub)){
			subscribers.add(sub);
		}
	}

	@Override
	public void detach(Subscribable<PlayableCharacter> sub) {
		subscribers.remove(sub);
	}

	@Override
	public void notifySubscribers() {
		for(PlayableCharacter p : players.toArray(new PlayableCharacter[0])){
			for(Subscribable<PlayableCharacter> s : subscribers){
				s.update(p);
			}
		}
	}
	
	/*
	 * GC takes in a canvas to draw on and a boolean representing its mode
	 * You can add Entities to it which will be updated and displayed to the screen
	 * You can add subsribers which will be alerted of Win or Loss conditions
	 * You can add players which are the Win and Loss Conditions
	 * - The GC will update everything and display it, 
	 * - then it will notify its subscribers of anything that has changed
	 */
}
