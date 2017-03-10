package Models.Map;

import java.util.ArrayList;

import Controller.StoryController;
import Models.Entity;

public class Floor5Map extends Floor1Map{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Entity> rooms;
	private ArrayList<Entity> npcs;
	private ArrayList<Entity> upgrades;
	private StoryController controller;

	public Floor5Map(StoryController controller, int width, int height) {
		super(controller, width, height);
		this.controller = controller;
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

	@Override
	public void populateLevelSpecificEntities() {

//		Entity lastRoom = rooms.get(rooms.size() - 1);
//		int x = rand.nextInt(lastRoom.getWidth() - 30) + lastRoom.getShape().getMinX() + 15;
//		int y = rand.nextInt(lastRoom.getHeight() - 30) + lastRoom.getShape().getMinY() + 15;
		//getMapObjects().add();
	}

	@Override
	public void populateNPC() {
		for (Entity e : rooms) {
			Entity temp = npcChoice(controller, e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight());
			if (temp != null) {
				npcs.add(temp);
			}
		}
		getMapObjects().addAll(npcs);
	}

	@Override
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

}
