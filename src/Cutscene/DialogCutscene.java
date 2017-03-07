package Cutscene;

import Controller.InputHandler;
import Controller.StoryController;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class DialogCutscene extends Cutscene {
	
	private WritableImage prevImage;
	private String[] phrases;
	private int phraseCount, letterCount;
	private int yPos;

	public DialogCutscene(StoryController st, double percent, String... phrases) {
		super(st);
		this.phrases = phrases;
		yPos = (int)(getCanvas().getHeight()*percent);
		
		prevImage = getCanvas().snapshot(null, null);
		
		getGraphics().setTextAlign(TextAlignment.CENTER);
	}
	
	public void setYPos(double v){
		yPos = (int)(getCanvas().getHeight()*v);
	}
	
	public void setFont(Font f){
		getGraphics().setFont(f);
	}
	
	public void setText(String...text){
		phrases = text;
	}

	@Override
	public void handle(long now) {
		getGraphics().clearRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
		getGraphics().drawImage(prevImage, 0, 0);
		
		getGraphics().setFill(new Color(1, 1, 1, .5));
		getGraphics().fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());

		getGraphics().setFill(Color.BLACK);
		
		letterCount++;
		if(!(phraseCount >= phrases.length) && letterCount >= phrases[phraseCount].length()){
			letterCount = 0;
			phraseCount++;
		}
		
		int tempYPos = yPos;
		for(int i = 0; i < phraseCount; i++){
			getGraphics().fillText(phrases[i], getCanvas().getWidth()/2, tempYPos);
			tempYPos += 12;
		}
		if(!(phraseCount >= phrases.length)){
			for(int i = 0; i < phrases[phraseCount].length()/90; i++){
				getGraphics().fillText(phrases[phraseCount].substring(0, letterCount), getCanvas().getWidth()/2, tempYPos);
			}
		}
			// Will display letters of the phrase, wait for input then display next phrase...
		
		if(InputHandler.keyInputContains(KeyCode.SPACE)){
			letterCount++;
		}
		if(InputHandler.keyInputContains(KeyCode.ENTER) ){
			if(phraseCount >= phrases.length){
				stop();
			} else {
				phraseCount += 1;
			}
		}
	}

}
