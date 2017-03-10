package Models.Upgrades;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class DamageReduction extends Upgrade{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DamageReduction(int x, int y, boolean permanent) {
		super(SpriteSheet.getDamageReduction(), x, y, permanent);
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
			c.setDamageReduction(c.getDamageReduction() + getBonus());
		} else {
			setBoostExpiration();
			c.setBonusReduction(c.getBonusReduction() + getBonus());
			c.setBonusReductionLength(getBoostExpiration());
		}
		
		setImage(SpriteSheet.getBlank());
		isCollected = true;
	}

//	@Override
//	public void reverseEffect() {
//		getPlayerAffected().setBonusReduction(getPlayerAffected().getBonusReduction() - getBonus());
//		if(getPlayerAffected().getBonusReductionLength() == getBoostExpiration()){
//			getPlayerAffected().setBonusReductionLength(0);
//		}	
//	}

}
