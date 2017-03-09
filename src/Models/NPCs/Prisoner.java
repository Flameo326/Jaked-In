package Models.NPCs;

import java.util.ArrayList;
import java.util.Random;

import Controller.StoryController;
import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class Prisoner extends NPC {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String[] dialogue = { "Thank you so much for saving me!", // 0
			"It’s about time you snapped out of it, thank you!", // 1
			"I gotta get out of here. I can’t spend any more time here", // 2
			"Thanks! Goodbye!", // 3
			"I’m so glad to be free, it’s time to get back to the front lines!", // 4
			"You killed my little girl!" }; // 5 attacks player
	private int selection;
	private boolean aggressive, hasInteracted;

	public Prisoner(StoryController st, int x, int y) {
		super(SpriteSheet.getPrisoner(), st, x, y);
		selection = new Random().nextInt(dialogue.length);
		aggressive = selection == 5;
	}

	@Override
	public void interact(PlayableCharacter c) {
		if(selection == 5 && !hasInteracted){
			Cutscene cutscene = new DialogCutscene(getController(), .5, dialogue[selection]);
			getController().startCutscene(cutscene);
			hasInteracted = true;
		}
		
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		if(aggressive && hasInteracted){
			// fight
		}
	}

}
