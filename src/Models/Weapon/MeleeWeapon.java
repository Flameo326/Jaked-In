package Models.Weapon;

import Controller.GameController;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.HitBox;
import SpriteSheet.SpriteSheet;

public class MeleeWeapon extends Weapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MeleeWeapon(PlayableCharacter e) {
		super(e, SpriteSheet.getMeleeAttackImage());
		setAttackTime(3 * 30);
	}

	@Override
	public HitBox attack() {
		HitBox h = null;
		if(GameController.getTimer() > getNextAttackTime()){
			setNextAttackTime();
			h = new HitBox(getOwnedEntity());
			h.setLifeTime((int)(getAttackTime() * .8));
		}
		return h;
	}
	
	public int getDamage(){
		return 20;
	}

}
