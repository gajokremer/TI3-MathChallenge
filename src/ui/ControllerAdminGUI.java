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
import model.Player;

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
			
			Player p = new Player(tfNewPlayerName.getText(), 0);
			gm.setPlayingNow(p);
			
			lbPlayingNow.setText("Player: " + gm.getPlayingNow().getName());
			lbCurrentPoints.setText("Score: " + gm.getPlayingNow().getScore());
			
			btnNewQuestion();
			
//			int[] answers = gm.newProblem();
//			
//			String question = gm.getCurrentQuestion();
//			
//			tfProblem.setText(question);
//			
////			System.out.print("\nGUI: ");
////			gm.printArray(answers);
//			
//			rbAnswer1.setText(String.valueOf(answers[0]));
//			rbAnswer2.setText(String.valueOf(answers[1]));
//			rbAnswer3.setText(String.valueOf(answers[2]));
//			rbAnswer4.setText(String.valueOf(answers[3]));
			
		} else {
			
			String header = "Game error";
			String message = "A name must be given to start the game";
			
			showWarningDialogue(header, message);
		}
	}
	
	@FXML
	void btnNewQuestion() {
		
		int[] answers = gm.newProblem();
		
		String question = gm.getCurrentQuestion();
		
		tfProblem.setText(question);
		
//		System.out.print("\nGUI: ");
//		gm.printArray(answers);
		
		rbAnswer1.setSelected(false);
		rbAnswer2.setSelected(false);
		rbAnswer3.setSelected(false);
		rbAnswer4.setSelected(false);
		
		rbAnswer1.setText(String.valueOf(answers[0]));
		rbAnswer2.setText(String.valueOf(answers[1]));
		rbAnswer3.setText(String.valueOf(answers[2]));
		rbAnswer4.setText(String.valueOf(answers[3]));
		
		lbCurrentPoints.setText("Score: " + gm.getPlayingNow().getScore());
	}
	
	@FXML
	void btnConfirm() throws IOException {
		
		int answer = 0;
		
		if(rbAnswer1.isSelected()) {
    		
    		answer = Integer.parseInt(rbAnswer1.getText());
    		
    	} else if(rbAnswer2.isSelected()) {
    		
    		answer = Integer.parseInt(rbAnswer2.getText());
    		
    	} else if(rbAnswer3.isSelected()) {
    		
    		answer = Integer.parseInt(rbAnswer3.getText());
    		
    	} else if(rbAnswer4.isSelected()) {
    		
    		answer = Integer.parseInt(rbAnswer4.getText());
    		
    	} else {
    		
    		String header = "Game error";
    		String message = "An answers must be selected";
    		
    		showWarningDialogue(header, message);
    	}
		
		boolean correct = gm.verifyAnswer(answer);
		System.out.println("\nCorrect: " + correct);

		if(correct) {
			
			int newScore = gm.getPlayingNow().getScore() + 10;
			gm.getPlayingNow().setScore(newScore);
			
		} else {
			
			if(gm.getPlayingNow().getScore() > 10) {
				
				int newScore = gm.getPlayingNow().getScore() - 10;
				gm.getPlayingNow().setScore(newScore);
				
			} else {
				
				gm.getPlayingNow().setScore(0);
			}
		}
		
		System.out.println("Current score: " + gm.getPlayingNow().getScore());
		
		btnNewQuestion();
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