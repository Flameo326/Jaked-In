package Models.Players;

import Controller.InputHandler;
import javafx.scene.image.Image;

public class HumanPlayer extends PlayableCharacter{

	public HumanPlayer(Image i, int x, int y) {
		super(i, x, y);
	}
	
	// Slight Problem here
	// If we want multiple Human Characters, they each have the same update Method. Can we change them for two player?
	@Override
	public void update(){
		if(InputHandler.keyInputContains("w")){
			move(0, -1);
		} 
		if(InputHandler.keyInputContains("s")){
			move(0, 1);
		}
		if(InputHandler.keyInputContains("a")){
			move(-1, 0);
		}
		if(InputHandler.keyInputContains("d")){
			move(1,  0);
		}
		if(InputHandler.keyInputContains("space")){
			
		}
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}	

}
