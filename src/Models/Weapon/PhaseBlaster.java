package Models.Weapon;

import Controller.GameController;
import Models.Players.PlayableCharacter;
import Models.Weapon.Attack.Attack;
import Projectiles.PhaseBlasterProjectile;
import SpriteSheet.SpriteSheet;;

public class PhaseBlaster extends ProjectileWeapon{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhaseBlaster(PlayableCharacter e, int bullets) {
		super(e, SpriteSheet.getPhaseBlasterWeapon(), bullets, 100, 200);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Attack attack() {
		PhaseBlasterProjectile p = null;
		if(GameController.getTimer() >= getNextAttackTime()){
			setNextAttackTime();
			p = new PhaseBlasterProjectile(getOwnedEntity());
			if(--bullets <= 0){
				getOwnedEntity().removeWeapon();
			}
		}
		return p;
	}
	
	public int getDamage(){
		return 15;
	}

}
