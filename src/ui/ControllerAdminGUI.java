package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
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
	void btnPlay() {

		
	}
	
	@FXML
	void btnScoreboard() {

		
	}
}