package Models.NPCs;

import java.util.ArrayList;

import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class StoryNPC extends NPC {
	private static int storyLineCount = 0;
	private static String[] storyLine = {"Null"};//fill in the rest of the array

	public StoryNPC(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
		
	}

	public String dialogue(){
		//array with 15-20 story dialogue
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
