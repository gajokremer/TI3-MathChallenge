package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.GameManager;

public class ControllerAdminGUI {
	
	private GameManager gm;
	
	public ControllerAdminGUI() {

		gm = new GameManager();
	}
	
	@FXML
	private Pane mainPane;
	
	@FXML
    private ImageView ivMainMenuLogo;

	@FXML
	private ImageView ivMainMenuLogo2;
	
    @FXML
    private TextField tfNewPlayerName;
    
    @FXML
    private Label lbTimer;
    
    @FXML
    private Label lbPlayingNow;
    
    @FXML
    private Label lbCurrentPoints;
    
    @FXML
    private TextField tfProblem;
    
    @FXML
    private ToggleGroup tgAnswers;
    
    @FXML
    private RadioButton rbAnswer1;
    
    @FXML
    private RadioButton rbAnswer2;
    
    @FXML
    private RadioButton rbAnswer3;
    
    @FXML
    private RadioButton rbAnswer4;

	@FXML
	void start() throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
		Image logo = new Image("Math Challenge Logo.png");
//		ivMainMenuLogo.setImage(logo);
		ivMainMenuLogo2.setImage(logo);
	}
	
	@FXML
	void btnPlay() throws IOException {
		
		if(!tfNewPlayerName.getText().trim().isEmpty()) {
			
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Questions.fxml"));
			fxmlloader.setController(this);
			Parent menu = fxmlloader.load();
			mainPane.getChildren().setAll(menu);
			
			Image logo = new Image("Math Challenge Logo.png");
			ivMainMenuLogo.setImage(logo);
			lbPlayingNow.setText("Player: " + tfNewPlayerName.getText());
			lbCurrentPoints.setText("Points: " + 0);
			
			gm.newProblem();
			String question = gm.getCurrentQuestion();
			
			tfProblem.setText(question);
			
		} else {
			
			String header = "Game error";
			String message = "A name must be given to start the game";
			
			showWarningDialogue(header, message);
		}
	}
	
	@FXML
	void btnScoreboard() {

		
	}
	
	@FXML
	void btnBack() throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
		Image logo = new Image("Math Challenge Logo.png");
		ivMainMenuLogo2.setImage(logo);
	}
	
	public void showSuccessDialogue(String header, String message) {

    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Math Challenge");
    	alert.setHeaderText(header);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
    
    public void showWarningDialogue(String header, String message) {
    	
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("Math Challenge");
    	alert.setHeaderText(header);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
}