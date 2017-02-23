package Interfaces;

import Models.Players.PlayableCharacter;

public interface Collectable extends Interactable{
	public void collect(PlayableCharacter c);
}
