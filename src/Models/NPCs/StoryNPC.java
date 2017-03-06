package Models.NPCs;

import java.util.ArrayList;

import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class StoryNPC extends NPC {
	private static int storyLineCount = 0;
	private static String[] storyLine = {"The ISOs are losing this war without you. You need to kill Watson to save them.",
		"He had you killing ISOs in the Arena.",
		"Now that you are back you can finally stop Watson",
		"Watson is on the bottom floor of the complex.",
		"Be careful in the prison. There is a combination lock to unlock the cells",
		"Please free all of the prisoners. They will all be sent to the Arena if you don’t.",
		"We heard that Tron hasn’t been seen in a long time. I hope Grinsler didn’t kill him",
		"We stand with the users! Destory Watson once and for all!"};

	public StoryNPC(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
		
	}

	public String dialogue(){
		
		if(storyLineCount > storyLine.length){
			storyLineCount = storyLine.length-1;
		}
		return storyLine[storyLineCount++];
	}
	
	@Override
	public void interact(PlayableCharacter c) {
	

	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// do nothing
	}

}
