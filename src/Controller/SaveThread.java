package Controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveThread implements Runnable{
	
	private StoryController s;
	private int x, y, width, height;
	
	public SaveThread(StoryController s, int x, int y, int width, int height){
		this.s =s;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void run() {
		ObjectOutputStream write;
		try {
			write = new ObjectOutputStream(new FileOutputStream("src/Other/text.txt"));
			write.writeLong(s.getSeed());
			write.writeObject(s);
			write.close();
			s.getCanvas().getGraphicsContext2D().clearRect(x, y+height/2, width, height);
			s.getCanvas().getGraphicsContext2D().strokeRoundRect(x, y+height/2, width, height, 5, 5);
			s.getCanvas().getGraphicsContext2D().fillText("Save Succesful", x + width/2, y+height/2 + height*3/4, width);
		} catch (IOException e) {
			e.printStackTrace();
			s.getCanvas().getGraphicsContext2D().clearRect(x, y+height/2, width, height);
			s.getCanvas().getGraphicsContext2D().strokeRoundRect(x, y+height/2, width, height, 5, 5);
			s.getCanvas().getGraphicsContext2D().fillText("Save Failed", x + width/2, y+height/2 + height*3/4, width);
		}
		
	}

}
