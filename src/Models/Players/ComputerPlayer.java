package Models.Players;

import java.util.ArrayList;
import java.util.Random;

import Enums.Direction;
import Models.Entity;
import Models.Weapon.HitBox;
import javafx.scene.image.Image;

public class ComputerPlayer extends PlayableCharacter{
	
	private static int computerID = 0;
	
	private Random rand;
	private int decisionChoice, decisionLength, timer;
	private int decisionLengthIncrement, decisionLengthRange;

	public ComputerPlayer(Image i, int x, int y) {
		super(i, x, y);
		rand = new Random();
		setTag("Computer." + ++computerID);
		setDecisionLengthIncrement(.1);
		setDecisionLengthRange(30);
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		// We can make the choices so it can move diagnolly
		// We can also make it so that the length is more random, so like .1 seconds to 3 but by leaps of .1 or something
		if(++timer > decisionLength){
			timer = 0;
			// Choose it's action
			decisionChoice = rand.nextInt(8);
			// Choose the length of time to do that action
			// 1 - 10 by Quarter Second = .25 Secs - 2.5 Secs
			decisionLength = (rand.nextInt(decisionLengthRange) + 1) * decisionLengthIncrement;
			setCurrDir(Direction.values()[decisionChoice > 4 ? decisionChoice+1 : decisionChoice]);
		}
		if(rand.nextInt(60) == 0){
			HitBox h = attack();
			if(h != null){
				entities.add(h);
			}
		}
		move(getCurrDir().getX(), getCurrDir().getY());
		//System.out.println(getTag() + " X: " + getCenterXPos() + " Y: " + getCenterYPos());
	}
	
	/**
	 * Sets the DecisionLengthIncrement or how much DecisionLength increases by for 
	 * each value in DecisionLengthRange.
	 * This value will get multiplied by 60 becasue of Frame calculations
	 * @param i - the value in seconds
	 */
	public void setDecisionLengthIncrement(double i){
		decisionLengthIncrement = (int)(i * 60);
	}
	
	/**
	 * This value with DecsisionLengthIncrement determines how long the computer will choose it's action.
	 * The function is decisionLength = (rand.nextInt(Range) + 1) * Increment
	 * @param i 
	 */
	public void setDecisionLengthRange(int i){
		decisionLengthRange = i;
	}
	
	public int getDecisionLengthIncrement(){
		return decisionLengthIncrement;
	}
	
	public int getDecisionLengthRange(){
		return decisionLengthRange;
	}


}
