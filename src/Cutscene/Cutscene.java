package Cutscene;

import Controller.StoryController;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

public abstract class Cutscene extends AnimationTimer{
	
	private StoryController controller;
	private Canvas myCanvas;
	private GraphicsContext g;
	
	public Cutscene(StoryController st){
		controller =  st;
		myCanvas = st.getCanvas();
		g = myCanvas.getGraphicsContext2D();
		
	}
	
	@Override
	public void stop(){
		super.stop();
		controller.update(true);
	}
	
	public GraphicsContext getGraphics(){
		return g;
	}
	
	public Canvas getCanvas(){
		return myCanvas;
	}

}
