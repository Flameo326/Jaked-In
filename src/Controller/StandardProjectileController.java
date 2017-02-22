package Controller;

import Models.Weapon.Projectile;

public class StandardProjectileController {
	
	Projectile projectile;
	
	// Basically All this will do is update the Projectile correctly every frame.
	// We could do that from the class itself???
	public StandardProjectileController(Projectile p){
		projectile = p;
	}

}
