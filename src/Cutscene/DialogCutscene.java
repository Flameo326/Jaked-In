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
	
	private Image prevImage;
	private String[] phrases;
	private int phraseCount, letterCount;
	private int yPos, speed;
	private int letterSpeed;
	private long nextLetter, nextUp;

	public DialogCutscene(StoryController st, String... phrases) {
		super(st);
		this.phrases = phrases;
		yPos = (int) (getCanvas().getHeight()*7/8);
		
		
//		getGraphics().getPixelWriter().getPixelFormat().
//		prevImage = new WritableImage((int)getCanvas().getWidth(), (int)getCanvas().getHeight());
//		for(int i = 0; i < prevImage.getHeight(); i++){
//			for(int y = 0; i < prevImage.getWidth(); y++){
//				
//			}
//		}
//		PixelWriter pw = ((WritableImage)prevImage).getPixelWriter();
//		getGraphics().getPixelWriter().getPixelFormat().
		
		getGraphics().setTextAlign(TextAlignment.CENTER);
	}
	
	public void setSpeed(int val){
		speed = val;
	}
	
	public void setLetterSpeed(int val){
		letterSpeed = val;
	}

	@Override
	public void handle(long now) {
		getGraphics().clearRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
//		getGraphics().drawImage(prevImage, 0, 0);
		
		getGraphics().setFill(new Color(1, 1, 1, .5));
		getGraphics().fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
		
		getGraphics().setFont(new Font(12));
		getGraphics().setFill(Color.BLACK);
		
		if(now >= nextLetter){
			letterCount++;
			nextLetter = letterSpeed/1000000l + now;
			if(!(phraseCount >= phrases.length) && letterCount >= phrases[phraseCount].length()){
				letterCount = 0;
				phraseCount++;
			}
		}
		if(now >= nextUp){
			yPos -= 1;
			nextUp = now + speed/1000000l;
		}
//		yPos += speed;
		int tempYPos = yPos;
		for(int i = 0; i < phraseCount; i++){
			getGraphics().fillText(phrases[i], getCanvas().getWidth()/2, tempYPos);
			tempYPos += 12;
		}
		if(!(phraseCount >= phrases.length)){
			getGraphics().fillText(phrases[phraseCount].substring(0, letterCount), getCanvas().getWidth()/2, tempYPos);
		}
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
