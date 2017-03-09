package Projectiles;

import java.util.ArrayList;
import java.util.HashSet;

import Models.Collision;
//github.com/Flameo326/Jaked-In.git
import Models.Entity;
import Models.Players.PlayableCharacter;
import SpriteSheet.SpriteSheet;
import javafx.scene.paint.Color;

public class Pulsar extends Projectile {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pulsar(PlayableCharacter e) {
		super(e, SpriteSheet.getBouceProjectile(), 8);
		setLifeTime(50);
	}

	@Override
	public void update(ArrayList<Entity> entities){
		entities.remove(this);
	}
	
	public HashSet<Entity> killOff(ArrayList<Entity> entities, boolean showLine){
		if(entities.contains(this)) {
			entities.remove(this);
		}
		for(int i = 0; i < lifeTime; i++){
			if(hasHit.isEmpty()){
//				move(entities);
				
//				for(int x = 0; x < getSpeed(); x++){
//					setXPos(getXPos() + getCurrDir().getX());
//					setYPos(getYPos() + getCurrDir().getY());
//					CollisionSystem.checkMovementCollisions(this, entities);
//				}
				move(entities);
				if(showLine){
					entities.add(new Entity(SpriteSheet.getBlock(2, 2, Color.RED), getXPos(), getYPos()) {
						private int timer = 0;
						
						@Override
						public void update(ArrayList<Entity> entities) {
							if(++timer > 20){
								entities.remove(this);
							}
							
						}
						
						@Override
						public void hasCollided(Collision c) {
							
						}
					});
				}
			}
		}
		return hasHit;
	}

	@Override
	public String toString() {
		String tempString = "Pulsar " + (hasHit.isEmpty() ? "has not hit." : "has hit.");
		for(Entity i : hasHit){
			tempString += "  " + i.getTag();
		}
		return tempString;
	}
}
