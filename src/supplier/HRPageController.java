package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Account;

public class HRPageController  {

	/**
	 * This class made for H.R main page
	 *
	 * @author Or Biton
	 * @author Einan Choen
	 * @author Tal Yehoshua
	 * @author Moshe Pretze;
	 * @author Tal-chen Ben-Eliyahu
	 * @version January 2022
	 * 
	 */
	
	
	@FXML
	private HBox Nav;

	@FXML
	private Button LogOut;

	@FXML
	private Button approve;

	@FXML
	private Button employeebutton;
	private HRFunction HRF = new HRFunction();
	public static Account Hmanger;

	
	/**
	 * Method to move in approve businesses screen
	 * @param event
	 */
	@FXML
	void Approveclick(ActionEvent event) {
		System.out.println("Approve");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/BusinessApprove.fxml").openStream());
			

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open eBusinessApprove page");
			e.printStackTrace();

		}
	}
	/**
	 * This Method made to logout from system
	 * 
	 * @param event
	 */
	@FXML
	void LogOut(ActionEvent event) {
		HRF.logout(event);
	}
	/**
	 * Method to move in employer registration screen
	 * @param event
	 */
	@FXML
	void employeeclick(ActionEvent event) {
		System.out.println("EmployeeRegister");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/EmployeeRegister.fxml").openStream());
	

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open EmployeeRegister page");
			e.printStackTrace();

		}
	}

	
}
