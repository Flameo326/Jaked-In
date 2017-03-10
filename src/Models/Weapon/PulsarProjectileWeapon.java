package Models.Weapon;

import java.util.ArrayList;

import Controller.GameController;
import Enums.Direction;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Projectiles.Pulsar;
import SpriteSheet.SpriteSheet;

public class PulsarProjectileWeapon extends ProjectileWeapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PulsarProjectileWeapon(PlayableCharacter e, int bullets) {
		super(e, SpriteSheet.getBouceProjectile(), bullets, 5, 30);
	}
	
	public Attack attack(ArrayList<Entity> entities) {
		Pulsar p = null;
		if(GameController.getTimer() >= getNextAttackTime()){
			setNextAttackTime();
			for(int i = 0; i < 8; i++){				
				p = new Pulsar(getOwnedEntity());
				p.setCurrDir(Direction.values()[i >= 4 ? i+1 : i]);
				p.setLifeTime((int)(bulletLifeTime * 3.33));
				p.killOff(entities);
			}
			if(--bullets <= 0){
				getOwnedEntity().removeWeapon();
			}
		}
		return p;
	}
	
	@Override
	public Attack attack(){
		throw new UnsupportedOperationException("Invalid attack method called");
	}
	
	public int getDamage(){
		return 0;
	}
	
}
