package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Upgrades.SpeedBoost;
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

	public Doctor(Image i, StoryController st, int x, int y) {
		super(i, st, x, y);
		setTag("NPC-Doctor");
	}

//	public void conversation(PlayableCharacter c) {
//		
//	}

	@Override
	public void interact(PlayableCharacter c) {
		Cutscene convo;
		
		if (count != dialogue.length-1) {
			if(count == 2){
				Upgrade u = new SpeedBoost(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), 0, 0);
				u.collect(c);
			}
			convo = new DialogCutscene(getController(), .5, dialogue[count++]);
		} else {
			convo = new DialogCutscene(getController(), .5, dialogue[count]);
		}
		
		getController().startCutscene(convo);
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// do nothign
	}

}
