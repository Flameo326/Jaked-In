package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Models.Entity;
import Models.Map.Map;
import Models.Players.ComputerPlayer;
import Models.Players.HumanPlayer;
import Models.Players.PlayableCharacter;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class ArenaController implements Initializable {
	
	@FXML
	private Canvas myCanvas;
	private GraphicsContext g;
	private Map arenaMap;
	private PlayableCharacter player1, player2;
	private ArrayList<Entity> entities;
	
	// What if we had:
	// - arrayList<Entity> walls or Entitys that don't need to be updated or tested for collision. Basically Don't Move
	//  - The reason this should be seperate is because walls don't need to be updated or tested for collision against each other
	// - arrayList<Entity> updateable Entities which do move and can collide. This can be Projectiles, HiitBoxes and Players
	//  - 
	
	public ArenaController(){
		// Initialize Entities
		entities = new ArrayList<>();
		
		WritableImage img = new WritableImage(50, 50);
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
		
		entities.add(player1 = new HumanPlayer(img, 50, 50));
		entities.add(player1.getWeapon());
		entities.add(player2 = new ComputerPlayer(img, 150, 150));
		entities.add(player2.getWeapon());
	}
	
	private void updateImage(){
		g.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
		for(Entity e : entities){
			g.drawImage(e.getImage(), e.getXPos(), e.getYPos());
		}	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Resizes the Canvas to it's Stage Width and Height...
		myCanvas.sceneProperty().addListener(new ChangeListener<Scene>(){
			@Override
			public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
				System.out.println(newValue);
				if(newValue != null){
					newValue.windowProperty().addListener(new ChangeListener<Window>(){
						@Override
						public void changed(ObservableValue<? extends Window> observable, Window oldValue,
								Window newValue) {
							System.out.println(newValue);
							if(newValue != null){
								myCanvas.widthProperty().bind(newValue.widthProperty());
								myCanvas.heightProperty().bind(newValue.heightProperty());
								
								// Create Map
								arenaMap = new Map((int)newValue.getWidth(), (int)newValue.getHeight());
								entities.addAll(0, arenaMap.getMapObjects());
								
								System.out.println("Stage Width: " + newValue.getWidth() + ", Height: " + newValue.getHeight());
							}
						}
					});
				}
			}
		});
		g = myCanvas.getGraphicsContext2D();
		
		// Neccesary
		myCanvas.setFocusTraversable(true);
		// KeyEvent to record input while playing
		myCanvas.setOnKeyPressed((e) -> InputHandler.keyPress(e));
		myCanvas.setOnKeyReleased((e) -> InputHandler.keyRelease(e));
		
		// This entire thing will be our "Run" method. It gets called constantly and updates accordingly.
		// We need to figure out a way for this class to communicate between The ArenaController and everything the player does.
		// For example, If they create a new Projectile, it needs to be added to the colliders and things that must be
		// -- graphicaly updated every Frame
		new AnimationTimer(){
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
		}.start();
	}

}

/*Our class needs to keep track of all the entities.
 * Our class also needs to update everything that needs to be updated frequently.
 * 
 * ArrayList Walls 
 *  -- Could be an array, would not need to be updated or checked but other things would need to check against it
 * ArrayList Projectiles
 *  -- All the moving objects in the field. These need to be updated and chacked against Walls and Players
 * ArrayList Hitboxes
 *  -- The Melee weapons?
 *   -- What if we had some generic object that had an update method, we could call it for all objects and if they do something 
 *   -- Then they do something, maybe they don't. This would work for projectiles, Walls, Players, ...nm
 * Array[] Players
 *  -- The players would need to get checked against everything
 *  -- Should we add "Tags" to things to tell what they are
 * */
