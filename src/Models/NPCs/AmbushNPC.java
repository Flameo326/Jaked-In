package Models.NPCs;

import java.util.ArrayList;

import com.sun.media.jfxmedia.MediaError;

import Controller.StoryController;
import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Enums.Difficulties;
import Interfaces.Interactable;
import Models.Collision;
import Models.Entity;
import Models.Players.ComputerPlayer;
import Models.Players.PlayableCharacter;
import Models.Weapon.NormalProjectileWeapon;
import SpriteSheet.SpriteSheet;

public class AmbushNPC extends PlayableCharacter implements Interactable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StoryController controller;
	private boolean interacted;

	public AmbushNPC(StoryController controller, int x, int y) {
		super(SpriteSheet.getRandomNPC(), x, y);
		this.controller = controller;
		setDisplayLayer(6);
		setTag(getTag() + "-EnemyNPC");
	}

	@Override
	public void interact(PlayableCharacter p) {
		Cutscene c = new DialogCutscene(controller, .5, "GET HIM!");
		controller.startCutscene(c);
		setImage(SpriteSheet.getEnemy());
		interacted = true;
	}

	@Override
	public void hasCollided(Collision c) {
		
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		if(interacted){
			PlayableCharacter p = new ComputerPlayer(SpriteSheet.getRandomEnemy(), getXPos(), getYPos(), Difficulties.NORMAL);
			p.setWeapon(new NormalProjectileWeapon(p, 20));
			for(Entity i : p.getDisplayableEntities()){
				entities.add(i);
			}
			p.setEnemys(PlayableCharacter.getFriendlies());
			PlayableCharacter.getEnemies().add(p);
			entities.remove(this);
			// make sure we add weapon and health bar
		}
	}
}
