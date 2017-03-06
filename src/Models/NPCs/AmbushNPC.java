package Models.NPCs;

import java.util.ArrayList;

import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class AmbushNPC extends PlayableCharacter implements Interactable {
	
	private String dialogue = "GET HIM!";

	public AmbushNPC(Image i, int x, int y) {
		super(i, x, y);
		setTag(getTag() + "-EnemyNPC");
		// TODO Auto-generated constructor stub
	}
	
	public String conversation(PlayableCharacter c) {
		return dialogue;
	}

	@Override
	public void interact(PlayableCharacter c) {
		conversation(c);

	}

	@Override
	public void hasCollided(Collision c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub
		
	}

}
