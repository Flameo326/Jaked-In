package Puzzle;

import java.util.ArrayList;
import java.util.Collections;
import Enums.ButtonColors;
import Interfaces.Collideable;
import Models.Collision;
import Models.Entity;
//import Models.NPCs.Door;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class CombinedColor extends Button implements Collideable{


	private static final long serialVersionUID = 1L;
//	private ArrayList<Door> subscribers = new ArrayList<>();
	private int currRed = 255, solutionRed;
	private int currGreen = 255, solutionGreen;
	private int currBlue = 255, solutionBlue;
	private ArrayList<Integer> possibleSolutions = new ArrayList<>();


	public CombinedColor(int x, int y) {
		super(SpriteSheet.getBorderedBlock(50, 50, Color.WHITE, 10), x, y);
		for (int j = 0; j < 6; j++) {
			possibleSolutions.add(j * 51);
		}
		Collections.shuffle(possibleSolutions);
		solutionRed = possibleSolutions.get(0);
		solutionGreen = possibleSolutions.get(1);
		solutionBlue = possibleSolutions.get(2);
		
		setImage(SpriteSheet.getBorderedBlock(90, 90, getColor(), 20, getSolutionColor()));
		setDisplayLayer(8);
		setTag("Wall");
	}

	public void changeColor(ButtonColors color, int adjustment) {
		if (color == ButtonColors.RED) {
//<<<<<<< HEAD
//			currRed = newColorValue;
//=======
			currRed += adjustment;
			if(currRed > 255){
				currRed = 255;
			}else if(currRed < 0){
				currRed = 0;
			}
		} else if (color == ButtonColors.GREEN) {
//<<<<<<< HEAD
//			currGreen = newColorValue;
//=======
			currGreen += adjustment;
			if(currGreen > 255){
				currGreen = 255;
			}else if(currGreen < 0){
				currGreen = 0;
			}
		} else {
//<<<<<<< HEAD
//			currBlue = newColorValue;
//=======
			currBlue += adjustment;
			if(currBlue > 255){
				currBlue = 255;
			}else if(currBlue < 0){
				currBlue = 0;
			}
		}
		setImage(SpriteSheet.getBorderedBlock(90, 90, getColor(), 20, getSolutionColor()));
//		System.out.println(red +" " + green + " " + blue );
//		System.out.println(possibleSolutions.get(0) + " " + possibleSolutions.get(1) + " " + possibleSolutions.get(2));
	}

	public Color getColor() {
		Color c = Color.rgb(currRed, currGreen, currBlue);
		return c;
	}
	
	public Color getSolutionColor(){
		return Color.rgb(solutionRed, solutionGreen, solutionBlue);
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		if(getColor().equals(getSolutionColor())){
			entities.remove(this);
		}
	}
	
	@Override
	public void update(Button b) {
		currRed = 255;
		currGreen = 255;
		currBlue = 255;
	}

//	@Override
//	public void attach(Subscribable<Door> sub) {
//		subscribers.add((Door) sub);	
//	}
//
//	@Override
//	public void detach(Subscribable<Door> sub) {
//		subscribers.remove(sub);
//	}
//
//	@Override
//	public void notifySubscribers() {
//		for (Subscribable<Door> s : subscribers) {
//			s.update(null);

	@Override
	public void hasCollided(Collision c) {
		// do nothingg
	}
}


