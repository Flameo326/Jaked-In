package Models.Weapon.Attack;

import java.util.ArrayList;

import Enums.Direction;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class Projectile extends Attack{
	
	private Direction direction;

	public Projectile(PlayableCharacter e, Image i) {
		super(e, i);
		setCurrDir(Direction.getDir(getOwnedEntity().getCurrDir().getX(), getOwnedEntity().getCurrDir().getY()));
		setSpeed(5);
		setTag(getTag() + "-Projectile");
	}

	// We can add a lifetime counter or something
	@Override
	public void update(ArrayList<Entity> entities) {
		super.update(entities);
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
				setXPos(getXPos() + c.collisionNormal.getX() * c.xPenDepth);
				setCurrDir(Direction.getDir(-getCurrDir().getX(), getCurrDir().getY()));
			} else {
				setYPos(getYPos() + c.collisionNormal.getY() * c.yPenDepth);
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
