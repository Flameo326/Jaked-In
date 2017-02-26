package Controller;

import java.util.HashSet;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {
	
	public static KeyCode Player1Up = KeyCode.W;
	public static KeyCode Player1Down = KeyCode.S;
	public static KeyCode Player1Left = KeyCode.A;
	public static KeyCode Player1Right = KeyCode.D;
	public static KeyCode Player1Attack = KeyCode.SPACE;
	public static KeyCode Player2Up = null;
	public static KeyCode Player2Down = null;
	public static KeyCode Player2Left = null;
	public static KeyCode Player2Right = null;
	public static KeyCode Player2Attack = null;
	
	// We can Add more Relevant Input Things here if Needed
	private static HashSet<KeyCode> keyInput = new HashSet<>();
	
	public static void keyPress(KeyEvent e){
		if(!keyInput.contains(e.getCode())){
			keyInput.add(e.getCode());
		}
	}
	
	public static void keyRelease(KeyEvent e){
		keyInput.remove(e.getCode());
	}
	
	public static boolean keyInputContains(KeyCode k){
		return keyInput.contains(k);
	}

}
