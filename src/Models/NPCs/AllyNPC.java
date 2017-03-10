package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Enums.Difficulties;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.ComputerPlayer;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class AllyNPC extends PlayableCharacter implements Interactable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StoryController controller;
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
			entities.remove(this);
			ComputerPlayer p = new ComputerPlayer(SpriteSheet.getRandomPlayer(), this.getXPos(), this.getYPos(), Difficulties.NORMAL);
			p.setTag("AllyNPC-"+p.getTag());
			Entity human = null;
			for(Entity e : entities){
				if(e.getTag().split("-")[0].equals("Human")){
					human = e;
				}
			}
			if(human != null){
				p.setLeader(((PlayableCharacter) human));
			}
			p.setEnemys(PlayableCharacter.getEnemies());
			PlayableCharacter.getFriendlies().add(p);
			for(Entity e : p.getDisplayableEntities()){
				entities.add(e);
			}
			
		}
	}

	@Override
	public void interact(PlayableCharacter c) {
		Cutscene convo = new DialogCutscene(controller, .5, "I will join you.");
		controller.startCutscene(convo);
		interacted = true;
	}

}
