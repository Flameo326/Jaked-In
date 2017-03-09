package Puzzle;

import Enums.ButtonColors;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ColorButton extends Button {
	private int color = 255;
	private final ButtonColors buttonColor;
	private final boolean isIncrementer;
	private CombinedColor solution;
	
	public ColorButton(int x, int y, ButtonColors buttonColor, boolean isIncrementer, CombinedColor b) {
		super(i, x, y);
		this.buttonColor = buttonColor;
		this.isIncrementer = isIncrementer;
		solution = b;
	}
	
	private void changeColor(int colorChange){
		color += colorChange;
		if(color > 255){
			color = 255;
		}else if(color < 0){
			color = 0;
		}
	}

	@Override
	public void interact(PlayableCharacter c) {
		if(isIncrementer){
			changeColor(51);
			solution.changeColor(buttonColor, color);
		}else{
			changeColor(-51);
			solution.changeColor(buttonColor, color);
		}
	}
	
	@Override
	public void update(Button b) {
		color = 255;
	}

}
