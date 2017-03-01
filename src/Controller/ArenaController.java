package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Models.Entity;
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

public class ArenaController implements Initializable {
	
	@FXML
	private Canvas myCanvas;
	private GameController gc;
	private Map arenaMap;
	private PlayableCharacter player1, player2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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

								// Create Map
								arenaMap = new Map((int)newValue.getWidth(), (int)newValue.getHeight());
								gc.add(arenaMap.getMapObjects().toArray(new Entity[0]));
								Entity room = arenaMap.createNewWall(20, 100, 20, 100);
								room.setXPos(50);
								room.setYPos(50);
								gc.add(room);
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
		
		Image img = SpriteSheet.getBorderedBlock(30, 30, Color.WHITE, 3);
		// Players
		player1 = new HumanPlayer(img, 0, 0);
		player2 = new ComputerPlayer(img, -100, -100);
		
		gc = new GameController(myCanvas);
		gc.add(player1.getDisplayableEntities());
		gc.add(player2.getDisplayableEntities());
		gc.add(new HumanPlayer(img, 50, 50));
		gc.setFocus(player1);
		gc.start();
	}
}

/*TODO
 * Fix Path and Generating Rooms
 * Add Walls
 * Fix Collision System
 * Implement Damage and Death
 * Start on Story Mode
 * 
 */
