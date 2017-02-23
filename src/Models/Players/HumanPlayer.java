package Models.Players;

import java.util.ArrayList;

import Controller.InputHandler;
import Models.Entity;
import Models.Weapon.HitBox;
import javafx.scene.image.Image;

public class HumanPlayer extends PlayableCharacter{
	
	private static int humanID = 0;

	public HumanPlayer(Image i, int x, int y) {
		super(i, x, y);
		setTag("Human." + ++humanID);
	}
	
	// Slight Problem here
	// If we want multiple Human Characters, they each have the same update Method. Can we change them for two player?
	@Override
	public void update(ArrayList<Entity> entities){
		if(getTag().equals("Human.1")){
			if(InputHandler.keyInputContains(InputHandler.Player1Up)){
				move(0, -1);
			} 
			if(InputHandler.keyInputContains(InputHandler.Player1Down)){
				move(0, 1);
			}
			if(InputHandler.keyInputContains(InputHandler.Player1Left)){
				move(-1, 0);
			}
			if(InputHandler.keyInputContains(InputHandler.Player1Right)){
				move(1,  0);
			}
			if(InputHandler.keyInputContains(InputHandler.Player1Attack)){
				System.out.println(getTag() + " attacked");
				HitBox h = attack();
				if(h != null){
					entities.add(h);
				}
			}
		} else if(getTag().equals("Human.2")){
			if(InputHandler.keyInputContains(InputHandler.Player2Up)){
				move(0, -1);
			} 
			if(InputHandler.keyInputContains(InputHandler.Player2Down)){
				move(0, 1);
			}
			if(InputHandler.keyInputContains(InputHandler.Player2Left)){
				move(-1, 0);
			}
			if(InputHandler.keyInputContains(InputHandler.Player2Right)){
				move(1,  0);
			}
			if(InputHandler.keyInputContains(InputHandler.Player2Attack)){
				HitBox h = attack();
				if(h != null){
					entities.add(h);
				}
			}
		} else {
			System.out.println("Human Player Tag is " + getTag());
		}
		
	}

	@Override
	public HitBox attack() {
		if(getWeapon() != null){
			return getWeapon().attack();
		} else {
			System.out.println(getTag() + "Weapon is Null"); 
			return null;
		}
		
	}	

}
