package Models.Shape;

public class Circle extends Shape{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int radius;
	
	public Circle(int centerX, int centerY, int radius){
		super(centerX, centerY, radius*2, radius*2);
		setRadius(radius);
	}
	
	public void setRadius(int val){
		radius = val;
		setWidth(radius*2);
		setHeight(radius*2);
	}
	
	public int getRadius(){
		return radius;
	}

}
