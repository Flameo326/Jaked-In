package Models.Map;

import java.util.ArrayList;

import Models.Entity;
import Models.NPCs.Doctor;
import Models.Players.PlayableCharacter;

public class Floor1Map extends Map{
	
	private Doctor doc;

	public Floor1Map(int width, int height) {
		super(width, height);
	}
	
	
	// For each Map, create a custom populateMap method which populates the map as neccesary
	@Override
	public void populateMap(ArrayList<Entity> rooms){

		for(Entity e : rooms){
			
		}
	}
	
	public void populateNPC(ArrayList<PlayableCharacter> players){
		
	}

}
