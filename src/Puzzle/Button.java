package Puzzle;

import java.util.ArrayList;

import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class Button extends Entity implements Interactable{
	
	public Button(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);

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
		// TODO Auto-generated method stub
		
	}

}
