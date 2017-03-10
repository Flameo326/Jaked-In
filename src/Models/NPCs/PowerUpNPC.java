package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Upgrades.Upgrade;
import SpriteSheet.SpriteSheet;

public class PowerUpNPC extends NPC {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean hasSpoken;

	public PowerUpNPC(StoryController st, int x, int y) {
		super(SpriteSheet.getRandomNPC(), st, x, y);
	}

	// Need to figure out this
	public String callPlayer() {
		return "OVER HERE!!";
	}

	@Override
	public void interact(PlayableCharacter c) {
		Cutscene convo;

		if (!hasSpoken) {// needs to be random powerup
			Upgrade u = getRandomUpgrade();
			u.collect(c);
			hasSpoken = true;
			String text = "Here, take this. It will help you fight Watson.\n\nYou recieved a "+u.getClass().getSimpleName();
			convo = new DialogCutscene(getController(), .5, text);
		} else {
			convo = new DialogCutscene(getController(), .5, "I have nothing more to help you!");
		}

		getController().startCutscene(convo);
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		

	}

}
