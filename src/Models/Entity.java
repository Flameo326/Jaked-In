package Models;

import Interfaces.Collideable;
import Interfaces.Moveable;
import javafx.scene.image.Image;

public abstract class Entity implements Collideable, Moveable, Comparable<Entity>{
	
	private int displayLayer = 0;
	private Image img;
	private String tag;
	private int speed = 1;
	private int xPos, yPos, width, height;
	
	public Entity(Image i, int x, int y, int width, int height){
		setImage(i);
		setXPos(x);
		setYPos(y);
		setWidth(width);
		setHeight(height);
		setTag("Entity");
	}
	
	@Override
	public void move(int x, int y) {
		setXPos(getXPos() + x * getSpeed());
		setYPos(getYPos() + y * getSpeed());
	}	
	
	public void setImage(Image i){
		if(i != null){
			img = i;
		}
	}
	public Image getImage(){ return img; }
	
	public void setTag(String s){
		if(s != null){
			tag = s;
		}
	}
	public String getTag(){ return tag; }

	public void setDisplayLayer(int displayLayer) { this.displayLayer = displayLayer; }
	public int getDisplayLayer() { return displayLayer; }
	
	public void setSpeed(int val){ speed = val; }
	public int getSpeed(){ return speed; }
	
	public void setXPos(int val){ xPos = val; }
	public int getXPos(){ return xPos; }
	
	public void setYPos(int val){ yPos = val; }
	public int getYPos(){ return yPos; }
	
	public void setWidth(int val){ width = val; }
	public int getWidth(){ return width; }
	
	public void setHeight(int val){ height = val; }
	public int getHeight(){ return height; }
	
	public int getCenterXPos(){
		return (int) (xPos + img.getWidth()/2);
	}
	
	public int getCenterYPos(){
		return (int) (yPos + img.getHeight()/2);
	}
	
	@Override
	public int compareTo(Entity o) {
		if(getDisplayLayer() < o.getDisplayLayer()){
			return -1;
		} else if(getDisplayLayer() == o.getDisplayLayer()){
			return 0;
		} else {
			return 1;
		}
	}

}
