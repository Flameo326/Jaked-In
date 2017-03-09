package Models.Map;

import java.io.IOException;
import java.util.ArrayList;

import Controller.StoryController;
import Enums.ButtonColors;
import Models.Entity;
import Puzzle.ColorButton;
import Puzzle.CombinedColor;

public class Floor6Map extends Floor1Map{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
			Entity temp = upgradeChoice(controller, e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight());
			if (temp != null) {
				upgrades.add(temp);
			}
		}
		getMapObjects().addAll(upgrades);
	}

	@Override
	public void generateDoors(ArrayList<Entity> rooms){
		createExit(rooms.get(0));
		// We should change this
		createEntrance(rooms.get(rooms.size()-1));
	}
	
	@Override
	public void generateMap(){
		super.generateMap();
		
		ArrayList<Entity> singleRoom = generateRooms(400, 400, 1, 1, 1);
		createPuzzleRoom(singleRoom);
		
		try {
			generatePaths(singleRoom);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void createPuzzleRoom(ArrayList<Entity> singleRoom){
		// e.getShape().getMinX(), e.getShape().getMinY(), e.getWidth(), e.getHeight()
		int xMid = singleRoom.get(0).getShape().getCenterX();
		int yMid = singleRoom.get(0).getShape().getCenterY();

		ArrayList<Entity> puzzleButtons = new ArrayList<>();
		CombinedColor solution = new CombinedColor(xMid, yMid);
		puzzleButtons.add(solution);
		puzzleButtons.add(new ColorButton(xMid - 100, yMid + 100, ButtonColors.RED, true, solution));//redIncrement
		puzzleButtons.add(new ColorButton(xMid + 100, yMid + 100, ButtonColors.RED, false, solution));//RedDecrement
		puzzleButtons.add(new ColorButton(xMid - 100, yMid, ButtonColors.GREEN, true, solution));//GreenIncrement
		puzzleButtons.add(new ColorButton(xMid + 100, yMid, ButtonColors.GREEN, false, solution));//GreenDecrement
		puzzleButtons.add(new ColorButton(xMid - 100, yMid - 100, ButtonColors.BLUE, true, solution));//BlueIncrement
		puzzleButtons.add(new ColorButton(xMid + 100, yMid - 100, ButtonColors.BLUE, false, solution));//BlueDecrement
		getMapObjects().addAll(puzzleButtons);
	}
		
}
