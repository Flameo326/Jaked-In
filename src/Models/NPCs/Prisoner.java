package Models.NPCs;

import java.util.ArrayList;
import java.util.Random;

import Controller.StoryController;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class Prisoner extends NPC {

	private String[] dialogue = { "Thank you so much for saving me!", // 0
			"It’s about time you snapped out of it, thank you!", // 1
			"I gotta get out of here. I can’t spend any more time here", // 2
			"Thanks! Goodbye!", // 3
			"I’m so glad to be free, it’s time to get back to the front lines!", // 4
			"You killed my little girl!" }; // 5 attacks player

	public Prisoner(Image i, StoryController st, int x, int y) {
		super(i, st, x, y);

	}

	public String conversation(PlayableCharacter c) {
		Random randy = new Random();
		int selection = randy.nextInt(dialogue.length);
		if (selection == 5) {

		}
		return dialogue[selection];

	}

	@Override
	public void interact(PlayableCharacter c) {
		// TODO Auto-generated method stub

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
