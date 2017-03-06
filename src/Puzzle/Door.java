package Puzzle;

import java.util.ArrayList;

import Interfaces.Subscribable;
import Models.Collision;
import Models.Entity;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Door extends Entity implements Subscribable<Door>{
	
	private boolean isLocked = true;
	private Color doorColor;
	

	public Door(Image i, int x, int y, int width, int height, CombinedColor b) {
		super(i, x, y, width, height);
		doorColor = b.getSolutionColor();
		
	}

	@Override
	public void update(Door value) {
		isLocked = false;
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
