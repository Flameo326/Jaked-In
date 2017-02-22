package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartController implements Initializable {
	
    @FXML
    private Button storyBtn;

    @FXML
    private Button arenaBtn;

    @FXML
    private Button exitBtn;
    
    public void arenaBtnAction() throws IOException{
    	StackPane root = FXMLLoader.load(getClass().getResource("/FXML/ArenaFXML.fxml"));
    	Scene scene = new Scene(root);
    	
    	Stage s = (Stage)arenaBtn.getScene().getWindow();
    	s.setScene(scene);
    }
    
    public void exitBtnAction(){
    	Stage s = (Stage) exitBtn.getScene().getWindow();
    	s.close();
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
