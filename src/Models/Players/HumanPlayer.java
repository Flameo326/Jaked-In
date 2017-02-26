package Models.Players;

import java.util.ArrayList;

import Controller.ArenaController;
import Controller.InputHandler;
import Enums.Direction;
import Models.Entity;
import Models.Weapon.HitBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class HumanPlayer extends PlayableCharacter{
	
	private static int humanID = 0;

	public HumanPlayer(Image i, int x, int y) {
		super(i, x, y);
		// Set Tag
		setTag("Human." + ++humanID);
		//setWeapon(new MeleeWeapon(this, SpriteSheet.getBorderedBlock(20, 20, Color.WHITE)));
		// Set Intiial Direction so it's not at 0, 0 or unmoving
		setCurrDir(Direction.RIGHT);
		setSpeed(3);
	}
	
	int generateTime = 0;
	// Right now Direction defaults to 0 if you are not moving, we should default it to a normal one if they haven't moved
	// and their last direction if they have
	@Override
	public void update(ArrayList<Entity> entities){
		if(getTag().equals("StoryHuman")){
			if(updateDirection(InputHandler.Player1Up, InputHandler.Player1Left, 
					InputHandler.Player1Down, InputHandler.Player1Right)){
				for(Entity e : entities){
					if(e != this){
						e.setYPos(e.getYPos() + (-getCurrDir().getY() * getSpeed()));
						e.setXPos(e.getXPos() + (-getCurrDir().getX() * getSpeed()));
					}
				}
			}
			if(InputHandler.keyInputContains(InputHandler.Player1Attack)){
				HitBox h = attack();
				if(h != null){
					System.out.println(getTag() + " attacked");
					entities.add(h);
				}
			}
			if(InputHandler.keyInputContains(KeyCode.CONTROL) && ++generateTime >= 50){
				generateTime = 0;
				entities.clear();
				entities.add(this);
				ArenaController.arenaMap.generateMap(70, 70, 210, 210, 2);
				ArenaController.entities.addAll(0, ArenaController.arenaMap.getMapObjects());
				for(Entity e : ArenaController.entities){
					if(e != this){
						e.setYPos(e.getYPos() + 250);
						e.setXPos(e.getXPos() + 250);
					}
				}
			}
		} else if(getTag().equals("Human.1")){
			if(updateDirection(InputHandler.Player1Up, InputHandler.Player1Left, 
					InputHandler.Player1Down, InputHandler.Player1Right)){
				move(this.getCurrDir().getX(), this.getCurrDir().getY());
			}
			if(InputHandler.keyInputContains(InputHandler.Player1Attack)){
				HitBox h = attack();
				if(h != null){
					System.out.println(getTag() + " attacked");
					entities.add(h);
				}
			}
		} else if(getTag().equals("Human.2")){
			if(updateDirection(InputHandler.Player1Up, InputHandler.Player1Left, 
					InputHandler.Player1Down, InputHandler.Player1Right)){
				move(this.getCurrDir().getX(), this.getCurrDir().getY());
			}
			if(InputHandler.keyInputContains(InputHandler.Player2Attack)){
				HitBox h = attack();
				if(h != null){
					System.out.println(getTag() + " attacked");
					entities.add(h);
				}
			}
		} else {
			System.out.println("Human Player Tag is " + getTag());
		}
		//System.out.println(getTag() + " X: " + getCenterXPos() + " Y: " + getCenterYPos());
	}
	
	public boolean updateDirection(KeyCode up, KeyCode left, KeyCode down, KeyCode right){
		byte xMovement = 0;
		byte yMovement = 0;
		if(InputHandler.keyInputContains(right)){ xMovement++; }
		if(InputHandler.keyInputContains(left)){ xMovement--; }
		if(InputHandler.keyInputContains(up)){ yMovement--; }
		if(InputHandler.keyInputContains(down)){ yMovement++; }
		boolean needsToMove = false;
		if(xMovement != 0 || yMovement != 0){
			this.setCurrDir(Direction.getDir(xMovement, yMovement));
			needsToMove = true;
		}
		return needsToMove;
	}
	
}
