package Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Controller.CollisionSystem;
import Enums.Direction;
import Interfaces.Collideable;
import Interfaces.Moveable;
import Models.Shape.Shape;
import SpriteSheet.SpriteSheet;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Entity implements Collideable, Moveable, Comparable<Entity>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Entity> colliders;
	private transient Image img;
	private Direction direction;
	private Shape shape;
	private String tag;
	private int speed = 1;
	private int prevX, prevY;
	private int displayLayer;
	
	public Entity(Image i, int x, int y){
		this(i, x, y, (int)i.getWidth(), (int)i.getHeight());
	}
	
	public Entity(Image i, int x, int y, int width, int height){
		this(i, new Shape(x, y, width, height));
	}
	
	public Entity(Image i, Shape shape){
		colliders = new ArrayList<>();
		setImage(i);
		setShape(shape);
		setTag("Entity");
		setDisplayLayer(0);
		setCurrDir(Direction.RIGHT);
	}
	
	@Override
	public void move(ArrayList<Entity> entities) {
		for(int i = 0; i < speed; i++){
			prevX = getXPos();
			prevY = getYPos();
			setXPos(getXPos() + getCurrDir().getX());
			setYPos(getYPos() + getCurrDir().getY());
			CollisionSystem.checkMovementCollisions(this, entities);
		}
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
	
	public int getPrevXPos(){
		return prevX;
	}
	
	public int getPrevYPos(){
		return prevY;
	}
	
	public void resetColliders(){
		colliders.clear();
	}
	
	public void addCollider(Entity e){
		colliders.add(e);
	}
	
	public ArrayList<Entity> getColliders(){
		return colliders;
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
	
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.defaultWriteObject();
		ImageIO.write(SwingFXUtils.fromFXImage(img, null), "jpg", out);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject();
		img = SwingFXUtils.toFXImage(ImageIO.read(in), null);
	}

}
