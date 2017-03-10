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

public class Doctor extends NPC {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] dialogue = {
			"I’m sorry for all of this.\n Watson forced me to work on you."
					+ "\n\nGo down to the Tech Wing.\n There is a security worker there who can help you.\n\nI wish I could do more", // 0
			"I already told you that I am sorry, what more do you want?", // 1
			"Fine take this power up and go away!", // 2
			"Please, just leave me alone." };// 3
	private int count = 0;

	public Doctor(StoryController st, int x, int y) {
		super(SpriteSheet.getMedic(), st, x, y);
		setTag(getTag() + "-Doctor");
	}

	@Override
	public void interact(PlayableCharacter c) {
		Cutscene convo;
		
		if (count != dialogue.length-1) {
			String text = dialogue[count++];
			if(count == 3){
				Upgrade u = new SpeedBoost(0, 0, true);
				u.collect(c);
				text +="\n\nYou recieved a " +u.getClass().getSimpleName();
			}
			convo = new DialogCutscene(getController(), .5, text);
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
