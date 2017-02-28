package Models.NPCs;

import java.util.ArrayList;
import java.util.Random;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class MedicNPC extends Entity implements Interactable {

	public MedicNPC(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
	}
	
	public String dialogue(){
		//Will this have access to the player HP? If so will need a if/if else statement.
		String response = "You are hurt! This will help.";
		return response;
	}
	
	public String callPlayer(){
		return "OVER HERE!!";
	}

	@Override
	public void interact(PlayableCharacter c) {
		Random randy = new Random();
		c.heal(randy.nextInt(21) + 20);

	}

	@Override
	public void hasCollided(Collision c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub
		
	}

}
