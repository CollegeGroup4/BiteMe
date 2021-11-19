package gui;

import java.io.IOException;


import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.ChatIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public  class MainScreenController   {
	private StudentFormController sfc;	
	private static int itemIndex = 3;
	
    @FXML
    private Button btnExit;

    @FXML
    private TextField idtxt;

    @FXML
    void EditOrder(ActionEvent event) {

		String[] id = new String[2];
		FXMLLoader loader = new FXMLLoader();
		id[0] = new String("EDIT");
		id[1] = new String(idtxt.getText());
		if (id[1].trim().isEmpty()) {
			System.out.println("You must enter an id number");
		} else {
			ClientUI.chat.accept(id);

			if (ChatClient.serverAns[0].equals("Error")) {
				System.out.println("Student ID Not Found");

			} else {
				System.out.println("Student ID Found");
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				Pane root = loader.load(getClass().getResource("/gui/StudentForm.fxml").openStream());
				StudentFormController studentFormController = loader.getController();
				studentFormController.loadStudent(ChatClient.serverAns);

				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
				primaryStage.setTitle("Student Managment Tool");

				primaryStage.setScene(scene);
				primaryStage.show();
			}
		}
    }

    @FXML
    void ShowAllOrders(ActionEvent event) {

    }

    @FXML
    void getExitBtn(ActionEvent event) {
    	System.out.println("exit Academic Tool");
    	System.exit(0);
    }
	
	private String getID() {
		return idtxt.getText();
	}
	
	public void Send(ActionEvent event) throws Exception {
		String id;
		FXMLLoader loader = new FXMLLoader();
		
		id=getID();
		if(id.trim().isEmpty())
		{

			System.out.println("You must enter an id number");	
		}
		else
		{
			ClientUI.chat.accept(id);
			
		
			if(ChatClient.s1.getId().equals("Error"))
			{
				System.out.println("Student ID Not Found");
				
			}
			else {
				System.out.println("Student ID Found");
				((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
				Stage primaryStage = new Stage();
				Pane root = loader.load(getClass().getResource("/gui/StudentForm.fxml").openStream());
				StudentFormController studentFormController = loader.getController();		
				studentFormController.loadStudent(ChatClient.s1);
			
				Scene scene = new Scene(root);			
				scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
				primaryStage.setTitle("Student Managment Tool");
	
				primaryStage.setScene(scene);		
				primaryStage.show();
			}
		}
	}

	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/AcademicFrame.fxml"));
				
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/AcademicFrame.css").toExternalForm());
		primaryStage.setTitle("Academic Managment Tool");
		primaryStage.setScene(scene);
		
		primaryStage.show();	 	   
	}
	
	
//	public void loadStudent(Student s1) {
//		this.sfc.loadStudent(s1);
//	}	
	
	public  void display(String message) {
		System.out.println("message");
		
	}
	
}
