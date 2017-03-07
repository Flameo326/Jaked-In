package Models.Players;

import java.util.ArrayList;

import Controller.GameController;
import Controller.InputHandler;
import Enums.Direction;
import Interfaces.Interactable;
import Models.Entity;
import Models.Weapon.NormalProjectileWeapon;
import Models.Weapon.Attack.Attack;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class HumanPlayer extends PlayableCharacter{
	
	private static int humanID = 0;
	private long weaponTimer;

	public HumanPlayer(Image i, int x, int y) {
		super(i, x, y);
		setTag("Human-" + ++humanID);
		addWeapon(new NormalProjectileWeapon(this, 30));
	}
	
	@Override
	public void update(ArrayList<Entity> entities){
		super.update(entities);
		if(getTag().equals("Human-1")){
			if(updateDirection(InputHandler.Player1Up, InputHandler.Player1Left, 
					InputHandler.Player1Down, InputHandler.Player1Right)){
				move(entities);
			}
			if(InputHandler.keyInputContains(InputHandler.Player1Attack)){
				Attack h = attack();
				if(h != null){
//					System.out.println(getTag() + " attacked");
					entities.add(h);
				}
			}
			if(GameController.getStoryMode() && InputHandler.keyInputContains(InputHandler.ChangeWeapon)
					&& GameController.getTimer() >= weaponTimer){
				changeWeapon();
				weaponTimer = GameController.getTimer() + 500000000l;
			}
			if(GameController.getStoryMode() && InputHandler.keyInputContains(InputHandler.Interact)){
				checkForInteraction();
			}
		} else if(getTag().equals("Human-2")){
			if(updateDirection(InputHandler.Player2Up, InputHandler.Player2Left, 
					InputHandler.Player2Down, InputHandler.Player2Right)){
				move(entities);
			}
			if(InputHandler.keyInputContains(InputHandler.Player2Attack)){
				Attack h = attack();
				if(h != null){
//					System.out.println(getTag() + " attacked");
					entities.add(h);
				}
			}
		} else {
//			System.out.println("Human Player Tag is " + getTag());
		}
	}
	
	public void checkForInteraction(){
		for(Entity e : getColliders()){
			if(e instanceof Interactable){
				((Interactable)e).interact(this);
			}
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
	
	public static void resetHumanID(){
		humanID = 0;
	}
	
}
