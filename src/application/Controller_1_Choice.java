package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller_1_Choice {
	
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Label labelWelcome;
	
	
	
	public void switchLogInPage(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("Scene_Main.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	
	public void switchManageRecord(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("Scene_ManageRecord.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	
	public void switchManageJobs(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("Scene_ManageJobs.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	
	public void switchQuery(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("Scene_Query.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	
	public void displayName(String username) {
		labelWelcome.setText("Bentornato " + username);
	}
	
}
