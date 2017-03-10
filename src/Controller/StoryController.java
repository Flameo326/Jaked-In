package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Cutscene.Introduction;
import Interfaces.Subscribable;
import Models.Entity;
import Models.Map.Floor1Map;
import Models.Map.Floor2Map;
import Models.Map.Floor3Map;
import Models.Map.Floor4Map;
import Models.Map.Floor5Map;
import Models.Map.Floor6Map;
import Models.Map.Floor7Map;
import Models.Map.Map;
import Models.Map.MapGeneratorThread;
import Models.Players.HumanPlayer;
import Models.Players.PlayableCharacter;
import Models.Upgrades.Upgrade;
import Models.Weapon.ProjectileWeapon;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;

public class StoryController implements Initializable, Subscribable<PlayableCharacter>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML
	private transient Canvas myCanvas;
	private transient GameController gc;
	private HumanPlayer player1;
	private Floor1Map[] levels;
	private long seed;
	private long upgradeTime, enemyTime;
	private int currentLevel;
	private int lives;
	
	public void newStory(){
		seed = Map.setRandomSeed();
		generateLevels();	
		lives = 5;
		player1 = new HumanPlayer(SpriteSheet.getRandomPlayer(), 0, 0);
		
		createGameController();
	}
	
	public void newStory(long l){
		seed = l;
		Map.setSeed(l);
		generateLevels();	
		lives = 5;
		player1 = new HumanPlayer(SpriteSheet.getRandomPlayer(), 0, 0);
		
		createGameController();
	}
	
	public void generateLevels(){
		levels = new Floor1Map[7];

		levels[0] = new Floor1Map(this, 500, 500);
		levels[1] = new Floor2Map(this, 500, 500);
		levels[2] = new Floor3Map(this, 500, 500);
		levels[3] = new Floor4Map(this, 500, 500);
		levels[4] = new Floor5Map(this, 500, 500);
		levels[5] = new Floor6Map(this, 500, 500);
		levels[6] = new Floor7Map(this, 500, 500);

		levels[0].generateMap();
		new Thread(new MapGeneratorThread(levels[1])).start();
		new Thread(new MapGeneratorThread(levels[2])).start();
		new Thread(new MapGeneratorThread(levels[3])).start();
		new Thread(new MapGeneratorThread(levels[4])).start();
		new Thread(new MapGeneratorThread(levels[5])).start();
		new Thread(new MapGeneratorThread(levels[6])).start();
	}
	
	public void createGameController(){
		gc = new GameController(myCanvas, true);
		gc.attach(this);
		gc.addEntity(player1.getDisplayableEntities());
		gc.addPlayer(player1);
		gc.setFocus(player1);
		gc.addEntity(levels[currentLevel].getMapObjects().toArray(new Entity[0]));
		start();
		startCutscene(new Introduction(this));
	}
	
	// Cutscenes need to be instantiated with the StoryController
	// Because we can not subscribe to them
	// Because java... pretty sure c++ can do that
	public void startCutscene(Cutscene c){
		stop();
		c.start();
	}
	
	public void start(){
		gc.start();
	}
	
	public void stop(){
		gc.stop();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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

	//2359911335623836
	
	@Override
	public void update(PlayableCharacter value) {
		displayHud();
		
		if(InputHandler.keyInputContains(KeyCode.ESCAPE)) { displayEscapeMenu(); }

		if(GameController.getTimer() >= upgradeTime){
			// avg of 20 seconds
			upgradeTime = GameController.getTimer()+ 10000000000l;
			Upgrade u = levels[currentLevel].generateRandomUpgrade();
			if(u != null) { 
				levels[currentLevel].getMapObjects().add(u);
				gc.addEntity(u); 
			}
		}
		if(GameController.getTimer() >= enemyTime){
			// Get current time + 30 seconds to 3 minutes ahead
			enemyTime = GameController.getTimer()+ 20000000000l/(currentLevel+1);
			PlayableCharacter enemy = levels[currentLevel].generateRandomEnemy();
			if(enemy != null) { 
				levels[currentLevel].addEntity(enemy.getDisplayableEntities());
				gc.addEntity(enemy.getDisplayableEntities()); 
			}
		}
		
		if(!value.isAlive()){
			System.out.println("Live lost"); 
			if(--lives <= 0){
				// displayLoss();
				displayGameOver();
				gc.stop();
			} else {
				// beginning of level?
				startCutscene(new DialogCutscene(this, .5, "You have Died\n\n"+lives+" Memory Units Remaining"));
				player1.setXPos(levels[currentLevel].getExit().getXPos());
				player1.setYPos(levels[currentLevel].getExit().getYPos());
				player1.setCurrentHealth(player1.getMaxHealth());
			}
		}
	}

	// Cutscenes will call this method to tell the Controller the Cutscene is over
	public void update(Boolean b) {
		if(b){ start(); }
	}
	
	public void setCanvas(Canvas c){
		myCanvas = c;
		myCanvas.widthProperty().bind(((Stage)c.getScene().getWindow()).widthProperty());
		myCanvas.heightProperty().bind(((Stage)c.getScene().getWindow()).heightProperty());
		// Neccesary
		myCanvas.setFocusTraversable(true);
		// KeyEvent to record input while playing
		myCanvas.setOnKeyPressed((e) -> InputHandler.keyPress(e));
		myCanvas.setOnKeyReleased((e) -> InputHandler.keyRelease(e));
	}
	
	public void displayHud(){
		GraphicsContext g = myCanvas.getGraphicsContext2D();
		
		// health
		// Weapon -Damage + Time - Bullets
		// Speed
		// Reduction
		// ForceField
		
		g.setFill(Color.rgb(0, 0, 0, .5));
		g.fillRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight()/5);
		
		g.setFont(new Font(20));
		g.setFill(Color.WHITE);
		g.fillText("Level " +(currentLevel+1), myCanvas.getWidth()*7/8, myCanvas.getHeight()/16);
		
		// Health
		g.fillText("HP: " + player1.getCurrentHealth() + "/" + player1.getMaxHealth(),
				myCanvas.getWidth()/4, 35);

		// Construct the Weapontext
		String weaponText = player1.getWeapon().getClass().getSimpleName()
				.replace("Weapon", "").replace("Normal", "").replace("\\wProjectile", "");
		String bullets = "";
		if(player1.getWeapon() instanceof ProjectileWeapon){
			bullets = ((ProjectileWeapon)player1.getWeapon()).getBullets()+"";
		} else {
			bullets = "\u221e";
		}
		
		// Display Weapon
		g.fillText(weaponText + ": " + bullets, myCanvas.getWidth()/4, 50);
		
		String damage = (player1.getWeapon().getDamage() + player1.getBaseDamage())
				+"+(" + player1.getBonusDamage() + ") " + 
				(player1.getBonusDamageLength() != 0 ? getFormattedTime(player1.getBonusDamageLength()) : "");
		
		// Display Damage
		g.fillText("Damage: " + damage, myCanvas.getWidth()/4, 65);
		
		// Display Speed
		g.fillText("Speed: " + player1.getSpeed() + "+(" + player1.getBonusSpeed() + ") " +
				(player1.getBonusSpeedLength() != 0 ? getFormattedTime(player1.getBonusSpeedLength()) : ""),
				myCanvas.getWidth()/4, 80);
		
		int y = 95;
		
		// Display Shield
		if(player1.getDamageReduction() != 0 || player1.getBonusReduction() != 0){
			g.fillText("Shield: " + player1.getDamageReduction() + 
					"+(" + player1.getBonusReduction() + ") " + (player1.getBonusReductionLength()!=0 ?
							getFormattedTime(player1.getBonusReductionLength()) : ""),
							myCanvas.getWidth()/4, y);
			y += 15;
		}
		
		// Display ForceField
		if(player1.getForceFieldLength() != 0){
			g.fillText("ForceField: " + getFormattedTime(player1.getForceFieldLength()),
					myCanvas.getWidth()/4, y);
			y += 15;
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
		
		int saveY = resumeY + btnHeight*2;
		g.strokeRoundRect(btnX, saveY+btnHeight/2, btnWidth, btnHeight, 5, 5);
		g.fillText("Save", btnX + btnWidth/2, saveY+btnHeight/2 + btnHeight*3/4, btnWidth);
		
		int backY = saveY + btnHeight*2;
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
						HumanPlayer.resetHumanID();
						Stage s = (Stage)myCanvas.getScene().getWindow();
						if(s == null) { return; }
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StartFXML.fxml"));
						BorderPane root = null;
						try {
							root = loader.load();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
						Scene scene = new Scene(root, s.getScene().getWidth(), s.getScene().getHeight());
						s.setScene(scene);
						s.centerOnScreen();
					}
					// Save button clicked
					else if(y > saveY && y < saveY + btnHeight){
						g.clearRect(btnX, saveY+btnHeight/2, btnWidth, btnHeight);
						g.strokeRoundRect(btnX, saveY+btnHeight/2, btnWidth, btnHeight, 5, 5);
						g.fillText("Saving", btnX + btnWidth/2, saveY+btnHeight/2 + btnHeight*3/4, btnWidth);
						try {
							save();
							g.clearRect(btnX, saveY+btnHeight/2, btnWidth, btnHeight);
							g.strokeRoundRect(btnX, saveY+btnHeight/2, btnWidth, btnHeight, 5, 5);
							g.fillText("Save Succesful", btnX + btnWidth/2, saveY+btnHeight/2 + btnHeight*3/4, btnWidth);
						} catch (IOException e1) {
							e1.printStackTrace();
							g.clearRect(btnX, saveY+btnHeight/2, btnWidth, btnHeight);
							g.strokeRoundRect(btnX, saveY+btnHeight/2, btnWidth, btnHeight, 5, 5);
							g.fillText("Save Failed", btnX + btnWidth/2, saveY+btnHeight/2 + btnHeight*3/4, btnWidth);
						}
					}
				}
			}
		});
	}
	
	public void displayGameOver(){
		int width = (int) myCanvas.getWidth();
		int height = (int) myCanvas.getHeight();
		GraphicsContext g = myCanvas.getGraphicsContext2D();
		
		g.setFill(Color.WHITE);
		g.fillRect(width/4, height/8, width/2, height*3/4);
		
		g.setStroke(Color.BLACK);
		g.strokeRect(width/4, height/8, width/2, height*3/4);
		
		g.setFont(new Font(48));
		g.setFill(Color.BLACK);
		g.setTextAlign(TextAlignment.CENTER);
		g.fillText("Game Over!", width/2, height*2/8);
		
		int btnWidth = width*2/5;
		int btnHeight =  height/16; 
		int btnX = width/2 - btnWidth/2;
		
		g.setFont(new Font(32));
		int backY = height*13/16 - btnHeight;
		g.strokeRoundRect(btnX, backY+btnHeight/2, btnWidth, btnHeight, 5, 5);
		g.fillText("Back to Main Menu", btnX + btnWidth/2, backY+btnHeight/2 + btnHeight*3/4, btnWidth);
		
		myCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				int x = (int)e.getSceneX();
				int y = (int)e.getSceneY();
				if(x > btnX && x < btnX + btnWidth){
					// back button called
					if(y > backY && y < backY + btnHeight){
						HumanPlayer.resetHumanID();
						Stage s = (Stage)myCanvas.getScene().getWindow();
						if(s == null) { return; }
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StartFXML.fxml"));
						BorderPane root = null;
						try {
							root = loader.load();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
						Scene scene = new Scene(root, s.getScene().getWidth(), s.getScene().getHeight());
						s.setScene(scene);
						s.centerOnScreen();
					}
				}
			}
		});
	}
	
	public void changeLevel(int i){
		stop();
		gc.removeEntity(levels[currentLevel].getMapObjects().toArray(new Entity[0]));
		int previousLevel = currentLevel;
		currentLevel = i;
		gc.addEntity(levels[currentLevel].getMapObjects().toArray(new Entity[0]));
		
		// Went up
		if(i > previousLevel){
			player1.setXPos(levels[currentLevel].getExit().getXPos());
			player1.setYPos(levels[currentLevel].getExit().getYPos());
		} 
		// Went down
		else if(i < previousLevel){
			player1.setXPos(levels[currentLevel].getEntrance().getXPos());
			player1.setYPos(levels[currentLevel].getEntrance().getYPos());
		}
		start();
	}
	
	public Canvas getCanvas(){
		return myCanvas;
	}
	
	public int getCurrentLevel(){
		return currentLevel;
	}
	
	public void save() throws FileNotFoundException, IOException{
		ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream("src/Other/text.txt"));
		write.writeLong(seed);
		write.writeObject(this);
		write.close();
	}
	
	public static StoryController load(){
		StoryController controller = null;
		long seed = 0;
		
		// Attempt to read the object
		// If we can not get the file or seed then null is returned
		// If we faile to grab the Controller but seed is known, 
		// --- construct a new controller based on that seed
		// If we get the controller then that is the controller we return
		try {
			ObjectInputStream read = new ObjectInputStream(new FileInputStream("src/Other/text.txt"));
			seed = read.readLong();
			
			try{
				controller = (StoryController) read.readObject();
			} catch(Exception e){	
				controller = new StoryController();
				controller.newStory(seed);
			}
			
			read.close();
		} catch (IOException e1) {}
		
		
		return controller;
	}
	
	public String getFormattedTime(long l){
//		String s = "";
//		
		long timeBetween = l - GameController.getTimer();
		
		long minute;
		timeBetween -= (minute = timeBetween/60000000000l);
		
		long seconds;
		timeBetween -= (seconds = timeBetween/1000000000l);
		
		return minute + ":" + seconds;
	}
}
