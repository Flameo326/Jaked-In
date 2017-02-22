package Models;

import Interfaces.Collideable;
import Interfaces.Moveable;
import javafx.scene.image.Image;

// Abstract right?
public abstract class Entity implements Collideable, Moveable{
	
	private Image img;
	private int xPos, yPos;
	
	public Entity(Image i, int x, int y){
		setImage(i);
		setXPos(x);
		setYPos(y);
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
	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
	public Image getImage(){
		return img;
	}

}
