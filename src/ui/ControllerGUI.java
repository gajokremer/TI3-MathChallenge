package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.GameManager;
import model.Player;
import threads.TimerThread;

public class ControllerGUI {
	
	private GameManager gm;
	private TimerThread tmThread;

	public ControllerGUI() {

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
    private TableView<Player> tvPodium;
    
    @FXML
    private TableColumn<Player, String> tcName;

    @FXML
    private TableColumn<Player, String> tcScore;
    
    @FXML
    private TextField tfPlayerName;
    
    @FXML
    private TextField tfPlayerScore;
    
    @FXML
    private TextField tfPlayerRank;
    
    @FXML
    private TextField tfPlayerToFind;

    @FXML
    private TextField tfRankFound;

    @FXML
    private TextField tfScoreFound;
    
    @FXML
    private Rectangle sqrProgressBar;

    private ObservableList<Player> observableList;

	@FXML
	void start() throws IOException, InterruptedException, ClassNotFoundException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
		Image logo = new Image("Math Challenge Logo.png");
		ivMainMenuLogo2.setImage(logo);
		
		gm.loadData();
	}
	
	@FXML
	void btnPlay(ActionEvent event) throws IOException, InterruptedException {
		
		if(!tfNewPlayerName.getText().trim().isEmpty()) {
			
			Player p = new Player(tfNewPlayerName.getText(), 0);
			
			if(!gm.playerExists(p)) {
				
				FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Questions.fxml"));
				fxmlloader.setController(this);
				Parent menu = fxmlloader.load();
				mainPane.getChildren().setAll(menu);
				
				Image logo = new Image("Math Challenge Logo.png");
				ivMainMenuLogo.setImage(logo);
				
				gm.setPlayingNow(p);
				
				lbPlayingNow.setText("Player: " + gm.getPlayingNow().getName());
				lbCurrentPoints.setText("Score: " + gm.getPlayingNow().getScore());
				
				btnNewQuestion(event);

				tmThread = new TimerThread(this);
				tmThread.start();
				
			} else {
				
				String header = "Game error";
				String message = "Player already exists";
				
				showWarningDialogue(header, message);
				
				tfNewPlayerName.setText("");
			}	
			
		} else {
			
			String header = "Game error";
			String message = "A name must be given to start the game";
			
			showWarningDialogue(header, message);

			tfNewPlayerName.setText("");
		}
	}
	
	@FXML
	void btnNewQuestion(ActionEvent event) {
		
		int[] answers = gm.newProblem();
		
		String question = gm.getCurrentQuestion();
		
		tfProblem.setText(question);
		
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
	void btnConfirm(ActionEvent event) throws IOException {
		
		int answer = 0;
		boolean hasAnswer = false;
		
		if(rbAnswer1.isSelected()) {
    		
    		answer = Integer.parseInt(rbAnswer1.getText());
    		hasAnswer = true;
    		
    	} else if(rbAnswer2.isSelected()) {
    		
    		answer = Integer.parseInt(rbAnswer2.getText());
    		hasAnswer = true;
    		
    	} else if(rbAnswer3.isSelected()) {
    		
    		answer = Integer.parseInt(rbAnswer3.getText());
    		hasAnswer = true;
    		
    	} else if(rbAnswer4.isSelected()) {
    		
    		answer = Integer.parseInt(rbAnswer4.getText());
    		hasAnswer = true;
    		
    	} else {
    		
    		String header = "Game error";
    		String message = "An answers must be selected";
    		
    		showWarningDialogue(header, message);
    	}
		
		if(hasAnswer) {
			
			boolean correct = gm.verifyAnswer(answer);
			
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
			
			btnNewQuestion(event);
		}
	}
	
	public void changeTimer() throws IOException {
		
		int newTime = Integer.parseInt(lbTimer.getText()) - 1;
		
		lbTimer.setText(String.valueOf(newTime));
		
		if(lbTimer.getText().equals("0")) {
			
			gm.addPlayer(gm.getPlayingNow());
			btnScoreboard(new ActionEvent());
		}
	}
	
	
	public void changeProgressBar(int width) {
		
		sqrProgressBar.setWidth(sqrProgressBar.getWidth() - width);
	}
	
	@FXML
	void btnScoreboard(ActionEvent event) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Scoreboard.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
		initializePodiumTableView();
		
		if(gm.getPlayingNow() != null) {
			
			tfPlayerName.setText(gm.getPlayingNow().getName());
			tfPlayerScore.setText(String.valueOf(gm.getPlayingNow().getScore()));
			tfPlayerRank.setText(String.valueOf(gm.findPlayerPos(gm.getPlayingNow().getName())));
		}
	}
	
	private void initializePodiumTableView() throws FileNotFoundException, IOException {
		
		gm.saveData();
		
		List<Player> players = gm.orderedPlayerList();
		
		Player[] podium = new Player[5];
		
		if(players.size() <= 5) {
			
			for(int i = 0; i < players.size(); i++) {
				
				if(players.get(i) != null) {
					
					podium[i] = players.get(i);
				}
			}
			
		} else {
			
			podium[0] = players.get(0);
			podium[1] = players.get(1);
			podium[2] = players.get(2);
			podium[3] = players.get(3);
			podium[4] = players.get(4);
		}
		
		observableList = FXCollections.observableArrayList(podium);

		tvPodium.setItems(observableList);
		tcName.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));   
		tcScore.setCellValueFactory(new PropertyValueFactory<Player, String>("score"));
	}
	
	@FXML
	void btnFindPlayer(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("FindPlayer.fxml"));
		fxmlloader.setController(this);
		DialogPane dialoguePane = fxmlloader.load();
		
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(dialoguePane);
		dialog.showAndWait();
	}
	
	
	@FXML
	void btnFind(ActionEvent event) throws IOException {

		if(!tfPlayerToFind.getText().trim().isEmpty()) {
			
			Player p =  new Player(tfPlayerToFind.getText(), 0);
			
			if(gm.playerExists(p)) {
				
				tfScoreFound.setText(String.valueOf(gm.findPlayerScore(tfPlayerToFind.getText())));;
				tfRankFound.setText(String.valueOf(gm.findPlayerPos(tfPlayerToFind.getText())));
				
				String header = "Find Player Successful";
				String message = "Player information found";

				showSuccessDialogue(header, message);
				
			} else {
				
				String header = "Find Player Error";
				String message = "This player doesn't exist";
				
				showWarningDialogue(header, message);
				
				tfPlayerToFind.setText("");
				tfScoreFound.setText("");
				tfRankFound.setText("");
			}

		} else {
			
			String header = "Find Player Error";
			String message = "A name must be given to find information";
			
			showWarningDialogue(header, message);
			
			tfPlayerToFind.setText("");
			tfScoreFound.setText("");
			tfRankFound.setText("");
		}
	}
	
	@FXML
	void btnRemovePlayer(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("RemovePlayer.fxml"));
		fxmlloader.setController(this);
		DialogPane dialoguePane = fxmlloader.load();
		
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(dialoguePane);
		dialog.showAndWait();
	}
	
	@FXML
	void btnRemove(ActionEvent event) throws IOException {

		if(!tfPlayerToFind.getText().trim().isEmpty()) {

			Player p = gm.findPlayer(tfPlayerToFind.getText());
			
			if(gm.playerExists(p)) {
				
				if(gm.removePlayer(p)) {
					
					String header = "Remove successful";
					String message = "Player has beeen removed successfully";
					
					showSuccessDialogue(header, message);
					
				} else {
					
					String header = "Remove unsuccessful";
					String message = "Player couldn't be removed";
					
					showWarningDialogue(header, message);
				}
				
				tfPlayerToFind.setText("");;
				tfPlayerRank.setText("");
				tfPlayerScore.setText("");
				tfPlayerName.setText("");

				if(tfPlayerToFind.getText().equals(p.getName())) {
					
					gm.setPlayingNow(null);
				}
				
				initializePodiumTableView();
				
			} else {

				String header = "Find Player Error";
				String message = "This player doesn't exist";
				
				showWarningDialogue(header, message);
				
				tfPlayerToFind.setText("");
			}

		} else {
			
			String header = "Remove Player Error";
			String message = "A name must be given to remove a player";
			
			showWarningDialogue(header, message);
		}
	}
	
	@SuppressWarnings("deprecation")
	@FXML
	void btnBack(ActionEvent event) throws IOException {
		
		if(tmThread.isAlive()) {
			
			tmThread.stop();
		}
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
		Image logo = new Image("Math Challenge Logo.png");
		ivMainMenuLogo2.setImage(logo);
		
		gm.setPlayingNow(null);
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