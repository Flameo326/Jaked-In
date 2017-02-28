package Models.NPCs;

import java.util.ArrayList;

import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class Doctor extends NPC {

	private String[] dialogue = {
			"I’m sorry for all of this. Watson forced me to work on you."
					+ " Take this key card it will get you down to the Tech Wing, I wish I could do more",
			"I already told you that I am sorry, what more do you want?", "Fine take this power up and go away!",
			"Please, just leave me alone." };
	private int count = 0;

	public Doctor(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public String conversation() {
		if (count != 3) {
			return dialogue[count++];
		} else {
			return dialogue[count];
		}
	}

	@Override
	public void interact(PlayableCharacter c) {
		

	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasCollided(Collision c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

}
