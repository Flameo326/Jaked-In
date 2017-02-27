package Models.NPCs;

import java.util.ArrayList;

import Interfaces.Collideable;
import Interfaces.Interactable;
import Models.Bounds;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Weapon.HitBox;
import javafx.scene.image.Image;

public class AllyNPC extends PlayableCharacter implements Interactable {


	public AllyNPC(Image i, int x, int y) {
		super(i, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interact(PlayableCharacter c) {
		
		// TODO Auto-generated method stub

	}

	@Override
	public void takeDamage() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isColliding(Collideable c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bounds getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HitBox attack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub
		
	}

}
