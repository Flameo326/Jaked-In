package Models.Players;

import java.util.ArrayList;

import Controller.InputHandler;
import Enums.Direction;
import Models.Entity;
import Models.Weapon.HitBox;
import Models.Weapon.MeleeWeapon;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class HumanPlayer extends PlayableCharacter{
	
	private static int humanID = 0;

	public HumanPlayer(Image i, int x, int y) {
		super(i, x, y);
		// Set Tag
		setTag("Human." + ++humanID);
		setWeapon(new MeleeWeapon(this, SpriteSheet.getBorderedBlock(20, 20, Color.WHITE)));
		// Set Intiial Direction so it's not at 0, 0 or unmoving
		setCurrDir(Direction.RIGHT);
	}
	
	// Right now Direction defaults to 0 if you are not moving, we should default it to a normal one if they haven't moved
	// and their last direction if they have
	@Override
	public void update(ArrayList<Entity> entities){
		if(getTag().equals("Human.1")){
			updateDirection(InputHandler.Player1Up, InputHandler.Player1Left, InputHandler.Player1Down, InputHandler.Player1Right);
			if(InputHandler.keyInputContains(InputHandler.Player1Attack)){
				HitBox h = attack();
				if(h != null){
					System.out.println(getTag() + " attacked");
					entities.add(h);
				}
			}
		} else if(getTag().equals("Human.2")){
			updateDirection(InputHandler.Player2Up, InputHandler.Player2Left, InputHandler.Player2Down, InputHandler.Player2Right);
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
		System.out.println(getTag() + " X: " + getCenterXPos() + " Y: " + getCenterYPos());
	}
	
	public void updateDirection(KeyCode up, KeyCode left, KeyCode down, KeyCode right){
		byte xMovement = 0;
		byte yMovement = 0;
		if(InputHandler.keyInputContains(right)){ xMovement++; }
		if(InputHandler.keyInputContains(left)){ xMovement--; }
		if(InputHandler.keyInputContains(up)){ yMovement--; }
		if(InputHandler.keyInputContains(down)){ yMovement++; }
		this.setCurrDir(Direction.getDir(xMovement, yMovement));
		move(this.getCurrDir().getX(), this.getCurrDir().getY());
	}
	
}
