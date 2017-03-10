package Models.Map;

import java.util.ArrayList;

import Controller.StoryController;
import Models.Entity;
import Models.NPCs.Doctor;
import Models.Players.PlayableCharacter;
import Models.Weapon.ExplosiveProjectileWeapon;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class Floor7Map extends Floor1Map{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Entity> rooms;
	private ArrayList<Entity> upgrades;
	private StoryController controller;
	private PlayableCharacter watson;

	public Floor7Map(StoryController controller, int width, int height, PlayableCharacter watson) {
		super(controller, width, height);
		this.controller = controller;
		this.watson = watson;
	}

	@Override
	public void populateMap(ArrayList<Entity> rooms) {
		this.upgrades = new ArrayList<Entity>();
		this.rooms = rooms;
		populateUpgrades();
		populateLevelSpecificEntities();
	}

	@Override
	public void populateLevelSpecificEntities() {

		Entity lastRoom = rooms.get(rooms.size() - 1);
		int x = rand.nextInt(lastRoom.getWidth() - 30) + lastRoom.getShape().getMinX() + 15;
		int y = rand.nextInt(lastRoom.getHeight() - 30) + lastRoom.getShape().getMinY() + 15;
		watson.setXPos(x);
		watson.setYPos(y);
		watson.setWeapon(new ExplosiveProjectileWeapon(watson, 300));
		watson.setEnemys(PlayableCharacter.getFriendlies());
		PlayableCharacter.getEnemies().add(watson);
		for(Entity e : watson.getDisplayableEntities()){
			getMapObjects().add(e);
		}
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
	}
}
