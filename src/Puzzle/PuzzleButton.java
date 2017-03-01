package Puzzle;

import Interfaces.Interactable;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PuzzleButton extends Button implements Interactable{
	private final int red;
	private final int green;
	private final int blue;

	
	public PuzzleButton(Image i, int x, int y, int width, int height, int red, int green, int blue) {
		super(i, x, y, width, height);
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public int getBlue() {
		return blue;
	}

	public int getGreen() {
		return green;
	}

	public int getRed() {
		return red;
	}
	
	public Color getColor() {
		Color c = Color.rgb(red,green,blue);
		return c;
		
	}

	@Override
	public void interact(PlayableCharacter c) {
		
		CombinedColor.addColor(red, green, blue);
	}

}
