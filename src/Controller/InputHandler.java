package Controller;

import java.util.HashSet;

import javafx.scene.input.KeyEvent;

public class InputHandler {
	
	// We can Add more Relevant Input Things here if Needed
	private static HashSet<String> keyInput = new HashSet<>();
	
	public static void keyPress(KeyEvent e){
		if(!keyInput.contains(e.getText())){
			keyInput.add(e.getText());
		}
	}
	
	public static void keyRelease(KeyEvent e){
		keyInput.remove(e.getText());
	}
	
	public static boolean keyInputContains(String s){
		return keyInput.contains(s);
	}

}
