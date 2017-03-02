package Puzzle;

import java.util.ArrayList;
import java.util.Collections;

import Enums.ButtonColors;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class CombinedColor extends Button {

	
	private static int red = 255;
	private static int green = 255;
	private static int blue = 255;
	private static ArrayList<Integer> possibleSolutions = new ArrayList<>();
	private static Color solutionColor;
	private static boolean isSolved = false;

	public CombinedColor(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
		for (int j = 0; j < 6; j++) {
			possibleSolutions.add(j * 51);
		}
		Collections.shuffle(possibleSolutions);
		solutionColor = Color.rgb(possibleSolutions.get(0), possibleSolutions.get(1), possibleSolutions.get(2));
	}

	public static void changeColor(ButtonColors color, int newColorValue) {
		if (color == ButtonColors.RED) {
			red = newColorValue;
		} else if (color == ButtonColors.GREEN) {
			green = newColorValue;
		} else {
			blue = newColorValue;
		}
	}

	public static Color getColor() {
		Color c = Color.rgb(red, green, blue);
		return c;
	}

	
	public static void checkForSolution(){
		isSolved = getColor().equals(solutionColor);
	}
	
	@Override
	public void update() {
		red = 255;
		green = 255;
		blue = 255;
	}

}
