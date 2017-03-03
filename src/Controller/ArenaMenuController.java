package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import FXML.PlayerBox;
import Models.Players.ComputerPlayer;
import Models.Players.HumanPlayer;
import Models.Players.PlayableCharacter;
import Models.Weapon.MeleeWeapon;
import Models.Weapon.ProjectileWeapon;
import SpriteSheet.SpriteSheet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ArenaMenuController implements Initializable{

	@FXML
    private HBox playersBox;
	private ArrayList<PlayerBox> players;

    @FXML
    private Button startButton;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    void addBtnAction(ActionEvent event) {

    }

    @FXML
    void removeBtnAction(ActionEvent event) {

    }

    @FXML
    void startBtnAction(ActionEvent event) throws IOException {
    	Stage s = (Stage)startButton.getScene().getWindow();
    	
    	// Initialize the File and Controller
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ArenaFXML.fxml"));
    	StackPane root = loader.load();
    	
    	ArenaController controller = loader.getController();
    	// Need to get players and Add them
    	PlayableCharacter p = null;
    	for(Node n : playersBox.getChildren()){
    		PlayerBox playBox;
    		if(n instanceof PlayerBox){
    			playBox = (PlayerBox)n;
    		} else { continue; }
    		
    		Image playImage = playBox.getPlayerImage();
    		
    		// Set type and Image
    		switch(playBox.getPlayerType()){
    		case "Human":
    			p = new HumanPlayer(playImage, 0, 0);
    			break;
    		case "Computer":
    			// Would need to get difficulty rating here
    			p = new ComputerPlayer(playImage, 0, 0);
    			break;
			default:
				System.out.println("Player Type is " + playBox.getPlayerType());
    		}
    		
    		// Set weapon
    		switch(playBox.getWeaponType()){
    		case "Projectile":
    			p.setWeapon(new ProjectileWeapon(p, SpriteSheet.getBorderedBlock(30, 30, Color.WHITE, 3)));
    			break;
    		case "Melee":
    			p.setWeapon(new MeleeWeapon(p, SpriteSheet.getBorderedBlock(20, 20, Color.WHITE, 3)));
    			break;
			default:
				System.out.println("Weapon Type is " + playBox.getWeaponType());
    		}
    		controller.addPlayer(p);
    	}
    	
    	
    	// This will create a new scene from the FXML file
    	// set the current stage to that scene 
    	// and start the controller when ready
    	Scene scene = new Scene(root, s.getWidth(), s.getHeight());
    	s.setScene(scene);
    	controller.start();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		// Intialize two blocks for Players then allow adding and removing from 2 - 8 from an arrayList
		PlayerBox p1 = new PlayerBox();
		p1.prefWidthProperty().bind(playersBox.widthProperty().multiply(.5));
		playersBox.getChildren().add(p1);
		
		PlayerBox p2 = new PlayerBox();
		p2.prefWidthProperty().bind(playersBox.widthProperty().multiply(.5));
		playersBox.getChildren().add(p2);
	}

}
