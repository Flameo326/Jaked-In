package SpriteSheet;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// This Class handles all the Graphics stuff
public final class SpriteSheet {
	
	private static Image spriteSheet;
	private static boolean isInit = false;
	public static final Image medic = getSpriteSheetImage(0, 0, 30, 30);
	
	public static void init(){
		spriteSheet = new Image("/Other/16x16_Sprite_Sheet.jpg");
		isInit = true;
	}	
	
	public static Image getSpriteSheetImage(int x, int y, int width, int height){
		if(!isInit){
			init();
		}
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

	public static Image getNPC() {
		return SpriteSheet.getBorderedBlock(30, 30, Color.DARKTURQUOISE, 2);
	}
	
	public static Image getDoctor(){
		return SpriteSheet.getBorderedBlock(30, 30, Color.WHITE, 2);
	}

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
}
