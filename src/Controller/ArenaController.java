package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import Interfaces.Collideable;
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
import javafx.stage.Stage;
import javafx.stage.Window;

public class ArenaController implements Initializable {
	
	@FXML
	private Canvas myCanvas;
	private GraphicsContext g;
	private PlayableCharacter player1, player2;
	private HumanController player1Controls;
	private RandomAIController player2Controls;
	private ArrayList<Collideable> colliders;
	private HashSet<String> input;
	
	// What if we had:
	// - arrayList<Entity> walls or Entitys that don't need to be updated or tested for collision. Basically Don't Move
	//  - The reason this should be seperate is because walls don't need to be updated or tested for collision against each other
	// - arrayList<Entity> updateable Entities which do move and can collide. This can be Projectiles, HiitBoxes and Players
	//  - 
	// 
	
	public ArenaController(){
		colliders = new ArrayList<>();
		input = new HashSet<>();
		
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
		
		player1 = new HumanPlayer(img, 50, 50);
		player2 = new ComputerPlayer(img, 150, 150);
	}
	
	private void updateImage(){
		g.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
		g.drawImage(player1.getImage(), player1.getXPos(), player1.getYPos());
		g.drawImage(player2.getImage(), player2.getXPos(), player2.getYPos());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Resizes the Canvas to it's Stage Widht and Height...
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
								
								System.out.println(newValue.getWidth() + " " + newValue.getHeight());
								System.out.println(myCanvas.getWidth() + " " + myCanvas.getHeight());
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
		myCanvas.setOnKeyPressed((e) -> {
			if(!input.contains(e.getText())){
				input.add(e.getText());
			}
		});
		myCanvas.setOnKeyReleased((e) -> {
			input.remove(e.getText());
		});
		player1Controls = new HumanController(input, player1);
		player2Controls = new RandomAIController(player2);
		
		// This entire thing will be our "Run" method. It gets called constantly and updates accordingly.
		// We need to figure out a way for this class to communicate between The ArenaController and everything the player does.
		// For example, If they create a new Projectile, it needs to be added to the colliders and things that must be
		// -- graphicaly updated every Frame
		new AnimationTimer(){
			@Override
			public void handle(long now) {
				// Should we add Controllers to the Player classes? 
				// This way all you do is call player update() Method instead of the correct Controller...
				player1Controls.checkForInput();
				player2Controls.checkForInput();
				// Handles the graphical Rendering 
				updateImage();
				// System Information
				System.out.println("Player 1 X: " + player1.getXPos() + " Y: " + player1.getYPos());
				System.out.println("Player 2 X: " + player2.getXPos() + " Y: " + player2.getYPos());
			}
		}.start();
		
		// What if Entity had an abstract update() which determines how every Entity moves each Frame.
		// But some methods would probably need different parameters...
		// -- Human would need Input, Projectiles would need a direction.
		// -- Would it be ok to just have them store data relevant to their update Method?
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
 * 
 * 
 * 
 * 
 * 
 * 
 * */
