package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Upgrades.Upgrade;
import SpriteSheet.SpriteSheet;

public class SecurityWorker extends NPC {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int counter = 0;
	private String[] dialogue = {
			"Watson is on the 7th floor.\n I hope you take him out"
					+ ", he can’t be allowed to kill all of the ISOs.\n\n I don’t have much but please take this.", // 0
			"Why are you still here? You don’t have much time left.\n You must stop Watson", // 1
			"You need to leave, I think I hear more guards coming" };// 2

	public SecurityWorker(StoryController st, int x, int y) {
		super(SpriteSheet.getRandomNPC(), st, x, y);
	}

	@Override
	public void interact(PlayableCharacter c) {
		Cutscene convo;
		
		if (counter < dialogue.length - 1) {
			String text = dialogue[counter++];
			if (counter == 1) {
				Upgrade u = getRandomUpgrade();
				u.collect(c);
				text += "\n\nYou recieved a "+u.getClass().getSimpleName();
			}
			convo = new DialogCutscene(getController(), .5, text);
		} else {
			convo = new DialogCutscene(getController(), .5, dialogue[counter]);
		}
		 
		getController().startCutscene(convo);
	}

	@Override
	public void update(ArrayList<Entity> entities) {

	}
	
	

}
