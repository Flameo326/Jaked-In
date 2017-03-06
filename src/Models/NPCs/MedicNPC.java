package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Upgrades.MedPack;
import Models.Upgrades.Upgrade;
import javafx.scene.image.Image;

public class MedicNPC extends NPC implements Interactable {
	
	private String[] dialogue = {"You dont need my help right now.", "You are hurt! This will help."};
	



		

	public MedicNPC(Image i, StoryController st, int x, int y) {
		super(i, st, x, y);


	}

	public String conversation(PlayableCharacter c) {
		if (c.getMaxHealth() > c.getCurrentHealth()) {
			Upgrade u = new MedPack(null, 0, 0);
			u.collect(c);
			return dialogue[1];
		} else {
			return dialogue[0];
		}
	}

	public String callPlayer() {
		return "OVER HERE!!";
	}

	@Override
	public void interact(PlayableCharacter c) {
		conversation(c);
	}

	@Override
	public void hasCollided(Collision c) {
		//throw new UnsupportedOperationException("Not yet Implemented");
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		//entity does not move. no need to update.

	}

}
