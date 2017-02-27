package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Models.Entity;
import Models.Map.Map;
import Models.Players.ComputerPlayer;
import Models.Players.HumanPlayer;
import Models.Players.PlayableCharacter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class ArenaController implements Initializable {
	
	@FXML
	private Canvas myCanvas;
	private GameController gc;
	public static Map arenaMap;
	private PlayableCharacter player1, player2;
	
	public void ArenaControllerTemp(){
		
		WritableImage img = new WritableImage(30, 30);
		PixelWriter pw = img.getPixelWriter();
		int lineWidth = 2;
		for(int i = 0; i < img.getHeight(); i++){
			for(int y = 0; y < img.getWidth(); y++){
				if(i < lineWidth || y < lineWidth || i > img.getHeight()-lineWidth ||
						y > img.getWidth()-lineWidth){
					pw.setColor(y, i, Color.BLACK);
				}
			}
		}
		
		// Players
		player1 = new HumanPlayer(img, 250, 250);
		player1.setTag("Human.1");
		gc.add(player1.getDisplayableEntities());
		gc.setFocus(player1);
		
		player2 = new ComputerPlayer(img, 150, 150);
		gc.add(player2.getDisplayableEntities());
	}
	
//	private void updateImage(){
//		g.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
//		for(Entity e : entities){
//			g.drawImage(e.getImage(), e.getXPos(), e.getYPos());
//		}	
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Resizes the Canvas to it's Stage Width and Height...
		gc = new GameController(myCanvas);
		ArenaControllerTemp();
		
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
		
		// This entire thing will be our "Run" method. It gets called constantly and updates accordingly.
		// We need to figure out a way for this class to communicate between The ArenaController and everything the player does.
		// For example, If they create a new Projectile, it needs to be added to the colliders and things that must be
		// -- graphicaly updated every Frame

		
//		new AnimationTimer(){
//			@Override
//			public void handle(long now) {
//				for(Entity e : entities.toArray(new Entity[0])){
//					// All Entities are updated even if they don't move
//					e.update(entities);
//					// Print out Entity Information
//					//System.out.println(e.getClass().getSimpleName() + " X: " + e.getXPos() + " Y: " + e.getYPos());
//				}
//				// Handles the graphical Rendering 
//				updateImage();
//			}
//		}.start();
		gc.start();
	}

}

/*Sometimes I still get width or heihgt must be positive errors, fix that.
 *Some objects only go to the very tip of the room
 *Some objects are completed but aren still a few pixels away, like an entire block
 * 
 * 
 * 
 */
