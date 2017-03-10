package SpriteSheet;

import java.util.Random;

import Enums.ButtonColors;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// This Class handles all the Graphics stuff
public final class SpriteSheet {
	
	/*
	 * AllyNPC will be default NPC with color
	 * Ambush "
	 * Angry "
	 * Doctor will be medic
	 * Door will be Orange/ Bronw, 40 x 40
	 * Medic will be Medic
	 * PowerUp "
	 * Prisoner will be Orange  shirt NPC
	 * Security Worker will be Police Like
	 * Story NPC will be Scientist
	 * 
	 * Computer will either be Enemy or Player with color
	 * Player will be Player with Color
	 * 
	 * Mine will be mine
	 * Spawner will Look like teleport?
	 * Turret will be blockish gray
	 * 
	 * Bonus Damage will be fist in air
	 * DamageReduction will be Shield
	 * ForceField will be ForceField
	 * Medpack will look like health
	 * Bullet Packs will look like corresponding bullets
	 * - Bounce
	 * - Explosive
	 * - Normal - Triple circle
	 * - Phase? Green opacity on outside
	 * Spped Boost will be shoes
	 * 
	 * Color button will be R, G, or B. with Plus or Minue
	 * 
	 */
	
	private static Image spriteSheet;
	
	private static Image medpack, damageBoost, damageReduction, forceFieldPickup, speed;
	private static Image forceField;
	
	private static Image turret, mine, spawner;
	
	private static Image redIncrement, redDecrement;
	private static Image greenIncrement, greenDecrement;
	private static Image blueIncrement, blueDecrement;
	
	private static Image explosiveProjectile, normalProjectile, phaseBlaster;
	private static Image explosiveProjectileWeapon, normalProjectileWeapon, phaseBlasterWeapon;
	private static Image explosiveProjectilePickup, normalProjectilePickup, bounceProjectilePickup, phaseBlasterPickup;
	private static Image meleeAttack;
	
	// Default NPC and defaultPlayer will be the normal look but with color variations
	private static Image defaultNPC, defaultPlayer; 
	private static Image medic;
	private static Image watson;
	
	// may be used for rooms and Paths?
	// otherwise it would be the standard colors but with darker edges
	private static Image door;
	
	public static void init(){
		spriteSheet = new Image("/Other/16x16_Sprite_Sheet.png");
		
		door = getSpriteSheetImage(159, 31, 40, 40);

//		security Worker
		forceField = getSpriteSheetImage(118, 31, 40, 40);
		
		defaultPlayer = getSpriteSheetImage(0, 0, 30, 30);
		medic = getSpriteSheetImage(31, 0, 30, 30);
		defaultNPC = getSpriteSheetImage(62, 0, 30, 30);
		watson = getSpriteSheetImage(93, 0, 30, 30);
		
		normalProjectileWeapon = getSpriteSheetImage(11, 70, 10, 10);
		explosiveProjectileWeapon = getSpriteSheetImage(22, 70, 10, 10);
		phaseBlasterWeapon = getSpriteSheetImage(0, 70, 10, 10);
		
		bounceProjectilePickup = getSpriteSheetImage(0, 53, 10, 10);
		explosiveProjectilePickup = getSpriteSheetImage(11, 53, 10, 10);
		normalProjectilePickup = getSpriteSheetImage(22, 53, 10, 10);
		phaseBlasterPickup = getSpriteSheetImage(33, 53, 10, 10);
		meleeAttack = getSpriteSheetImage(0, 81, 20, 20);
		
		normalProjectile = getBlock(5, 5, Color.BLACK);
		// bounce = normal
		explosiveProjectile = getBlock(10, 10, Color.BLACK);
		phaseBlaster = getSpriteSheetImage(0, 64, 5, 5);
		
		redIncrement = getSpriteSheetImage(200, 31, 15, 15);
		redDecrement = getSpriteSheetImage(200, 47, 15, 15);
		greenIncrement = getSpriteSheetImage(216, 31, 15, 15);
		greenDecrement = getSpriteSheetImage(216, 47, 15, 15);
		blueIncrement = getSpriteSheetImage(232, 31, 15, 15);
		blueDecrement = getSpriteSheetImage(232, 47, 15, 15);
		
		turret = getSpriteSheetImage(22, 31, 10, 10);
		mine = getSpriteSheetImage(0, 31, 10, 10);
		spawner = getSpriteSheetImage(11, 31, 10, 10);
		
		medpack = getSpriteSheetImage(0, 42, 10, 10);
		damageBoost = getSpriteSheetImage(11, 42, 10, 10);
		damageReduction = getSpriteSheetImage(22, 42, 10, 10);
		forceFieldPickup = getSpriteSheetImage(33, 42, 10, 10);
		speed = getSpriteSheetImage(44, 42, 10, 10);
	}	
	
	public static Image getSpriteSheetImage(int x, int y, int width, int height){
		PixelReader pr = spriteSheet.getPixelReader();
		WritableImage img = new WritableImage(width, height);
		img.getPixelWriter().setPixels(0, 0, width, height, pr, x, y);
		return img;
	}
	
	public static Image getBlank(){
		WritableImage img = new WritableImage(1, 1);
		return img;
	}
	
	public static Image getBorderedBlock(int width, int height, Color c, int borderWidth){
		return getBorderedBlock(width, height, c, borderWidth, Color.BLACK);
	}
	
	public static Image getBorderedBlock(int width, int height, Color c, int borderWidth, Color borderColor){
		WritableImage img = new WritableImage(width, height);
		PixelWriter pw = img.getPixelWriter();
		for(int i = 0; i < img.getHeight(); i++){
			for(int e = 0; e < img.getWidth(); e++){
				if(i < borderWidth || e < borderWidth || i > img.getHeight()-borderWidth ||
						e > img.getWidth()-borderWidth){
					pw.setColor(e, i, borderColor);
				} else {
					pw.setColor(e, i, c);
				}
			}
		}
		return img;
	}
	
	public static Image getBlock(int width, int height, Color c){
		WritableImage img = new WritableImage(width, height);
		PixelWriter pw = img.getPixelWriter();
		for(int i = 0; i < img.getHeight(); i++){
			for(int y = 0; y < img.getWidth(); y++){
				pw.setColor(y, i, c);
				
			}
		}
		return img;
	}
	
	public static Image getHealthBarImage(double percent, int width){
		WritableImage img = new WritableImage(width, 10);
		PixelWriter pw = img.getPixelWriter();
		int border = 2;
		for(int i = 0; i < img.getHeight(); i++){
			for(int y = 0; y < img.getWidth(); y++){
				if(i < border || y < border || i > img.getWidth()-border || i > img.getHeight()-border){
					pw.setColor(y, i, Color.BLACK);
				} else if(y < width * percent){
					pw.setColor(y, i, Color.RED);
				}
			}
		}
		return img;
	}
	
	public static Image getForceField(){
		return forceField;
	}
	
	public static Image getDoor(){
		return door;
	}
	
	public static Image getRoomImage(){
		return null;
	}
	
	public static Image getPathImage(){
		return null;
	}
	
	public static Image getExplosiveProjectileWeapon(){
		return explosiveProjectileWeapon;
	}
	
	public static Image getPhaseBlasterWeapon(){
		return phaseBlasterWeapon;
	}
	
	public static Image getNormalProjectileWeapon(){
		return normalProjectileWeapon;
	}

	public static Image getExplosiveProjectile() {
		return explosiveProjectile;
	}

	public static Image getNormalProjectile() {
		return normalProjectile;
	}

	public static Image getBouceProjectile() {
		return normalProjectile;
	}
	
	public static Image getPhaseBlasterProjectile(){
		return phaseBlaster;
	}
	
	public static Image getMeleeAttackImage(){
		return meleeAttack;
	}
	
	public static Image getExplosiveProjectilePickup(){
		return explosiveProjectilePickup;
	}
	
	public static Image getNormalProjectilePickup(){
		return normalProjectilePickup;
	}
	
	public static Image getBounceProjectilePickup(){
		return bounceProjectilePickup;
	}
	
	public static Image getPhaseBlasterPickup(){
		return phaseBlasterPickup;
	}

//	public static Image getMeleeWeapon() {
//		return SpriteSheet.getBorderedBlock(20, 20, Color.WHITE, 3);
//	}

	public static Image getAlly() {
		return SpriteSheet.getBorderedBlock(30, 30, Color.TURQUOISE, 3);
	}
	// Works for Players and Enemies
	
	public static Image getRandomPlayer(){
		Random rand = new Random();
		return getPlayerWithColor(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
	}
	
	public static Image getPlayerWithColor(Color c){
		PixelReader pr = defaultPlayer.getPixelReader();
		WritableImage img = new WritableImage((int)defaultPlayer.getWidth(), (int)defaultPlayer.getHeight());
		PixelWriter pw = img.getPixelWriter();
		for(int y = 0; y < img.getHeight(); y++){
			for(int x = 0; x < img.getWidth(); x++){
				Color pxCol = pr.getColor(x, y);
				if(pxCol.getOpacity() == 1 && pxCol.getRed() == 0 && pxCol.getBlue() == 0 && pxCol.getGreen() == 0){
					pw.setColor(x, y, c);
				} else {
					pw.setColor(x, y, pr.getColor(x, y));
				}
			}
		}
		return img;
	}
	
	public static Image getDefaultPlayer(){
		return defaultPlayer;
	}
	
	public static Image getRandomNPC(){
		Random rand = new Random();
		return getNPCWithColor(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
	}
	
	public static Image getNPCWithColor(Color c){
		PixelReader pr = defaultNPC.getPixelReader();
		WritableImage img = new WritableImage((int)defaultPlayer.getWidth(), (int)defaultPlayer.getHeight());
		PixelWriter pw = img.getPixelWriter();
		for(int y = 0; y < img.getHeight(); y++){
			for(int x = 0; x < img.getWidth(); x++){
				Color pxCol = pr.getColor(x, y);
				if(pxCol.getOpacity() == 1 && pxCol.getRed() == 0 && pxCol.getBlue() == 0 && pxCol.getGreen() == 0){
					pw.setColor(x, y, c);
				} else {
					pw.setColor(x, y, pr.getColor(x, y));
				}
			}
		}
		return img;
	}
	
	public static Image getDefaultNPC(){
		return defaultNPC;
	}
	
	public static Image getWatson(){
		return watson;
	}
	
	public static Image getMedic(){
		return medic;
	}
	
	public static Image getPrisoner() {
		return SpriteSheet.getBorderedBlock(30, 30, Color.DARKGRAY, 2);
	}

	public static Image getSecurityWorker() {
		return SpriteSheet.getBorderedBlock(30, 30, Color.BLUE, 2);
	}

	public static Image getSpeedBoost() {
		return speed;
	}

	public static Image getMedPack() {
		return medpack;
	}
	
	public static Image getBonusDamage() {
		return damageBoost;
	}
	
	public static Image getDamageReduction() {
		return damageReduction;
	}
	
	public static Image getForceFieldPickup() {
		return forceFieldPickup;
	}
	
	public static Image getTurret(){
		return turret;
	}
	
	public static Image getMine(){
		return mine;
	}
	
	public static Image getSpawner(){
		return spawner;
	}
	
	public static Image getColorButtonImage(ButtonColors color, boolean incrementing){
		Image i = null;
		if(incrementing){
			switch(color){
			case BLUE:
				i = blueIncrement;
				break;
			case GREEN:
				i = greenIncrement;
				break;
			case RED:
				i = redIncrement;
				break;
			}
		} else {
			switch(color){
			case BLUE:
				i = blueDecrement;
				break;
			case GREEN:
				i = greenDecrement;
				break;
			case RED:
				i = redDecrement;
				break;
			}
		}
		return i;
	}
}
