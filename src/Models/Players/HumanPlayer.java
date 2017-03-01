package Models.Players;

import java.util.ArrayList;

import Controller.InputHandler;
import Enums.Direction;
import Models.Entity;
import Models.Weapon.Attack.Attack;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class HumanPlayer extends PlayableCharacter{
	
	private static int humanID = 0;

	public HumanPlayer(Image i, int x, int y) {
		super(i, x, y);
		// Set Intiial Direction so it's not at 0, 0 or unmoving
		setCurrDir(Direction.RIGHT);
		setTag("Human-" + ++humanID);
		//setWeapon(new MeleeWeapon(this, SpriteSheet.getBorderedBlock(20, 20, Color.WHITE, 3)));
	}
	
	@Override
	public void update(ArrayList<Entity> entities){
		if(getTag().equals("Human-1")){
			if(updateDirection(InputHandler.Player1Up, InputHandler.Player1Left, 
					InputHandler.Player1Down, InputHandler.Player1Right)){
				move(this.getCurrDir().getX(), this.getCurrDir().getY());
			}
			if(InputHandler.keyInputContains(InputHandler.Player1Attack)){
				Attack h = attack();
				if(h != null){
					System.out.println(getTag() + " attacked");
					entities.add(h);
				}
			}
		} else if(getTag().equals("Human-2")){
			if(updateDirection(InputHandler.Player2Up, InputHandler.Player2Left, 
					InputHandler.Player2Down, InputHandler.Player2Right)){
				move(this.getCurrDir().getX(), this.getCurrDir().getY());
			}
			if(InputHandler.keyInputContains(InputHandler.Player2Attack)){
				Attack h = attack();
				if(h != null){
					System.out.println(getTag() + " attacked");
					entities.add(h);
				}
			}
		} else {
			System.out.println("Human Player Tag is " + getTag());
		}
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
