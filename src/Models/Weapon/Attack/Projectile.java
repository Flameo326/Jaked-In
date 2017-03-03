package Models.Weapon.Attack;

import java.util.ArrayList;

import com.sun.javafx.image.PixelConverter;

import Enums.Direction;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;

public class Projectile extends Attack{
	
	private Direction direction;

	public Projectile(PlayableCharacter e, Image i) {
		super(e, i);
		setCurrDir(getOwnedEntity().getCurrDir());
		setSpeed(5);
		setTag(getTag() + "-Projectile");
	}

	// We can add a lifetime counter or something
	@Override
	public void update(ArrayList<Entity> entities) {
		super.update(entities);
		Image i = this.getImage();
		
//		this.setImage(new PixelWriter() i.getPixelReader().);
		move(getCurrDir().getX(), getCurrDir().getY());
	}
	
	@Override 
	public void hasCollided(Collision c){
		// Test for damage in parent Class
		super.hasCollided(c);
		
		Entity collider;
		if(c.collidedEntity == this){
			collider = c.collidingEntity;
		} else { collider = c.collidedEntity; }
		
		String[] tagElements = collider.getTag().split("-");
//		String[] ourElements = getTag().split("-");
		
		switch(tagElements[0]){
		// If I collide against these then just move away
		case "Attack":
		case "Wall":
			if(c.xPenDepth < c.yPenDepth){
				setXPos(getXPos() + (-getCurrDir().getX() * c.xPenDepth) * 2);
				setCurrDir(Direction.getDir(-getCurrDir().getX(), getCurrDir().getY()));
			} else {
				setYPos(getYPos() + (-getCurrDir().getY() * c.yPenDepth) * 2);
				setCurrDir(Direction.getDir(getCurrDir().getX(), -getCurrDir().getY()));
			}
			break;
		}
	}
	
	public void setCurrDir(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getCurrDir(){
		return direction;
	}

}
