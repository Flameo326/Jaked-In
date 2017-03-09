package Puzzle;

import Enums.ButtonColors;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;

public class ColorButton extends Button {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int color = 255;
	private final ButtonColors buttonColor;
	private final boolean isIncrementer;
	private CombinedColor solution;
	
	public ColorButton(int x, int y, ButtonColors buttonColor, boolean isIncrementer, CombinedColor b) {
		super(SpriteSheet.getColorButtonImage(buttonColor, isIncrementer), x, y);
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
