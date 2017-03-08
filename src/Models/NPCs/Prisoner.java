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

	private String[] dialogue = { "Thank you so much for saving me!", // 0
			"It’s about time you snapped out of it, thank you!", // 1
			"I gotta get out of here. I can’t spend any more time here", // 2
			"Thanks! Goodbye!", // 3
			"I’m so glad to be free, it’s time to get back to the front lines!", // 4
			"You killed my little girl!" }; // 5 attacks player

	public Prisoner(StoryController st, int x, int y) {
		super(SpriteSheet.getPrisoner(), st, x, y);

	}

//	public String conversation(PlayableCharacter c) {
//		
//	}

	@Override
	public void interact(PlayableCharacter c) {
		Random rand = new Random();
		int selection = rand.nextInt(dialogue.length);
		if (selection == 5) {

		}

		Cutscene convo = new DialogCutscene(getController(), .5, dialogue[selection]);
		getController().startCutscene(convo);
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// do nothing
	}

}
