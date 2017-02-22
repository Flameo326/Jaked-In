package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class arenaController implements Initializable {
	
	@FXML
	private Canvas myCanvas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GraphicsContext g = myCanvas.getGraphicsContext2D();
		g.setFill(Color.color(255.0/255, 50.0/255, 50.0/255));
		g.fillRect(50, 50, 100, 100);
	}

}
