package Entry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Jaked_In extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = FXMLLoader.load(getClass().getResource("/FXML/StartFXML.fxml"));
		
		Scene scene = new Scene(root);
		stage.setScene(scene);;
		
		stage.setTitle("Jak'd In");
		stage.centerOnScreen();
		stage.show();
	}

}
