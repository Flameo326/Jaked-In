package Controller;

import java.util.ArrayList;
import java.util.Random;

import Models.Players.PlayableCharacter;

public class RandomAIController {
	
	private static final int FPS = 30;
	
	private PlayableCharacter computer;
	private Random rand;
	private int decisionChoice, decisionLength, timer;
	
	public RandomAIController(PlayableCharacter comp){
		computer = comp;
		rand = new Random();
	}
	
	public void checkForInput(){
		if(++timer > decisionLength){
			timer = 0;
			// Choose it's action
			decisionChoice = rand.nextInt(4);
			// Choose the length of time to do that action
			// 1 - 10 by FPS/2 = .5 Secs - 5 Secs
			decisionLength = (rand.nextInt(10) + 1) * FPS/2;
		}
		switch(decisionChoice){
		case 0:
			computer.move(0, 1);
			System.out.println("Computer Choose DOWN");
			break;
		case 1:
			computer.move(0, -1);
			System.out.println("Computer Choose UP");
			break;
		case 2:
			computer.move(1, 0);
			System.out.println("Computer Choose RIGHT");
			break;
		case 3:
			computer.move(-1, 0);
			System.out.println("Computer Choose LEFT");
			break;
		}
	}

}
