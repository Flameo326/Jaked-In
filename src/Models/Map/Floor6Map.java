package Models.Map;

import java.io.IOException;
import java.util.ArrayList;

import Controller.CollisionSystem;
import Controller.StoryController;
import Enums.ButtonColors;
import Models.Collision;
import Models.Entity;
import Models.NPCs.Door;
import Puzzle.ColorButton;
import Puzzle.CombinedColor;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class Floor6Map extends Floor1Map {

	private ArrayList<Entity> rooms;
	private ArrayList<Entity> npcs;
	private ArrayList<Entity> upgrades;
	private StoryController controller;

	public Floor6Map(StoryController controller, int width, int height) {
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

	public void populateLevelSpecificEntities() {

		// Entity lastRoom = rooms.get(rooms.size() - 1);
		// int x = rand.nextInt(lastRoom.getWidth() - 30) +
		// lastRoom.getShape().getMinX() + 15;
		// int y = rand.nextInt(lastRoom.getHeight() - 30) +
		// lastRoom.getShape().getMinY() + 15;
		// getMapObjects().add();
	}

	public void populateNPC() {
		for (Entity e : rooms) {
			Entity temp = npcChoice(controller, e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(),
					e.getHeight());
			if (temp != null) {
				npcs.add(temp);
			}
		}
		getMapObjects().addAll(npcs);
	}

	public void populateUpgrades() {
		for (Entity e : rooms) {
			Entity temp = upgradeChoice(controller, e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(),
					e.getHeight());
			if (temp != null) {
				upgrades.add(temp);
			}
		}
		getMapObjects().addAll(upgrades);
	}

	@Override
	public void generateDoors(ArrayList<Entity> rooms) {
		createExit(rooms.get(0));
		createEntrance(rooms.get(rooms.size() - 1));
	}

	@Override
	public void generateMap() {
		try {
			ArrayList<Entity> rooms = generateRooms(150, getMapWidth(), .8, 1.2, 10);
			generatePuzzleRoom(rooms);
			this.rooms = rooms;
			generatePaths(rooms);
			createPuzzleRoom(rooms.get(rooms.size()-1));
			populateMap(rooms);
			generateDoors(rooms);
		} catch (IOException e) {

		}

	}
	
	@Override
	public void createEntrance(Entity room){
		Entity entrance = new Door(SpriteSheet.getBlock(40, 40, Color.ORANGE), controller, 0, 0, false);
		entrance.setXPos(rooms.get(rooms.size()-1).getXPos());
		entrance.setYPos(rooms.get(rooms.size()-1).getYPos());
		getMapObjects().add(entrance);
		setEnterance(entrance);
	}

	public void generatePuzzleRoom(ArrayList<Entity> rooms) {
		int radius = (int) (((getMapWidth() * .8 - 150 * 1.2)) * Math.sqrt(rooms.size()));
		room: for (int i = 1; i < 2; i++) {
			Entity previousRoom = rooms.get(rooms.size() - 1);
			Entity currentRoom = createNewRoom(500, 500, 1, 1);

			int maxDist = Math.max(currentRoom.getWidth(), currentRoom.getHeight());
			// in radians
			int degree = rand.nextInt(360);
			int initialDegree = degree;

			boolean hasNotCollided = false;
			while (!hasNotCollided) {
				int x = (int) (Math.cos(degree * Math.PI / 180) * radius);
				int y = (int) (Math.sin(degree * Math.PI / 180) * radius);

				// Scatterplot
				currentRoom.setXPos(x);
				currentRoom.setYPos(y);

				hasNotCollided = true;
				// do a collision check against the rooms
				for (Collision c : CollisionSystem.getCollision(currentRoom, rooms.toArray(new Entity[0]))) {
					if (Math.min(c.xPenDepth, c.yPenDepth) > -20) {
						hasNotCollided = false;
					}
				}
				degree += 99;
				if (degree >= 360) {
					degree -= 360;
				}
				if (degree == initialDegree) {
					i--;
					continue room;
				}
			}
			rooms.add(currentRoom);
		}
	}

	public void createPuzzleRoom(Entity singleRoom) {
		// e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(),
		// e.getHeight()
		int xMid = singleRoom.getShape().getCenterX();
		int yMid = singleRoom.getShape().getCenterY();

		ArrayList<Entity> puzzleButtons = new ArrayList<>();
		CombinedColor solution = new CombinedColor(xMid, yMid);
		puzzleButtons.add(solution);
		puzzleButtons.add(new ColorButton(xMid - 100, yMid + 100, ButtonColors.RED, true, solution));// redIncrement
		puzzleButtons.add(new ColorButton(xMid + 100, yMid + 100, ButtonColors.RED, false, solution));// RedDecrement
		puzzleButtons.add(new ColorButton(xMid - 100, yMid, ButtonColors.GREEN, true, solution));// GreenIncrement
		puzzleButtons.add(new ColorButton(xMid + 100, yMid, ButtonColors.GREEN, false, solution));// GreenDecrement
		puzzleButtons.add(new ColorButton(xMid - 100, yMid - 100, ButtonColors.BLUE, true, solution));// BlueIncrement
		puzzleButtons.add(new ColorButton(xMid + 100, yMid - 100, ButtonColors.BLUE, false, solution));// BlueDecrement
		getMapObjects().addAll(puzzleButtons);
	}

}
