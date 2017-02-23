package Models.Players;

import java.util.ArrayList;
import java.util.Random;

import Models.Entity;
import Models.Weapon.HitBox;
import javafx.scene.image.Image;

public class ComputerPlayer extends PlayableCharacter{
	
	private static int computerID = 0;
	
	private Random rand;
	private int decisionChoice, decisionLength, timer;

	public ComputerPlayer(Image i, int x, int y) {
		super(i, x, y);
		rand = new Random();
		setTag("Computer." + ++computerID);
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		// We can make the choices so it can move diagnolly
		// We can also make it so that the length is more random, so like .1 seconds to 3 but by leaps of .1 or something
		if(++timer > decisionLength){
			timer = 0;
			// Choose it's action
			decisionChoice = rand.nextInt(4);
			// Choose the length of time to do that action
			// 1 - 10 by Quarter Second = .25 Secs - 2.5 Secs
			decisionLength = rand.nextInt(10) * 15;
			switch(decisionChoice){
			case 0:
				setCurrXDir(0);
				setCurrYDir(1);
				break;
			case 1:
				setCurrXDir(0);
				setCurrYDir(-1);
				break;
			case 2:
				setCurrXDir(1);
				setCurrYDir(0);
				break;
			case 3:
				setCurrXDir(-1);
				setCurrYDir(0);
				break;
			}
		}
		if(rand.nextInt(60) == 0){
			HitBox h = attack();
			if(h != null){
				entities.add(h);
			}
		}
		move(getCurrXDir(), getCurrYDir());
		System.out.println(getTag() + " X: " + getCenterXPos() + " Y: " + getCenterYPos());
	}


}
