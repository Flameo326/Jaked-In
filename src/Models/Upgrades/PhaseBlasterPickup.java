package Models.Upgrades;

import Models.Players.PlayableCharacter;
import Models.Weapon.PhaseBlaster;
import SpriteSheet.SpriteSheet;

public class PhaseBlasterPickup extends Upgrade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhaseBlasterPickup(int x, int y) {
		super(SpriteSheet.getPhaseBlasterPickup(), x, y, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void collect(PlayableCharacter c) {
		if(c.getWeapon() instanceof PhaseBlaster){
			((PhaseBlaster)c.getWeapon()).addBullets(30);
		} else {
			c.addWeapon(new PhaseBlaster(c, 20));
		}
		isCollected = true;
	}

	@Override
	public void reverseEffect() {
		// do nothign
	}

}
