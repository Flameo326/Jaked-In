package Cutscene;

import Controller.InputHandler;
import Controller.StoryController;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class DialogCutscene extends Cutscene {
	
	private String[] phrases;
	private int phraseCount, letterCount;
	private int yPos, speed;
	private int letterSpeed;
	private long nextLetter, nextUp;

	public DialogCutscene(StoryController st, String... phrases) {
		super(st);
		this.phrases = phrases;
		yPos = (int) (getCanvas().getHeight()*7/8);
		
		getGraphics().setFill(new Color(1, 1, 1, .5));
		getGraphics().fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
		
		getGraphics().setTextAlign(TextAlignment.CENTER);
		getGraphics().setFont(new Font(12));
		getGraphics().setFill(Color.BLACK);
	}
	
	public void setSpeed(int val){
		speed = val;
	}
	
	public void setLetterSpeed(int val){
		letterSpeed = val;
	}

	@Override
	public void handle(long now) {
		if(now >= nextLetter){
			letterCount++;
			nextLetter = letterSpeed/1000000l + now;
		}
		if(now >= nextUp){
			yPos += 1;
			nextUp = now + speed/1000000l;
		}
		yPos += speed;
		int tempYPos = yPos;
		for(int i = 0; i < phraseCount; i++){
			getGraphics().fillText(phrases[i], getCanvas().getWidth()/2, tempYPos);
			tempYPos += 12;
		}
		getGraphics().fillText(phrases[phraseCount].substring(0, letterCount), getCanvas().getWidth()/2, tempYPos);
		// Will display letters of the phrase, wait for input then display next phrase...
		
		if(InputHandler.keyInputContains(KeyCode.SPACE)){
			letterCount++;
		}
		if(InputHandler.keyInputContains(KeyCode.ENTER)){
			if(phraseCount >= phrases.length){
				stop();
			} else {
				phraseCount += 1;
			}
		}
	}

}
