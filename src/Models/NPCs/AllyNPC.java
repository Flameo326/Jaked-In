package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class AllyNPC extends PlayableCharacter implements Interactable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StoryController controller;
	private PlayableCharacter ally;
	private boolean interacted;
	
	public AllyNPC(StoryController controller, int x, int y) {
		super(SpriteSheet.getRandomNPC(), x, y);
		this.controller = controller;
		setDisplayLayer(6);
		setTag(getTag() + "-AllyNPC");
	}
	
	@Override
	public void hasCollided(Collision c) {
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		if(interacted){
			// follow the player around...
		}
	}

	@Override
	public void interact(PlayableCharacter c) {
		Cutscene convo = new DialogCutscene(controller, .5, "I will join you.");
		controller.startCutscene(convo);
		
		setAlly(c);
		interacted = true;
	}
	
	public void setAlly(PlayableCharacter c){
		ally = c;
	}

}
