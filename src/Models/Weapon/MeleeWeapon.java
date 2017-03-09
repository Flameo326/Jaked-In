package Models.Weapon;

import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.HitBox;
import SpriteSheet.SpriteSheet;

public class MeleeWeapon extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MeleeWeapon(PlayableCharacter e) {
		super(e, SpriteSheet.getMeleeWeapon());
		setAttackTime(3 * 30);
		setTimer(getAttackTime());
	}

	@Override
	public HitBox attack() {
		HitBox h = null;
		if(getTimer() > getAttackTime()){
			setTimer(0);
			h = new HitBox(getOwnedEntity(), SpriteSheet.getMeleeWeapon());
			h.setLifeTime((int)(getAttackTime() * .8));
		}
		return h;
	}

}
