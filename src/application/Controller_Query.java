package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controller_Query {
	
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	String jdbcURL = "jdbc:postgresql://localhost:5432/test";
	String username = "postgres";
	String password = "POCHImabuoni123";
	
	
	String exportedTitle = "Titolo errore generico";
	String exportedText  = "Campo errore generico";
	
	
	@FXML
	public ChoiceBox<String> CBoperatoriLogici;

	@FXML
	public ChoiceBox<String> CBoperationONE;
	@FXML
	public ChoiceBox<String> CBoperationTWO;
	
	
	@FXML
	TextField TFone;
	@FXML
	TextField TFtwo;
	
	@FXML
	ScrollPane scrollPane1;
	@FXML
	ScrollPane scrollPane2;
	@FXML
	ScrollPane scrollPane3;
	
	@FXML
	TextArea TAresultArea;
	@FXML
	TextArea TAresultArea2;
	@FXML
	TextArea TAresultArea3;
	
	@FXML
	BorderPane border1;
	@FXML
	BorderPane border2;
	
	@FXML
	ComboBox<String> comboQueryONE;
	@FXML
	ComboBox<String> comboQueryTWO;
	
	ComboBox<String> sourceCombo;
	
	
	
	Map<String, String> mapQuery = new HashMap<>();

	
	
	
	


	
	
	
	public void switchLManagerPage(ActionEvent event) throws IOException {

		root = FXMLLoader.load(getClass().getResource("Scene_Manager.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	

	
	
	ObservableList<String> listaOperatori	= FXCollections.observableArrayList(null, "AND","OR");			
	ObservableList<String> lista1			= FXCollections.observableArrayList
			(
				null,
				"Nome lavoratore",
				"Cognome lavoratore",
				"Domicio lavoratore",
				"Inizio disponibilità",
				"Fine disponibilità",
				"Nazionalità",
				"lingue parlate",
				"Patente",
				"Automunito",
				"Mansioni"
			);
	ObservableList<String> listaALPHA		= FXCollections.observableArrayList
			(null, "=","<>");
	ObservableList<String> listaBRAVO		= FXCollections.observableArrayList
			(null, "=","<>",">",">=","<","<=");
	ObservableList<String> listaPatent		= FXCollections.observableArrayList
			(null, "A","B","C","D");
	ObservableList<String> listaTF			= FXCollections.observableArrayList
			("Si","No");
	ObservableList<String> listaNULL		= FXCollections.observableArrayList
			("");
	

	
	
	
	private void callAlertBox(String importedTitle, String importedText) 
	{
		//messaggi
		AlertBox test = new AlertBox();
		test.display(importedTitle, importedText);
	}
	
	
	@FXML
	public void initialize() {
		CBoperatoriLogici.setItems(listaOperatori);
		CBoperatoriLogici.setValue(null);
		
		
		ObservableList<String> list  = comboQueryONE.getItems();
		ObservableList<String> list2 = comboQueryTWO.getItems();
		
		
		list.add(null);
		list.add("Nome lavoratore");
		list.add("Cognome lavoratore");
		list.add("Domicio lavoratore");
		list.add("Inizio disponibilità");
		list.add("Fine disponibilità");
		list.add("Zona disponibilità");
		list.add("Nazionalità");
		list.add("Lingue parlate");
		list.add("Patente");
		list.add("Automunito");
		list.add("Lavoro");
		list.add("Mansioni");
		
		
		list2.add(null);
		list2.add("Nome lavoratore");
		list2.add("Cognome lavoratore");
		list2.add("Domicio lavoratore");
		list2.add("Inizio disponibilità");
		list2.add("Fine disponibilità");
		list2.add("Zona disponibilità");
		list2.add("Nazionalità");
		list2.add("Lingue parlate");
		list2.add("Patente");
		list2.add("Automunito");
		list2.add("Lavoro");
		list2.add("Mansioni");
		

		TAresultArea.setEditable(false);
		TAresultArea.setWrapText(true);
		
		
	//fine
	//initialize()
	}
	

	
	
	
	@SuppressWarnings("unchecked")
	public void updateOperatorList(ActionEvent event) throws IOException {
		
		
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene_Query.fxml"));
        //loader.setController(new Controller());
		
        
        sourceCombo = (ComboBox<String> ) event.getSource();

		
		if (sourceCombo.getValue() == null) {
			setOperatorListNULL();
		}else {
			switch(sourceCombo.getValue()) {
				
				case "Nome lavoratore":
					setOperatorListALPHA(sourceCombo);
					break;
				case "Cognome lavoratore":
					setOperatorListALPHA(sourceCombo);
					break;
				case "Domicio lavoratore":
					setOperatorListALPHA(sourceCombo);
					break;	
				case "Inizio disponibilità":
					setOperatorListBRAVO(sourceCombo);
					break;
				case "Fine disponibilità":
					setOperatorListBRAVO(sourceCombo);
					break;
				case "Zona disponibilità":
					setOperatorListALPHA(sourceCombo);
					break;
				case "Nazionalità":
					setOperatorListALPHA(sourceCombo);
					break;
				case "Lingue parlate":
					setOperatorListALPHA(sourceCombo);
					break;
				case "Patente":
					setOperatorListALPHA(sourceCombo);
					break;
				case "Automunito":
					setOperatorListALPHA(sourceCombo);
					break;
				case "Lavoro":
					setOperatorListALPHA(sourceCombo);
					break;
				case "Mansioni":
					setOperatorListALPHA(sourceCombo);
					break;
				default:
					setOperatorListNULL();
					break;
			}
		}	
		
	//fine
	//updateOperatorList
	}
	
	
	
	private void setOperatorListNULL() {
		// TODO Auto-generated method stub
		if(sourceCombo == comboQueryONE)
			CBoperationONE.setItems(listaNULL);
		else
			CBoperationTWO.setItems(listaNULL);
	}
	
	private void setOperatorListALPHA(ComboBox<String> chosenCombo) {
		// TODO Auto-generated method stub
		if(sourceCombo == comboQueryONE)
			CBoperationONE.setItems(listaALPHA);
		else
			CBoperationTWO.setItems(listaALPHA);
		
	}
	
	private void setOperatorListBRAVO(ComboBox<String> chosenCombo) {
		// TODO Auto-generated method stub
		if(sourceCombo == comboQueryONE)
			CBoperationONE.setItems(listaBRAVO);
		else
			CBoperationTWO.setItems(listaBRAVO);
	}
	
	
	
	/*  Possibili future implementazioni
	  
	 
	private void setOperatorListPatent() {
		// TODO Auto-generated method stub
		if(sourceCombo == comboQueryONE)
			CBoperationONE.setItems(listaPatent);
		else
			CBoperationTWO.setItems(listaPatent);
	}
	
	private void setOperatorListTF() {
		// TODO Auto-generated method stub
		if(sourceCombo == comboQueryONE)
			CBoperationONE.setItems(listaTF);
		else
			CBoperationTWO.setItems(listaTF);
	}
	*/
	
	


	//================================================================================================
	//===================EXECUTE==QUERY===============================================================
	//================================================================================================
	public void executeQuery(ActionEvent event) throws IOException 
	{
		
		//Mappatura valori catturati nei ComboBox  --> valori presi dal DB lavori
		mapQuery.put("Nome lavoratore", 		"nome");
		mapQuery.put("Cognome lavoratore", 		"cognome");
		mapQuery.put("Domicio lavoratore", 		"indirizzo");
		mapQuery.put("Inizio disponibilità", 	"inizio_disp");
		mapQuery.put("Fine disponibilità", 		"fine_disp");
		mapQuery.put("Zona disponibilità", 		"disponibilita");
		mapQuery.put("Nazionalità", 			"nazionalita");
		mapQuery.put("Lingue parlate", 			"lingue");
		mapQuery.put("Patente", 				"patent");
		mapQuery.put("Automunito",				"automunito");
		
		mapQuery.put("Mansioni", 				"mansioni_svolte");
		mapQuery.put("Lavoro", 					"nome_lavoro");



		String OperatorSelected	= CBoperatoriLogici.getValue();
		
		
		if(CBoperatoriLogici.getValue() == null) {
		
			try {
				Connection connection = DriverManager.getConnection(jdbcURL, username, password);
				System.out.println("Connesso a server per effettuare query!");
				
				String dinamicFrom = "lavoratori ";
				String expr = comboQueryONE.getValue();
				String theta = CBoperationONE.getValue();
				String expr_const = TFone.getText();
				
				boolean needPerCent = false;
				

				if( (expr == "Zona disponibilità" ) || (expr == "Lingue parlate" ) || (expr == "Mansioni" ) || (expr == "Lavoro" ) )
				{
					needPerCent = true;
					
					if( (expr == "Mansioni" ) || (expr == "Lavoro" ) )
					{
						dinamicFrom += "JOIN lavori ON id = id_lavoratore ";
					}
					
					if(theta == "<>")
						theta = "NOT SIMILAR TO";
					else 
					{
						System.out.println("Converno null in =   :)");
						theta = "SIMILAR TO";
					}
				}
				
				
				if((expr_const.isEmpty()) )
				{	
					exportedTitle = "Errore inserimento valori";
					exportedText = "Il campo è vuoto";
					exportedText += "\n\n";
					
					callAlertBox(exportedTitle, exportedText);
					return;
				}
				
				
				
				String sql 	= "SELECT * "
							+ "FROM " + dinamicFrom
							+ "WHERE " + mapQuery.get(expr)
							+ " "
							+ theta 
							+ " '"
							+ (needPerCent ? "%" : "") 
							+ expr_const 
							+ (needPerCent ? "%" : "")
							+ "' "
							+ "ORDER BY id ASC;";
	
				
				System.out.println("ecco la stringa:\n" + sql);
				
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(sql);
				

				String outputQuery = "";
				
				while(result.next()) {
					int id = result.getInt("id");
					String nomeStampa = result.getString("nome");
					String cognommeStampa = result.getString("cognome");
					

					outputQuery += String.valueOf(id);
					outputQuery += "  -  ";
					outputQuery += nomeStampa;
					outputQuery += "  -  ";
					outputQuery += cognommeStampa;

					String firstMappedString  = mapQuery.get(expr);
					
				
					
					if		(  		
							(firstMappedString.equals("indirizzo") ) 
							)
							{
								String jobStampa =   result.getString("indirizzo");
								outputQuery += "  -  ";
								outputQuery += jobStampa;
							}
					
					
					else if	(  		
								(firstMappedString.equals("inizio_disp") ) 
							)
							{
								String jobStampa =   result.getString("inizio_disp");
								outputQuery += "  -  ";
								outputQuery += jobStampa;
							}
					
					else if	(  		
							(firstMappedString.equals("fine_disp") ) 
							)
							{
								String jobStampa =   result.getString("fine_disp");
								outputQuery += "  -  ";
								outputQuery += jobStampa;
							}
					
					else if	(  		
							(firstMappedString.equals("disponibilita") ) 
							)
							{
								String jobStampa =   result.getString("disponibilita");
								outputQuery += "  -  ";
								outputQuery += jobStampa;
							}
					
					else if	(  		
							(firstMappedString.equals("nazionalita") ) 
							)
							{
								String jobStampa =   result.getString("nazionalita");
								outputQuery += "  -  ";
								outputQuery += jobStampa;
							}
					
					else if	(  		
							(firstMappedString.equals("lingue") ) 
							)
							{
								String jobStampa =   result.getString("lingue");
								outputQuery += "  -  ";
								outputQuery += jobStampa;
							}
					
					else if	(  		
							(firstMappedString.equals("patent") ) 
							)
							{
								String jobStampa =   result.getString("patent");
								outputQuery += "  -  ";
								outputQuery += jobStampa;
							}
					
					else if	(  		
							(firstMappedString.equals("automunito") ) 
							)
							{
								String jobStampa =   result.getString("automunito");
								outputQuery += "  -  ";
								outputQuery += jobStampa;
							}
					
					else if	(  		
							(firstMappedString.equals("mansioni_svolte") ) 
							)
							{
								String jobStampa =   result.getString("mansioni_svolte");
								outputQuery += "  -  ";
								outputQuery += jobStampa;
							}
					else if	(  		
							(firstMappedString.equals("nome_lavoro") ) 
							)
							{
								String jobStampa =   result.getString("nome_lavoro");
								outputQuery += "  -  ";
								outputQuery += jobStampa;
							}
					
					outputQuery += "\n\n";
				}
				

				
				TAresultArea.setText (outputQuery);

				
				connection.close();
				System.out.println("DB terminato03");
			
			}catch(SQLException e){
				System.out.println("Ricerca nel DB fallita\n");

				
				exportedTitle = "Feedback ricerca";
				exportedText = "Ricerca nel DB fallita\n";
				exportedText += "\n\n";
				
				callAlertBox(exportedTitle, exportedText);
				return;
				
				//e.printStackTrace();
				
			}
		
			
		//fine
		//if(CBoperatoriLogici.getValue() == null)
		}else{
			
			switch( OperatorSelected ) {
				
			
				//Intersezione tra 2 risultati
				case "AND":
					
					try {
						Connection connection = DriverManager.getConnection(jdbcURL, username, password);
						
						String dinamicFrom = "lavoratori ";
						
						String one_expr = comboQueryONE.getValue();
						String one_theta = CBoperationONE.getValue();
						String one_expr_const = TFone.getText();
						
						String two_expr = comboQueryTWO.getValue();
						String two_theta = CBoperationTWO.getValue();
						String two_expr_const = TFtwo.getText();
						
						boolean needPerCent1 = false;
						boolean needPerCent2 = false;
						
						
						if( (one_expr == "Zona disponibilità" ) || 
							(one_expr == "Lingue parlate" ) || 
							(one_expr == "Mansioni" ) || 
							(one_expr == "Lavoro" ) )
						{
							needPerCent1 = true;
							
							if( ( (one_expr == "Mansioni" ) || (one_expr == "Lavoro" ) ) && dinamicFrom != "Lavoratori " )
							{
								dinamicFrom += "JOIN lavori ON id = id_lavoratore ";
							}
							
							if(one_theta == "<>")
								one_theta = "NOT SIMILAR TO";
							else 
							{
								//Converno null  (in realtà qualunque cosa) in" SIMILAR TO"    :)
								one_theta = "SIMILAR TO";
							}
						}
						
						
						if((one_expr_const.isEmpty()) )
						{	
							exportedTitle = "Errore inserimento valori";
							exportedText = "Il campo Principale è vuoto";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
							return;
						}
						
						if( (two_expr == "Zona disponibilità" ) || 
							(two_expr == "Lingue parlate" ) || 
							(two_expr == "Mansioni" ) || 
							(two_expr == "Lavoro" ) )
						{
							needPerCent2 = true;
							
							if( ( (two_expr == "Mansioni" ) || (two_expr == "Lavoro" ) ) && dinamicFrom != "Lavoratori " )
							{
								dinamicFrom += "JOIN lavori ON id = id_lavoratore ";					
							}
							
							if(two_theta == "<>")
								two_theta = "NOT SIMILAR TO";
							else 
							{
								//Converno null  (in realtà qualunque cosa) in" SIMILAR TO"    :)
								two_theta = "SIMILAR TO";
							}
						}
						
						
						if((two_expr_const.isEmpty()) )
						{	
							exportedTitle = "Errore inserimento valori";
							exportedText = "Il campo secondario è vuoto";
							exportedText += "\n\n";
							
							callAlertBox(exportedTitle, exportedText);
							return;
						}
						
						
						String sql =  "SELECT * "
									+ "FROM " + dinamicFrom
									+ "WHERE " + mapQuery.get(one_expr)  
									+ "  " 
									+ one_theta 
									+ " '"
									+ (needPerCent1 ? "%" : "") 
									+ one_expr_const 
									+ (needPerCent1 ? "%" : "")
									+ "' "
									
									+ "AND "
									
									+ mapQuery.get(two_expr)  
									+ "  " 
									+ two_theta 
									+ " '"
									+ (needPerCent2 ? "%" : "") 
									+ two_expr_const 
									+ (needPerCent2 ? "%" : "")
									+ "' "
									+ "ORDER BY id ASC;"
									;
						

						
						Statement statement = connection.createStatement();
						ResultSet result = statement.executeQuery(sql);
						

						String outputQuery = "";
						
						while(result.next()) {
							int id = result.getInt("id");
							String nomeStampa = result.getString("nome");
							String cognommeStampa = result.getString("cognome");
							

							outputQuery += String.valueOf(id);
							outputQuery += "  -  ";
							outputQuery += nomeStampa;
							outputQuery += "  -  ";
							outputQuery += cognommeStampa;


							String firstMappedString  = mapQuery.get(one_expr);
							String secondMappedString = mapQuery.get(two_expr);
							
							
							if		(  		
									(firstMappedString.equals("indirizzo") )
									||
									(secondMappedString.equals("indirizzo") )
									)
									{
										String jobStampa =   result.getString("indirizzo");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							
							if	(  		
									(firstMappedString.equals("inizio_disp") ) 
									||
									(secondMappedString.equals("inizio_disp") ) 
									)
									{
										String jobStampa =   result.getString("inizio_disp");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("fine_disp") ) 
									||
									(secondMappedString.equals("fine_disp") )
									)
									{
										String jobStampa =   result.getString("fine_disp");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("disponibilita") ) 
									||
									(secondMappedString.equals("disponibilita") ) 
									)
									{
										String jobStampa =   result.getString("disponibilita");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("nazionalita") )
									||
									(secondMappedString.equals("nazionalita") ) 
									)
									{
										String jobStampa =   result.getString("nazionalita");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("lingue") )
									||
									(secondMappedString.equals("lingue") )
									)
									{
										String jobStampa =   result.getString("lingue");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("patent") ) 
									||
									(secondMappedString.equals("patent") ) 
									)
									{
										String jobStampa =   result.getString("patent");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("automunito") ) 
									||
									(secondMappedString.equals("automunito") )
									)
									{
										String jobStampa =   result.getString("automunito");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("mansioni_svolte") ) 
									||
									(secondMappedString.equals("mansioni_svolte") )
									)
									{
										String jobStampa =   result.getString("mansioni_svolte");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							if	(  		
									(firstMappedString.equals("nome_lavoro") ) 
									||
									(secondMappedString.equals("nome_lavoro") ) 
									)
									{
										String jobStampa =   result.getString("nome_lavoro");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							outputQuery += "\n\n";
						}
						
						
						TAresultArea.setText (outputQuery);
						
						connection.close();
						System.out.println("DB terminato03");
					
					}catch(SQLException e){
						System.out.println("Ricerca nel DB fallita\n");

						
						exportedTitle = "Feedback ricerca";
						exportedText = "Ricerca \"AND\" nel DB fallita\n";
						exportedText += "\n\n";
						
						callAlertBox(exportedTitle, exportedText);
						return;
						
						//e.printStackTrace();
					}
					
					break;
					
				//unione tra 2 risultati	
				case "OR":
					
					try {
						Connection connection = DriverManager.getConnection(jdbcURL, username, password);

						String dinamicFrom = "lavoratori ";
						
						String one_expr = comboQueryONE.getValue();
						String one_theta = CBoperationONE.getValue();
						String one_expr_const = TFone.getText();
						
						String two_expr = comboQueryTWO.getValue();
						String two_theta = CBoperationTWO.getValue();
						String two_expr_const = TFtwo.getText();
						
						boolean needPerCent1 = false;
						boolean needPerCent2 = false;
						
						
						
						
						if( (one_expr == "Zona disponibilità" ) || 
							(one_expr == "Lingue parlate" ) || 
							(one_expr == "Mansioni" ) || 
							(one_expr == "Lavoro" ) )
							{
								needPerCent1 = true;
								
								if( ( (one_expr == "Mansioni" ) || (one_expr == "Lavoro" ) ) && dinamicFrom.equals("lavoratori ") )
								{
									dinamicFrom += "LEFT JOIN lavori ON id = id_lavoratore ";	
								}
								
								if(one_theta == "<>")
									one_theta = "NOT SIMILAR TO";
								else 
								{
									//Converno null  (in realtà qualunque cosa) in" SIMILAR TO"    :)
									one_theta = "SIMILAR TO";
								}
							}
							
							
							if((one_expr_const.isEmpty()) )
							{	
								exportedTitle = "Errore inserimento valori";
								exportedText = "Il campo Principale è vuoto";
								exportedText += "\n\n";
								
								callAlertBox(exportedTitle, exportedText);
								return;
							}
							
							if( (two_expr == "Zona disponibilità" ) || 
								(two_expr == "Lingue parlate" ) || 
								(two_expr == "Mansioni" ) || 
								(two_expr == "Lavoro" ) )
							{
								needPerCent2 = true;
								
								if( ( (two_expr == "Mansioni" ) || (two_expr == "Lavoro" ) ) && dinamicFrom.equals("lavoratori ") )
								{
									dinamicFrom += "LEFT JOIN lavori ON id = id_lavoratore ";
								}
								
								if(two_theta == "<>")
									two_theta = "NOT SIMILAR TO";
								else 
								{
									two_theta = "SIMILAR TO";
								}
							}
							
							
							if((two_expr_const.isEmpty()) )
							{	
								exportedTitle = "Errore inserimento valori";
								exportedText = "Il campo secondario è vuoto";
								exportedText += "\n\n";
								
								callAlertBox(exportedTitle, exportedText);
								return;
							}
						
						
						
						String sql =  "SELECT * "
									+ "FROM " + dinamicFrom
									+ "WHERE " 
									
									+ mapQuery.get(one_expr)  
									+ "  " 
									+ one_theta 
									+ " '"
									+ (needPerCent1 ? "%" : "") 
									+ one_expr_const 
									+ (needPerCent1 ? "%" : "")
									+ "' "
									
									+ "OR "
									
									+ mapQuery.get(two_expr)  
									+ "  " 
									+ two_theta 
									+ " '"
									+ (needPerCent2 ? "%" : "") 
									+ two_expr_const 
									+ (needPerCent2 ? "%" : "")
									+ "' "
									+ "ORDER BY id ASC;"
									;
						
						
						Statement statement = connection.createStatement();
						ResultSet result = statement.executeQuery(sql);
						

						String outputQuery = "";
						
						while(result.next()) {
							int id = result.getInt("id");
							String nomeStampa = result.getString("nome");
							String cognommeStampa = result.getString("cognome");
							

							outputQuery += String.valueOf(id);
							outputQuery += "  -  ";
							outputQuery += nomeStampa;
							outputQuery += "  -  ";
							outputQuery += cognommeStampa;

							
							String firstMappedString  = mapQuery.get(one_expr);
							String secondMappedString = mapQuery.get(two_expr);
							
							
							System.out.println("\ntest1" + firstMappedString);
							System.out.println("\ntest2" + secondMappedString);
							
							
							if	(  		
									(firstMappedString.equals("indirizzo") )
									||
									(secondMappedString.equals("indirizzo") )
									)
									{
										String jobStampa =   result.getString("indirizzo");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							
							if	(  		
									(firstMappedString.equals("inizio_disp") ) 
									||
									(secondMappedString.equals("inizio_disp") ) 
									)
									{
										String jobStampa =   result.getString("inizio_disp");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("fine_disp") ) 
									||
									(secondMappedString.equals("fine_disp") )
									)
									{
										String jobStampa =   result.getString("fine_disp");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("disponibilita") ) 
									||
									(secondMappedString.equals("disponibilita") ) 
									)
									{
										String jobStampa =   result.getString("disponibilita");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("nazionalita") )
									||
									(secondMappedString.equals("nazionalita") ) 
									)
									{
										String jobStampa =   result.getString("nazionalita");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("lingue") )
									||
									(secondMappedString.equals("lingue") )
									)
									{
										String jobStampa =   result.getString("lingue");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("patent") ) 
									||
									(secondMappedString.equals("patent") ) 
									)
									{
										String jobStampa =   result.getString("patent");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("automunito") ) 
									||
									(secondMappedString.equals("automunito") )
									)
									{
										String jobStampa =   result.getString("automunito");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							if	(  		
									(firstMappedString.equals("mansioni_svolte") ) 
									||
									(secondMappedString.equals("mansioni_svolte") )
									)
									{
										String jobStampa =   result.getString("mansioni_svolte");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							if	(  		
									(firstMappedString.equals("nome_lavoro") ) 
									||
									(secondMappedString.equals("nome_lavoro") ) 
									)
									{
										String jobStampa =   result.getString("nome_lavoro");
										outputQuery += "  -  ";
										outputQuery += jobStampa;
									}
							
							outputQuery += "\n\n";
							//System.out.printf("%d - %s - %s \n", id, nomeStampa, cognommeStampa );
						}
						
						
						TAresultArea.setText (outputQuery);
						
						connection.close();
						System.out.println("DB terminato03");
					
					}catch(SQLException e){
						System.out.println("Ricerca nel DB fallita\n");

						
						exportedTitle = "Feedback ricerca";
						exportedText = "Ricerca \"OR\" nel DB fallita\n";
						exportedText += "\n\n";
						
						callAlertBox(exportedTitle, exportedText);
						return;
						
						//e.printStackTrace();
					}
					
					break;
			
				//dead code
				default:
					System.out.println("irraggiungibile in teoria");
					break;
			
			//fine		
			//switch( OperatorSelected )
			}
			
		//fine 	
		//else -->	if(CBoperatoriLogici.getValue() == null) {
		}
		
	//fine
	//executeQuery()
	}
	
	
	
	
	
	
	//================================================================================================
	//===================DISPLAY==ALL==WORKER=========================================================
	//================================================================================================
	public void displayAllWorker(ActionEvent event) throws IOException {
		String jdbcURL = "jdbc:postgresql://localhost:5432/test";
		String username = "postgres";
		String password = "POCHImabuoni123";
		
		
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			System.out.println("Connesso a server \"lavoratori\"!");
			
			
			String sql =  "SELECT * "
						+ "FROM lavoratori "
						+ "ORDER BY id ASC;";
			
			
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			String outputQuery = "";
	
			
			while(result.next()) {

				outputQuery += "   id:";
				outputQuery += result.getString("id");
				
				outputQuery += " \nnome: ";
				outputQuery += result.getString("nome");
				outputQuery += " \t| cognome: ";
				outputQuery += result.getString("cognome");
				outputQuery += "  | luogo nascita: ";
				outputQuery += result.getString("luogo_nascita");
				outputQuery += "  | nazionalità: ";
				outputQuery += result.getString("nazionalita");
				
				outputQuery += "  \nindirizzo: ";
				outputQuery += result.getString("indirizzo");
				outputQuery += "  | n° telefono: ";
				outputQuery += result.getString("n_telefono");
				outputQuery += "  | email: ";
				outputQuery += result.getString("email");
				outputQuery += "  | esperienze: ";
				outputQuery += result.getString("esperienze");
				
				outputQuery += "  \nlingue:  ";
				outputQuery += result.getString("lingue");
				outputQuery += "  | patente: ";
				outputQuery += result.getString("patent");
				outputQuery += "  | automunito: ";
				outputQuery += result.getString("automunito");
				
				outputQuery += "  \ninizio disponibilità: ";
				outputQuery += result.getString("inizio_disp");
				outputQuery += "  | fine disponibilità: ";
				outputQuery += result.getString("fine_disp");
				outputQuery += "  | zone disponibilità: ";
				outputQuery += result.getString("disponibilita");
				
				outputQuery += "  \nnome emergenze: ";
				outputQuery += result.getString("x_emergenze_nome");
				outputQuery += "  | cognome emergenze: ";
				outputQuery += result.getString("x_emergenze_cognome");
				outputQuery += "  | email emergenze ";
				outputQuery += result.getString("x_emergenze_email");
				outputQuery += "  | n°cell emergenze: ";
				outputQuery += result.getString("x_emergenze_cell");
			
				outputQuery += "\n\n\n";
				
			//fine	
			//while(result.next())
			}
			
			
			TAresultArea.setText (outputQuery);
			
			connection.close();
			System.out.println("DB chiusOOOOOo");
		
			
		}catch(SQLException e){
			System.out.println("Errore durante la connessione al server");
			e.printStackTrace();
		}
		
	//fine
	//displayAllWorker()
	}
	
	
}
