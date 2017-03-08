package Cutscene;

import Controller.InputHandler;
import Controller.StoryController;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class DialogCutscene extends Cutscene {
	
	private WritableImage prevImage;
	private Font textFont;
	private String[] phrases;
	private int phraseCount, letterCount;
	private int yPos;

	public DialogCutscene(StoryController st, double percent, String... phrases) {
		super(st);
		this.phrases = phrases;
		yPos = (int)(getCanvas().getHeight()*percent);
		
		prevImage = getCanvas().snapshot(null, null);
		
		getGraphics().setTextAlign(TextAlignment.CENTER);
		textFont = new Font(18);
	}
	
	public void setYPos(double v){
		yPos = (int)(getCanvas().getHeight()*v);
	}
	
	public void setFont(Font f){
		textFont = f;
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
		getGraphics().setFont(textFont);
		
		letterCount++;
		if(!(phraseCount >= phrases.length) && letterCount >= phrases[phraseCount].length()){
			letterCount = 0;
			phraseCount++;
		}
		
		int tempYPos = yPos;
		for(int i = 0; i < phraseCount; i++){
			 tempYPos += (int) (displayText(phrases[i], (int)(getCanvas().getWidth()/2), tempYPos) * getGraphics().getFont().getSize());
		}
		if(!(phraseCount >= phrases.length)){
			displayText(phrases[phraseCount].substring(0, letterCount), (int)(getCanvas().getWidth()/2), tempYPos);
		}
			// Will display letters of the phrase, wait for input then display next phrase...
		
		if(InputHandler.keyInputContains(KeyCode.SPACE)){
			letterCount += 5;
		}
		if(InputHandler.keyInputContains(KeyCode.ENTER) ){
			if(phraseCount >= phrases.length){
				stop();
			} else {
				phraseCount += 1;
			}
		}
		getGraphics().setFont(new Font(18));
		getGraphics().fillText("Press Enter to skip", getCanvas().getWidth()*6/7,
				getCanvas().getHeight()-getGraphics().getFont().getSize());
	}
	
	public int displayText(String val, int x, int y){
		Text t = new Text(val);
		t.setFont(getGraphics().getFont());
		int width = (int) t.prefWidth(-1);
		
		int lineAmo = (int) (width/getCanvas().getWidth()+1);
		String lines[] = new String[lineAmo];
		
		String words[] = val.split(" ");
		int splitPoint = Math.round(((float)words.length)/lineAmo);
		for(int i = 0; i < lineAmo; i++){
			lines[i] = "";
			for(int e = 0; e < splitPoint; e++){
				if(i*splitPoint+e >= words.length) { break; }
				lines[i] += words[i*splitPoint+e] + " ";
			}
		}
		for(int i = 0; i < lineAmo ; i++){
			getGraphics().fillText(lines[i], x, y + i*getGraphics().getFont().getSize());
		}
		return lineAmo+1;
	}

}
