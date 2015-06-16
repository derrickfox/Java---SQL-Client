/*
 * Derrick Fox
 * CS 214 - Advanced Java
 * Project 11 - Table View
 * April 29, 2015
 * Java 1.8 JavaFX 2.2
 */

package application;

import java.sql.*;
import java.util.Date;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


 public class SQLClient extends Application {
	 public static void main(String[] args) {
		    launch(args);
		  }
 
	 private Connection connection;
	 private Statement statement;


	 // Text area to enter SQL commands 
	 private TextArea tasqlCommand = new TextArea();

	 private TextArea taSQLResult = new TextArea();

	 private TextField tfUsername = new TextField();
	 private PasswordField pfPassword = new PasswordField();
	 private ComboBox<String> cboURL = new ComboBox<>();
	 private ComboBox<String> cboDriver = new ComboBox<>();
	
	 private Button btExecuteSQL = new Button("Execute SQL Command");
	 private Button btClearSQLCommand = new Button("Clear");
	 private Button btConnectDB = new Button("Connect to Database");
	 private Button btClearSQLResult = new Button("Clear Result");
	 private Label lblConnectionStatus
	 = new Label("No connection now");
	 
	 TableView<Student> table;

	 // Override the start method in the Application class
	 @Override  
	 public void start(Stage primaryStage) {
		 TableColumn<Student, String> ssnColumn = new TableColumn<>("ssn");
		 ssnColumn.setMinWidth(200);
		 ssnColumn.setCellValueFactory(new PropertyValueFactory<>("ssn"));		 
		 
		 TableColumn<Student, String> firstNameColumn = new TableColumn<>("First Name");
		 firstNameColumn.setMinWidth(200);
		 firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		 
		 TableColumn<Student, String> miColumn = new TableColumn<>("mi");
		 miColumn.setMinWidth(100);
		 miColumn.setCellValueFactory(new PropertyValueFactory<>("mi"));

		 TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last Name");
		 lastNameColumn.setMinWidth(200);
		 lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

		 TableColumn<Student, String> phoneColumn = new TableColumn<>("Phone");
		 phoneColumn.setMinWidth(200);
		 phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

		 TableColumn<Student, Date> birthDayColumn = new TableColumn<>("Birthday");
		 birthDayColumn.setMinWidth(200);
		 birthDayColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

		 TableColumn<Student, String> streetColumn = new TableColumn<>("Street");
		 streetColumn.setMinWidth(200);
		 streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));

		 TableColumn<Student, String> zipcodeColumn = new TableColumn<>("Zipcode");
		 zipcodeColumn.setMinWidth(200);
		 zipcodeColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
		 
		 TableColumn<Student, String> departId = new TableColumn<>("Department ID");
		 departId.setMinWidth(200);
		 departId.setCellValueFactory(new PropertyValueFactory<>("deptId"));

		 table = new TableView<>();
		 table.setItems(getStudents());
		 table.getColumns().addAll(ssnColumn, firstNameColumn, miColumn, lastNameColumn, phoneColumn, birthDayColumn, streetColumn, zipcodeColumn, departId);
		 
		 cboURL.getItems().addAll(FXCollections.observableArrayList(
				 "jdbc:mysql://localhost/javabook",
				 "jdbc:mysql://liang.armstrong.edu/javabook",
				 "jdbc:odbc:exampleMDBDataSource",
				 "jdbc:oracle:thin:@liang.armstrong.edu:1521:orcl"));
		 		 cboURL.getSelectionModel().selectFirst();

		 cboDriver.getItems().addAll(FXCollections.observableArrayList(
				 "com.mysql.jdbc.Driver", "sun.jdbc.odbc.dbcOdbcDriver",
				 "oracle.jdbc.driver.OracleDriver"));
		 cboDriver.getSelectionModel().selectFirst();

		 // Create UI for connecting to the database
		 GridPane gridPane = new GridPane();
		
		 gridPane.add(cboURL, 1, 0);
		 gridPane.add(cboDriver, 1, 1);
		 gridPane.add(tfUsername, 1, 2);
		 gridPane.add(pfPassword, 1, 3);
		 gridPane.add(new Label("JDBC Driver"), 0, 0);
		 gridPane.add(new Label("Database URL"),0, 1);
		 gridPane.add(new Label("Username"), 0, 2);
		 gridPane.add(new Label("Password"), 0, 3);
		
		 HBox hBoxConnection = new HBox();
		 hBoxConnection.getChildren().addAll(
		 lblConnectionStatus, btConnectDB);
		 hBoxConnection.setAlignment(Pos.CENTER_RIGHT);
		
		 VBox vBoxConnection = new VBox(5);
		 vBoxConnection.getChildren().addAll(  new Label("Enter Database Information"),
		 gridPane, hBoxConnection);
		
		 gridPane.setStyle("-fx-border-color: black;");
		
		 HBox hBoxSQLCommand = new HBox(5);
		 hBoxSQLCommand.getChildren().addAll(
		 btClearSQLCommand, btExecuteSQL);
		 hBoxSQLCommand.setAlignment(Pos.CENTER_RIGHT);
		
		 BorderPane borderPaneSqlCommand = new BorderPane();
		 borderPaneSqlCommand.setTop(  new Label("Enter an SQL Command"));
		 borderPaneSqlCommand.setCenter(
		 new ScrollPane(tasqlCommand));
		 borderPaneSqlCommand.setBottom(
		 hBoxSQLCommand);
		
		 HBox hBoxConnectionCommand = new HBox(10);
		 hBoxConnectionCommand.getChildren().addAll(
		      vBoxConnection, borderPaneSqlCommand);
		
		 BorderPane borderPaneExecutionResult = new BorderPane();
		 borderPaneExecutionResult.setTop(  new Label("SQL Execution Result"));
		 //borderPaneExecutionResult.setCenter(taSQLResult);
		 borderPaneExecutionResult.setCenter(table);
		 borderPaneExecutionResult.setBottom(btClearSQLResult);
		
		 BorderPane borderPane = new BorderPane();
		 borderPane.setTop(hBoxConnectionCommand);
		 borderPane.setCenter(borderPaneExecutionResult);
		
		// Create a scene and place it in the stage
		 Scene scene = new Scene(borderPane, 670, 400);
		 primaryStage.setTitle("SQLClient"); // Set the stage title
		 primaryStage.setScene(scene); // Place the scene in the stage
		 primaryStage.show(); // Display the stage
		
		 btConnectDB.setOnAction(e -> connectToDB());
		 //btExecuteSQL.setOnAction(e -> executeSQL());
		 btExecuteSQL.setOnAction(e -> getStudents());
		 btClearSQLCommand.setOnAction(e -> tasqlCommand.setText(null));
		 btClearSQLResult.setOnAction(e -> taSQLResult.setText(null));
		 }

	 public ObservableList<Student> getStudents(){
		 ObservableList<Student> students = FXCollections.observableArrayList();
		 /*
		 students.add(new Student("1", "TestOne", "M", "OneTester", "2022022222", new Date(0), "1 Street Test", "92828", "TestDepot"));        
		 students.add(new Student("2", "TestTwo", "D", "TwoTester", "656465654", new Date(0), "2 Street Test", "876878", "TestDepot"));    
		 students.add(new Student("Any", "Help", "M", "Helper", "2983", new Date(2), "Bla street", "88220", "Dam Depot"));
		 students.add(new Student("3", "TestThree", "B", "ThreeTester", "754864767", new Date(0), "3 Street Test", "54355", "TestDepot")); 
		 */
		try{
			String ssn = "";
			String firstName = "";
			String MI = "";
			String lastName = "";
			String phone = "";
			Date birthDate = null;
			String street = "";
			String zipCode = "";
			String deptId = "";

		 String sql = tasqlCommand.getText().trim();
	     PreparedStatement myStmt = connection.prepareStatement(sql);   
	     ResultSet rs = myStmt.executeQuery(sql);
	     while(rs.next()){
	    	 
	        	//Retrieve by column name
	           ssn  = rs.getString(1);
	           firstName = rs.getString(2);
	           MI = rs.getString(3);
	           lastName = rs.getString(4);
	           phone = rs.getString(5);
	           birthDate = rs.getDate(6);
	           street = rs.getString(7);
	           zipCode = rs.getString(8);
	           deptId = rs.getString(9);
	           System.out.println(rs.getString(4));  
				
	    	   students.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getString(8), rs.getString(9)));
	        }
	     //students.add(new Student(ssn, firstName, MI, lastName, phone, birthDate, street, zipCode, deptId));
 		 //return students;
	     
		        
		 

		}catch(Exception e){}
		table.setItems(students);

		return students;
	 }
	 
	 /** Connect to DB */
	 private void connectToDB() {
	
		 // Get database information from the user input
		 String driver = cboDriver.getSelectionModel().getSelectedItem();
		 String url = cboURL.getSelectionModel().getSelectedItem();
		 String username = tfUsername.getText().trim();
		 String password = pfPassword.getText().trim();
		
		 // Connection to the database
		 try {
		 Class.forName(driver);
		 connection = DriverManager.getConnection(
		 url, username, password);
		 lblConnectionStatus.setText("Connected to " + url);
		 }
		 catch (java.lang.Exception ex) {
		 ex.printStackTrace();
		 }
		 }
		
		/* cSQL commands */
		private void executeSQL() {
			if (connection == null) {
				taSQLResult.setText("Please connect to a database first");
				return;
			} else {
				String sqlCommands = tasqlCommand.getText().trim();
				String[] commands = sqlCommands.replace('\n', ' ').split(";");
		
			for (String aCommand: commands) {
				if (aCommand.trim().toUpperCase().startsWith("SELECT")) {
					processSQLSelect(aCommand);
				}  else {
					processSQLNonSelect(aCommand);
				}
			}
		 }
		}
		
		 /** Execute SQL SELECT commands */
		 private void processSQLSelect(String sqlCommand) {
			 try {
			 // Get a new statement for the current connection
			 statement = connection.createStatement();
			
			// Execute a SELECT SQL command
			ResultSet resultSet = statement.executeQuery(sqlCommand);
			
			// Find the number of columns in the result set
			int columnCount = resultSet.getMetaData().getColumnCount();
			 String row = "";
			
			 // Display column names
			 for (int i = 1; i <= columnCount; i++) {
			 row += resultSet.getMetaData().getColumnName(i) + "\t";
			 }
			
			taSQLResult.appendText(row + '\n');
			
			 while (resultSet.next()) {
			 // Reset row to empty
			
			 row = "";
			
			 for (int i = 1; i <= columnCount; i++) {
			 // A non-String column is converted to a string
			 row += resultSet.getString(i) + "\t";
			 }
			
			 taSQLResult.appendText(row + '\n');
			}
			 }
			 catch (SQLException ex) {
			 taSQLResult.setText(ex.toString());
			 }
	}
	
	/* cSQL DDL, and modification commands */
	 private void processSQLNonSelect(String sqlCommand) {
		 try {
		 // Get a new statement for the current connection
		 statement = connection.createStatement();
		
		 // Execute a non-SELECT SQL command
		 statement.executeUpdate(sqlCommand);
		
		 taSQLResult.setText("SQL command executed");
		
		} 
		catch (SQLException ex) {
		 taSQLResult.setText(ex.toString());
		 }
		 }
	 }