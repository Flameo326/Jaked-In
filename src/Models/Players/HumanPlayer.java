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
		//setWeapon(new MeleeWeapon(this, SpriteSheet.getBorderedBlock(20, 20, Color.WHITE)));
		// Set Intiial Direction so it's not at 0, 0 or unmoving
		setCurrXDir(1);
	}
	
	// Right now Direction defaults to 0 if you are not moving, we should default it to a normal one if they haven't moved
	// and their last direction if they have
	@Override
	public void update(ArrayList<Entity> entities){
		if(getTag().equals("StoryHuman")){
			if(InputHandler.keyInputContains(InputHandler.Player1Up)){
				if(!InputHandler.keyInputContains(InputHandler.Player1Down)){
					for(Entity e : entities){
						if(e != this){
							e.setYPos(e.getYPos() + (1 * getSpeed()));
						}
					}
					setCurrYDir(-1);
				}
			} else if(InputHandler.keyInputContains(InputHandler.Player1Down)){
				for(Entity e : entities){
					if(e != this){
						e.setYPos(e.getYPos() + (-1 * getSpeed()));
					}
				}
				setCurrYDir(1);
			} else {
				if(getCurrXDir() != 0){
					setCurrYDir(0);
				}
			}
			if(InputHandler.keyInputContains(InputHandler.Player1Left)){
				if(!InputHandler.keyInputContains(InputHandler.Player1Right)){
					for(Entity e : entities){
						if(e != this){
							e.setXPos(e.getXPos() + (1 * getSpeed()));
						}
					}
					setCurrXDir(-1);
				}
			} else if(InputHandler.keyInputContains(InputHandler.Player1Right)){
				for(Entity e : entities){
					if(e != this){
						e.setXPos(e.getXPos() + (-1 * getSpeed()));
					}
				}
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
		}
		// To Fix the Direction, I would need to check every possible collection of key inputs they could have for input
		// For example, if they are pressing W then they shoot up
		// If they press A and W then they shoot left up
		// If they press A, S, and W then they shoot... UP?
		// What if I made it so that InputHandler can test combinations of keys
		// For example, A means -1, D means 1 Both means 2 and neither means 0
		// Or should Both be 0?
		
		// Also, this is getting long and complaicated for a single method, if we could break it up it would be better, 
		// If we could maybe have it as a Controller then it would be even better, reuseable, and generalized.
		// For example, in the case of computer, we can just give it the AI it needs
		else if(getTag().equals("Human.1")){
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
		//System.out.println(getTag() + " X: " + getCenterXPos() + " Y: " + getCenterYPos());
	}
}
