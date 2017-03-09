package Puzzle;

import java.util.ArrayList;
import java.util.Collections;

import Enums.ButtonColors;
import Interfaces.Publishable;
import Interfaces.Subscribable;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class CombinedColor extends Button implements Publishable<Door>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Door> subscribers = new ArrayList<>();
	private int red = 255;
	private int green = 255;
	private int blue = 255;
	private ArrayList<Integer> possibleSolutions = new ArrayList<>();
	private final Color solutionColor;

	public CombinedColor(int x, int y) {
		super(SpriteSheet.getBorderedBlock(10, 10, Color.WHITE, 2), x, y);
		for (int j = 0; j < 6; j++) {
			possibleSolutions.add(j * 51);
		}
		Collections.shuffle(possibleSolutions);
		solutionColor = Color.rgb(possibleSolutions.get(0), possibleSolutions.get(1), possibleSolutions.get(2));
		
	}

	public void changeColor(ButtonColors color, int newColorValue) {
		if (color == ButtonColors.RED) {
			red = newColorValue;
		} else if (color == ButtonColors.GREEN) {
			green = newColorValue;
		} else {
			blue = newColorValue;
		}
		setImage(SpriteSheet.getBorderedBlock(10, 10, getColor(), 2, getSolutionColor()));
		checkForSolution();
	}

	public Color getColor() {
		Color c = Color.rgb(red, green, blue);
		return c;
	}
	
	public Color getSolutionColor(){
		return solutionColor;
	}

	
	public void checkForSolution(){
		if(getColor().equals(solutionColor)){
			notifySubscribers();
		}
	}
	
	@Override
	public void update(Button b) {
		red = 255;
		green = 255;
		blue = 255;
	}

	@Override
	public void attach(Subscribable<Door> sub) {
		subscribers.add((Door) sub);	
	}

	@Override
	public void detach(Subscribable<Door> sub) {
		subscribers.remove(sub);
		
	}

	@Override
	public void notifySubscribers() {
		for (Subscribable<Door> s : subscribers) {
			s.update(null);
		}
		
	}

}
