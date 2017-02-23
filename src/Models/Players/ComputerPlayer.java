package Models.Players;

import java.util.ArrayList;
import java.util.Random;

import Models.Entity;
import Models.Weapon.HitBox;
import javafx.scene.image.Image;

public class ComputerPlayer extends PlayableCharacter{
	
	private static final int FPS = 30;
	
	private Random rand;
	private int decisionChoice, decisionLength, timer;

	public ComputerPlayer(Image i, int x, int y) {
		super(i, x, y);
		rand = new Random();
	}
	
	@Override
	public void update(ArrayList<Entity> entities) {
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
			move(0, 1);
			break;
		case 1:
			move(0, -1);
			break;
		case 2:
			move(1, 0);
			break;
		case 3:
			move(-1, 0);
			break;
		}
	}

	@Override
	public HitBox attack() {
		throw new UnsupportedOperationException("Not yet Implemented");
	}
}
