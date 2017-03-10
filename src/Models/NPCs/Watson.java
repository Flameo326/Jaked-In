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
import Models.Weapon.ExplosiveProjectileWeapon;
import SpriteSheet.SpriteSheet;

public class Watson extends PlayableCharacter implements Interactable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StoryController controller;
	private boolean interacted;
	private PlayableCharacter watson;
	private boolean watsonAlive = true;
	
	public Watson(StoryController controller, int x, int y) {
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
		if(interacted && watson != null){
			entities.remove(this);
			ComputerPlayer p = new ComputerPlayer(SpriteSheet.getAlly(), this.getXPos(), this.getYPos(), Difficulties.HARD);
			p.setEnemys(PlayableCharacter.getFriendlies());
			p.setWeapon(new ExplosiveProjectileWeapon(this, 30));
			PlayableCharacter.getEnemies().add(p);
			watson = p;
			entities.add(p);
		}
		if(watson != null && watson.getCurrentHealth() <= 0){
			watsonAlive = false;
		}
	}

	@Override
	public void interact(PlayableCharacter c) {
		Cutscene convo = new DialogCutscene(controller, .5, "I will join you.");
		controller.startCutscene(convo);
		interacted = true;
	}

	
}
