package Models.Map;

import java.util.ArrayList;

import Controller.StoryController;
import Models.Entity;
import Models.NPCs.Doctor;
import Models.NPCs.Door;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class Floor1Map extends Map {

	// Also need a list of doors and exits
	private ArrayList<Entity> rooms;
	private ArrayList<Entity> npcs;
	private ArrayList<Entity> upgrades;
	private StoryController controller;
	private Entity entrance, exit;

	public Floor1Map(StoryController controller, int width, int height) {
		super(width, height);
		this.controller = controller;
		
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
		populateLevelSpecificEntities();
	}
	
	public void populateLevelSpecificEntities(){
		ArrayList<Entity> levelSpecificNPCs = new ArrayList<>();
		Entity lastRoom = rooms.get(rooms.size()-1);
		int x = rand.nextInt(lastRoom.getWidth() - 30) + lastRoom.getShape().getMinX() + 15;
		int y = rand.nextInt(lastRoom.getHeight() - 30) + lastRoom.getShape().getMinY() + 15;
		Doctor doc = new Doctor(controller, x, y);
		levelSpecificNPCs.add(doc);
		getMapObjects().addAll(levelSpecificNPCs);
	}

	public void populateNPC() {
		for (Entity e : rooms) {
			Entity temp = npcChoice(controller, e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight());
			if (temp != null) {
				npcs.add(temp);
			}
		}
		getMapObjects().addAll(npcs);
	}

	public void populateUpgrades() {
		for (Entity e : rooms) {
			Entity temp = upgradeChoice(controller, e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight());
			if (temp != null) {
				upgrades.add(temp);
			}
		}
		getMapObjects().addAll(upgrades);
	}

	
	
	@Override
	public void generateDoors(ArrayList<Entity> rooms){
//		createExit(rooms.get(0));
		createEntrance(rooms.get(rooms.size()-1));
	}
	
	public void createExit(Entity room){
		// Exit door goes to the level previous
		exit = new Door(SpriteSheet.getBlock(40, 40, Color.ORANGE), controller, 0, 0, true);
		int x = rand.nextInt(room.getWidth() - exit.getWidth()) + room.getShape().getMinX() + exit.getWidth()/2;
		int y = rand.nextInt(room.getHeight() - exit.getHeight()) + room.getShape().getMinY() + exit.getHeight()/2;
		exit.setXPos(x);
		exit.setYPos(y);
		getMapObjects().add(exit);
	}
	
	public void createEntrance(Entity room){
		// Entrance door goes to the next level
		entrance = new Door(SpriteSheet.getBlock(40, 40, Color.ORANGE), controller, 0, 0, false);
		int x = rand.nextInt(room.getWidth() - entrance.getWidth()) + room.getShape().getMinX() + entrance.getWidth()/2;
		int y = rand.nextInt(room.getHeight() - entrance.getHeight()) + room.getShape().getMinY() + entrance.getHeight()/2;
		entrance.setXPos(x);
		entrance.setYPos(y);
		getMapObjects().add(entrance);
	}
	
	public Entity getEntrance(){
		return entrance;
	}
	
	public Entity getExit(){
		return exit;
	}


}
