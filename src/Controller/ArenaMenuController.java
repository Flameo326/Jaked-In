package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Enums.BulletType;
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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ArenaMenuController implements Initializable{

    @FXML
    private VBox playerMenu;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

	@FXML
    private HBox playersBox;

    @FXML
    private Button startButton;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    void addBtnAction(ActionEvent event) {
    	if(playersBox.getChildren().size() < 4) {
    		playersBox.getChildren().add(new PlayerBox());
    		resizePlayerBox();
    	} else {
    		// Add Error Message about maximum players
    	}
    }

    @FXML
    void removeBtnAction(ActionEvent event) {
    	if(playersBox.getChildren().size() > 2) {
    		playersBox.getChildren().remove(playersBox.getChildren().size()-1);
    		resizePlayerBox();
    	} else {
    		// Add Error Message about minimum players
    	}
    	
    }
    
    private void resizePlayerBox(){
    	for(Node n : playersBox.getChildren()){
    		PlayerBox playBox;
    		if(n instanceof PlayerBox){
    			playBox = (PlayerBox)n;
    		} else { continue; }
    		
    		playBox.prefWidthProperty().bind(playersBox.widthProperty().divide(playersBox.getChildren().size()));
    	}
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
    			Image img = SpriteSheet.getBorderedBlock(5, 5, Color.WHITE, 3);
    			p.setWeapon(new ProjectileWeapon(p, img, 1, 400, BulletType.NORMAL));
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
		label1.setPadding(new Insets(10, 0, 0, 0));
		label1.prefWidthProperty().bind(playerMenu.widthProperty());
		label2.prefWidthProperty().bind(playerMenu.widthProperty());
		label3.prefWidthProperty().bind(playerMenu.widthProperty());
		
		addBtnAction(null);
		addBtnAction(null);
	}

}
