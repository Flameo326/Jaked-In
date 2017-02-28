package Models.Weapon;

import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.HitBox;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MeleeWeapon extends Weapon{

	public MeleeWeapon(PlayableCharacter e, Image i) {
		super(e, i);
		setAttackTime(3 * 30);
		// Otherwise yoou can't attack right off the bat... 
		// Maybe some simpler way to fix?
		setTimer(getAttackTime());
	}

	@Override
	public HitBox attack() {
		HitBox h = null;
		if(getTimer() > getAttackTime()){
			setTimer(0);
			h = new HitBox(getOwnedEntity(), SpriteSheet.getBorderedBlock(20, 20, Color.WHITE, 3));
			h.setLifeTime((int)(getAttackTime() * .8));
		}
		return h;
	}

}
