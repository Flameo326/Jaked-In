package Models;

import java.util.ArrayList;

import Controller.CollisionSystem;
import Enums.Direction;
import Interfaces.Collideable;
import Interfaces.Moveable;
import Models.Shape.Shape;
import javafx.scene.image.Image;

public abstract class Entity implements Collideable, Moveable, Comparable<Entity>{
	
	private Image img;
	private Direction direction;
	private Shape shape;
	private String tag;
	private int speed = 1;
	private int displayLayer;
	
	public Entity(Image i, int x, int y){
		this(i, x, y, (int)i.getWidth(), (int)i.getHeight());
	}
	
	public Entity(Image i, int x, int y, int width, int height){
		this(i, new Shape(x, y, width, height));
	}
	
	public Entity(Image i, Shape shape){
		setImage(i);
		setShape(shape);
		setTag("Entity");
		setDisplayLayer(0);
		setCurrDir(Direction.RIGHT);
	}
	
	@Override
	public void move(ArrayList<Entity> entities) {
		setXPos(getXPos() + getCurrDir().getX() * getSpeed());
		setYPos(getYPos() + getCurrDir().getY() * getSpeed());
		CollisionSystem.checkMovementCollisions(this, entities);
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
	
	public void setShape(Shape s) { shape = s; }
	public Shape getShape() { return shape; }
	
	public void setCurrDir(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getCurrDir(){
		return direction;
	}
	
	// X and Y now correlate to the center position...
	// Should we provide method to get display point???
	// does it actually matter?
	public void setXPos(int val){ shape.setCenterX(val); }
	public int getXPos(){ return shape.getCenterX(); }
	
	public void setYPos(int val){ shape.setCenterY(val); }
	public int getYPos(){ return shape.getCenterY(); }
	
	public void setWidth(int val){ shape.setWidth(val); }
	public int getWidth(){ return shape.getWidth(); }
	
	public void setHeight(int val){ shape.setHeight(val); }
	public int getHeight(){ return shape.getHeight(); }
	
	public int getDisplayableXPos(){
		return shape.getMinX();
	}
	
	public int getDisplayableYPos(){
		return shape.getMinY();
	}
	
//	public int getPreviousXPos(){
//		return prevXPos;
//	}
//	
//	public int getPreviousYPos(){
//		return prevYPos;
//	}
//	
//	public void setMoved(boolean b){
//		moved = b;
//	}
//	
//	public boolean hasMoved(){
//		return moved;
//	}
	
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
