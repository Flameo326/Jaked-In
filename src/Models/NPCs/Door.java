package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Models.Entity;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class Door extends NPC{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isExit, hasInteracted;

	public Door(Image i, StoryController st, int x, int y, boolean isExit) {
		super(i, st, x, y);
		this.isExit = isExit;
	}
	
	@Override
	public void update(ArrayList<Entity> entities){
		hasInteracted = false;
	}

	@Override
	public void interact(PlayableCharacter c) {
		if(!hasInteracted){
			getController().changeLevel(getController().getCurrentLevel() + (isExit ? -1 : 1));
			hasInteracted = true;
		}
	}

}
