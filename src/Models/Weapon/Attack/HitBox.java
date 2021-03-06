package Models.Weapon.Attack;

import java.util.ArrayList;

import Enums.Direction;
import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;

public class HitBox extends Attack{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HitBox(PlayableCharacter e) {
		super(e, SpriteSheet.getMeleeAttackImage());
		setTag(getTag() + "-Melee");
		setDamage(getDamage() + 20);
		setCurrDir(Direction.getDir(0, 0));
	}
	
	// Position the hitBox in the direction that the object was moving
	@Override
	public void update(ArrayList<Entity> entities) {
		super.update(entities);
		int xDir = getOwnedEntity().getCurrDir().getX();
		int yDir = getOwnedEntity().getCurrDir().getY();
		if(xDir > 0){
			setXPos(getOwnedEntity().getXPos() + getOwnedEntity().getWidth()/2 + getWidth()/2);
		} else if(xDir < 0){
			setXPos(getOwnedEntity().getXPos() - getOwnedEntity().getWidth()/2 - getWidth()/2);
		} else {
			setXPos(getOwnedEntity().getXPos());
		}
		if(yDir > 0){
			setYPos(getOwnedEntity().getYPos() + getOwnedEntity().getHeight()/2 + getHeight()/2);
		} else if(yDir < 0){
			setYPos(getOwnedEntity().getYPos() - getOwnedEntity().getHeight()/2 - getHeight()/2);
		} else {
			setYPos(getOwnedEntity().getYPos());
		}
		move(entities);
	}
}
