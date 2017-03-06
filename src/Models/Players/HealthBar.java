package Models.Players;

import java.util.ArrayList;

import Models.Collision;
import Models.Entity;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class HealthBar extends Entity{
	
	private PlayableCharacter ownedEntity;
	private double currPercent;
	
	public HealthBar(PlayableCharacter ownedEntity){
		super(SpriteSheet.getBorderedBlock(ownedEntity.getWidth(), 5, Color.RED, 2), 0, 0);
		this.ownedEntity = ownedEntity;
		setDisplayLayer(6);
	}

	@Override
	public void hasCollided(Collision c) {
		// Don't care if collision
	}

	@Override
	public void update(ArrayList<Entity> entities) {
		double percent = ((double)getOwnedEntity().getCurrentHealth()) / getOwnedEntity().getMaxHealth();
		if(percent != currPercent){
			setImage(SpriteSheet.getHealthBarImage(percent, getWidth()));
			currPercent = percent;
		}
		
		setYPos(getOwnedEntity().getShape().getMinY() - 15);
		setXPos(getOwnedEntity().getXPos());
	}
	
	public PlayableCharacter getOwnedEntity(){
		return ownedEntity;
	}

}
