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

	private StoryController controller;
	private PlayableCharacter ally;
	private boolean interacted;
	
	public AllyNPC(StoryController controller, int x, int y) {
		super(SpriteSheet.getNPC(), x, y);
		this.controller = controller;
		setDisplayLayer(6);
		setTag(getTag() + "-AllyNPC");
	}
	
	@Override
	public void hasCollided(Collision c) {
//		Entity collider;
//		if(c.collidingEntity == this){
//			collider = c.collidedEntity;
//		} else { 
//			collider = c.collidingEntity;
//		}
//		System.out.println(collider);
//		System.out.println(collider.getTag());
//		String[] tagElements = collider.getTag().split("-");
//		switch(tagElements[0]){
//		case "Human":
//			if(InputHandler.keyInputContains(KeyCode.F)){
//				interact((PlayableCharacter)collider);
//			}
//			break;
//		}
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
