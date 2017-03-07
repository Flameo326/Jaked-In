package Models.Stairs;

import java.util.ArrayList;

import Controller.StoryController;
import Models.Entity;
import Models.NPCs.NPC;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class StairsDown extends NPC{
	
	private StoryController st;
	private int currentLevel;

	public StairsDown(Image i, StoryController st, int x, int y, int currentLevel) {
		super(i, st, x, y);
		this.st = st;
		this.currentLevel = currentLevel - 1;
	}

	@Override
	public void interact(PlayableCharacter c) {
		st.changeLevel(currentLevel + 1);
		
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub
		
	}

}
