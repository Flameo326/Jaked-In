package Models.Players;

import java.util.ArrayList;

import Controller.GameController;
import Models.Collision;
import Models.Entity;
import SpriteSheet.SpriteSheet;

public class ForceField extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlayableCharacter c;
	private long expirationTime;

	public ForceField(PlayableCharacter c, int x, int y, int length) {
		super(SpriteSheet.getForceField(), x, y);
		this.c = c;
		setDisplayLayer(8);
		setExpirationLength(length);
		setTag("Wall-ForceField-"+c.getTag());
		
		c.setForceFieldLength(expirationTime);
	}

	@Override
	public void hasCollided(Collision c) {
		// do nothing
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		setXPos(c.getXPos());
		setYPos(c.getYPos());
		if(GameController.getTimer() >= expirationTime){
			entities.remove(this);
			if(c.getForceFieldLength() == expirationTime){
				c.setForceFieldLength(0);
			}
		}
	}
	
	public void setExpirationLength(int v){
		expirationTime = GameController.getTimer() + 1000000000l * v;
	}

}
