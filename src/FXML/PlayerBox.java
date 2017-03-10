package FXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import SpriteSheet.SpriteSheet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	
	private static int HUMAN_PLAYERS = 0;
	
	private static final String[] PLAYER_TYPES = {"Human", "Computer"};
	private static final ArrayList<Color> colors;
	static{
		colors = new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.AQUA);
		colors.add(Color.BLACK);
		colors.add(Color.BLUEVIOLET);
		colors.add(Color.CHARTREUSE);
		colors.add(Color.CORAL);
		colors.add(Color.HOTPINK);
	}
	
	private HBox playerNavigation;
	private ImageView playerImage;
	private Color currColor;
	private int playerCount;
	
	private ChoiceBox<String> playerType;
	
	private VBox diffTypes;
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
		currColor = colors.get(playerCount);
		colors.remove(playerCount);
		playerImage.setImage(SpriteSheet.getPlayerWithColor(currColor));
		
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
		getChildren().add(playerNavigation);
		
		easyBox = new CheckBox("Easy");
		mediumBox = new CheckBox("Medium");
		hardBox = new CheckBox("Hard");
		
		easyBox.setOnAction(e -> {
			if(!hardBox.isSelected() && !mediumBox.isSelected() && !easyBox.isSelected()){
				easyBox.setSelected(true);
			}
			mediumBox.setSelected(false);
			hardBox.setSelected(false);
		});
		mediumBox.setOnAction(e -> {
			if(!hardBox.isSelected() && !mediumBox.isSelected() && !easyBox.isSelected()){
				mediumBox.setSelected(true);
			}
			easyBox.setSelected(false);
			hardBox.setSelected(false);
		});
		hardBox.setOnAction(e -> {
			if(!hardBox.isSelected() && !mediumBox.isSelected() && !easyBox.isSelected()){
				hardBox.setSelected(true);
			}
			mediumBox.setSelected(false);
			easyBox.setSelected(false);
		});
		easyBox.setSelected(true);
		
		diffTypes = new VBox(easyBox, mediumBox, hardBox);
		diffTypes.setAlignment(Pos.CENTER);
		diffTypes.setSpacing(10);
		
		playerType = new ChoiceBox<>();
		
		playerType.getItems().addAll(PLAYER_TYPES);
		getChildren().add(playerType);
		
		if(HUMAN_PLAYERS <= 1){
			playerType.setValue(PLAYER_TYPES[0]);
			HUMAN_PLAYERS++;
		} else {
			playerType.setValue(PLAYER_TYPES[1]);
			getChildren().add(diffTypes);
		}
		playerType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(oldValue.intValue() == 0){
					HUMAN_PLAYERS--;
				}
				if(newValue.intValue() == 1){
					getChildren().add(2, diffTypes);
				} else {
					getChildren().remove(diffTypes);
				}
				if(newValue.intValue() == 0){
					System.out.println("Here");
					if(HUMAN_PLAYERS <= 1){
						HUMAN_PLAYERS++;
					} else {
						playerType.getSelectionModel().select(oldValue.intValue());
						HUMAN_PLAYERS++;
					}
				}
			}
		});
		
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
		
		getChildren().add(weaponType);
		getChildren().add(weaponNavigation);
	}
	
	public void changePlayerImage(){
		colors.add(currColor);
		if(playerCount >= colors.size()) { playerCount = 0; }
		if(playerCount < 0) { playerCount = colors.size()-1; }
		currColor = colors.get(playerCount);
		
		playerImage.setImage(SpriteSheet.getPlayerWithColor(currColor));
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
	
	public static void resetHumanPlayers(){
		HUMAN_PLAYERS = 0;
	}

}
