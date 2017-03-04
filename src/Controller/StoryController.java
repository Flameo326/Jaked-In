package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Interfaces.Subscribable;
import Models.Entity;
import Models.Map.Map;
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

public class StoryController implements Initializable, Subscribable<PlayableCharacter> {
	
	@FXML
	private Canvas myCanvas;
	private GameController gc;
	private PlayableCharacter player1;
	private Map currentLevel;
	// create variables for the levels and generate them during init
	int level = 0;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image img = SpriteSheet.getBorderedBlock(30, 30, Color.WHITE, 3);
		player1 = new HumanPlayer(img, 0, 0);
		
		gc = new GameController(myCanvas, true);
		gc.attach(this);
		gc.addEntity(player1.getDisplayableEntities());
		gc.addPlayer(player1);
		gc.setFocus(player1);
		
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
								currentLevel = new Map((int)newValue.getWidth(), (int)newValue.getHeight());
								currentLevel.generateMap();
								gc.addEntity(currentLevel.getMapObjects().toArray(new Entity[0]));
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
		
		gc.start();
	}

	@Override
	public void update(PlayableCharacter value) {
		if(!value.isAlive()){
			System.out.println("Live lost"); 
			
			// stop when a win condition is achieved
			// in this case, it's when one player is killed
			gc.stop();
		}
	}

}
