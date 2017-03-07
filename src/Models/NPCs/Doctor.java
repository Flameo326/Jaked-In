package Models.NPCs;

import java.util.ArrayList;

import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Upgrades.MedPack;
import Models.Upgrades.Upgrade;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Doctor extends NPC {

	private String[] dialogue = {
			"I’m sorry for all of this. Watson forced me to work on you."
					+ " Take this key card it will get you down to the Tech Wing, I wish I could do more", // 0
			"I already told you that I am sorry, what more do you want?", // 1
			"Fine take this power up and go away!", // 2
			"Please, just leave me alone." };// 3
	private int count = 0;

	public Doctor(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
		setTag("NPC-Doctor");

	}

	public String conversation(PlayableCharacter c) {
		if (count != dialogue.length-1) {
			if(count == 2){
				Upgrade u = new MedPack(null, 0, 0);
				u.collect(c);
			}
			return dialogue[count++];
		} else {
			return dialogue[count];
		}
	}

	@Override
	public void interact(PlayableCharacter c) {
		conversation(c);
	}

	@Override
	public void update(ArrayList<Entity> entities) {

	}

	@Override
	public void hasCollided(Collision c) {
		throw new UnsupportedOperationException("Not yet Implemented");
	}

}
