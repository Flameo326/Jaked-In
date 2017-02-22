package Controller;

import java.net.URL;
import java.util.ArrayList;
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
	private HumanMovementController player1Controls;
	private ArrayList<Collideable> colliders;
	private ArrayList<String> input;
	// We need some thing to indicate Walls
	
	public ArenaController(){
		colliders = new ArrayList<>();
		input = new ArrayList<>();
		
		WritableImage img = new WritableImage(50, 50);
		PixelWriter pw = img.getPixelWriter();
		for(int i = 0; i < img.getHeight(); i++){
			for(int y = 0; y < img.getWidth(); y++){
				pw.setColor(y, i, Color.RED);
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
			if(!input.contains(e.getText())){
				input.add(e.getText());
			}
		});
		myCanvas.setOnKeyReleased((e) -> {
			input.remove(e.getText());
		});
		player1Controls = new HumanMovementController(input, player1);
		
		new AnimationTimer(){
			@Override
			public void handle(long now) {
				player1Controls.checkForInput();
				updateImage();
				System.out.println("Player 1 X: " + player1.getXPos() + " Y: " + player1.getYPos());
				System.out.print("[");
				for(String s : input){
					System.out.print(s + ", ");
				}
				System.out.println("]");
			}
		}.start();
	}

}
