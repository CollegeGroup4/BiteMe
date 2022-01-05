package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import logic.Employer;

public class EmployeeRegisterController implements Initializable {
	/**
	 * This class made for the Employer Register process
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
	private Label dishname;

	@FXML
	private Label price;

	@FXML
	private Button createmployeebtn;

	@FXML
	private TextField BusinessNmaeText;

	@FXML
	private Button back;

	@FXML
	private TextField NameText;

	@FXML
	private TextField UserText;

	@FXML
	private TextField BranchName;

	@FXML
	private HBox Nav;

	@FXML
	private Button LogOut;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	private HRFunction HRF = new HRFunction();

	/**
	 * This method send back to H.R main screen
	 * 
	 * @param event
	 */
	@FXML
	void Back(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/supplier/H.R.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("H.R");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to save new employer in DB
	 * 
	 * @param employer
	 */

	void sendtoserver(Employer employer) {

		System.out.println(employer.toString());

		Request request = new Request();
		request.setPath("/hr");
		request.setMethod("POST");
		Gson gson = new Gson();

		request.setBody(gson.toJson(employer));
		ClientUI.chat.accept(gson.toJson(request));

		if (ChatClient.serverAns.getCode() != 200 || ChatClient.serverAns.getCode() != 201) {

			// Warning
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
	 * Method to set Employer values and send to DB
	 * 
	 * @param event
	 */
	@FXML
	void createmployee(ActionEvent event) {
		Employer employee = new Employer(null, false, null, null, 0);

		employee.setApproved(false);
		employee.setBranchManagerID(HRPageController.Hmanger.getBranch_manager_ID());
		employee.setBusinessName(BusinessNmaeText.getText());
		employee.setHrName(HRPageController.Hmanger.getFirstName());
		employee.setHrUserName(HRPageController.Hmanger.getUserName());

		System.out.println(employee.getBusinessName());
		// *** and send to DB ***///

		sendtoserver(employee);

	}
	/**
	 * This Method made to initialize all the buttons and text requirements
	 *@param location resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HRF.initializeNavigation_SidePanel(myHamburger, drawer);
		UserText.setText(HRPageController.Hmanger.getUserName());
		NameText.setText(HRPageController.Hmanger.getFirstName());
		BranchName.setText(String.valueOf(HRPageController.Hmanger.getBranch_manager_ID()));
	}

}
