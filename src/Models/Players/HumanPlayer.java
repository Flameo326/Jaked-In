package Models.Players;

import java.util.ArrayList;

import Controller.InputHandler;
import Models.Entity;
import Models.Weapon.HitBox;
import Models.Weapon.MeleeWeapon;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class HumanPlayer extends PlayableCharacter{
	
	private static int humanID = 0;

	public HumanPlayer(Image i, int x, int y) {
		super(i, x, y);
		// Set Tag
		setTag("Human." + ++humanID);
		setWeapon(new MeleeWeapon(this, SpriteSheet.getBorderedBlock(20, 20, Color.WHITE)));
		// Set Intiial Direction so it's not at 0, 0 or unmoving
		setCurrXDir(1);
	}
	
	// Right now Direction defaults to 0 if you are not moving, we should default it to a normal one if they haven't moved
	// and their last direction if they have
	@Override
	public void update(ArrayList<Entity> entities){
		if(getTag().equals("Human.1")){
			if(InputHandler.keyInputContains(InputHandler.Player1Up)){
				if(!InputHandler.keyInputContains(InputHandler.Player1Down)){
					move(0, -1);
					setCurrYDir(-1);
				}
			} else if(InputHandler.keyInputContains(InputHandler.Player1Down)){
				move(0, 1);
				setCurrYDir(1);
			} else {
				if(getCurrXDir() != 0){
					setCurrYDir(0);
				}
			}
			if(InputHandler.keyInputContains(InputHandler.Player1Left)){
				if(!InputHandler.keyInputContains(InputHandler.Player1Right)){
					move(-1, 0);
					setCurrXDir(-1);
				}
			} else if(InputHandler.keyInputContains(InputHandler.Player1Right)){
				move(1,  0);
				setCurrXDir(1);
			} else{
				if(getCurrYDir() != 0){
					setCurrXDir(0);
				}
			}
			if(InputHandler.keyInputContains(InputHandler.Player1Attack)){
				HitBox h = attack();
				if(h != null){
					System.out.println(getTag() + " attacked");
					entities.add(h);
				}
			}
		} else if(getTag().equals("Human.2")){
			if(InputHandler.keyInputContains(InputHandler.Player2Up)){
				if(!InputHandler.keyInputContains(InputHandler.Player2Down)){
					move(0, -1);
					setCurrYDir(-1);
				}
			} else if(InputHandler.keyInputContains(InputHandler.Player2Down)){
				move(0, 1);
				setCurrYDir(1);
			} else {
				setCurrYDir(0);
			}
			if(InputHandler.keyInputContains(InputHandler.Player2Left)){
				if(!InputHandler.keyInputContains(InputHandler.Player2Right)){
					move(-1, 0);
					setCurrXDir(-1);
				}
			} else if(InputHandler.keyInputContains(InputHandler.Player2Right)){
				move(1,  0);
				setCurrXDir(1);
			} else{
				setCurrXDir(0);
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
		System.out.println(getTag() + " X: " + getCenterXPos() + " Y: " + getCenterYPos());
	}
}
