package Cutscene;

import Controller.StoryController;

public class DialogCutscene extends Cutscene {
	
	private String[] phrase;
	private int phraseCount, letterCount;

	public DialogCutscene(StoryController st, String... phrases) {
		super(st);
		this.phrase = phrase;
	}

	@Override
	public void handle(long now) {
		// Will display letters of the phrase, wait for input then display next phrase...
	}

}
