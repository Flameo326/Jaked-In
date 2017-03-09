package Models.Players;

import java.util.ArrayList;
import java.util.Random;

import Enums.Difficulties;
import Enums.Direction;
import Models.Entity;
import Models.Weapon.Attack.Attack;
import javafx.scene.image.Image;

public class ComputerPlayer extends PlayableCharacter{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int computerID = 0;
	private int prevX = 0, prevY = 0;
	private Difficulties difficulty = Difficulties.EASY;
	
	private Random rand;
	private int decisionChoice, decisionLength, timer;
	private int decisionLengthIncrement, decisionLengthRange;

	
	public ComputerPlayer(Image i, int x, int y, Difficulties difficulty) {
		super(i, x, y);
		rand = new Random();
		setTag("Computer-" + ++computerID);
		setDecisionLengthIncrement(.1);
		setDecisionLengthRange(30);
		this.difficulty = difficulty;
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
		super.update(entities);
		// We can make the choices so it can move diagnolly
		// We can also make it so that the length is more random, so like .1 seconds to 3 but by leaps of .1 or something
		switch(difficulty){
		case EASY:
			if(++timer > decisionLength){
				timer = 0;
				// Choose the length of time to do that action
				// 1 - 10 by Quarter Second = .25 Secs - 2.5 Secs
				decisionLength = (rand.nextInt(decisionLengthRange) + 1) * decisionLengthIncrement;
				// Choose it's action
				decisionChoice = rand.nextInt(8);
				setCurrDir(Direction.values()[decisionChoice >= 4 ? decisionChoice+1 : decisionChoice]);
				move(entities);
			}
		case NORMAL:
			if(timer > decisionLength){
				timer = 0;
				decisionLength = (rand.nextInt(decisionLengthRange) + 1) * decisionLengthIncrement;
				decisionChoice = rand.nextInt(8);
				setCurrDir(Direction.values()[decisionChoice >= 4 ? decisionChoice+1 : decisionChoice]);
			}
			prevX = getXPos(); prevY = getYPos();
			do {
				move(entities);
				if(prevX == getXPos() && prevY == getYPos()){
					decisionLength = (rand.nextInt(decisionLengthRange) + 1) * decisionLengthIncrement;
					decisionChoice = rand.nextInt(8);
					setCurrDir(Direction.values()[decisionChoice >= 4 ? decisionChoice+1 : decisionChoice]);
				}
			} while(prevX == getXPos() && prevY == getYPos());
			timer += Math.abs(prevX - getXPos()) + Math.abs(prevY - getYPos()); 
			break;
		case HARD:
			break;
		}
		if(rand.nextInt(60) == 0){
			Attack h = attack();
			if(h != null){
				entities.add(h);
			}
		}
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

	public static void resetComputerID(){
		computerID = 0;
	}

}
