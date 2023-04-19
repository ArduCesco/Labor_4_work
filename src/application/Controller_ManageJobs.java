package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller_ManageJobs {
	
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	
	//espressi in secondi
	long cinqueAnni1B =  157766400L;
	//long cinqueAnni2B =  157852800L;
	
	
	//Campi INSERIMENTO
	@FXML
	TextField TF_jIDworker; 
	@FXML
	TextField TF_jLavoro; 
	@FXML
	TextField TF_jAzienda;
	@FXML
	TextField TF_jSede;
	@FXML
	TextField TF_jRLG;
	@FXML
	TextField TF_jIDworker1;
	
	@FXML
	TextArea  TA_jMansioni;

	@FXML
	DatePicker DP_jStartP;
	@FXML
	DatePicker DP_jEndP;

	

	//campi visualizzazione
	@FXML
	Label displayJobID;
	@FXML
	Label displayJobName;
	@FXML
	Label displayJobCompany;
	@FXML
	Label displayJobSite;
	@FXML
	Label displayJobRLG;
	
	@FXML
	TextArea  TA_jDisplayTasks;

	@FXML
	Label DP_jDisplayStartP;
	@FXML
	Label DP_jDisplayEndP;
	
	@FXML
	TextArea TAresultArea;

	String exportedTitle = "Titolo Errore Generico!";
	String exportedText  = "Testo Errore Generico!";
	
	
	//==========================================================
	//==========================================================
	//==========================================================
	private void callAlertBox(String importedTitle, String importedText) {
		
		//messaggi
		AlertBox test = new AlertBox();
		test.display(importedTitle, importedText);
		
	}
	
	
	
	
	
	//==========================================================
	//==========SWITCH==TO==MANAGER==PAGE=======================
	//==========================================================
	public void switchLManagerPage(ActionEvent event) throws IOException {

		root  = FXMLLoader.load(getClass().getResource("Scene_Manager.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
	
	
	//==========================================================
	//==========INSERT==NEW==JOB================================
	//==========================================================
	public void insertNewJob(ActionEvent event) throws IOException {
		
		String jdbcURL  = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
		
		
		if	(
				(TF_jIDworker.getText().length() == 0)	||
				(TF_jLavoro.getText().length() == 0)	||
				(DP_jStartP.getValue() == null  )
			)		
			{
				System.out.println("Inserisci i tre criteri di ricerca!");
				
				exportedTitle = "Errore Aggiornamento Lavoro";
				exportedText  =   "Almeno uno dei parametri \n"
								+ "   di ricerca è vuoto";
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				
				return;
			}
		
		
		
		String JobWorkerID = TF_jIDworker.getText();
		String JobName = TF_jLavoro.getText();
		String JobNameCompany = TF_jAzienda.getText();
		String JobSite = TF_jSede.getText();	
		String rlgString = TF_jRLG.getText();
		
		ObservableList<CharSequence>  JobTasks = TA_jMansioni.getParagraphs();

		LocalDate JobBeginAv	= DP_jStartP.getValue();
		LocalDate JobEndAv		= DP_jEndP.getValue();



		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connesso a server locale PSQL!"
					+ "\n mi collego ora a JOBS\n");

			
			//tento l'impresa dell'inserimento
			try(PreparedStatement pst = connection.prepareStatement
					( "INSERT INTO lavori "
							+ "( id_lavoratore,"
							+ "	 nome_lavoro,"
							+ "  inizio_periodo,"
							+ "  fine_periodo,"
							+ "  nome_azienda,"
							+ "  mansioni_svolte,"
							+ "  sede_lavoro,"
							+ "  rlg"
							+ ")"
					+ " VALUES "
							+ "(" 		
							+ "  '" +	JobWorkerID			+"'"
							+ ", '" +	JobName				+"'"
							+ ", '" +	JobBeginAv			+"'"
							+ ", '" +	JobEndAv			+"'"
							+ ", '" +	JobNameCompany		+"'"
							+ ", '" +	JobTasks			+"'"
							+ ", '" +	JobSite				+"'"
							+ ", '" +	rlgString	
							+ "');" 
					)
				) 
			{
				
	//GESTIONE ERRORI INSERIMENTO-!-!-!-!-!-!-!-!-!-!-!-!-!-!-
				LocalDate dataInizio = JobBeginAv;
				String LDtoS2 = dataInizio.format(DateTimeFormatter.ofPattern("dd/LL/yyyy"));
				
				
				
					//Errori JobWorkerID
				
					//devo scoprire se un certo IDworker esiste
						boolean idExist = false; 
				
						if(!JobWorkerID.matches("^[0-9]*$"))
						{
							exportedTitle = "Errore inserimento ID lavoratore";
							exportedText = "Sono presenti caratteri anomali!";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobName.matches("^[a-zA-Z\\s]*$") )
						{
							exportedTitle = "Errore inserimento Lavoro";
							exportedText = "\"" + JobName + "\"  presenta caratteri non validi";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobName.matches("^[A-Z][a-z]*(\\s[a-z]+)*") )
						{
							exportedTitle = "Errore inserimento Lavoro";
							exportedText = "\"" + JobBeginAv + "\"  non inizia con lettera maiuscola";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						
						
					//Errori DATA Inizio lavori
						else if(!LDtoS2.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") )
						{
							exportedTitle = "Errore inserimento Data Inizio Lavoro";
							exportedText = "\"" + JobName + "\"  \n"
											+ "seguire il formato:\n"
											+ "####/##/##";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(JobEndAv == null) 
						{
							exportedTitle = "Errore valori Data Fine Lavoro'";
							exportedText = "Data fine lavoro non inserita";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(((System.currentTimeMillis()/1000 - ((long)JobBeginAv.getYear()-1970) * 31557600L) - ((long)JobBeginAv.getDayOfYear() * 86400L)) < 0)
						{	
							exportedTitle = "Errore valori Data Inizio Lavoro";
							exportedText = "La data si riferisce al futuro!";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(((System.currentTimeMillis()/1000 - ((long)JobEndAv.getYear()-1970) * 31557600L) - ((long)JobEndAv.getDayOfYear() * 86400L)) < 0)
						{	
							exportedTitle = "Errore valori Data Fine Lavoro";
							exportedText = "La data si riferisce al futuro!";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(((System.currentTimeMillis()/1000 - ((long)JobEndAv.getYear()-1970) * 31557600L) - ((long)JobEndAv.getDayOfYear() * 86400L)) > cinqueAnni1B)
						{	
							exportedTitle = "Errore valori Data Fine Lavoro";
							exportedText =    "Non è possibile inserire valori\n"
											+ "che superano i cinque anni!";
							exportedText += "\n\n";

							callAlertBox(exportedTitle, exportedText);
						}
						
					//Errori CHECK date 
						else if ( ( (long)JobBeginAv.getYear()*365.25 - (long)JobEndAv.getYear()*365.25   )  +  ( (long)JobBeginAv.getDayOfYear() - (long)JobEndAv.getDayOfYear()   ) > 0  )
						{
							exportedTitle = "Errore valori DATE LAVORO'";
							exportedText = "Data fine lavoro non può \nvenire prima di inizio!";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						
					//errori JobNameCompany
						else if((JobNameCompany.isEmpty()) )
						{	
							exportedTitle = "Errore inserimento Nome Azienda";
							exportedText = "Il campo è vuoto";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if((JobNameCompany.length() <= 2) || (JobNameCompany.length() >= 30)) 
						{	
							exportedTitle = "Errore inserimento Nome Azienda";
							exportedText = "Lunghezza \"" + JobNameCompany + "\" fuori dai limiti ";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobNameCompany.matches("^[a-zA-Z\\s-]*$")) 
						{	
							exportedTitle = "Errore inserimento Nome Azienda";
							exportedText = "\"" + JobNameCompany + "\"  presenta caratteri non validi";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobNameCompany.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
						{	
							exportedTitle = "Errore inserimento Nome Azienda";
							exportedText = "\"" + JobNameCompany + "\"  non inizia con lettera Maiuscola";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
	
					//errori JobTasks
						else if((TA_jMansioni.getText().isEmpty()) )
						{	
							exportedTitle = "Errore inserimento Mansioni";
							exportedText = "Il campo è vuoto";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if((TA_jMansioni.getText().length() <= 2) || (TA_jMansioni.getText().length() >= 300)) 
						{	
							exportedTitle = "Errore inserimento Mansioni";
							exportedText = "Lunghezza \"" + JobTasks + "\" fuori dai limiti ";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!TA_jMansioni.getText().matches("^[a-zA-Z\\s-]*$")) 
						{	
							exportedTitle = "Errore inserimento Mansioni";
							exportedText = "\"" + JobTasks + "\"  presenta caratteri non validi";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!TA_jMansioni.getText().matches("^[a-z]+(\\s-\\s[a-z]+)*")) 
						{	
							exportedTitle = "Errore inserimento Mansioni";
							exportedText =    "\"" + JobTasks + "\"  \n"
											+ "non rispetta il formato corretto:\n"
											+ "parola \"spazio\" \"-\" \"spazio\" altraparola";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						
					//errori JobSite
						else if((JobSite.isEmpty()) )
						{	
							exportedTitle = "Errore inserimento Sede Lavoro";
							exportedText = "Il campo è vuoto";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if((JobSite.length() <= 2) || (JobSite.length() >= 30)) 
						{	
							exportedTitle = "Errore inserimento Sede Lavoro";
							exportedText = "Lunghezza \"" + JobSite + "\" fuori dai limiti ";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobSite.matches("^[a-zA-Z\\s]*$")) 
						{	
							exportedTitle = "Errore inserimento Sede Lavoro";
							exportedText = "\"" + JobSite + "\"  presenta caratteri non validi";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobSite.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
						{	
							exportedTitle = "Errore inserimento Sede Lavoro";
							exportedText = "\"" + JobSite + "\"  non inizia con lettera maiuscola";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						

					//errori Retribuzione Lorda Giornaliera
						else if((rlgString.isEmpty()) )
						{	
							exportedTitle = "Errore inserimento RLG";
							exportedText = "Il campo è vuoto";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if((rlgString.length() <= 0) || (rlgString.length() >= 7)) 
						{	
							exportedTitle = "Errore inserimento RLG";
							exportedText = "Lunghezza \"" + rlgString + "\" fuori dai limiti ";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!rlgString.matches("^[0-9]*$")) 
						{	
							exportedTitle = "Errore inserimento RLG";
							exportedText = "\"" + rlgString + "\"  usare solo cifre (0-9)";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						
						
						else
						{
							try {
								Connection connection2 = DriverManager.getConnection(jdbcURL, username, password);
								System.out.println("Connesso a server \"lavoratori\"!");
								

								
								
								String sql =  "SELECT * "
											+ "FROM lavoratori "
											//+ "WHERE id = " +  rememberIndexAborted + ";";
											+ "WHERE id = '" + JobWorkerID + "';";
								
								
								Statement statement2 = connection2.createStatement();
								ResultSet result2 = statement2.executeQuery(sql);
								
								
								if(result2.next()) 
								{
									idExist = true;
									System.out.println("Ho trovato il lavoratore n°: " + JobWorkerID);
									

									
								}else {
									System.out.println("Lavoratore non trovato!");
								}
								
								connection2.close();
								System.out.println("DB lavoratori chiuso");
							
								}catch(SQLException e){
									System.out.println("Errore durante la connessione al server");
									e.printStackTrace();
									
									exportedTitle = "Errore Connessione DataBase";
									exportedText  =   "Il database è irraggiungibile!\n";
									exportedText += "\n\n";
						
									callAlertBox(exportedTitle, exportedText);
									return;
								}
							
						
							
							if(!idExist) 
							{
								exportedTitle = "Errore Inserimento Lavoro";
								exportedText  = "Il lavoratore n° " + JobWorkerID + " non esiste!";
								exportedText += "\n\n";
					
								callAlertBox(exportedTitle, exportedText);
								return;
							}
							

							
		//Effettiva esecuzione dell'inserimento
							int n =	pst.executeUpdate();
											
							System.out.println( "Inserite " + n + " righe " );							
							
							exportedTitle = "Feedback Inserimento Lavoro";
							exportedText  = "Inserimento riuscito per lavoratore n° " +JobWorkerID;
							exportedText += "\n\n";
				
							callAlertBox(exportedTitle, exportedText);
							
						}

					} catch (SQLException e) {
						System.out.println( "Errore durante inserimento dati : " + e.getMessage () );	
						
						exportedTitle = "Errore Inserimento Lavoro";
						exportedText  = e.getMessage ();
						exportedText += "\n\n";
			
						callAlertBox(exportedTitle, exportedText);
						return;
					}
			
			connection.close();
			System.out.println("DB lavori chiuso");
		
		}catch(SQLException e){
			System.out.println("Errore durante la connessione al server");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void DisplayJob(ActionEvent event) throws IOException {

		String jdbcURL = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
		
		if	(
				(TF_jIDworker.getText().length() == 0)	||
				(TF_jLavoro.getText().length() == 0)	||
				(DP_jStartP.getValue() == null  )
			)		
		{
			System.out.println("Inserisci i tre criteri di ricerca!");
			
			exportedTitle = "Errore Ricerca Lavoro";
			exportedText  =   "Almeno uno dei parametri \n"
							+ "   di ricerca è vuoto";
			exportedText += "\n\n";
			
			callAlertBox(exportedTitle, exportedText);
			
			return;
		}
		
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connesso a server \"lavoratori\"!");
			
			String s = String.valueOf(TF_jIDworker.getText());
			
			LocalDate dataInizioSQL = DP_jStartP.getValue();
			String LDtoS = dataInizioSQL.format(DateTimeFormatter.ofPattern("yyyy-LL-dd"));
			System.out.println(LDtoS);
			
			String sql =  "SELECT * "
						+ "FROM lavori "
						+ "WHERE (id_lavoratore = '" + s + "') "
						+	"AND (nome_lavoro = '" + TF_jLavoro.getText() + "')"
						+	"AND (inizio_periodo = '" + LDtoS + "');"
						;
			
			
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			if(result.next()) {
				
				

				displayJobID.setText (result.getString("id_lavoratore"));
				displayJobName.setText (result.getString("nome_lavoro"));
				displayJobCompany.setText (result.getString("nome_azienda"));
				displayJobSite.setText (result.getString("sede_lavoro"));
				displayJobRLG.setText (result.getString("rlg"));
				
				TA_jDisplayTasks.setText (result.getString("mansioni_svolte"));
				
				DP_jDisplayStartP.setText (result.getString("inizio_periodo"));
				DP_jDisplayEndP.setText (result.getString("fine_periodo"));
				
	
			}else {
				System.out.println("Lavoro non trovato!\n");
				
				exportedTitle = "Errore Visualizzazione Lavoro";
				exportedText =    "Lavoro non trovato!\n"
								+ "È necessario inserire\n"
								+ "nuovamente ID_lavoratore";
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				
				displayJobID.setText (null);
			}
			
			connection.close();
			System.out.println("DB chiusOOOOOo\n");
			
		
		}catch(SQLException e){
			System.out.println("Errore durante la connessione al server\n");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	public void UpdateJob(ActionEvent event) throws IOException {
		
		
		//variabile per evitare display inutili
		int a = 0;
		
		//Dati accesso DB 
		String jdbcURL = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
		if(displayJobID.getText() == null) 
		{
			displayJobID.setText("");
		}
		
		if(displayJobName.getText() == null) 
		{
			displayJobName.setText("");
		}
		
		if(DP_jDisplayStartP.getText() == null) 
		{
			DP_jDisplayStartP.setText("");
		}
		
		if	(
			(displayJobID.getText().length() == 0)	||
			(displayJobName.getText().length() == 0)	||
			(DP_jDisplayStartP.getText().length() == 0 )
			)		
			{
				System.out.println("Inserisci i tre criteri di ricerca!");
				
				exportedTitle = "Errore Aggiornamento Lavoro";
				exportedText  =   "Almeno uno dei parametri \n"
								+ "   di ricerca è vuoto";
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				
				return;
			}
		
		
		
		//LocalDate W2Birth = DP_jStartP.getValue();
		//String formattedDate = W2Birth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		//Errore: dati visualizzati e a rischio aggiornamento sono diversi
		if(  	(!displayJobID.getText().equals(TF_jIDworker.getText()))  
			//	||
			//	(!displayJobName.getText().equals(TF_jLavoro.getText()))  
			//	||
			//	(!DP_jDisplayStartP.getText().equals(formattedDate)) 
		  ) 
		{
			System.out.println(" 2 campi non coincidono");
			
			exportedTitle = "Errore Aggiornamento";
			exportedText  = "Misura di protezione aggiornamento richiesta:\n"
						+ 	"I valori dei campo ID usato per la ricerca \n"
						+ 	"e quello visualizzato devono coincidere";
			exportedText += "\n\n";
			
			callAlertBox(exportedTitle, exportedText);
			return;
		}
		
		
		
		
		
		//String JobIDWorker	= TF_jIDworker.getText();
		String JobName		= TF_jLavoro.getText();
		String JobCompany	= TF_jAzienda.getText();
		String JobSite		= TF_jSede.getText();
		String JobRLG		= TF_jRLG.getText();
		
		LocalDate JobStart	= DP_jStartP.getValue();
		LocalDate JobEnd	= DP_jEndP.getValue();
		
		
		//Integer RLG = Integer.valueOf(JobRLG);
		
		
		
		String text = TA_jMansioni.getText();
		//ObservableList<CharSequence>  JobTasks = TA_jMansioni.getParagraphs();
		
		//String ennesimoTest = TA_jMansioni.getText();


		
		
		 	//informazioni temporanee
		//String J2ID			= displayJobID.getText();
		String J2Name		= displayJobName.getText();
		String J2Company	= displayJobCompany.getText();
		String J2Site		= displayJobSite.getText();
		String J2RLG		= displayJobRLG.getText();
		//String J2Start		= DP_jDisplayStartP.getText();
		//String J2End		= DP_jDisplayEndP.getText();
		//String J2Tasks		= TA_jDisplayTasks.getText();
		
		String date = DP_jDisplayStartP.getText();
        //default, ISO_LOCAL_DATE
		LocalDate W2Begin = LocalDate.parse(date);
		date = DP_jDisplayEndP.getText();
		LocalDate W2Final = LocalDate.parse(date);
		
		
		//ObservableList<CharSequence>  J2Tasks = TA_jDisplayTasks.getParagraphs();
			
		String test = TA_jDisplayTasks.getText();
		


			
		//String dateB = DP_jDisplayStartP.getText();
		//LocalDate J2StartAv = LocalDate.parse(dateB);
		//dateB = DP_jDisplayEndP.getText();
		//LocalDate J2EndAv = LocalDate.parse(dateB);
		
		
		

		JobName 		= ((JobName.length() != 0)?JobName:J2Name);
		JobCompany 		= ((JobCompany.length() != 0)?JobCompany:J2Company);
		JobSite 		= ((JobSite.length() != 0)?JobSite:J2Site);
		JobRLG 			= ((JobRLG.length() != 0)?JobRLG:J2RLG);
		
		//text 			= ((text.isEmpty())?  test.substring(1, test.length()-1)  : text.substring(1, text.length()-1));
		text 			= ((text.isEmpty())?  (String)test  : (String)text);
		
		
		JobStart 		= ((JobStart != null)?JobStart:W2Begin);
		JobEnd 			= ((JobEnd != null)?JobEnd:W2Final);
		
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connesso a server locale PSQL!"
					+ "\n mi collego ora a lavoratori\n");
		
			try(PreparedStatement pst = connection.prepareStatement
						( "UPDATE lavori "
						+ "SET "
						+ " nome_lavoro = '"	 + JobName 		+ "',"	
						+ " inizio_periodo = '"  + JobStart 	+ "',"
						+ " fine_periodo = '" 	 + JobEnd 		+ "',"
						+ " nome_azienda = '" 	 + JobCompany 	+ "',"
						+ " mansioni_svolte = '" + text 		+ "',"
						+ " sede_lavoro = '" 	 + JobSite 		+ "',"
						+ " rlg = '" 			 + JobRLG		
						+ "' "
						
						+ " WHERE"
						+ " id_lavoratore = '" 	+ displayJobID.getText() + "' AND "
						+ " nome_lavoro = '"	+ displayJobName.getText()+ "' AND "
						+ " inizio_periodo = '"	+ DP_jDisplayStartP.getText()
						
						+"';"
					);
				)
			{
				//qui metterò mucho controllo errori
				
				
				
	//GESTIONE ERRORI AGGIORNAMENTO-!-!-!-!-!-!-!-!-!-!-!-!-!-!-
				LocalDate dataInizio = JobStart;
				String LDtoS2 = dataInizio.format(DateTimeFormatter.ofPattern("dd/LL/yyyy"));
				
				
				

						
						if(!JobName.matches("^[a-zA-Z\\s]*$") )
						{
							exportedTitle = "Errore inserimento Lavoro";
							exportedText = "\"" + JobName + "\"  presenta caratteri non validi";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobName.matches("^[A-Z][a-z]*(\\s[a-z]+)*") )
						{
							exportedTitle = "Errore inserimento Lavoro";
							exportedText = "\"" + JobName + "\"  non inizia con lettera maiuscola";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						
						
					//Errori DATA Inizio lavori
						else if(!LDtoS2.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}") )
						{
							exportedTitle = "Errore inserimento Data Inizio Lavoro";
							exportedText = "\"" + dataInizio + "\"  \n"
											+ "seguire il formato:\n"
											+ "####/##/##";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(((System.currentTimeMillis()/1000 - ((long)JobStart.getYear()-1970) * 31557600L) - ((long)JobStart.getDayOfYear() * 86400L)) < 0)
						{	
							exportedTitle = "Errore valori Data Inizio Lavoro";
							exportedText = "La data si riferisce al futuro!";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(((System.currentTimeMillis()/1000 - ((long)JobEnd.getYear()-1970) * 31557600L) - ((long)JobEnd.getDayOfYear() * 86400L)) < 0)
						{	
							exportedTitle = "Errore valori Data Fine Lavoro";
							exportedText = "La data si riferisce al futuro!";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(((System.currentTimeMillis()/1000 - ((long)JobEnd.getYear()-1970) * 31557600L) - ((long)JobEnd.getDayOfYear() * 86400L)) > cinqueAnni1B)
						{	
							exportedTitle = "Errore valori Data Fine Lavoro";
							exportedText =    "Non è possibile inserire valori\n"
											+ "che superano i cinque anni!";
							exportedText += "\n\n";

							callAlertBox(exportedTitle, exportedText);
						}
						
					//Errori CHECK date 
						else if ( ( (long)JobStart.getYear()*365.25 - (long)JobEnd.getYear()*365.25   )  +  ( (long)JobStart.getDayOfYear() - (long)JobEnd.getDayOfYear()   ) > 0  )
						{
							exportedTitle = "Errore valori DATE LAVORO'";
							exportedText = "Data fine lavoro non può \nvenire prima di inizio!";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						
					//errori JobNameCompany
						else if((JobCompany.isEmpty()) )
						{	
							exportedTitle = "Errore inserimento Nome Azienda";
							exportedText = "Il campo è vuoto";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if((JobCompany.length() <= 2) || (JobCompany.length() >= 30)) 
						{	
							exportedTitle = "Errore inserimento Nome Azienda";
							exportedText = "Lunghezza \"" + JobCompany + "\" fuori dai limiti ";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobCompany.matches("^[a-zA-Z\\s-]*$")) 
						{	
							exportedTitle = "Errore inserimento Nome Azienda";
							exportedText = "\"" + JobCompany + "\"  presenta caratteri non validi";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobCompany.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
						{	
							exportedTitle = "Errore inserimento Nome Azienda";
							exportedText = "\"" + JobCompany + "\"  non inizia con lettera Maiuscola";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
	
					//errori JobTasks
						else if((text.isEmpty()) )
						{	
							exportedTitle = "Errore inserimento Mansioni";
							exportedText = "Il campo è vuoto";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if((text.length() <= 2) || (text.length() >= 300)) 
						{	
							exportedTitle = "Errore inserimento Mansioni";
							exportedText = "Lunghezza \"" + text + "\" fuori dai limiti ";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!text.matches("^[a-zA-Z\\s-]*$")) 
						{	
							exportedTitle = "Errore inserimento Mansioni";
							exportedText = "\"" + text + "\"  presenta caratteri non validi";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!text.matches("^[a-z]+(\\s-\\s[a-z]+)*")) 
						{	
							exportedTitle = "Errore inserimento Mansioni";
							exportedText =    "\"" + text + "\"  \n"
											+ "non rispetta il formato corretto:\n"
											+ "parola \"spazio\" \"-\" \"spazio\" altraparola";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						
					//errori JobSite
						else if((JobSite.isEmpty()) )
						{	
							exportedTitle = "Errore inserimento Sede Lavoro";
							exportedText = "Il campo è vuoto";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if((JobSite.length() <= 2) || (JobSite.length() >= 30)) 
						{	
							exportedTitle = "Errore inserimento Sede Lavoro";
							exportedText = "Lunghezza \"" + JobSite + "\" fuori dai limiti ";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobSite.matches("^[a-zA-Z\\s]*$")) 
						{	
							exportedTitle = "Errore inserimento Sede Lavoro";
							exportedText = "\"" + JobSite + "\"  presenta caratteri non validi";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobSite.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]+)*")) 
						{	
							exportedTitle = "Errore inserimento Sede Lavoro";
							exportedText = "\"" + JobSite + "\"  non inizia con lettera maiuscola";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						

					//errori Retribuzione Lorda Giornaliera
						else if((JobRLG.isEmpty()) )
						{	
							exportedTitle = "Errore inserimento RLG";
							exportedText = "Il campo è vuoto";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if((JobRLG.length() <= 0) || (JobRLG.length() >= 7)) 
						{	
							exportedTitle = "Errore inserimento RLG";
							exportedText = "Lunghezza \"" + JobRLG + "\" fuori dai limiti ";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
						
						else if(!JobRLG.matches("^[0-9]*$")) 
						{	
							exportedTitle = "Errore inserimento RLG";
							exportedText = "\"" + JobRLG + "\"  usare solo cifre (0-9)";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
				
						
						else 
						{
							a++;
							
	//EFFETTIVO AGGIORNAMENTO
							int n =	pst.executeUpdate();
							System.out.println( "Aggiornate " + n + " righe " );
							
							exportedTitle = "Notifica Feedback";
							exportedText  = "Aggiornamento compiuto con successo!";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
						}
				
				
				

				
			}
			catch (SQLException e) {
				System.out.println( "Errore durante aggiornamento dati : \n" + e.getMessage () );	
				
				exportedTitle = "Notifica Feedback";
				exportedText  =   "Aggiornamento fallito!\n"
								+ e.getMessage();
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				
				return ;
			}
	
			connection.close();
			System.out.println("DB lavoratori chiuso");
		
		}
		catch(SQLException e){
			System.out.println("Errore durante la connessione al server"
					+ "\n cancellazione record fallita\n\n");
			
			
			exportedTitle = "Feedback";
			exportedText  =   "Connessione al DB fallita!\n"
							+ e.getMessage();
			exportedText += "\n\n";
			
			callAlertBox(exportedTitle, exportedText);
			
			return ;
			//e.printStackTrace();
		}
		
		
		if(a != 0) 
		{
			//provo ad aggiornare i risultati SOLO se sono cambiati
			DisplayJob(event);
		}
		
	}
	
	
	
	
	
	
	
	
		//================================================================================================
		//===================DISPLAY==ALL==WORK==PER==ID==================================================
		//================================================================================================
		public void displayWorkPerID(ActionEvent event) throws IOException {
			String jdbcURL = "jdbc:postgresql://localhost:5432/test";
			String username = "postgres";
			String password = "POCHImabuoni123";
			
			
			try {
				Connection connection = DriverManager.getConnection(jdbcURL, username, password);
				System.out.println("Connesso a server \"lavoratori\"!");
				
				
				String sql =  "SELECT * "
							+ "FROM lavoratori JOIN lavori ON id = id_lavoratore "
							+ "WHERE id_lavoratore = "
							+ TF_jIDworker1.getText()
							+ " ORDER BY id ASC;";
				
				
				
				
				
				//Verifico se un tale LAVORATORE esiste tramite ricerca ID
				String sql2 = "SELECT * "
							+ "FROM lavoratori "
							+ "WHERE id = '"
							+ TF_jIDworker1.getText()
							+ "';"
							;
				
				Statement statement2 = connection.createStatement();
				ResultSet result2 = statement2.executeQuery(sql2);
				
				if(!result2.next()) 
				{
					exportedTitle = "Errore ricerca ID lavoratore";
					exportedText = "L'utente con ID: "+ TF_jIDworker1.getText() + " non esite!";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
					return;
				}
				
				
				
				
				//Il lavoratore esiste, ora cerco SE ha lavori
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(sql);
				
				String outputQuery = "";
				int n = 1;
				String s = "";
				
				
				
				while(result.next()) {


					s = String.valueOf(n++);
					
					outputQuery += s;

					outputQuery += " \nnome: ";
					outputQuery += result.getString("nome");
					outputQuery += "  | cognome: ";
					outputQuery += result.getString("cognome");
					
					outputQuery += "  \nLavoro: ";
					outputQuery += result.getString("nome_lavoro");		
					outputQuery += "  | Inizio Periodo: ";
					outputQuery += result.getString("inizio_periodo");
					outputQuery += "  | Fine Periodo: ";
					outputQuery += result.getString("fine_periodo");
					outputQuery += "  | Nome Azienda: ";
					outputQuery += result.getString("nome_azienda");
					
					outputQuery += "  \nMansioni svolte: ";
					outputQuery += result.getString("mansioni_svolte");
					
					outputQuery += "  \nSede: ";
					outputQuery += result.getString("sede_lavoro");
					outputQuery += "  | Retribuzione lorda giornaliera: ";
					outputQuery += result.getString("rlg");
					
					outputQuery += "\n\n\n";
				}
				
				
				TAresultArea.setText (outputQuery);

				
				//se n non è stato aggiornato questo lavoratore non ha lavori passati
				if(n == 1) 
				{
					exportedTitle = "Errore ricerca ID lavoratore";
					exportedText = "L'utente esiste ma non ha lavori assegnati!";
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
	
	
	
	
	
	
	
	public void DeleteJob(ActionEvent event) throws IOException {
		
		
		//Dati accesso DB 
		String jdbcURL = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
		
		
		
		//Conversioni necessarie per rilevazione errori
		if(displayJobID.getText() == null) 
		{
			displayJobID.setText("");
		}
		
		if(displayJobName.getText() == null) 
		{
			displayJobName.setText("");
		}
		
		if(DP_jDisplayStartP.getText() == null) 
		{
			DP_jDisplayStartP.setText("");
		}
		
		
		
		
		//almeno uno dei campi ricerca è vuoto
		if ((displayJobID.getText().length() == 0)		||
			(displayJobName.getText().length() == 0)	||
			(DP_jDisplayStartP.getText().length() == 0 ))		
		{
			System.out.println("Inserisci i tre criteri di ricerca!");
			
			exportedTitle = "Errore Aggiornamento Lavoro";
			exportedText  =   "Almeno uno dei parametri \n"
							+ "   di ricerca è vuoto";
			exportedText += "\n\n";
					
			callAlertBox(exportedTitle, exportedText);
					
			return;
		}
		
		
		
		LocalDate W2Birth = DP_jStartP.getValue();
		String formattedDate = W2Birth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		
		//Errore dati visualizzati e a rischio cancellazione sono diversi
		if(  	(!displayJobID.getText().equals(TF_jIDworker.getText()))  			||
				(!displayJobName.getText().equals(TF_jLavoro.getText()))  		||
				(!DP_jDisplayStartP.getText().equals(formattedDate)) 
		  ) 
		{
			System.out.println(" 2 campi non coincidono");
			
			exportedTitle = "Errore Cancellazione";
			exportedText  = "Misura di protezione cancellazione richiesta:\n"
						+ 	"I valori dei campi usati per la ricerca \n"
						+ 	"e quelli visualizzati devono coincidere";
			exportedText += "\n\n";
			
			callAlertBox(exportedTitle, exportedText);
			return;
		}
		
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connesso a server locale PSQL!");
			
			
			String s = String.valueOf(TF_jIDworker.getText());
			LocalDate dataInizioSQL = DP_jStartP.getValue();
			String LDtoS = dataInizioSQL.format(DateTimeFormatter.ofPattern("yyyy-LL-dd"));
			System.out.println(LDtoS);
			
			try(PreparedStatement pst = connection.prepareStatement
					( "DELETE FROM lavori "
					+ "WHERE (id_lavoratore = '" + s + "') "
					+	"AND (nome_lavoro = '" + TF_jLavoro.getText() + "') "
					+	"AND (inizio_periodo = '" + LDtoS + "'); "
					);
				) 
			{
				int n =	pst.executeUpdate();
				System.out.println( "Tolte " + n + " righe " );
				
				exportedTitle 	= "Feedback Cancellazione";
				exportedText 	= "Record eliminato correttamente!\n";
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				
				
			} catch (SQLException e) {
				System.out.println( "Errore durante cancellazione dati : " + e.getMessage () );	
				
				exportedTitle 	= "Feedback Cancellazione";
				exportedText 	= "Errore durante cancellazione dati : " + e.getMessage ();
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				
				return ;
			}

			connection.close();
			System.out.println("DB lavori chiuso");
		
		}catch(SQLException e){
			System.out.println("Errore durante la connessione al server"
					+ "\n cancellazione record fallita\n\n");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	public void checkEveryWorker(ActionEvent event) throws IOException {
		
		String jdbcURL = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
		
		
		Map<Integer, Boolean> mapResultFromResultset = new HashMap<>();

		Queue<Integer> queue0 = new PriorityQueue<Integer>();

		ArrayList<String> lista1 = new ArrayList<String>();
		ArrayList<String> lista2 = new ArrayList<String>();
		ArrayList<String> lista3 = new ArrayList<String>();
		ArrayList<String> lista4 = new ArrayList<String>();
		
		String outputQuery = "Non sono presenti record nel database";
		
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connesso a server \"lavoratori\"!");
			
			
			String sql =  "SELECT id, esperienze "
						+ "FROM lavoratori  "
						+ "ORDER BY id ASC; "
						;
			
			
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			
			
			//int n = 1;
			//String s = "";
			
			
			
			//Uso la prima query per riempire la mappa
			while(result.next()) {
				
				boolean testEXP = result.getBoolean("esperienze");
				String id_found = result.getString("id");
				
				Integer TESTA = Integer.valueOf(id_found);
				
				queue0.add(TESTA);
				mapResultFromResultset.put(TESTA, testEXP);

			}
			
			
			//rapido controllo se il DB è vuoto
			if(queue0.isEmpty())
			{
				exportedTitle = "Notifica Feedback";
				exportedText  = "Non sono presenti record!";
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
			}
			
				
			System.out.println(mapResultFromResultset);
			
			
			
			connection.close();
			System.out.println("DB chiusOOOOOo");
			
		
		}catch(SQLException e){
			System.out.println("Errore durante la connessione al server");
			e.printStackTrace();
		}
		
		
		
			
		while(!queue0.isEmpty()) 
		{
			
			//Conversione Integer -> String
			Integer	integerFromQueue = queue0.poll();
			
			String fromIntegerToString = Integer.toString(integerFromQueue);
			
			int fromIntegerToInt = integerFromQueue;
			
			//integerFromQueue = queue0.poll();
			System.out.println("\n test "+fromIntegerToInt+"\n");
			
			
			try {
				Connection connection = DriverManager.getConnection(jdbcURL, username, password);
				System.out.println("Connesso a server \"lavoratori\"!");
				
				
				
				//prima sottoquery
				String sql2 = "SELECT * "
							+ "FROM lavori "
							+ "WHERE id_lavoratore ='" 
							+ fromIntegerToString
							+ "' "
							;
				
				
				Statement statement = connection.createStatement();
				ResultSet result2 = statement.executeQuery(sql2);
				 
				 
				 
		
			
				boolean foundEXP = false;
				boolean needThirdQuery = false;
				String guida = "true";
				
				if(result2.next())
				{
					foundEXP = true;
				}
				
				
				boolean testEXP = mapResultFromResultset.get(fromIntegerToInt);
				
				
				
			//caso 1: trovate esperienze e segnalate correttamente
				if( (testEXP == true)  &&  (foundEXP) )
				{
					System.out.println("trovate esperienze e segnalate correttamente");
					lista1.add(fromIntegerToString);
				}
			//caso 2: NON trovate esperienze E NON segnalate correttamente	
				else if( (testEXP == false) && (!foundEXP) )
				{
					System.out.println("NON trovate esperienze E NON segnalate correttamente ");
					lista2.add(fromIntegerToString);
				}
			//caso 3: trovate esperienze MA NON segnalate
				else if( (testEXP == false) && (foundEXP) )
				{
					System.out.println("trovate esperienze MA NON segnalate ");	
					needThirdQuery = true;
					lista3.add(fromIntegerToString);
				}
			//caso 4: NON trovate esperienze MA segnalate 
				else
				{
					System.out.println("NON trovate esperienze MA segnalate "); 
					needThirdQuery = true;
					guida = "false";
					lista4.add(fromIntegerToString);
				}
				
				
				//eventuale terza query di aggiornamento per i casi 3 e 4
				if(needThirdQuery) 
				{
					try(PreparedStatement pst = connection.prepareStatement
							(	  "UPDATE lavoratori "
								+ "SET "
								+ "esperienze = '"
								+ guida 
								+ "' "
							 	+ "WHERE id = '" 
								+ fromIntegerToString 
								+ "';" 
							);
						) 
					{
					
					int no =	pst.executeUpdate();
					System.out.println( "Aggiornate " + no + " righe " );
				
				
					}catch(SQLException e){
						System.out.println("Errore durante la connessione al server");
						e.printStackTrace();
					}
					
				//fine if(needThirdQuery)
				}
			
				
			} catch (SQLException e) {
				System.out.println( "Errore durante cancellazione dati : " + e.getMessage () );	
						
				exportedTitle = "Errore Aggiornamento";
				exportedText  =   "Questo utente non può essere cancellato\n"
								+ "perché ha almeno un record nella tabella lavori";
				exportedText += "\n\n";

				callAlertBox(exportedTitle, exportedText);
							
				return ;
			}
					
			System.out.println("\n test "+fromIntegerToInt+"\n");
	
		//fine while	
		//(!lista0.isEmpty()) 
		}	
			
		
		//necessito 4 arraylist
		outputQuery 	= "Questi utenti hanno effettivamente esperienze di "
						+ "lavoro negli ultimi cinque anni:\n" 
						+ lista1 
						+ "\n\n" 
						+ "Questi utenti NON hanno alcun esperienza di "
						+ "lavoro negli ultimi cinque anni\n" 
						+ lista2 
						+ "\n\n" 
						+ "Questi utenti hanno esperienze di lavoro che "
						+ "non erano ancora segnalate\n" 
						+ lista3 
						+ "\n\n" 
						+ "Infine costoro non avevano esperienze di lavoro "
						+ "negli ultimi cinque anni al contrario di quanto riportato\n" 
						+ lista4 
						+ "\n\n"
						;
		
		TAresultArea.setText (outputQuery);


			
	//Fine metodo checkEveryWorker() 		
	}
	
//Fine Classe
}
