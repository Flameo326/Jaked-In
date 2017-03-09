package SpriteSheet;

import java.util.Random;

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
	 * Door is going to be red Door
	 * 
	 */
	
	private static Image spriteSheet;
	
	// watson as the Image
	private static Image watson;
	
	// Default NPC and defaultPlayer will be the normal look but with color variations
	private static Image defaultNPC, defaultPlayer; 
	
	
	private static Image explosiveProjectile, normalProjectile, bounceProjectile;
	private static Image meleeAttack;
	
	private static Image medic, ally, enemy;
	private static Image door;
	
	// may be used for rooms and Paths?
	// otherwise it would be the standard colors but with darker edges
	private static Image roomTile, pathTile;
	
	public static void init(){
		spriteSheet = new Image("/Other/16x16_Sprite_Sheet.jpg");
//		medic 
	}	
	
	public static Image getSpriteSheetImage(int x, int y, int width, int height){
		PixelReader pr = spriteSheet.getPixelReader();
		WritableImage img = new WritableImage(width, height);
		img.getPixelWriter().setPixels(0, 0, width, height, pr, x, y);
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

	public static Image getExplosiveProjectile() {
		return SpriteSheet.getBlock(10, 10, Color.BLACK);
	}

	public static Image getNormalProjectile() {
		return SpriteSheet.getBlock(5, 5, Color.BLACK);
	}

	public static Image getBouceProjectile() {
		return SpriteSheet.getBlock(5, 5, Color.BLACK);
	}

	public static Image getMeleeWeapon() {
		return SpriteSheet.getBorderedBlock(20, 20, Color.WHITE, 3);
	}

	public static Image getAllyNPC() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Image getMedic(){
		return SpriteSheet.getBorderedBlock(30, 30, Color.RED, 2);
	}
	
	public static Image getRandomNPC(){
		Random rand = new Random();
		return getNPCWithColor(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
	}

	// General NPC Format with possible coloring for clothes?
	public static Image getNPCWithColor(Color c) {
		return SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2);
	}
	
	// Will just be Medic
//	public static Image getDoctor(){
//		return SpriteSheet.getBorderedBlock(30, 30, Color.WHITE, 2);
//	}

	public static Image getSpeedBoost() {
		return SpriteSheet.getBorderedBlock(10, 10, Color.MEDIUMPURPLE, 2);
	}

	public static Image getMedPack() {
		return SpriteSheet.getBorderedBlock(10, 10, Color.RED, 2);
	}

	public static Image getPrisoner() {
		return SpriteSheet.getBorderedBlock(30, 30, Color.DARKGRAY, 2);
	}

	public static Image getSecurityWorker() {
		return SpriteSheet.getBorderedBlock(30, 30, Color.BLUE, 2);
	}
	
	public static Image getBonusDamage() {
		return SpriteSheet.getBorderedBlock(10, 10, Color.DARKORANGE, 2);
	}
	
	public static Image getDamageReduction() {
		return SpriteSheet.getBorderedBlock(10, 10, Color.GOLD, 2);
	}
	
	public static Image getForceField() {
		return SpriteSheet.getBorderedBlock(10, 10, Color.YELLOW, 2);
	}

	public static Image getEnemy(){
		return SpriteSheet.getBorderedBlock(30, 30, Color.DARKRED, 2);
	}
	
	public static Image getWatson(){
		return watson;
	}
}
