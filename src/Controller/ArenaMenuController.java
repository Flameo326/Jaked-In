package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import FXML.PlayerBox;
import Models.Players.ComputerPlayer;
import Models.Players.HumanPlayer;
import Models.Players.PlayableCharacter;
import Models.Weapon.MeleeWeapon;
import Models.Weapon.NormalProjectileWeapon;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private Button backBtn;

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
    void backBtnAction() throws IOException {
    	PlayerBox.resetHumanPlayers();
    	Stage s = (Stage)backBtn.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StartFXML.fxml"));
		BorderPane root = loader.load();
		
		Scene scene = new Scene(root, s.getScene().getWidth(), s.getScene().getHeight());
		s.setScene(scene);
		s.centerOnScreen();
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
    			p = new ComputerPlayer(playImage, 0, 0, 1);
    			break;
			default:
				System.out.println("Player Type is " + playBox.getPlayerType());
    		}
    		
    		// Set weapon
    		switch(playBox.getWeaponType()){
    		case "Projectile":
    			p.setWeapon(new NormalProjectileWeapon(p, 30));
    			break;
    		case "Melee":
    			p.setWeapon(new MeleeWeapon(p));
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
    	controller.gameStart();
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
