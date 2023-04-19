package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_ManageRecord {
	
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	int rememberIndexAborted = 0;
	
	//in secondi
	long diciottoAnni = 567864000L;
	//long cinqueAnni1B =  31622400L;
	//long cinqueAnni2B =  31708800L;
	
	
	String exportedTitle = "Errore titolo non specificato";
	String exportedText  = "Errore testo non specificato";
	
	
	@FXML
	TextField idSearchTextField;

	
	@FXML
	TextField TF_wName; 
	@FXML
	TextField TF_wSurname;
	@FXML
	TextField TF_wBirthPlace;
	@FXML
	TextField TF_wNationality;
	@FXML
	TextField TF_wEmail;
	@FXML
	TextField TF_wAdress;
	@FXML
	TextField TF_wTel;
	@FXML
	TextField TF_wLanguages;
	@FXML
	TextField TF_wAvailability;
	@FXML
	TextField TF_eName;
	@FXML
	TextField TF_eSurname;
	@FXML
	TextField TF_eTel;
	@FXML
	TextField TF_eEmail;

	@FXML
	DatePicker DP_wBirth;
	@FXML
	DatePicker DP_wBeginAv;
	@FXML
	DatePicker DP_wEndAv;
	
	@FXML
	CheckBox CheckExp;
	@FXML
	CheckBox CheckVeh;
	
	@FXML
	public ChoiceBox<String> patentiBox;
	
	//Image myImage = new Image(getClass().getResourceAsStream("img2.jpg"));
	
	@FXML
	Label displayCurrentID;
	
	@FXML
	Label labelWelcome;
	
	@FXML
	Label displayWorkerName;
	
	@FXML
	Label displayWorkerSurname;
	
	@FXML
	Label displayWorkerPlaceBirth;
	
	@FXML
	Label displayWorkerBirth;
	
	@FXML
	Label displayWorkerNat;
	
	@FXML
	Label displayWorkerAdress;
	
	@FXML
	Label displayWorkerTel;
	
	@FXML
	Label displayWorkerEmail;
	
	@FXML
	Label displayWorkerExp;
	
	@FXML
	Label displayWorkerLanguages;
	
	@FXML
	Label displayWorkerPatent;
	
	@FXML
	Label displayWorkerVeh;
	
	@FXML
	Label displayWorkerStartAv;
	
	@FXML
	Label displayWorkerEndAv;
	
	@FXML
	Label displayWorkerAv;
	
	@FXML
	Label displayEmergencyName;
	
	@FXML
	Label displayEmergencySurname;
	
	@FXML
	Label displayEmergencyEmail;
	
	@FXML
	Label displayEmergencyTel;
	
	
	
	
	public void switchLManagerPage(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("Scene_Manager.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	ObservableList<String> listaPatenti = FXCollections.observableArrayList(null, "A","B","C","D");
	
	
	
	@FXML
	public void initialize() {
		patentiBox.setItems(listaPatenti);
	    patentiBox.setValue(null);
	}
	
	
	
	

	//===========CALL==ALERT==BOX===============================
	//==========================================================	
	private void callAlertBox(String importedTitle, String importedText) {
		
		//messaggi
		AlertBox test = new AlertBox();
		test.display(importedTitle, importedText);

	}
	
	
	
	//==========================================================	
	//==========DISPLAY==RECORD=================================
	//==========================================================	
	public void DisplayRecord(ActionEvent event) throws IOException {

		//Stringhe connessione DB
		String jdbcURL = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
		//Stringhe Errore locali
		String exportedTitle = "Errore titolo non specificato";
		String exportedText  = "Errore testo non specificato";
		
		
		//idSearchTextField.getText();
		//String str = idSearchTextField.getText();
		
		String str = null;
		
		
		if(displayCurrentID.getText() == null) 
		{
			displayCurrentID.setText("");
		}
		
		
		// I 4 casi
		
		if((idSearchTextField.getText().isEmpty()) && (displayCurrentID.getText().isEmpty()))
		{
			System.out.println("Inserisci un valore da caricare!");
			
			exportedTitle =   "Errore Visualizzazione";
			exportedText  =   "Il campo id necessario\n"
							+ "per la ricerca è vuoto";
			exportedText += "\n\n";

			callAlertBox(exportedTitle, exportedText);
			
			return;
		}
		
		else if( (!idSearchTextField.getText().isEmpty()) && (displayCurrentID.getText().isEmpty())  )
		{
			str = idSearchTextField.getText();
			/*
			System.out.println("campo inserimento pieno\n"
					+ (String)displayCurrentID.getText()+ "\n" 
					+ (String)idSearchTextField.getText()+ "\n\n");
			*/
		}
		
		else if( (!displayCurrentID.getText().isEmpty()) && (idSearchTextField.getText().isEmpty())  )
		{
			str = displayCurrentID.getText();
			/*
			System.out.println("campo display pieno\n"
					+ (String)displayCurrentID.getText()+ "\n" 
					+ (String)idSearchTextField.getText()+ "\n\n");
			*/
		}	
		
		else {
			
			if(displayCurrentID.getText().equals(idSearchTextField.getText()) )
			{
				/*
				System.out.println("I 2 campi sono entrambi pieni e uguali\n"
						+ (String)displayCurrentID.getText()+ "\n" 
						+ (String)idSearchTextField.getText()+ "\n\n");
				*/
				str = idSearchTextField.getText();
			}
		
			else 
			{
				/*
				System.out.println("I 2 campi sono entrambi pieni e diversi\n"
						+ (String)displayCurrentID.getText()+ "\n" 
						+ (String)idSearchTextField.getText()+ "\n\n");
				*/
				str = idSearchTextField.getText();
				
				
				
			}
		}
		
		
		//se sto visualizzando un lavoratore e provo a visualizzare avendo campo ricerca vuoto
		if(str == null) {
			System.out.println("str è null ma sto visualizzando qualcosa \n"
							+  "non è necessario fare nulla\n\n	");
			return;
		}

		
		if(!str.matches("^[0-9]*$")) 
		{	
			exportedTitle = "Errore inserimento valore di ricerca";
			exportedText = "\"" + str + "\"  usare solo cifre (0-9)";
			exportedText += "\n\n";
			
			callAlertBox(exportedTitle, exportedText);
			return;
		}
		
		
		try{
            rememberIndexAborted = Integer.parseInt(str);
            System.out.println(rememberIndexAborted); // output = 25
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		


		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connesso a server \"lavoratori\"!");
			

			String s = String.valueOf(rememberIndexAborted);
			
			String sql =  "SELECT * "
						+ "FROM lavoratori "
						//+ "WHERE id = " +  rememberIndexAborted + ";";
						+ "WHERE id = '" + s + "';";
			
						Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			if(result.next()) {
				System.out.println("Stampo le informazioni del lavoratore");
				

				displayCurrentID.setText (result.getString("id"));
				
				displayWorkerName.setText (result.getString("nome"));
				displayWorkerSurname.setText (result.getString("cognome"));
				displayWorkerPlaceBirth.setText (result.getString("luogo_nascita"));
				displayWorkerBirth.setText (result.getString("data_nascita"));
				
				Date date_birth = result.getDate("data_nascita");
				System.out.println("ecco la data ->  " + date_birth);
				//LocalDate test = (LocalDate) date_birth;
				//displayWorkerBirth.setValue(date_birth);
				
				
				displayWorkerNat.setText (result.getString("nazionalita"));
				
				displayWorkerAdress.setText (result.getString("indirizzo"));		
				displayWorkerTel.setText (result.getString("n_telefono"));				
				displayWorkerEmail.setText (result.getString("email"));
				//(result.getBoolean("experienze") == true) ? displayWorkerExp.setText("true"):
				

				
				Boolean test = result.getBoolean("esperienze");
				String testString = test ? "true" : "false" ;
				displayWorkerExp.setText(testString);
				
				displayWorkerLanguages.setText (result.getString("lingue"));
				displayWorkerPatent.setText (result.getString("patent"));
				
				Boolean test2 = result.getBoolean("automunito");
				String testString2 = test2 ? "true" : "false" ;
				displayWorkerVeh.setText (testString2);
				
				displayWorkerStartAv.setText (result.getString("inizio_disp"));
				displayWorkerEndAv.setText (result.getString("fine_disp"));
				displayWorkerAv.setText (result.getString("disponibilita"));
				
				displayEmergencyName.setText (result.getString("x_emergenze_nome"));
				displayEmergencySurname.setText (result.getString("x_emergenze_cognome"));
				displayEmergencyEmail.setText (result.getString("x_emergenze_email"));
				displayEmergencyTel.setText (result.getString("x_emergenze_cell"));	
				
			}else {
				System.out.println("Lavoratore non trovato!");
				
				displayCurrentID.setText(null);
				
				exportedTitle = "Errore Visualizzazione Lavoratore";
				exportedText  = "Lavoratore non trovato!";
				exportedText += "\n\n";

				callAlertBox(exportedTitle, exportedText);

			}
			
			connection.close();
			System.out.println("DB chiusOOOOOo");
		
		}catch(SQLException e){
			System.out.println("Errore durante la connessione al server");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	//==========================================================
	//=============INSERT=======================================
	//==========================================================
	public void insertNewRecord(ActionEvent event) throws IOException {
		
		//bigSerial NextIDfree = 1;
		
		String WorkerName		= TF_wName.getText();
		String WorkerSurname	= TF_wSurname.getText();
		String WorkerBirthPlace	= TF_wBirthPlace.getText();
		String WorkerNat		= TF_wNationality.getText();
		String WorkerEmail		= TF_wEmail.getText();
		String WorkerAdress		= TF_wAdress.getText();
		String WorkerTel		= TF_wTel.getText();
		String WorkerLanguages	= TF_wLanguages.getText();
		String WorkerAv			= TF_wAvailability.getText();
		String EmergencyName	= TF_eName.getText();
		String EmergencySurname	= TF_eSurname.getText();
		String Emergencytel		= TF_eTel.getText();
		String EmergencyEmail	= TF_eEmail.getText();

		//importato Date.sql
		LocalDate WorkerBirth	= DP_wBirth.getValue();
		LocalDate WorkerBeginAv	= DP_wBeginAv.getValue();
		LocalDate WorkerEndAv	= DP_wEndAv.getValue();

		Boolean WorkerPastWork;
		Boolean WorkerVehOwner;
		
		 if (CheckExp.isSelected ()) {
			 WorkerPastWork = true;
		 }else {
			 WorkerPastWork = false;
		 }
			
		 if (CheckVeh.isSelected ()) {
			 WorkerVehOwner = true;
		 }else {
			 WorkerVehOwner = false;
		 }	 
		
		
		//patentiBox
		 String WorkerPatent	= patentiBox.getValue();
		 if (WorkerPatent == null) {
			 WorkerPatent = "n";
		 }
		 
		 
		 
		
		String jdbcURL = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connesso a server locale PSQL!"
					+ "\n mi collego ora a lavoratori\n");
					

			//tento l'impresa dell'inserimento
			try(PreparedStatement pst = connection.prepareStatement
					( "INSERT INTO lavoratori "
							+ "( nome, cognome, luogo_nascita,"
							+ "data_nascita, nazionalita, indirizzo,"
							+ "n_telefono, email, esperienze,lingue,"
							+ "patent, automunito, inizio_disp, fine_disp, "
							+ "disponibilita, x_emergenze_nome,"
							+ "x_emergenze_cognome, x_emergenze_email,"
							+ "x_emergenze_cell) "
						
					+ "VALUES"
							+ "(" 
					
							//+			NextIDfree			
							+"  '" +	WorkerName			+"'"
							+", '" +	WorkerSurname		+"'"
							+", '" +	WorkerBirthPlace	+"'"
							+", '" +	WorkerBirth			+"'"
							+", '" +	WorkerNat			+"'"
							
							+", '" +	WorkerAdress		+"'"
							+", '" +	WorkerTel			+"'"
							+", '" +	WorkerEmail			+"'"
							+", '" +	WorkerPastWork		+"'"
							+", '" +	WorkerLanguages		+"'"
							+", '" +	WorkerPatent		+"'"	
							+", '" +	WorkerVehOwner		+"'"
							
							+", '" +	WorkerBeginAv		+"'"
							+", '" +	WorkerEndAv			+"'"
							+", '" +	WorkerAv			+"'"
							
							+", '" +	EmergencyName		+"'"
							+", '" +	EmergencySurname	+"'"
							+", '" +	EmergencyEmail		+"'"
							+", '" +	Emergencytel		+"'"
							+ ");" 
					)
				) 
			{
				
//GESTIONE ERRORI-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!
				String exportedTitle = "Errore titolo non specificato";
				String exportedText  = "Errore testo non specificato";
				
				//errori NOME
				if((WorkerName.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento NOME";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}

				else if((WorkerName.length() <= 2) || (WorkerName.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento NOME";
					exportedText = "Lunghezza \"" + WorkerName + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerName.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento NOME";
					exportedText = "\"" + WorkerName + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerName.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento NOME";
					exportedText = "\"" + WorkerName + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori COGNOME
				else if((WorkerSurname.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento COGNOME";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerSurname.length() <= 2) || (WorkerSurname.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento COGNOME";
					exportedText = "Lunghezza \"" + WorkerSurname + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerSurname.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento COGNOME";
					exportedText = "\"" + WorkerSurname + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerSurname.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento COGNOME";
					exportedText = "\"" + WorkerSurname + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				
				//errori LUOGO NASCITA
				else if((WorkerBirthPlace.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento LUOGO NASCITA";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerBirthPlace.length() <= 2) || (WorkerBirthPlace.length() >= 30)) 
				{	
					exportedTitle = "Errore inserimento LUOGO NASCITA";
					exportedText = "Lunghezza \"" + WorkerBirthPlace + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerBirthPlace.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento LUOGO NASCITA";
					exportedText = "\"" + WorkerBirthPlace + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerBirthPlace.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento LUOGO NASCITA";
					exportedText = "\"" + WorkerBirthPlace + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori NAZIONALITA'
				else if((WorkerNat.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento NAZIONALITA'";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerNat.length() <= 5) || (WorkerNat.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento NAZIONALITA'";
					exportedText = "Lunghezza \"" + WorkerNat + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerNat.matches("^[a-zA-Z]*$")) 
				{	
					exportedTitle = "Errore inserimento NAZIONALITA'";
					exportedText = "\"" + WorkerNat + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(WorkerNat.matches("^[A-Z][a-z]+")) 
				{	
					exportedTitle = "Errore inserimento NAZIONALITA'";
					exportedText = "\"" + WorkerNat + "\"  non inizia con lettera minuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori EMAIL LAVORATORE
				else if((WorkerEmail.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento EMAIL";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerEmail.length() <= 2) || (WorkerEmail.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento EMAIL";
					exportedText = "Lunghezza \"" + WorkerEmail + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerEmail.matches("[a-zA-Z0-9]+@{1}[a-zA-Z]+.{1}[a-zA-Z]+")) 
				{	
					exportedTitle = "Errore inserimento EMAIL";
					exportedText = "\"" + WorkerEmail + "\"  presenta errori ( @ . )";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				
				//errori INDIRIZZO LAVORATORE
				else if((WorkerAdress.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento INDIRIZZO";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerAdress.length() <= 2) || (WorkerAdress.length() >= 30)) 
				{	
					exportedTitle = "Errore inserimento INDIRIZZO";
					exportedText = "Lunghezza \"" + WorkerAdress + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerAdress.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento INDIRIZZO";
					exportedText = "\"" + WorkerAdress + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerAdress.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento INDIRIZZO";
					exportedText = "\"" + WorkerAdress + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				
				//errori TELEFONO LAVORATORE
				else if((WorkerTel.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento recapito telefonico";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerTel.length() <= 5) || (WorkerTel.length() >= 15)) 
				{	
					exportedTitle = "Errore inserimento recapito telefonico";
					exportedText = "Lunghezza \"" + WorkerTel + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerTel.matches("^[0-9]*$")) 
				{	
					exportedTitle = "Errore inserimento recapito telefonico";
					exportedText = "\"" + WorkerTel + "\"  usare solo cifre (0-9)";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori LINGUE
				else if((WorkerLanguages.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento LINGUE";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerLanguages.length() <= 2) || (WorkerLanguages.length() >= 200)) 
				{	
					exportedTitle = "Errore inserimento LINGUE";
					exportedText = "Lunghezza \"" + WorkerLanguages + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerLanguages.matches("^[a-z\\s-]*$")) 
				{	
					exportedTitle = "Errore inserimento LINGUE";
					exportedText = "\"" + WorkerLanguages + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerLanguages.matches("^[a-z]+(\\s-\\s[a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento LINGUE";
					exportedText = "\"" + WorkerLanguages + "\"  formato lingue non valido \n (separare con \"spazio trattino spazio\"\n e scrivere in minuscolo)";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}

				

				
				
				//errori ZONE DISPONIBILITA'
				else if((WorkerAv.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento Zona Disponibilità";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerAv.length() <= 2) || (WorkerAv.length() >= 200)) 
				{	
					exportedTitle = "Errore inserimento Zona Disponibilità";
					exportedText = "Lunghezza \"" + WorkerAv + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerAv.matches("^[a-zA-Z\\s-]*$")) 
				{	
					exportedTitle = "Errore inserimento Zona Disponibilità";
					exportedText = "\"" + WorkerAv + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerAv.matches("^[A-Z][a-z]*(\\s-\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento Zona Disponibilità";
					exportedText = "\"" + WorkerAv 	+ "\"  formato non valido!\n"
													+ "Assicurarsi che il formato sia:\n"
													+ "Zona1 \"spazio\" \"trattino\" \"spazio\" Zona2 ... ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				
				//errori NOME EMERGENZA
				else if((EmergencyName.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento NOME Emergenza";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((EmergencyName.length() <= 2) || (EmergencyName.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento NOME Emergenza";
					exportedText = "Lunghezza \"" + EmergencyName + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!EmergencyName.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento NOME Emergenza";
					exportedText = "\"" + EmergencyName + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!EmergencyName.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento NOME Emergenza";
					exportedText = "\"" + EmergencyName + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori COGNOME EMERGENZA
				else if((EmergencySurname.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento COGNOME Emergenza";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((EmergencySurname.length() <= 2) || (EmergencySurname.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento COGNOME Emergenza";
					exportedText = "Lunghezza \"" + EmergencySurname + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!EmergencySurname.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento COGNOME Emergenza";
					exportedText = "\"" + EmergencySurname + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!EmergencySurname.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento COGNOME Emergenza";
					exportedText = "\"" + EmergencySurname + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori TELEFONO EMERGENZA
				else if((Emergencytel.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento TELEFONO Emergenze";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((Emergencytel.length() <= 6) || (Emergencytel.length() >= 15)) 
				{	
					exportedTitle = "Errore inserimento TELEFONO Emergenze";
					exportedText = "Lunghezza \"" + Emergencytel + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!Emergencytel.matches("^[0-9]*$")) 
				{	
					exportedTitle = "Errore inserimento TELEFONO Emergenze";
					exportedText = "\"" + Emergencytel + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				

				
				
				//errori EMAIL EMERGENZA
				else if((EmergencyEmail.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento EMAIL Contatto Emergenze";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((EmergencyEmail.length() <= 5) || (EmergencyEmail.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento EMAIL Contatto Emergenze";
					exportedText = "Lunghezza \"" + EmergencyEmail + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!EmergencyEmail.matches("[a-zA-Z0-9]+@{1}[a-zA-Z]+.{1}[a-zA-Z]+")) 
				{	
					exportedTitle = "Errore inserimento EMAIL Contatto Emergenze";
					exportedText = "\"" + EmergencyEmail + "\"  presenta errori ( @ . )";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				

				
				
				/*errori DATE
				WorkerBirth;
				WorkerBeginAv;
				WorkerEndAv;
				*/
				
				
				//errori DATA di NASCITA
				else if(WorkerBirth == null) 
					{
						exportedTitle = "Errore valori DATA NASCITA";
						exportedText = "Data nascita non inserita";
						exportedText += "\n\n";
						
						callAlertBox(exportedTitle, exportedText);
					}
					
				else if(((System.currentTimeMillis()/1000 - ((long)WorkerBirth.getYear()-1970) * 31557600L) - ((long)WorkerBirth.getDayOfYear() * 86400L)) < diciottoAnni)
					{	
						exportedTitle = "Errore valori DATA NASCITA";
						exportedText = "L'utente non è maggiorenne!";
						exportedText += "\n\n";
						
						callAlertBox(exportedTitle, exportedText);
					}

				
				
				//errori DATA INIZIO DISPONIBILITA'
				else if(WorkerBeginAv == null) 
				{
					exportedTitle = "Errore valori DATA INIZIO DISPONIBILITA'";
					exportedText = "Data inizio disponibilità non inserita";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(((System.currentTimeMillis()/1000 - ((long)WorkerBeginAv.getYear()-1970) * 31557600L) - ((long)WorkerBeginAv.getDayOfYear() * 86400L)) > 0)
				{	
					exportedTitle = "Errore valori DATA INIZIO DISPONIBILITA'";
					exportedText = "La data si riferisce al passato!";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori DATA FINE DISPONIBILITA'
				else if(WorkerEndAv == null) 
				{
					exportedTitle = "Errore valori DATA FINE DISPONIBILITA'";
					exportedText = "Data fine disponibilità non inserita";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((System.currentTimeMillis()/1000 - ((long)WorkerEndAv.getYear()-1970) * 31557600L - ((long)WorkerEndAv.getDayOfYear() * 86400L)) > 0)
				{	
					exportedTitle = "Errore valori DATA FINE DISPONIBILITA'";
					exportedText = "La data si riferisce al passato!";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				//errori CHECK DISPONIBILITA'
				else if ( ( (long)WorkerBeginAv.getYear()*365.25 - (long)WorkerEndAv.getYear()*365.25   )  +  ( (long)WorkerBeginAv.getDayOfYear() - (long)WorkerEndAv.getDayOfYear()   ) > 0  )
				{
					exportedTitle = "Errore valori DATE DISPONIBILITA'";
					exportedText = "Data fine disponibilità non può \nvenire prima di inizio disponibilità";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				

				//se passo tutti i controlli arrivo qui
				else 
				{
					int n =	pst.executeUpdate();
					System.out.println( "Inserite " + n + " righe " );
					
					exportedTitle = "Feedback Creazione Record";
					exportedText = "Record lavoratore inserito correttamente nel DB";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				

				
			} catch (SQLException e) {
				System.out.println( "Errore durante inserimento dati : " + e.getMessage () );
				
				String exportedTitle = "Errore Creazione Record";
				String exportedText = "Errore durante inserimento dati : " + e.getMessage ();
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				
				return;
			}
			
			
			
			connection.close();
			System.out.println("DB lavoratori chiuso");
		
		}catch(SQLException e){
			System.out.println("Errore durante la connessione al server lavoratori");
			
			exportedTitle = "Errore Connessione Database";
			exportedText  = "Connessione fallita, rivedere i dati";
			exportedText += "\n\n";
			
			callAlertBox(exportedTitle, exportedText);
			return;
			//e.printStackTrace();
		}
	}
	

	
	
	
	


	
	//==========================================================
	//===========UPDATE==RECORD=================================
	//==========================================================
	public void UpdateRecord(ActionEvent event) throws IOException {
		
		String exportedTitle = "Errore titolo non specificato";
		String exportedText  = "Errore testo non specificato";
		
		
		if(	(displayCurrentID.getText() == null) 	|| 
			(displayCurrentID.getText() == "")	 )
		{
			System.out.println("Inserisci prima un valore da ricercare");
			
			exportedTitle = "Errore Aggiornamento";
			exportedText  = "Non è visualizzato alcun profilo attualmente!";
			exportedText += "\n\n";
			
			callAlertBox(exportedTitle, exportedText);
			return;
		}
		 
		
		if(displayCurrentID.getText() == null) 
		{
			displayCurrentID.setText("");
		}
		
		if(!displayCurrentID.getText().equals(idSearchTextField.getText()) ) 
		{
			System.out.println("I 2 campi non coincidono");
			
			exportedTitle = "Errore Aggiornamento";
			exportedText  = "Misura di protezione aggiornamento richiesta:\n"
						+ 	"I valori dei campi ricercaID e ID visualizzato\n"
						+ 	"devono coincidere";
			exportedText += "\n\n";
			
			callAlertBox(exportedTitle, exportedText);
			return;
		}
		
		
		String WorkerName		= TF_wName.getText();
		
		//System.out.println("test del nome:::: " + WorkerName);
		
		String WorkerSurname	= TF_wSurname.getText();
		String WorkerBirthPlace	= TF_wBirthPlace.getText();
		String WorkerNat		= TF_wNationality.getText();
		String WorkerEmail		= TF_wEmail.getText();
		String WorkerAdress		= TF_wAdress.getText();
		String WorkerTel		= TF_wTel.getText();
		String WorkerLanguages	= TF_wLanguages.getText();
		String WorkerAv			= TF_wAvailability.getText();
		String EmergencyName	= TF_eName.getText();
		String EmergencySurname	= TF_eSurname.getText();
		String Emergencytel		= TF_eTel.getText();
		String EmergencyEmail	= TF_eEmail.getText();

		//importato Date.sql
		LocalDate WorkerBirth	= DP_wBirth.getValue();
		LocalDate WorkerBeginAv	= DP_wBeginAv.getValue();
		LocalDate WorkerEndAv	= DP_wEndAv.getValue();

		Boolean WorkerPastWork;
		Boolean WorkerVehOwner;
		
		 if (CheckExp.isSelected ()) {
			 WorkerPastWork = true;
		 }else {
			 WorkerPastWork = false;
		 }
			
		 if (CheckVeh.isSelected ()) {
			 WorkerVehOwner = true;
		 }else {
			 WorkerVehOwner = false;
		 }	 
		
		
		//patentiBox
		 String WorkerPatent	= patentiBox.getValue();
		 if (WorkerPatent == null) 
		 {
			 WorkerPatent = "n";
		 }
		 
		 
		 	//informazioni temporanee
			String W2Name		= displayWorkerName.getText();
			String W2Surname	= displayWorkerSurname.getText();
			String W2BirthPlace	= displayWorkerPlaceBirth.getText();
			String W2Nat		= displayWorkerNat.getText();
			String W2Email		= displayWorkerEmail.getText();
			String W2Adress		= displayWorkerAdress.getText();
			String W2Tel		= displayWorkerTel.getText();
			String W2Languages	= displayWorkerLanguages.getText();
			String W2Av			= displayWorkerAv.getText();
			String E2Name		= displayEmergencyName.getText();
			String E2Surname	= displayEmergencySurname.getText();
			String E2tel		= displayEmergencyTel.getText();
			String E2Email		= displayEmergencyEmail.getText();

			//importato Date.sql
			
			String date = displayWorkerBirth.getText();

		        //default, ISO_LOCAL_DATE
			LocalDate W2Birth = LocalDate.parse(date);
			date = displayWorkerStartAv.getText();
			LocalDate W2BeginAv = LocalDate.parse(date);
			date = displayWorkerEndAv.getText();
			LocalDate W2EndAv = LocalDate.parse(date);

			//Boolean WorkerPastWork = displayWorkerExp.getBool();
			//Boolean WorkerVehOwner = displayWorkerVeh;
			
			Boolean W2displayWorkerExp = (displayWorkerExp.getText() =="true")?true:false;
			
			Boolean W2displayWorkerVeh = (displayWorkerVeh.getText() =="true")?true:false;

			String W2Patent	= displayWorkerPatent.getText();
			
			

			
		 

		//Dati accesso DB 
		String jdbcURL = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
			
		
		//Le informazioni temporanee vengono sostituite da eventuali nuovi valori se presenti
		
		WorkerName 			= ((WorkerName.length() != 0)		? WorkerName		: W2Name);
		WorkerSurname 		= ((WorkerSurname.length() != 0)	? WorkerSurname		: W2Surname);
		WorkerBirthPlace 	= ((WorkerBirthPlace.length() != 0)	? WorkerBirthPlace	: W2BirthPlace);
		WorkerBirth 		= ((WorkerBirth!=null)				? WorkerBirth		: W2Birth);
		WorkerNat 			= ((WorkerNat.length() != 0)		? WorkerNat			: W2Nat);
		
		WorkerAdress		= ((WorkerAdress.length() != 0)		? WorkerAdress		: W2Adress);
		WorkerTel 			= ((WorkerTel.length() != 0)		? WorkerTel			: W2Tel);
		WorkerEmail			= ((WorkerEmail.length() != 0)		? WorkerEmail		: W2Email);
		WorkerPastWork 		= ((WorkerPastWork!=null)			? WorkerPastWork	: W2displayWorkerExp);
		WorkerLanguages 	= ((WorkerLanguages.length() != 0)	? WorkerLanguages	: W2Languages);
		WorkerPatent 		= ((WorkerPatent!=null)				? WorkerPatent		: W2Patent);
		WorkerVehOwner 		= ((WorkerVehOwner!=null)			? WorkerVehOwner	: W2displayWorkerVeh);
		
		WorkerBeginAv 		= ((WorkerBeginAv!=null)			? WorkerBeginAv		: W2BeginAv);
		WorkerEndAv 		= ((WorkerEndAv!=null)				? WorkerEndAv		: W2EndAv);
		WorkerAv 			= ((WorkerAv.length() != 0)			? WorkerAv			: W2Av);
		
		EmergencyName 		= ((EmergencyName.length() != 0)	? EmergencyName		: E2Name);
		EmergencySurname 	= ((EmergencySurname.length() != 0)	? EmergencySurname	: E2Surname);
		EmergencyEmail 		= ((EmergencyEmail.length() != 0)	? EmergencyEmail	: E2Email);
		Emergencytel 		= ((Emergencytel.length() != 0)		? Emergencytel		: E2tel);
		
		
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			
			System.out.println("Connesso a server locale PSQL!"
					+ "\n mi collego ora a lavoratori\n");

			String sas = String.valueOf(rememberIndexAborted);
			
			
			try(PreparedStatement pst = connection.prepareStatement
					( "UPDATE lavoratori "
					+ "SET "
						+" nome = '" 			+	WorkerName			+"',"		
						+" cognome = '" 		+	WorkerSurname		+"',"
						+" luogo_nascita = '" 	+	WorkerBirthPlace	+"',"
						+" data_nascita = '" 	+	WorkerBirth			+"',"
						+" nazionalita = '" 	+	WorkerNat			+"',"
						
						+" indirizzo = '" 		+	WorkerAdress		+"',"
						+" n_telefono = '" 		+	WorkerTel			+"',"
						+" email = '" 			+	WorkerEmail			+"',"
						+" esperienze = '" 		+	WorkerPastWork		+"',"
						+" lingue = '" 			+	WorkerLanguages		+"',"
						+" patent = '" 			+	WorkerPatent		+"',"	
						+" automunito = '" 		+	WorkerVehOwner		+"',"
						
						+" inizio_disp = '" 	+	WorkerBeginAv		+"',"
						+" fine_disp = '" 		+	WorkerEndAv			+"',"
						+" disponibilita = '" 	+	WorkerAv			+"',"
							
						+" x_emergenze_nome = '" 	+	EmergencyName		+"',"
						+" x_emergenze_cognome = '" +	EmergencySurname	+"',"
						+" x_emergenze_email = '" 	+	EmergencyEmail		+"',"
						+" x_emergenze_cell = '" 	+	Emergencytel		 +"'"
					
						+" WHERE id = '" +	sas +  "';"
					);
				) 
			{
				
				
				
		//GESTIONE ERRORI-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!-!
				
				//errori NOME
				if((WorkerName.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento NOME";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}

				else if((WorkerName.length() <= 2) || (WorkerName.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento NOME";
					exportedText = "Lunghezza \"" + WorkerName + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerName.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento NOME";
					exportedText = "\"" + WorkerName + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerName.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento NOME";
					exportedText = "\"" + WorkerName + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori COGNOME
				else if((WorkerSurname.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento COGNOME";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					exportedText += "\n\n";
					exportedText += "\n\n";
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerSurname.length() <= 2) || (WorkerSurname.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento COGNOME";
					exportedText = "Lunghezza \"" + WorkerSurname + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerSurname.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento COGNOME";
					exportedText = "\"" + WorkerSurname + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerSurname.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento COGNOME";
					exportedText = "\"" + WorkerSurname + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				
				//errori LUOGO NASCITA
				else if((WorkerBirthPlace.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento LUOGO NASCITA";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerBirthPlace.length() <= 2) || (WorkerBirthPlace.length() >= 30)) 
				{	
					exportedTitle = "Errore inserimento LUOGO NASCITA";
					exportedText = "Lunghezza \"" + WorkerBirthPlace + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerBirthPlace.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento LUOGO NASCITA";
					exportedText = "\"" + WorkerBirthPlace + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerBirthPlace.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento LUOGO NASCITA";
					exportedText = "\"" + WorkerBirthPlace + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori NAZIONALITA'
				else if((WorkerNat.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento NAZIONALITA'";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerNat.length() <= 4) || (WorkerNat.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento NAZIONALITA'";
					exportedText = "Lunghezza \"" + WorkerNat + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerNat.matches("^[a-zA-Z]*$")) 
				{	
					exportedTitle = "Errore inserimento NAZIONALITA'";
					exportedText = "\"" + WorkerNat + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(WorkerNat.matches("^[A-Z][a-z]+")) 
				{	
					exportedTitle = "Errore inserimento NAZIONALITA'";
					exportedText = "\"" + WorkerNat + "\"  non inizia con lettera minuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori EMAIL LAVORATORE
				else if((WorkerEmail.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento EMAIL";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerEmail.length() <= 5) || (WorkerEmail.length() >= 30)) 
				{	
					exportedTitle = "Errore inserimento EMAIL";
					exportedText = "Lunghezza \"" + WorkerEmail + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerEmail.matches("[a-zA-Z0-9]+@{1}[a-zA-Z]+.{1}[a-zA-Z]+")) 
				{	
					exportedTitle = "Errore inserimento EMAIL";
					exportedText = "\"" + WorkerEmail + "\"  presenta errori ( @ . )";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				
				//errori INDIRIZZO LAVORATORE
				else if((WorkerAdress.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento INDIRIZZO";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerAdress.length() <= 2) || (WorkerAdress.length() >= 30)) 
				{	
					exportedTitle = "Errore inserimento INDIRIZZO";
					exportedText = "Lunghezza \"" + WorkerAdress + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerAdress.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento INDIRIZZO";
					exportedText = "\"" + WorkerAdress + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerAdress.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento INDIRIZZO";
					exportedText = "\"" + WorkerAdress + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				
				//errori TELEFONO LAVORATORE
				else if((WorkerTel.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento recapito telefonico";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerTel.length() <= 5) || (WorkerTel.length() >= 15)) 
				{	
					exportedTitle = "Errore inserimento recapito telefonico";
					exportedText = "Lunghezza \"" + WorkerTel + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerTel.matches("^[0-9]*$")) 
				{	
					exportedTitle = "Errore inserimento recapito telefonico";
					exportedText = "\"" + WorkerTel + "\"  usare solo cifre (0-9)";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori LINGUE
				else if((WorkerLanguages.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento LINGUE";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerLanguages.length() <= 2) || (WorkerLanguages.length() >= 200)) 
				{	
					exportedTitle = "Errore inserimento LINGUE";
					exportedText = "Lunghezza \"" + WorkerLanguages + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerLanguages.matches("^[a-z\\s-]*$")) 
				{	
					exportedTitle = "Errore inserimento LINGUE";
					exportedText = "\"" + WorkerLanguages + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerLanguages.matches("^[a-z]+(\\s-\\s[a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento LINGUE";
					exportedText = "\"" + WorkerLanguages + "\"  formato lingue non valido \n (separare con \"spazio trattino spazio\"\n e scrivere in minuscolo)";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}

				

				
				
				//errori ZONE DISPONIBILITA'
				else if((WorkerAv.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento Zona Disponibilità";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((WorkerAv.length() <= 2) || (WorkerAv.length() >= 200)) 
				{	
					exportedTitle = "Errore inserimento Zona Disponibilità";
					exportedText = "Lunghezza \"" + WorkerAv + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerAv.matches("^[a-zA-Z\\s-]*$")) 
				{	
					exportedTitle = "Errore inserimento Zona Disponibilità";
					exportedText = "\"" + WorkerAv + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!WorkerAv.matches("^[A-Z][a-z]*(\\s-\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento Zona Disponibilità";
					exportedText = "\"" + WorkerAv 	+ "\"  formato non valido!\n"
													+ "Assicurarsi che il formato sia:\n"
													+ "Zona1 \"spazio\" \"trattino\" \"spazio\" Zona2 ... ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				
				//errori NOME EMERGENZA
				else if((EmergencyName.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento NOME Emergenza";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((EmergencyName.length() <= 2) || (EmergencyName.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento NOME Emergenza";
					exportedText = "Lunghezza \"" + EmergencyName + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!EmergencyName.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento NOME Emergenza";
					exportedText = "\"" + EmergencyName + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!EmergencyName.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento NOME Emergenza";
					exportedText = "\"" + EmergencyName + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori COGNOME EMERGENZA
				else if((EmergencySurname.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento COGNOME Emergenza";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((EmergencySurname.length() <= 2) || (EmergencySurname.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento COGNOME Emergenza";
					exportedText = "Lunghezza \"" + EmergencySurname + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!EmergencySurname.matches("^[a-zA-Z\\s]*$")) 
				{	
					exportedTitle = "Errore inserimento COGNOME Emergenza";
					exportedText = "\"" + EmergencySurname + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!EmergencySurname.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
				{	
					exportedTitle = "Errore inserimento COGNOME Emergenza";
					exportedText = "\"" + EmergencySurname + "\"  non inizia con lettera maiuscola";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori TELEFONO EMERGENZA
				else if((Emergencytel.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento TELEFONO Emergenze";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((Emergencytel.length() <= 6) || (Emergencytel.length() >= 15)) 
				{	
					exportedTitle = "Errore inserimento TELEFONO Emergenze";
					exportedText = "Lunghezza \"" + Emergencytel + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!Emergencytel.matches("^[0-9]*$")) 
				{	
					exportedTitle = "Errore inserimento TELEFONO Emergenze";
					exportedText = "\"" + Emergencytel + "\"  presenta caratteri non validi";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				

				
				
				//errori EMAIL EMERGENZA
				else if((EmergencyEmail.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento EMAIL Contatto Emergenze";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((EmergencyEmail.length() <= 5) || (EmergencyEmail.length() >= 20)) 
				{	
					exportedTitle = "Errore inserimento EMAIL Contatto Emergenze";
					exportedText = "Lunghezza \"" + EmergencyEmail + "\" fuori dai limiti ";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(!EmergencyEmail.matches("[a-zA-Z0-9]+@{1}[a-zA-Z]+.{1}[a-zA-Z]+")) 
				{	
					exportedTitle = "Errore inserimento EMAIL Contatto Emergenze";
					exportedText = "\"" + EmergencyEmail + "\"  presenta errori ( @ . )";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				

				

				
			
				//errori DATA di NASCITA
				else if(WorkerBirth == null) 
					{
						exportedTitle = "Errore valori DATA NASCITA";
						exportedText = "Data nascita non inserita";
						exportedText += "\n\n";
						
						callAlertBox(exportedTitle, exportedText);
					}
					
				else if(((System.currentTimeMillis()/1000 - ((long)WorkerBirth.getYear()-1970) * 31557600L) - ((long)WorkerBirth.getDayOfYear() * 86400L)) < diciottoAnni)
					{	
						exportedTitle = "Errore valori DATA NASCITA";
						exportedText = "L'utente non è maggiorenne!";
						exportedText += "\n\n";
						
						callAlertBox(exportedTitle, exportedText);
					}

				
				
				//errori DATA INIZIO DISPONIBILITA'
				else if(WorkerBeginAv == null) 
				{
					exportedTitle = "Errore valori DATA INIZIO DISPONIBILITA'";
					exportedText = "Data inizio disponibilità non inserita";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if(((System.currentTimeMillis()/1000 - ((long)WorkerBeginAv.getYear()-1970) * 31557600L) - ((long)WorkerBeginAv.getDayOfYear() * 86400L)) > 0)
				{	
					exportedTitle = "Errore valori DATA INIZIO DISPONIBILITA'";
					exportedText = "La data si riferisce al passato!";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				
				//errori DATA FINE DISPONIBILITA'
				else if(WorkerEndAv == null) 
				{
					exportedTitle = "Errore valori DATA FINE DISPONIBILITA'";
					exportedText = "Data fine disponibilità non inserita";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				else if((System.currentTimeMillis()/1000 - ((long)WorkerEndAv.getYear()-1970) * 31557600L - ((long)WorkerEndAv.getDayOfYear() * 86400L)) > 0)
				{	
					exportedTitle = "Errore valori DATA FINE DISPONIBILITA'";
					exportedText = "La data si riferisce al passato!";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				

				
				//errori CHECK DISPONIBILITA'
				else if ( ( (long)WorkerBeginAv.getYear()*365.25 - (long)WorkerEndAv.getYear()*365.25   )  +  ( (long)WorkerBeginAv.getDayOfYear() - (long)WorkerEndAv.getDayOfYear()   ) > 0  )
				{
					exportedTitle = "Errore valori DATE DISPONIBILITA'";
					exportedText = "Data fine disponibilità non può \nvenire prima di inizio disponibilità";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
				}
				
				

			//se passo tutti i controlli arrivo qui
				else 
				{
					
					int n =	pst.executeUpdate();
					System.out.println( "Aggiornate " + n + " righe " );
					
					exportedTitle = "Notifica Feedback";
					exportedText  = "Aggiornamento compiuto con successo!";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
					
				}
				
				
	
			} catch (SQLException e) {
				System.out.println( "Errore durante aggiornamento dati : " + e.getMessage () );	
				
				exportedTitle = "Notifica Feedback";
				exportedText  = "Errore durante aggiornamento dati : \" " + e.getMessage () + "\" ";
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				
				return ;
			}


			connection.close();
			System.out.println("DB lavoratori chiuso");
		
		}catch(SQLException e){
			System.out.println("Errore durante la connessione al server"
					+ "\n cancellazione record fallita\n\n");
			
			exportedTitle = "Notifica Feedback";
			exportedText  = "cancellazione record fallita!";
			exportedText += "\n\n";
			
			callAlertBox(exportedTitle, exportedText);
			
			e.printStackTrace();
		}	
		
		
		//provo ad aggiornare i risultati
		DisplayRecord(event);
		
	}
	
	

	
	
	
	//==========================================================
	//==========DELETE==========================================
	//==========================================================
	
	public void DeleteRecord(ActionEvent event) throws IOException {
		
		
		//messaggi errore
		String exportedTitle = "Errore titolo non specificato";
		String exportedText  = "Errore testo non specificato";
		
		
		//Dati accesso DB 
		String jdbcURL = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
		
		
		//test1
		if(	(displayCurrentID.getText() == null) 	|| 
				(displayCurrentID.getText() == "")	 )
			{
				displayCurrentID.setText("");
			
				System.out.println("Inserisci prima un valore da ricercare");
				
				exportedTitle = "Errore Cancellazione";
				exportedText  = "Non è visualizzato alcun profilo attualmente!";
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				return;
			}
			 
			
			
			if(!displayCurrentID.getText().equals(idSearchTextField.getText()) ) 
			{
				System.out.println("I 2 campi non coincidono");
				
				exportedTitle = "Errore Cancellazione record";
				exportedText  = "Misura di protezione cancellazione richiesta:\n"
							+ 	"I valori dei campi ricercaID e ID visualizzato\n"
							+ 	"devono coincidere";
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				return;
			}
		//test2
		
		

		
		else 
		{
			
			//DisplayRecord(event);
			String sas2 = idSearchTextField.getText();
			
			
			
			if(sas2 != null) {
				try {
					Connection connection = DriverManager.getConnection(jdbcURL, username, password);
					System.out.println("Connesso a server locale PSQL!"
							+ "\n mi collego ora a lavoratori\n");

					//String sas = String.valueOf(rememberIndexAborted);
					String sas = idSearchTextField.getText();
					
					try(PreparedStatement pst = connection.prepareStatement
							( "DELETE FROM lavoratori "
							+ "WHERE id='" + sas + "';" 
							)
						) 
					{
						int n =	pst.executeUpdate();
						System.out.println( "Eliminate " + n + " righe " );
						
						exportedTitle = "Feedback Cancellazione";
						exportedText  =   "Cancellazione del lavoratore\n"
										+ "con id" + sas + "eseguita con successo";
						exportedText += "\n\n";

						callAlertBox(exportedTitle, exportedText);
						
						
						
					} catch (SQLException e) {
						System.out.println( "Errore durante cancellazione dati : " + e.getMessage () );	
						
						exportedTitle = "Errore Cancellazione";
						exportedText  =   "Questo utente non può essere cancellato\n"
										+ "perché ha almeno un record nella tabella lavori";
						exportedText += "\n\n";

						callAlertBox(exportedTitle, exportedText);
						
						return ;
					}

					
					connection.close();
					System.out.println("DB lavoratori chiuso");
				
				}catch(SQLException e){
					System.out.println("Errore durante la connessione al server"
							+ "\n cancellazione record fallita\n\n");
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
