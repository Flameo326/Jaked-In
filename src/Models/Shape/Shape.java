package Models.Shape;

import java.io.Serializable;

public class Shape implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int centerX, centerY;
	
	// Width and Height are deceptive, they can be one mre than the actual value because of floating point conversion
	private int width, height;
	
	// Should be stored or derived?
	private int minX, maxX;
	private int minY, maxY;
	
	// By default, a shape will always be in AABB form
	public Shape(int centerX, int centerY, int width, int height){
		setCenterX(centerX);
		setCenterY(centerY);
		setWidth(width);
		setHeight(height);
	}
	
	public void setCenterX(int val){
		centerX = val;
		setMinX(getCenterX() - getWidth()/2);
		setMaxX(getCenterX() + getWidth()/2);
	}
	
	public void setCenterY(int val){
		centerY = val;
		setMinY(getCenterY() - getHeight()/2);
		setMaxY(getCenterY() + getHeight()/2);
	}
	
	public void setWidth(int val){
		width = val;
		setMinX(getCenterX() - width/2);
		setMaxX(getCenterX() + width/2);
	}
	
	public void setHeight(int val){
		height = val;
		setMinY(getCenterY() - height/2);
		setMaxY(getCenterY() + height/2);
	}
	
	public void setMinX(int val){
		minX = val;
	}
	
	public void setMaxX(int val){
		maxX = val;
	}
	
	public void setMinY(int val){
		minY = val;
	}
	
	public void setMaxY(int val){
		maxY = val;
	}
	
	public int getCenterX(){
		return centerX;
	}
	
	public int getCenterY(){
		return centerY;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getRoundedWidth(){
		return getMaxX()-getMinX();
	}
	
	public int getRoundedHeight(){
		return getMaxY()-getMinY();
	}
	
	public int getMinX(){
		return minX;
	}
	
	public int getMaxX(){
		return maxX;
	}
	
	public int getMinY(){
		return minY;
	}
	
	public int getMaxY(){
		return maxY;
	}

}
