package FXML;

import SpriteSheet.SpriteSheet;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class PlayerBox extends VBox{
	
	private static final String[] PLAYER_TYPES = {"Human", "Computer"};
//	private static final String[] WEAPON_TYPES = {"Projectile", "Melee"};
	
	private HBox playerNavigation;
	private ImageView playerImage;
	private int playerCount;
	
	private ChoiceBox<String> playerType;
	
	private HBox diffTypes;
	private CheckBox easyBox, mediumBox, hardBox;
	
	// Will display Text representing Weapon Type
	// Then  has an HBox with 2 arrows and an ImageView
	// The Arrows will navigate Picture and Label
	private Label weaponType;
	private HBox weaponNavigation;
	private ImageView weaponImage;
	private int weaponCount;
	
	public PlayerBox(){
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
		
		// Left Player Button
		Button leftPlayerNav = new Button();
		leftPlayerNav.setStyle("-fx-shape: 'M-100 0 L0 100 L0 -100 L-100 0 Z';");
		leftPlayerNav.setOnAction(e -> {
			playerCount--;
			changePlayerImage();
		});
		
		// Player Image
		playerImage = new ImageView();
		changePlayerImage();
		
		// Right Player Button
		Button rightPlayerNav = new Button();
		rightPlayerNav.setStyle("-fx-shape: 'M100 0 L0 100 L0 -100 L100 0 Z';");
		rightPlayerNav.setOnAction(e -> {
			playerCount++;
			changePlayerImage();
		});
		
		playerNavigation = new HBox(leftPlayerNav, playerImage, rightPlayerNav);
		playerNavigation.setAlignment(Pos.CENTER);
		playerNavigation.setSpacing(10);
		
		playerType = new ChoiceBox<>();
		playerType.getItems().addAll(PLAYER_TYPES);
		playerType.setValue(PLAYER_TYPES[0]);
		playerType.setOnAction(e -> {
			if(playerType.getValue().equals(PLAYER_TYPES[1])){
				getChildren().add(2, diffTypes);
			} else {
				getChildren().remove(diffTypes);
			}
		});
		
		easyBox = new CheckBox("Easy");
		mediumBox = new CheckBox("Medium");
		hardBox = new CheckBox("Hard");
		
		easyBox.setOnAction(e -> {
			mediumBox.setSelected(false);
			hardBox.setSelected(false);
		});
		mediumBox.setOnAction(e -> {
			easyBox.setSelected(false);
			hardBox.setSelected(false);
		});
		hardBox.setOnAction(e -> {
			mediumBox.setSelected(false);
			easyBox.setSelected(false);
		});
		
		diffTypes = new HBox(easyBox, mediumBox, hardBox);
		diffTypes.setAlignment(Pos.CENTER);
		diffTypes.setSpacing(10);
		
		weaponType = new Label();
		
		// Left Weapon Button
		Button leftWeapNav = new Button();
		leftWeapNav.setStyle("-fx-shape: 'M-100 0 L0 100 L0 -100 L-100 0 Z';");
		leftWeapNav.setOnAction(e -> {
			weaponCount--;
			changeWeaponImage();
		});
		
		// Weapon Image
		weaponImage = new ImageView();
		changeWeaponImage();
		
		// Right Weapon Button
		Button rightWeapNav = new Button();
		rightWeapNav.setStyle("-fx-shape: 'M100 0 L0 100 L0 -100 L100 0 Z';");
		rightWeapNav.setOnAction(e -> {
			weaponCount++;
			changeWeaponImage();
		});
		
		weaponNavigation = new HBox(leftWeapNav, weaponImage, rightWeapNav);
		weaponNavigation.setAlignment(Pos.CENTER);
		weaponNavigation.setSpacing(10);
		
		// Player Image
		// Player Type
		// Player Diff if neccesary
		// Weapon Type Name
		// Weapon Image
		getChildren().add(playerNavigation);
		getChildren().add(playerType);
		getChildren().add(weaponType);
		getChildren().add(weaponNavigation);
	}
	
	public void changePlayerImage(){
		if(playerCount > 1) { playerCount = 0; }
		if(playerCount < 0) { playerCount = 1; }
		switch(playerCount){
		case 0:
			playerImage.setImage(SpriteSheet.getBorderedBlock(30, 30, Color.WHITE, 3));
			break;
		case 1:
			playerImage.setImage(SpriteSheet.getBorderedBlock(30, 30, Color.GOLD, 3));
			break;
			
		}
	}
	
	public void changeWeaponImage(){
		// Wrap it
		if(weaponCount < 0) { weaponCount = 1; }
		if(weaponCount > 1) { weaponCount = 0; }
		switch(weaponCount){
		case 0:
			// Projectile
			weaponImage.setImage(SpriteSheet.getBorderedBlock(5, 5, Color.WHITE, 3));
			weaponType.setText("Projectile");
			break;
		case 1:
			// Melee
			weaponImage.setImage(SpriteSheet.getBorderedBlock(20, 20, Color.WHITE, 3));
			weaponType.setText("Melee");
			break;
		}
	}

	public Image getPlayerImage() {
		return playerImage.getImage();
	}

	public String getPlayerType() {
		return playerType.getValue();
	}

	public String getWeaponType() {
		return weaponType.getText();
	}
	
	public String getPlayerDifficulty(){
		for(Node n : diffTypes.getChildren()){
			CheckBox c;
			if(n instanceof CheckBox){
				c = (CheckBox)n;
			} else { continue; }
			
			if(c.isSelected()){
				return c.getText();
			}
		}
		return "NO DIFFICULTY";
	}

}
