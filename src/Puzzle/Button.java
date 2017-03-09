package Puzzle;

import java.util.ArrayList;

import Interfaces.Interactable;
import Interfaces.Subscribable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class Button extends Entity implements Interactable, Subscribable<Button>{
	
	protected int timer = 0;
	
	public Button(Image i, int x, int y) {
		super(i, x, y);

	}

	@Override
	public void interact(PlayableCharacter c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hasCollided(Collision c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		timer++;
		
	}

	@Override
	public void update(Button value) {
		// TODO Auto-generated method stub
		
	}

}
