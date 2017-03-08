package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import FXML.PlayerBox;
import Interfaces.Subscribable;
import Models.Entity;
import Models.Map.ArenaMap;
import Models.Players.ComputerPlayer;
import Models.Players.HumanPlayer;
import Models.Players.PlayableCharacter;
import Models.Upgrades.MedPack;
import Models.Upgrades.ProjectileWeaponBullets;
import Models.Upgrades.SpeedBoost;
import Models.Upgrades.Upgrade;
import SpriteSheet.SpriteSheet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ArenaController implements Initializable, Subscribable<PlayableCharacter> {
	
	@FXML
	private Canvas myCanvas;
	private GameController gc;
	private int width, height;
	
	private ArenaMap arenaMap;
	private ArrayList<PlayableCharacter> players, deadPlayers;
	private Random rand;
	private long upgradeTime, weaponTime;
	
	public void addPlayer(PlayableCharacter p){
		players.add(p);
		gc.addEntity(p.getDisplayableEntities());
		gc.addPlayer(p);
	}
	
	public void gameStart(){
		width = 600;
		height = 500;
		// Position Players
		for(int i = 0; i < players.size(); i++){
			Entity p = players.get(i);
			switch(players.size()){
			case 2:
				p.setYPos(0);
				p.setXPos(i == 0 ? -width/4 : width/4);
				break;
			case 3:
				if(i > 1){
					p.setYPos(height/4);
					p.setXPos(0);
				} else {
					p.setYPos(-height/4);
					p.setXPos(i == 0 ? -width/4 : width/4);
				}
				break;
			case 4:
				if(i > 1){
					p.setYPos(height/4);
					p.setXPos(i == 2 ? -width/4 : width/4);
				} else {
					p.setYPos(-height/4);
					p.setXPos(i == 0 ? -width/4 : width/4);
				}
				break;
			default:
				System.out.println("The player size is " + players.size());
			}
		}
		
		// Generate Map
		arenaMap = new ArenaMap(width, height, players);
		gc.addEntity(arenaMap.getMapObjects().toArray(new Entity[0]));
		start();
	}
	
	public void start(){
		// Start
		gc.start();
	}
	
	public void stop(){
		gc.stop();
	}
	
	public void displayWinner(){
		GraphicsContext g = myCanvas.getGraphicsContext2D();
		g.setFill(Color.WHITE);
		g.fillRect(width/4, height/8, width/2, height*3/4);
		
		g.setStroke(Color.BLACK);
		g.strokeRect(width/4, height/8, width/2, height*3/4);
		
		g.setFont(new Font(32));
		g.setFill(Color.BLACK);
		g.setTextAlign(TextAlignment.CENTER);
		
		int yPos = height*3/8;
		g.fillText("1.) " + players.get(0).getTag(), width/2, yPos);
		yPos += 32;
		
		g.setFont(new Font(18));
		for(int i = deadPlayers.size()-1; i > -1; i--){
			Entity e = deadPlayers.get(i);
			g.fillText(2 + (deadPlayers.size()-1-i) + ".) " + e.getTag(), width/2, yPos);
			yPos += 18;
		}
		
		int btnWidth = width*2/5;
		int btnHeight =  height/16;
		int btnX = width/2 - btnWidth/2;
		int btnY = height*13/16 - btnHeight/2;
		g.strokeRoundRect(btnX, btnY, btnWidth, btnHeight, 5, 5);
		g.fillText("Back to Main Menu", btnX + btnWidth/2, btnY + btnHeight*3/4, btnWidth);
		myCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			int x = (int)e.getSceneX();
			int y = (int)e.getSceneY();
			if(x < btnX || x > btnX + btnWidth || y < btnY || y > btnY + btnHeight){
				// Succesfuly clicked button
				Stage s = (Stage)myCanvas.getScene().getWindow();
				if(s == null) { return; }
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StartFXML.fxml"));
				
				try {
					BorderPane root = loader.load();
					Scene scene = new Scene(root, s.getScene().getWidth(), s.getScene().getHeight());
					s.setScene(scene);
					s.centerOnScreen();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		players = new ArrayList<>();
		deadPlayers = new ArrayList<>();
		rand = new Random();
		
		gc = new GameController(myCanvas, false);
		gc.attach(this);
		
		// Resizes the Canvas to it's Stage Width and Height...
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
	}

	@Override
	public void update(PlayableCharacter value) {
		if(InputHandler.keyInputContains(KeyCode.ESCAPE)) { displayEscapeMenu(); }
		// from 3o seconds to 3 minuutes
		if(GameController.getTimer() >= upgradeTime){
			// Get current time + 30 seconds to 3 minutes ahead
			upgradeTime = GameController.getTimer()+ (rand.nextInt(12) + 1) * 5000000000l;
			
			Upgrade u;
			switch(rand.nextInt(2)){
			case 1:
				u = new SpeedBoost(0, 0);
				break;
			default:
				u = new MedPack(0, 0);
				break;
			}
			
			arenaMap.checkCollision(u);
			gc.addEntity(u);
		}
		if(GameController.getTimer() >= weaponTime){
			weaponTime = GameController.getTimer()+ (rand.nextInt(12) + 1) * 15000000000l;
			
			Upgrade w;
			switch(rand.nextInt(1)){
//			case 1:
//				w = new SpeedBoost(SpriteSheet.getBlock(15, 15, Color.MEDIUMPURPLE), 0, 0);
//				break;
			default:
				w = new ProjectileWeaponBullets(SpriteSheet.getBlock(20, 10, Color.BLACK), 0, 0);
				break;
			}
			
			arenaMap.checkCollision(w);
			gc.addEntity(w);
		}
		
		// Check for win
		if(!value.isAlive()){
			gc.removePlayer(value);
			players.remove(value);
			deadPlayers.add(value);
			
			// stop when a win condition is achieved
			// in this case, it's when one player is remaining
			if(players.size() == 1){
				gc.handle(GameController.getTimer());
				gc.stop();
				PlayerBox.resetHumanPlayers();
				HumanPlayer.resetHumanID();
				ComputerPlayer.resetComputerID();
				displayWinner();
			}
		}
	}
	
	public void displayEscapeMenu(){
		int width = (int) myCanvas.getWidth();
		int height = (int) myCanvas.getHeight();
		GraphicsContext g = myCanvas.getGraphicsContext2D();
		stop();
		
		g.setFill(Color.WHITE);
		g.fillRect(width/4, height/8, width/2, height*3/4);
		
		g.setStroke(Color.BLACK);
		g.strokeRect(width/4, height/8, width/2, height*3/4);
		
		g.setFont(new Font(32));
		g.setFill(Color.BLACK);
		g.setTextAlign(TextAlignment.CENTER);
		
		int btnWidth = width*2/5;
		int btnHeight =  height/16;
		int btnX = width/2 - btnWidth/2;
		
		int resumeY = height*5/16 - btnHeight/2;
		g.strokeRoundRect(btnX, resumeY+btnHeight/2, btnWidth, btnHeight, 5, 5);
		g.fillText("Resume", btnX + btnWidth/2, resumeY+btnHeight/2 + btnHeight*3/4, btnWidth);
		
		int backY = resumeY + btnHeight*2;
		g.strokeRoundRect(btnX, backY+btnHeight/2, btnWidth, btnHeight, 5, 5);
		g.fillText("Back to Main Menu", btnX + btnWidth/2, backY+btnHeight/2 + btnHeight*3/4, btnWidth);
		
		myCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				int x = (int)e.getSceneX();
				int y = (int)e.getSceneY();
				if(x > btnX && x < btnX + btnWidth){
					// Resume button
					if(y > resumeY && y < resumeY + btnHeight){
						// Succesfuly clicked button
						myCanvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
						start();
					} 
					// back button called
					else if(y > backY && y < backY + btnHeight){
						myCanvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
						PlayerBox.resetHumanPlayers();
						
						Stage s = (Stage)myCanvas.getScene().getWindow();
						if(s == null) { return; }
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StartFXML.fxml"));
						try {
							BorderPane root = loader.load();
							Scene scene = new Scene(root, s.getScene().getWidth(), s.getScene().getHeight());
							s.setScene(scene);
							s.centerOnScreen();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
	}
}
