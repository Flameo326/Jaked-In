package Models.Map;

import java.util.ArrayList;

import Models.Entity;

public class Floor1Map extends Map{

	public Floor1Map(int width, int height) {
		super(width, height);
	}
	
	// For each Map, create a custom populateMap method which populates the map as neccesary
	@Override
	public void populateMap(ArrayList<Entity> rooms){
		rooms.get(0).getXPos();
//		getMapObjects().add();
		
	}

}
