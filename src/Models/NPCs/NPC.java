package Models.NPCs;

import Interfaces.Interactable;
import Models.Entity;
import javafx.scene.image.Image;

public abstract class NPC extends Entity implements Interactable{

	public NPC(Image i, int x, int y, int width, int height) {
		super(i, x, y, width, height);
		setTag("NPC");
		setDisplayLayer(6);
	}

}
