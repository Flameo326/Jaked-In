package Models.Upgrades;

import java.util.ArrayList;

import Models.Entity;
import Models.Players.ForceField;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class ForceFieldReflection extends Upgrade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entity forceField;

	public ForceFieldReflection(int x, int y) {
		super(SpriteSheet.getForceFieldPickup(), x, y, true);
		setLength(15);
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		if(isCollected){
			entities.add(forceField);
			entities.remove(this);
		}
	}

	@Override
	public void collect(PlayableCharacter c) {
		forceField = new ForceField(c, c.getXPos(), c.getYPos(), getLength());
		isCollected = true;
	}

//	@Override
//	public void reverseEffect() {
//		// TODO Auto-generated method stub
//		
//	}

}
