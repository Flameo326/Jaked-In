package Models.Map;

import java.util.ArrayList;

import Controller.StoryController;
import Models.Entity;

public class Floor3Map extends Floor1Map{

	public Floor3Map(StoryController controller, int width, int height) {
		super(controller, width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void generateDoors(ArrayList<Entity> rooms){
		createExit(rooms.get(0));
		createEntrance(rooms.get(rooms.size()-1));
	}

}
