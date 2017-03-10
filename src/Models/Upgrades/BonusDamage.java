package Models.Upgrades;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class BonusDamage extends Upgrade{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BonusDamage(int x, int y, boolean permanent) {
		super(SpriteSheet.getBonusDamage(), x, y, permanent);
		if(permanent){
			setBonus(2);
		} else {
			setBonus(5);
			setLength(15);
		}
	}

	@Override
	public void collect(PlayableCharacter c) {
		setPlayerAffected(c);
		if(isPermanent()){
			c.setBaseDamage(c.getBaseDamage() + getBonus());
		} else {
			setBoostExpiration();
			c.setBonusDamage(c.getBonusDamage() + getBonus());
			c.setBonusDamageLength(getBoostExpiration());
		}
		
		setImage(SpriteSheet.getBlank());
		isCollected = true;
	}

	@Override
	public void reverseEffect() {
		getPlayerAffected().setBonusDamage(getPlayerAffected().getBonusDamage() - getBonus());
		if(getPlayerAffected().getBonusDamageLength() == getBoostExpiration()){
			getPlayerAffected().setBonusDamageLength(0);
		}
	}

}
