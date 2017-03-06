package Cutscene;

import Controller.StoryController;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public abstract class Cutscene extends AnimationTimer{
	
	private StoryController controller;
	private GraphicsContext g;
	
	public Cutscene(StoryController st){
		controller =  st;
		g = st.getCanvas().getGraphicsContext2D();
	}
	
	@Override
	public void stop(){
		super.stop();
		controller.update(true);
	}
	
	public GraphicsContext getGraphics(){
		return g;
	}

}
