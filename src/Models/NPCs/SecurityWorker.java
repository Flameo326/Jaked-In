package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Upgrades.MedPack;
import Models.Upgrades.Upgrade;
import javafx.scene.image.Image;

public class SecurityWorker extends NPC {

	private int counter = 0;
	private String[] dialogue = {
			"Watson is on the 7th floor. I hope you take him out"
					+ ", he can’t be allowed to kill all of the ISOs. I don’t have much but please take this.", // 0
			"Why are you still here? You don’t have much time left. You must stop Watson", // 1
			"You need to leave, I think I hear more guards coming" };// 2

	public SecurityWorker(Image i, StoryController st, int x, int y) {
		super(i, st, x, y);
	}

	public String conversation(PlayableCharacter c) {
		if (counter < dialogue.length - 1) {
			if (counter == 0) {
				// change to random upgrade
				Upgrade u = new MedPack(null, 0, 0);
				u.collect(c);
			}
			return dialogue[counter++];
		} else {
			return dialogue[counter];
		}
	}

	@Override
	public void interact(PlayableCharacter c) {
		conversation(c);
	}

	@Override
	public void update(ArrayList<Entity> entities) {

	}

}
