package Models;

import Interfaces.Collideable;
import Interfaces.Moveable;
import javafx.scene.image.Image;

// Abstract right?
public abstract class Entity implements Collideable, Moveable{
	
	private Image img;
	private String tag;
	private int speed = 1;
	private int xPos, yPos;
	
	public Entity(Image i, int x, int y){
		setImage(i);
		setXPos(x);
		setYPos(y);
	}
	
	@Override
	public void move(int x, int y) {
		setXPos(getXPos() + x * getSpeed());
		setYPos(getYPos() + y * getSpeed());
	}
	
	public void setSpeed(int val){
		speed = val;
	}
	
	public void setYPos(int val){
		yPos = val;
	}
	
	public void setXPos(int val){
		xPos = val;
	}
	
	public void setImage(Image i){
		if(i != null){
			img = i;
		}
	}
	
	public void setTag(String s){
		if(s != null){
			tag = s;
		}
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public int getCenterXPos(){
		return (int) (xPos + img.getWidth()/2);
	}
	
	public int getYPos(){
		return yPos;
	}
	
	public int getCenterYPos(){
		return (int) (yPos + img.getHeight()/2);
	}
	
	public int getWidth(){
		return (int)img.getWidth();
	}
	
	public int getHeight(){
		return (int) img.getHeight();
	}
	
	public Image getImage(){
		return img;
	}
	
	public String getTag(){
		return tag;
	}

}
