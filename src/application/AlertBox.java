package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

	public void display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(400);
		window.setMinHeight(200);
		
		Label label = new Label();
		label.setText(message);
		
		Button closeButton = new Button("Ho capito");
		closeButton.setOnAction(e -> window.close());
		
		VBox alertLayout = new VBox();
		alertLayout.getChildren().addAll(label, closeButton);
		alertLayout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(alertLayout);
		window.setScene(scene);
		window.showAndWait();
	}
	
}
