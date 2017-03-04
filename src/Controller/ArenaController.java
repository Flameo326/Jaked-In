package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import Interfaces.Subscribable;
import Models.Entity;
import Models.Map.ArenaMap;
import Models.Map.Map;
import Models.Players.ComputerPlayer;
import Models.Players.HumanPlayer;
import Models.Players.PlayableCharacter;
import Models.Upgrades.MedPack;
import Models.Upgrades.SpeedBoost;
import Models.Upgrades.Upgrade;
import SpriteSheet.SpriteSheet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class ArenaController implements Initializable, Subscribable<PlayableCharacter> {
	
	@FXML
	private Canvas myCanvas;
	private GameController gc;
	private int width, height;
	
	private ArenaMap arenaMap;
	private ArrayList<PlayableCharacter> players;
	private Random rand;
	private long upgradeTime;
	
	public void addPlayer(PlayableCharacter p){
		players.add(p);
		gc.addEntity(p.getDisplayableEntities());
		gc.addPlayer(p);
	}
	
	public void start(){
		width = 600;
		height = 500;
		// Position Players
		for(int i = 0; i < players.size(); i++){
			Entity p = players.get(i);
			switch(players.size()){
			case 2:
				p.setYPos(0);
				p.setXPos(i == 0 ? -width/4 : width/4);
				break;
			case 3:
				if(i > 1){
					p.setYPos(height/4);
					p.setXPos(0);
				} else {
					p.setYPos(-height/4);
					p.setXPos(i == 0 ? -width/4 : width/4);
				}
				break;
			case 4:
				if(i > 1){
					p.setYPos(height/4);
					p.setXPos(i == 2 ? -width/4 : width/4);
				} else {
					p.setYPos(-height/4);
					p.setXPos(i == 0 ? -width/4 : width/4);
				}
				break;
			default:
				System.out.println("The player size is " + players.size());
			}
		}
		
		// Generate Map
		arenaMap = new ArenaMap(width, height, players);
		gc.addEntity(arenaMap.getMapObjects().toArray(new Entity[0]));
		
		// Start
		gc.start();
	}
	
	public void stop(){
		gc.stop();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		players = new ArrayList<>();
		rand = new Random();
		
		gc = new GameController(myCanvas, false);
		gc.attach(this);
		
		// Resizes the Canvas to it's Stage Width and Height...
		myCanvas.sceneProperty().addListener(new ChangeListener<Scene>(){
		
			// This is the actual method when ^ this action happens
			@Override
			public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
				if(newValue != null){
					newValue.windowProperty().addListener(new ChangeListener<Window>(){
						@Override
						
						// This is the actual method when ^ this action happens
						public void changed(ObservableValue<? extends Window> observable, Window oldValue,
								Window newValue) {
							if(newValue != null){
								myCanvas.widthProperty().bind(newValue.widthProperty());
								myCanvas.heightProperty().bind(newValue.heightProperty());
							}
						}
					});
				}
			}
		});
		
		// Neccesary
		myCanvas.setFocusTraversable(true);
		// KeyEvent to record input while playing
		myCanvas.setOnKeyPressed((e) -> InputHandler.keyPress(e));
		myCanvas.setOnKeyReleased((e) -> InputHandler.keyRelease(e));
	}

	@Override
	public void update(PlayableCharacter value) {
		// from 3o seconds to 3 minuutes
		if(GameController.timer >= upgradeTime){
			// Get current time + 30 seconds to 3 minutes ahead
			upgradeTime = GameController.timer + (rand.nextInt(12) + 1) * 5000000000l;
			
			Upgrade u;
			switch(rand.nextInt(2)){
			case 1:
				u = new SpeedBoost(SpriteSheet.getBlock(15, 15, Color.MEDIUMPURPLE), 0, 0);
				break;
			default:
				u = new MedPack(SpriteSheet.getBlock(10, 10, Color.RED), 0, 0);
				break;
			}
			
			arenaMap.checkCollision(u);
			gc.addEntity(u);
		}
		
		// Check for win
		if(!value.isAlive()){
			players.remove(value);
			
			// stop when a win condition is achieved
			// in this case, it's when one player is remaining
			if(players.size() == 1){
				System.out.println("Player " + players.get(0).getTag() + " is the Winner!");
				gc.stop();
			}
		}
	}
	
	/*
	 * We need to be able to determine Loss in Story and Arena
	 *
	 * Loss in Arena is either of the two players dying
	 * 
	 * Loss in Story is the players expiring their lives.
	 * 
	 * GC has access to Story/ Arena boolean
	 * 
	 * Subscriber and Publisher Model would be useful, but it sounds ugly
	 * 
	 * 
	 */
}

