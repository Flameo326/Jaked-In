package Puzzle;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class CombinedColor extends Button{
	
	private static ArrayList<PuzzleButton> puzzleButtons = new ArrayList<>();	
	private static int red = 255;
	private static int green = 255;
	private static int blue = 255;

	public CombinedColor(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
	}

	public static void addColor(int newRed, int newGreen, int newBlue){
		red = (red + newRed)/2;
		green = (green + newGreen)/2;
		blue = (blue + newBlue)/2;
	}

	public static Color getColor() {
		Color c = Color.rgb(red, green, blue);
		return c;
	}
	
	public static void resetColor(){
		red = 255;
		green = 255;
		blue = 255;
	}

}
