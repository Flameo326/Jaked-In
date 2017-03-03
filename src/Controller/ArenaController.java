package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Interfaces.Subscribable;
import Models.Entity;
import Models.Map.ArenaMap;
import Models.Map.Map;
import Models.Players.ComputerPlayer;
import Models.Players.HumanPlayer;
import Models.Players.PlayableCharacter;
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
	
	// Do we need the map??
	private Map arenaMap;
	
	// This is going to be the amount of players competing
	// For now leave this at 2
	private PlayableCharacter player1, player2;
	private ArrayList<Entity> players;
	
	// How do we know when gc has ended?
	// aka when one of the players has died?
	// gc can't do it or it won't work for story...
	
	public void addPlayer(PlayableCharacter p){
		players.add(p);
		gc.addEntity(p.getDisplayableEntities());
		gc.addPlayer(p);
	}
	
	public void start(){
		int size = players.size()*200;
		
		// Position Players
		for(int i = 0; i < players.size(); i++){
			Entity p = players.get(i);
			switch(players.size()){
			case 2:
				p.setYPos(0);
				p.setXPos(i == 0 ? -size/4 : size/4);
			case 3:
				// -150, 0
				// 150, 0
				// 0, 150
				if(i > 1){
					p.setYPos(size/4);
					p.setXPos(0);
				} else {
					p.setYPos(-size/4);
					p.setXPos(i == 0 ? -size/4 : size/4);
				}
				break;
			case 4:
				// -150, -150
				// 150, -150
				// -150, 150
				// 150, 150
				if(i > 1){
					p.setYPos(size/4);
					p.setXPos(i == 2 ? -size/4 : size/4);
				} else {
					p.setYPos(-size/4);
					p.setXPos(i == 0 ? -size/4 : size/4);
				}
				break;
			default:
				System.out.println("The player size is " + players.size());
			}
		}
		
		// Generate Map
		arenaMap = new ArenaMap(size, size, players);
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
		
//		Image img = SpriteSheet.getBorderedBlock(30, 30, Color.WHITE, 3);
//		// Players
//		player1 = new HumanPlayer(img, 0, 0);
//		player2 = new ComputerPlayer(img, -100, -100);
//		players.add(player1);
//		players.add(player2);
		
		gc = new GameController(myCanvas, false);
		gc.attach(this);
//		gc.addEntity(player1.getDisplayableEntities());
//		gc.addEntity(player2.getDisplayableEntities());
//		gc.addPlayer(player1);
//		gc.addPlayer(player2);
//		gc.setFocus(player1);
		
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
		if(!value.isAlive()){
			if(player1 == value){
				// winner is player 2
				System.out.println("Player 1 Won");
			} else if(player2 == value){
				// winner is player 1
				System.out.println("Player 1 Won");
			}
			
			// stop when a win condition is achieved
			// in this case, it's when one player is killed
			gc.stop();
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

