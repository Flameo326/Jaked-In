package Models.Weapon;

import java.util.ArrayList;

import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class Projectile extends HitBox{
	
	private int yDir, xDir;

	public Projectile(PlayableCharacter e, Image i) {
		super(e, i);
		yDir = getOwnedEntity().getCurrYDir();
		xDir = getOwnedEntity().getCurrXDir();
		setSpeed(5);
		setTag(getOwnedEntity().getTag() + ".Projectile");
	}

	// We can add a lifetime counter or something
	@Override
	public void update(ArrayList<Entity> entities) {
		incrementTimer();
		if(getTimer() >= getLifeTime()){
			entities.remove(this);
		}
		move(xDir, yDir);
	}

}
