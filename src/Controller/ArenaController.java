package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Interfaces.Collideable;
import Models.Players.ComputerPlayer;
import Models.Players.HumanPlayer;
import Models.Players.PlayableCharacter;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ArenaController implements Initializable {
	
	@FXML
	private Canvas myCanvas;
	private GraphicsContext g;
	private PlayableCharacter player1, player2;
	private HumanController player1Controls;
	private RandomAIController player2Controls;
	private ArrayList<Collideable> colliders;
	private HashMap<String, Boolean> input;
	// We need some thing to indicate Walls
	
	public ArenaController(){
		colliders = new ArrayList<>();
		input = new HashMap<>();
		
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
	
	public void updateImage(){
		g.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
		g.drawImage(player1.getImage(), player1.getXPos(), player1.getYPos());
		g.drawImage(player2.getImage(), player2.getXPos(), player2.getYPos());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		g = myCanvas.getGraphicsContext2D();
		myCanvas.setFocusTraversable(true);
		myCanvas.setOnKeyPressed((e) -> {
			if(!input.containsKey(e.getText())){
				input.put(e.getText(), true);
			}
		});
		myCanvas.setOnKeyReleased((e) -> {
			input.remove(e.getText());
		});
		player1Controls = new HumanController(input, player1);
		player2Controls = new RandomAIController(player2);
		
		new AnimationTimer(){
			@Override
			public void handle(long now) {
				player1Controls.checkForInput();
				player2Controls.checkForInput();
				updateImage();
				System.out.println("Player 1 X: " + player1.getXPos() + " Y: " + player1.getYPos());
				System.out.println("Player 2 X: " + player2.getXPos() + " Y: " + player2.getYPos());
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
 * 
 * 
 * 
 * 
 * 
 * 
 * */
