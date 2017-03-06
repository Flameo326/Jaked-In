package Models.Map;

import java.util.ArrayList;
import java.util.Random;

import Models.Entity;
import Models.NPCs.AllyNPC;
import Models.NPCs.AmbushNPC;
import Models.NPCs.AngryNPC;
import Models.NPCs.Doctor;
import Models.NPCs.MedicNPC;
import Models.NPCs.PowerUpNPC;
import Models.NPCs.StoryNPC;
import Models.Upgrades.BonusDamage;
import Models.Upgrades.DamageReduction;
import Models.Upgrades.ForceFieldReflection;
import Models.Upgrades.MedPack;
import Models.Upgrades.SpeedBoost;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class Floor1Map extends Map {

	private Doctor doc;
	private ArrayList<Entity> rooms;
	private ArrayList<Entity> npcs;
	private ArrayList<Entity> upgrades;

	public Floor1Map(int width, int height) {
		super(width, height);
		generateMap();
	}

	// For each Map, create a custom populateMap method which populates the map
	// as neccesary
	@Override

	public void populateMap(ArrayList<Entity> rooms) {
		this.npcs = new ArrayList<Entity>();
		this.upgrades = new ArrayList<Entity>();
		this.rooms = rooms;
		populateNPC();
		populateUpgrades();
		
	}
	
	public void populateLevelSpecificEntities(int roomX, int roomY, int width, int height){//need to complete
		ArrayList<Entity> levelSpecificEntities = new ArrayList<>(); 
		Random randy = new Random();
		int x = randy.nextInt(width - 30) + roomX + 15;
		int y = randy.nextInt(height - 30) + roomY + 15;
	}

	public void populateNPC() {
		for (Entity e : rooms) {
			Entity temp = npcChoice(e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight());
			if (temp != null) {
				npcs.add(temp);
			}
		}
		for (Entity e : npcs) {
			getMapObjects().add(e);
		}
	}

	public void populateUpgrades() {
		for (Entity e : rooms) {
			Entity temp = upgradeChoice(e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight());
			if (temp != null) {
				upgrades.add(temp);
			}
		}
		for (Entity e : upgrades) {
			getMapObjects().add(e);
		}
	}

	public Entity npcChoice(int roomX, int roomY, int width, int height) {
		Random randy = new Random();
		if (randy.nextInt(100) + 1 < 61) {
			int selection = randy.nextInt(6) + 1;// choosing what npc is
													// created
			int x = randy.nextInt(width - 30) + roomX + 15;
			int y = randy.nextInt(height - 30) + roomY + 15;
			switch (selection) {
			case 1:
				return new AllyNPC(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), x, y);
			case 2:
				return new AmbushNPC(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), x, y);
			case 3:
				return new AngryNPC(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), x, y);
			case 4:
				return new MedicNPC(SpriteSheet.getBorderedBlock(30, 30, Color.RED, 2), x, y);
			case 5:
				return new PowerUpNPC(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), x, y);
			default:
				return new StoryNPC(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), x, y);
			}
		} else {
			return null;
		}
	}
	
	public Entity upgradeChoice(int roomX, int roomY, int width, int height){
		Random randy = new Random();
		if (randy.nextInt(100) + 1 < 51) {
			int selection = randy.nextInt(5) + 1;// choosing what npc is
													// created
			int x = randy.nextInt(width - 30) + roomX + 15;
			int y = randy.nextInt(height - 30) + roomY + 15;
			switch (selection) {
			case 1:
				return new BonusDamage(SpriteSheet.getBorderedBlock(10, 10, Color.BLANCHEDALMOND, 2), x, y);
			case 2:
				return new DamageReduction(SpriteSheet.getBorderedBlock(10, 10, Color.CORNSILK, 2), x, y);
			case 3:
				return new ForceFieldReflection(SpriteSheet.getBorderedBlock(10, 10, Color.YELLOW, 2), x, y);
			case 4:
				return new MedPack(SpriteSheet.getBorderedBlock(10, 10, Color.RED, 2), x, y);
			default:
				return new SpeedBoost(SpriteSheet.getBorderedBlock(10, 10, Color.PLUM, 2), x, y);
			
			}
		} else {
			return null;
		}
	}

}
