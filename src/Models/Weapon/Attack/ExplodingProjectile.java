package Models.Weapon.Attack;

import java.util.ArrayList;

import Enums.Direction;
import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ExplodingProjectile extends Projectile {

	public ExplodingProjectile(PlayableCharacter e, Image i) {
		super(e, i);
		setCurrDir(Direction.getDir(getOwnedEntity().getCurrDir().getX(), getOwnedEntity().getCurrDir().getY()));
		setSpeed(5);
		setTag(getTag() + "-Projectile");
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		if(++timer >= lifeTime || !hasHit.isEmpty()){
			for(int i = 0; i < 8; i++){
				Projectile p = new Projectile(getOwnedEntity(), SpriteSheet.getBlock(5, 5, Color.BLACK));
				p.setXPos(this.getXPos());
				p.setYPos(this.getYPos());
				p.setCurrDir(Direction.values()[i < 4 ? i : i + 1]);
				p.setLifeTime((int)(20 * 3.33));
				entities.add(p);
			}
			entities.remove(this);
		}
		move(entities);
	}

}
