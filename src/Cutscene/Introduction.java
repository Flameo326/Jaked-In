package Cutscene;


import Controller.StoryController;
import javafx.scene.text.Font;

public class Introduction extends DialogCutscene{

	public Introduction(StoryController st) {
		super(st, .2, "");
		setFont(new Font(32));
		setText(new String[] {"Use the WASD controls to move around", 
				"Space will shoot in the direction you are moving",
				"Press F to interact with NPC's", "Press C to toggle between your weapons once you have more"});
	}

}
