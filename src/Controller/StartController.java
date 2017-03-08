package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartController implements Initializable {
	
    @FXML
    private Button storyBtn;
    
    @FXML
    private Button loadBtn;

    @FXML
    private Button arenaBtn;

    @FXML
    private Button exitBtn;
    
    public void storyBtnAction() throws IOException{
    	Stage s = (Stage)storyBtn.getScene().getWindow();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StoryFXML.fxml"));
    	StackPane root = loader.load();
    	Scene scene = new Scene(root, s.getWidth(), s.getHeight());
    	s.setScene(scene);
    	
    	StoryController controller = loader.getController();
    	controller.newStory();
    }
    
    @FXML
    void loadBtnAction() throws IOException {
//    	Stage s = (Stage)storyBtn.getScene().getWindow();
//    	
//    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/StoryFXML.fxml"));
//    	StackPane root = loader.load();
//    	Scene scene = new Scene(root, s.getWidth(), s.getHeight());
//    	s.setScene(scene);
//    	
//    	StoryController controller = loader.getController();
//    	controller.load();
    	
    	Stage s = (Stage)storyBtn.getScene().getWindow();
    	Canvas c = new Canvas(s.getScene().getWidth(), s.getScene().getHeight());
    	StackPane root = new StackPane(c);
    	Scene scene = new Scene(root, s.getScene().getWidth(), s.getScene().getHeight());
    	s.setScene(scene);
    	
    	StoryController controller = StoryController.load();
    	if(controller != null){
    		controller.setCanvas(c);
        	controller.createGameController();
    	} else {
    		storyBtnAction();
    	}
    	
    	
    	// I need to load the story controller
    	// attach the canvas
    	//
    }
    
    public void arenaBtnAction() throws IOException{
    	Stage s = (Stage)arenaBtn.getScene().getWindow();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ArenaMenuFXML.fxml"));
    	BorderPane root = loader.load();
    	Scene scene = new Scene(root, s.getScene().getWidth(), s.getScene().getHeight());
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
