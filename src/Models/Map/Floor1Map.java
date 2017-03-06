package Models.Map;

import java.util.ArrayList;
import java.util.Random;

import Models.Entity;
import Models.NPCs.Doctor;
import Models.NPCs.NPC;

public class Floor1Map extends Map {

	private Doctor doc;
	private ArrayList<Entity> rooms;
	private ArrayList<Entity> npcs;
	private ArrayList<Entity> upgrades;

	public Floor1Map(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	// For each Map, create a custom populateMap method which populates the map
	// as neccesary
	@Override
	public void populateMap(ArrayList<Entity> rooms) {
		this.// why is this here?
				npcs = new ArrayList<Entity>(rooms.size());
		this.rooms = rooms;
		populateNPC();
		populateUpgrades();
	}

	public void populateNPC() {
		for (Entity e : rooms) {
			npcs.add(npcChoice(e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight()));
		}
	}

	public void populateUpgrades() {

	}

	public NPC npcChoice(int roomX, int roomY, int width, int height) {
		Random randy = new Random();
		if (randy.nextInt(101) + 1 < 61) {
			int selection = randy.nextInt(10) + 1;//choosing what npc is created
			int x = randy.nextInt(width-30) + roomX + 15;
			int y = randy.nextInt(height-30) + roomY + 15;
			switch (selection) {
			case 1:
				// roomwidth-npcwidth+npcwidth/2
				//
				return null;
			case 2:
				return null;
			case 3:
				return null;
			case 4:
				return null;
			case 5:
				return null;
			case 6:
				return null;
			case 7:
				return null;
			case 8:
				return null;
			case 9:
				return null;
			default:
				return null;
			}
		} else {
			return null;
		}
	}

}
