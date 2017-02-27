package SpriteSheet;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// This Class handles all the Graphics stuff
public abstract class SpriteSheet {
	
	private static Image spriteSheet;
	private static boolean isInit = false;
	
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
	
	public static Image getBorderedBlock(int width, int height, Color c){
		WritableImage img = new WritableImage(width, height);
		PixelWriter pw = img.getPixelWriter();
		int borderWidth = 3;
		for(int i = 0; i < img.getHeight(); i++){
			for(int e = 0; e < img.getWidth(); e++){
				if(i < borderWidth || e < borderWidth || i > img.getHeight()-borderWidth ||
						e > img.getWidth()-borderWidth){
					pw.setColor(e, i, Color.BLACK);
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

}
