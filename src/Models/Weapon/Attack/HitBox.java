package Models.Weapon.Attack;

import java.util.ArrayList;

import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class HitBox extends Attack{

	// This will be for Like Melee Weapons...
	public HitBox(PlayableCharacter e, Image i) {
		super(e, i);
		setTag(getTag() + "-Melee");
		setDamage(20);
	}
	
	// Position the hitBox in the direction that the object was moving
	@Override
	public void update(ArrayList<Entity> entities) {
		super.update(entities);
		int xDir = getOwnedEntity().getCurrDir().getX();
		int yDir = getOwnedEntity().getCurrDir().getY();
		if(xDir > 0){
			setXPos(getOwnedEntity().getCenterXPos() + getOwnedEntity().getWidth()/2);
		} else if(xDir < 0){
			setXPos(getOwnedEntity().getCenterXPos() - getOwnedEntity().getWidth()/2 - getWidth());
		} else {
			setXPos(getOwnedEntity().getCenterXPos()-getWidth()/2);
		}
		if(yDir > 0){
			setYPos(getOwnedEntity().getCenterYPos() + getOwnedEntity().getHeight()/2);
		} else if(yDir < 0){
			setYPos(getOwnedEntity().getCenterYPos() - getOwnedEntity().getHeight()/2 - getHeight());
		} else {
			setYPos(getOwnedEntity().getCenterYPos() - getHeight()/2);
		}
	}
}
