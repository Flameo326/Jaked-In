package Puzzle;

import Enums.ButtonColors;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class ColorButton extends Button {

	private static final long serialVersionUID = 1L;
//	private int color = 255;
	private final ButtonColors buttonColor;
	private final boolean isIncrementer;
	private CombinedColor solution;

	public ColorButton(int x, int y, ButtonColors buttonColor, boolean isIncrementer, CombinedColor b) {
		super(SpriteSheet.getColorButtonImage(buttonColor, isIncrementer), x, y);

		this.buttonColor = buttonColor;
		this.isIncrementer = isIncrementer;
		solution = b;
//		setImage(SpriteSheet.getBorderedBlock(30, 30, getColor(), 2, getColor()));
		setDisplayLayer(6);
	}

	private Color getColor() {
		if (buttonColor == ButtonColors.RED) {
			return Color.RED;
		} else if (buttonColor == ButtonColors.GREEN) {
			return Color.GREEN;
		} else {
			return Color.BLUE;
		}
	}

	@Override
	public void interact(PlayableCharacter c) {
		if (timer > 20) {
			if (isIncrementer) {
				solution.changeColor(buttonColor, 51);
			} else {
				solution.changeColor(buttonColor, -51);
			}
			timer = 0;
		}
	}

}
