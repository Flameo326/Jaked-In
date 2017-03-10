package Models.Map;

import Models.Entity;

public class MapGeneratorThread implements Runnable{
	
	private Floor1Map m;
	private Entity required;
	
	public MapGeneratorThread(Floor1Map m){
		this.m = m;
	}
	
	public MapGeneratorThread(Floor1Map m, Entity required){
		this.m = m;
		this.required = required;
	}

	@Override
	public void run() {
		m.generateMap();
		if(required != null){
			required.setXPos(m.getRooms().get(m.getRooms().size()-1).getXPos());
			required.setXPos(m.getRooms().get(m.getRooms().size()-1).getYPos());
		}
	}

}
