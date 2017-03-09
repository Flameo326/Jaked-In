package Puzzle;

import java.util.ArrayList;

import Interfaces.Interactable;
import Interfaces.Publishable;
import Interfaces.Subscribable;
import Models.Players.PlayableCharacter;
import javafx.scene.image.Image;

public class ResetButton extends Button implements Interactable, Publishable<Button>{
	
	private  ArrayList<Subscribable<Button>> subscribers = new ArrayList<>();

	public ResetButton(Image i, int x, int y) {
		super(i, x, y);
	}

	@Override
	public void interact(PlayableCharacter c) {
		notifySubscribers();
		
	}
	@Override
	public  void attach(Subscribable<Button> sub) {
		subscribers.add(sub);
	}

	@Override
	public  void detach(Subscribable<Button> sub) {
		subscribers.remove(sub);
		
	}

	@Override
	public  void notifySubscribers() {
		for (Subscribable<Button> s : subscribers) {
			s.update(null);
		}
	}

}
