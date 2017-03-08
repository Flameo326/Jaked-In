package Models.Weapon;

import java.util.ArrayList;

import Enums.Direction;
import Models.Entity;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Projectiles.Pulsar;
import SpriteSheet.SpriteSheet;

public class PulsarProjectileWeapon extends ProjectileWeapon{

	public PulsarProjectileWeapon(PlayableCharacter e, int bullets) {
		super(e, SpriteSheet.getBouceProjectile(), 10000, 5, 30);
	}
	
	public Attack attack(ArrayList<Entity> entities) {
		Pulsar p = null;
		if(getTimer() >= getAttackTime()){
			setTimer(0);
			for(int i = 0; i < 8; i++){				
				p = new Pulsar(getOwnedEntity());
				p.setCurrDir(Direction.values()[i >= 4 ? i+1 : i]);
				p.setLifeTime((int)(bulletLifeTime * 3.33));
				p.killOff(entities);
			}
			if(--bullets <= 0){
				getOwnedEntity().removeWeapon(this);
			}
		}
		return p;
	}
	
	public Attack attack(){
		System.out.println("Wrong attack?");
		return null;
	};
	
}
