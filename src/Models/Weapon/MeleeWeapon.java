package Models.Weapon;

import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MeleeWeapon extends Weapon{

	public MeleeWeapon(PlayableCharacter e, Image i) {
		super(e, i);
		setAttackTime(3 * 30);
	}

	@Override
	public HitBox attack() {
		HitBox h = null;
		if(getTimer() > getAttackTime()){
			setTimer(0);
			h = new HitBox(getOwnedEntity(), SpriteSheet.getBorderedBlock(20, 20, Color.WHITE));
			h.setLifeTime((int)(getAttackTime() * 1.05));
		}
		return h;
	}

}
