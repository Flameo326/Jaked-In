package Models.NPCs;

import java.util.ArrayList;

import Controller.StoryController;
import Cutscene.Cutscene;
import Cutscene.DialogCutscene;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Upgrades.MedPack;
import Models.Upgrades.Upgrade;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PowerUpNPC extends NPC {

	private boolean hasSpoken;

	public PowerUpNPC(Image i, StoryController st, int x, int y) {
		super(i, st, x, y);
	}

	public void conversation(PlayableCharacter p) {
		if (!hasSpoken) {//needs to be random powerup
			Upgrade u = new MedPack(null, 0, 0);
			u.collect(p);
			hasSpoken = true;
			Cutscene c = new DialogCutscene(getController(), .5, "Here, take this. It will help you fight Watson");
			getController().startCutscene(c);
		}else{
			Cutscene c = new DialogCutscene(getController(), .5, "I have nothing more to help you!");
			getController().startCutscene(c);
		}
	}

	// Need to figur out this 
	public String callPlayer() {
		return "OVER HERE!!";
	}

	@Override
	public void interact(PlayableCharacter c) {
		Cutscene convo;
		
		if (!hasSpoken) {//needs to be random powerup
			Upgrade u = new MedPack(SpriteSheet.getBlock(1, 1, Color.ANTIQUEWHITE), 0, 0);
			u.collect(c);
			hasSpoken = true;
			convo = new DialogCutscene(getController(), .5, "Here, take this. It will help you fight Watson");
		}else{
			convo = new DialogCutscene(getController(), .5, "I have nothing more to help you!");
		}
		
		getController().startCutscene(convo);
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		// TODO Auto-generated method stub

	}

}
