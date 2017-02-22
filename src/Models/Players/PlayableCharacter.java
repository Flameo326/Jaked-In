package Models.Players;

import Interfaces.Collideable;
import Models.Bounds;
import Models.Entity;
import javafx.scene.image.Image;

public class PlayableCharacter extends Entity {
	
	private int speed = 1;

	public PlayableCharacter(Image i, int x, int y) {
		super(i, x, y);
	}
	// It may be best to define this is Entity so the same rules apply to other Entities
	// Maybe...
	@Override
	public void move(int x, int y) {
		setXPos(getXPos() + x * getSpeed());
		setYPos(getYPos() + y * getSpeed());
	}
	
	public void setSpeed(int val){
		speed = val;
	}
	
	public int getSpeed(){
		return speed;
	}

	@Override
	public boolean isColliding(Collideable c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public Bounds getBounds() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}
}
