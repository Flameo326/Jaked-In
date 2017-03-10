package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class StoryNPC extends NPC {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dialogue = "";
	private static int storyLineCount = -1;
	private boolean hasSpoken = false;
	private static String[] storyLine = {"The ISOs are losing this war without you.\n You need to kill Watson to save them.",
		"He had you killing ISOs in the Arena.",
		"Now that you are back you can finally stop Watson",
		"Watson is on the bottom floor of the complex.",
		"Be careful in the prison. There is a combination lock to unlock the cells",
		"Please free all of the prisoners. They will all be sent to the Arena if you don’t.",
		"We heard that Tron hasn’t been seen in a long time.\n I hope Grinsler didn’t kill him",
		"We stand with the users! Destory Watson once and for all!"};


	public StoryNPC(StoryController st, int x, int y) {
		super(SpriteSheet.getRandomNPC(), st, x, y);
	}
	
	@Override
	public void interact(PlayableCharacter c) {
		Cutscene convo;
		if(!hasSpoken){
			hasSpoken = true;
			storyLineCount++;
			if(storyLineCount > storyLine.length-1){
				storyLineCount = storyLine.length-1;
			}
			dialogue = storyLine[storyLineCount];
		}
		
		convo = new DialogCutscene(getController(), .5, dialogue);
	
		getController().startCutscene(convo);
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// do nothing
	}

}
