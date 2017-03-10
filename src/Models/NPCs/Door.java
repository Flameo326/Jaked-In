package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class Door extends NPC{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isExit, hasInteracted;

	public Door(StoryController st, int x, int y, boolean isExit) {
		super(SpriteSheet.getDoor(), st, x, y);
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
