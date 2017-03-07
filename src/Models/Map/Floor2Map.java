package Models.Map;

import java.util.ArrayList;

import Controller.StoryController;
import Models.Entity;
import Models.NPCs.AllyNPC;
import Models.NPCs.AmbushNPC;
import Models.NPCs.AngryNPC;
import Models.NPCs.MedicNPC;
import Models.NPCs.PowerUpNPC;
import Models.NPCs.SecurityWorker;
import Models.NPCs.StoryNPC;
import Models.Upgrades.BonusDamage;
import Models.Upgrades.DamageReduction;
import Models.Upgrades.ForceFieldReflection;
import Models.Upgrades.MedPack;
import Models.Upgrades.SpeedBoost;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class Floor2Map extends Floor1Map{

	private ArrayList<Entity> rooms;
	private ArrayList<Entity> npcs;
	private ArrayList<Entity> upgrades;
	private StoryController controller;

	public Floor2Map(StoryController controller, int width, int height) {
		super(controller, width, height);
		this.controller = controller;
		generateMap();

	}

	@Override
	public void populateMap(ArrayList<Entity> rooms) {
		this.npcs = new ArrayList<Entity>();
		this.upgrades = new ArrayList<Entity>();
		this.rooms = rooms;
		populateNPC();
		populateUpgrades();
		populateLevelSpecificEntities();
	}

	public void populateLevelSpecificEntities() {
		ArrayList<Entity> levelSpecificNPCs = new ArrayList<>();
		Entity lastRoom = rooms.get(rooms.size()-1);
		int x = rand.nextInt(lastRoom.getWidth() - 30) + lastRoom.getShape().getMinX() + 15;
		int y = rand.nextInt(lastRoom.getHeight() - 30) + lastRoom.getShape().getMinY() + 15;
		SecurityWorker worker = new SecurityWorker(SpriteSheet.getBorderedBlock(30, 30, Color.BLACK, 2), controller, x, y);
		levelSpecificNPCs.add(worker);
		getMapObjects().addAll(levelSpecificNPCs);
	}

	public void populateNPC() {
		for (Entity e : rooms) {
			Entity temp = npcChoice(e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight());
			if (temp != null) {
				npcs.add(temp);
			}
		}
		getMapObjects().addAll(npcs);
	}

	public void populateUpgrades() {
		for (Entity e : rooms) {
			Entity temp = upgradeChoice(e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight());
			if (temp != null) {
				upgrades.add(temp);
			}
		}
		getMapObjects().addAll(upgrades);
	}
	
	@Override
	public void generateDoors(ArrayList<Entity> rooms){
		createExit(rooms.get(0));
		createEntrance(rooms.get(rooms.size()-1));
	}

	public Entity npcChoice(int roomX, int roomY, int width, int height) {

		if (rand.nextInt(100) + 1 < 61) {
			int selection = rand.nextInt(6) + 1;// choosing what npc is
												// created
			int x = rand.nextInt(width - 30) + roomX + 15;
			int y = rand.nextInt(height - 30) + roomY + 15;
			switch (selection) {
			case 1:
				return new AllyNPC(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), controller, x, y);
			case 2:
				return new AmbushNPC(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), controller, x, y);
			case 3:
				return new AngryNPC(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), controller, x, y);
			case 4:
				return new MedicNPC(SpriteSheet.getBorderedBlock(30, 30, Color.RED, 2), controller, x, y);
			case 5:
				return new PowerUpNPC(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), controller, x, y);
			default:
				return new StoryNPC(SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2), controller, x, y);
			}
		} else {
			return null;
		}
	}

	public Entity upgradeChoice(int roomX, int roomY, int width, int height) {

		if (rand.nextInt(100) + 1 < 51) {
			int selection = rand.nextInt(5) + 1;// choosing what Upgrade is
												// created
			int x = rand.nextInt(width - 30) + roomX + 15;
			int y = rand.nextInt(height - 30) + roomY + 15;
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
