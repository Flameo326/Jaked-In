package Puzzle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import Enums.ButtonColors;
import Interfaces.Publishable;
import Interfaces.Subscribable;
import SpriteSheet.SpriteSheet;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.paint.Color;

public class CombinedColor extends Button implements Publishable<Door>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Door> subscribers = new ArrayList<>();
	private int currRed = 255, solutionRed;
	private int currGreen = 255, solutionGreen;
	private int currBlue = 255, solutionBlue;
	private ArrayList<Integer> possibleSolutions = new ArrayList<>();
//	private final Color solutionColor;

	public CombinedColor(int x, int y) {
		super(SpriteSheet.getBorderedBlock(10, 10, Color.WHITE, 2), x, y);
		for (int j = 0; j < 6; j++) {
			possibleSolutions.add(j * 51);
		}
		Collections.shuffle(possibleSolutions);
		solutionRed = possibleSolutions.get(0);
		solutionGreen = possibleSolutions.get(1);
		solutionBlue = possibleSolutions.get(2);
	}

	public void changeColor(ButtonColors color, int newColorValue) {
		if (color == ButtonColors.RED) {
			currRed = newColorValue;
		} else if (color == ButtonColors.GREEN) {
			currGreen = newColorValue;
		} else {
			currBlue = newColorValue;
		}
		setImage(SpriteSheet.getBorderedBlock(10, 10, getColor(), 2, getSolutionColor()));
		checkForSolution();
	}

	public Color getColor() {
		Color c = Color.rgb(currRed, currGreen, currBlue);
		return c;
	}
	
	public Color getSolutionColor(){
		return Color.rgb(solutionRed, solutionGreen, solutionBlue);
	}

	
	public void checkForSolution(){
		if(getColor().equals(getSolutionColor())){
			notifySubscribers();
		}
	}
	
	@Override
	public void update(Button b) {
		currRed = 255;
		currGreen = 255;
		currBlue = 255;
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
