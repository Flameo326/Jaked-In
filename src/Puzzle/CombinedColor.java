package Puzzle;

import java.util.ArrayList;
import java.util.Collections;

import Enums.ButtonColors;
import Interfaces.Collideable;
import Models.Collision;
import Models.Entity;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class CombinedColor extends Button implements Collideable{


	private static final long serialVersionUID = 1L;
	private int red = 255;
	private int green = 255;
	private int blue = 255;
	private ArrayList<Integer> possibleSolutions = new ArrayList<>();
	private final Color solutionColor;


	public CombinedColor(int x, int y) {
		super(SpriteSheet.getBorderedBlock(50, 50, Color.WHITE, 10), x, y);
		for (int j = 0; j < 6; j++) {
			possibleSolutions.add(j * 51);
		}
		Collections.shuffle(possibleSolutions);
		solutionColor = Color.rgb(possibleSolutions.get(0), possibleSolutions.get(1), possibleSolutions.get(2));
		setImage(SpriteSheet.getBorderedBlock(90, 90, getColor(), 20, getSolutionColor()));
		setDisplayLayer(8);
		setTag("Wall");
	}

	public void changeColor(ButtonColors color, int adjustment) {
		if (color == ButtonColors.RED) {
			red += adjustment;
			if(red > 255){
				red = 255;
			}else if(red < 0){
				red = 0;
			}
		} else if (color == ButtonColors.GREEN) {
			green += adjustment;
			if(green > 255){
				green = 255;
			}else if(green < 0){
				green = 0;
			}
		} else {
			blue += adjustment;
			if(blue > 255){
				blue = 255;
			}else if(blue < 0){
				blue = 0;
			}
		}
		setImage(SpriteSheet.getBorderedBlock(90, 90, getColor(), 20, getSolutionColor()));
		System.out.println(red +" " + green + " " + blue );
		System.out.println(possibleSolutions.get(0) + " " + possibleSolutions.get(1) + " " + possibleSolutions.get(2));
	}

	public Color getColor() {
		Color c = Color.rgb(red, green, blue);
		return c;
	}
	
	public Color getSolutionColor(){
		return solutionColor;
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		if(getColor().equals(solutionColor)){
			entities.remove(this);
		}
	}
	
	public void hasCollided(Collision c) {
		}
	}


