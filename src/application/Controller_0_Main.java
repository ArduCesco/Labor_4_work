package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_0_Main {
	
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;

	
	private String exportedTitle = "Titolo Errore Generico!";
	private String exportedText  = "Testo Errore Generico!";
	
	
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	
	
	
	
	//==========================================================
	//=========AVVISO==POP=UP==================================
	//==========================================================
	private void callAlertBox(String importedTitle, String importedText) {
		
		//messaggi
		AlertBox test = new AlertBox();
		test.display(importedTitle, importedText);
	
	}
	

	
	
	
	public String[] transmitData(ActionEvent event) throws IOException {
		
		String username = usernameField.getText();
		String password = passwordField.getText(); 
		
		String[] test = new String[2];
		test[0] = username;
		test[1] = password;
		
		return test;
	
	}
	
	
	
	
	public void login(ActionEvent event) throws IOException {
		
		String user = usernameField.getText();
		String codiceAccesso = passwordField.getText();
		
		
		String jdbcURL = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connesso a server locale PSQL!");
				
			
			String sql =  "SELECT * "
						+ "FROM admins "
						+ "WHERE loginadmin = '" + user + "' "
						+ "AND passadmin = '" + codiceAccesso + "';";
			
			
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			
			if(result.next()) {
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_Manager.fxml"));
				root = loader.load();
				
				Controller_1_Choice Controller_1_Choice = loader.getController();
				Controller_1_Choice.displayName(user);

				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
				
			}else {
				
				System.out.println("Accesso fallito!");
				
				exportedTitle = "Errore Accesso";
				exportedText  =   "Combinazione nome utente / password\n"
								+ "non trovata, riprovare";
				exportedText += "\n\n";

				callAlertBox(exportedTitle, exportedText);
				
			}

			
			connection.close();
			System.out.println("DB chiuso");

		
		}catch(SQLException e){
			System.out.println("Errore durante la connessione al server");
			
			//da convertire in un pop-up
			System.out.println("riprovare più tardi");
			
			exportedTitle = "Errore Accesso";
			exportedText  =   "Accesso al Database fallito\n"
							+ "riprovare più tardi";
			exportedText += "\n\n";

			callAlertBox(exportedTitle, exportedText);
			
			e.printStackTrace();
			
		}
		
	//fine login()
	}
	
}
