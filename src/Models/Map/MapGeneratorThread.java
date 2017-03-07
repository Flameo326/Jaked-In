package Models.Map;

public class MapGeneratorThread implements Runnable{
	
	private Map m;
	
	public MapGeneratorThread(Map m){
		this.m = m;
	}

	@Override
	public void run() {
		m.generateMap();
	}

}
