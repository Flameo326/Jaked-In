package Models.Upgrades;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class SpeedBoost extends Upgrade{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpeedBoost(int x, int y, boolean permanent) {
		super(SpriteSheet.getSpeedBoost(), x, y, permanent);
		this.permanent = permanent;
		if(permanent){
			setBonus(1);
		} else {
			setBonus(2);
			setLength(30);
		}
	}
	
	// instead we could check if the boost is permanent, 
	// if it is then isCollected is true,
	// otherwise we call setTimer() and check to see when it has expired...
	@Override 
	public void collect(PlayableCharacter c){
		setPlayerAffected(c);
		if(isPermanent()){
			c.setSpeed(c.getSpeed() + getBonus());
		} else {
			setBoostExpiration();
			c.setBonusSpeed(c.getBonusSpeed() + getBonus());
			c.setBonusSpeedLength(getBoostExpiration());
		}
		
		setImage(SpriteSheet.getBlank());
		isCollected = true;
	}
	
	@Override
	public void reverseEffect(){
		getPlayerAffected().setBonusSpeed(getPlayerAffected().getBonusSpeed() - getBonus());
		if(getPlayerAffected().getBonusSpeedLength() == getBoostExpiration()){
			getPlayerAffected().setBonusSpeedLength(0);
		}
		System.out.println("Removed boost from " + getPlayerAffected().getTag() + " with boost of " + getBonus());
	}

}
