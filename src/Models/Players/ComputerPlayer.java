package Models.Players;

import java.util.ArrayList;
import java.util.Random;

import Controller.CollisionSystem;
import Enums.Difficulties;
import Enums.Direction;
import Models.Entity;
import Models.Weapon.MeleeWeapon;
import Models.Weapon.Attack.Attack;
import Projectiles.Pulsar;
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
			
			//Move
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
			
			// Attack
			if(rand.nextInt(60) == 0){
				Attack h = attack();
				if(h != null){
					entities.add(h);
				}
			}
		
		case NORMAL:
		case HARD:
			
			// Move
			prevX = getXPos(); prevY = getYPos();
			
			int shortestDistanceFromEnemy = Integer.MAX_VALUE;
			Entity enemy = null;
			for(PlayableCharacter p : Enemys){
				if(Math.abs(p.getXPos() - getXPos()) + Math.abs(p.getYPos() - getYPos()) < shortestDistanceFromEnemy){
					shortestDistanceFromEnemy = Math.abs(p.getXPos() - getXPos()) + Math.abs(p.getYPos() - getYPos());
					enemy = p;
				}
			}
			if(getWeapon().getClass() == MeleeWeapon.class){
				
				//Move choice when melee
				if(timer > decisionLength){
					if(shortestDistanceFromEnemy < 800 || difficulty == Difficulties.HARD){
						decisionLength = (new Random()).nextInt(20) * 20;
						timer = 0;
						setCurrDir(directionOfClosestEnemy());
					} else {
						timer = 0;
						decisionLength = (new Random()).nextInt(20) * 20;
						decisionChoice = rand.nextInt(8);
						setCurrDir(Direction.values()[decisionChoice >= 4 ? decisionChoice+1 : decisionChoice]);
					}
				}
			} else {
				if(enemy != null){
					
					//Move choice when ranged
					if(timer > decisionLength){
						if(shortestDistanceFromEnemy < 100){
							setCurrDir(Direction.getInverse(directionOfClosestEnemy()));
						} else if(shortestDistanceFromEnemy > 300 && (shortestDistanceFromEnemy < 800 || difficulty == Difficulties.HARD)) {
							setCurrDir(directionOfClosestEnemy());	
						} else {
							timer = 0;
							decisionLength = (new Random()).nextInt(20) * 20;
							decisionChoice = rand.nextInt(8);
							setCurrDir(Direction.values()[decisionChoice >= 4 ? decisionChoice+1 : decisionChoice]);
						}
					}
				}
			}
			
			move(entities);
			
			// if didn't move
			if(prevX == getXPos() && prevY == getYPos()){
				timer = decisionLength + 200;
			}
			timer += Math.abs(prevX - getXPos()) + Math.abs(prevY - getYPos()); 
			
			
			// Attack
			if(!Enemys.isEmpty()){
				if(rand.nextInt(60) == 0){
					Direction currDir = getCurrDir();
					//change direction to fire, then change the direction back
					setCurrDir(directionOfClosestEnemy());
					if(difficulty != Difficulties.HARD){
						Pulsar p = new Pulsar(this);
						if(!p.killOff(entities, true).isEmpty()){
							Attack h = attack();
							if(h != null){
								entities.add(h);
							}
						}
					} else {
						for(int i = 0; i < 8; i++){
							setCurrDir(Direction.values()[i >= 4 ? i+1 : i]);
							Pulsar p = new Pulsar(this);
							if(!p.killOff(entities, false).isEmpty()){
								Pulsar temp = new Pulsar(this);
								p.killOff(entities, true);
								
								Attack h = attack();
								if(h != null){
									entities.add(h);
								}
							}
						}	
					}
					setCurrDir(currDir);
				}
			}
			
			
			break;
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

	public Direction directionOfClosestEnemy(){
		Direction tempDirection = null;
		int closestEnemy = Integer.MAX_VALUE;
		for(PlayableCharacter i : Enemys){
			if(closestEnemy > Math.abs(i.getXPos() - getXPos()) + Math.abs(i.getYPos() - getYPos())){
				closestEnemy = Math.abs(i.getXPos() - getXPos()) + Math.abs(i.getYPos() - getYPos());
				tempDirection = Direction.getInverse(CollisionSystem.getCollision(this, i).collisionNormal);
			}
		}
		return tempDirection;
	}
	
}
