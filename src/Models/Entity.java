package Models;

import Interfaces.Collideable;
import Interfaces.Moveable;
import Models.Shape.Shape;
import javafx.scene.image.Image;

public abstract class Entity implements Collideable, Moveable, Comparable<Entity>{
	
	private Image img;
	private Shape shape;
	private String tag;
	private int speed = 1;
	// Since we have a shape, these are no longer neccesary
//	private int xPos, yPos, width, height;
	private int displayLayer;
	
	public Entity(Image i, int x, int y, int width, int height){
		this(i, new Shape(x, y, width, height));
//		setXPos(x);
//		setYPos(y);
//		setWidth(width);
//		setHeight(height);
	}
	
	public Entity(Image i, Shape shape){
		setImage(i);
		setShape(shape);
		setTag("Entity");
		setDisplayLayer(0);
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
	
	public void setShape(Shape s) { shape = s; }
	public Shape getShape() { return shape; }
	
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
