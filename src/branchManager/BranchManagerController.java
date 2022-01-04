package branchManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXButton;

import Server.EchoServer;
import Server.Response;
import client.ChatClient;
import client.ClientUI;
import client.CustomerPageController;
import common.Request;
import guiNew.Navigation_SidePanelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Account;

public class BranchManagerController implements Initializable {
	public static Account branchManager;
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	@FXML
	private HBox Nav;

	@FXML
	private Button btnOrderFood;

	@FXML
	private Button btnViewReports;

	@FXML
	private Button btnOpenAccount;

	@FXML
	private Button btnEditPersonalInfo;

	@FXML
	private Button btnCreateReports;

	@FXML
	private Button btnRegisterationApprovalSupplier;

	@FXML
	private HBox Nav1;

	@FXML
	private Hyperlink linkApprovals;
	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button btnLogout;

	@FXML
	private Label lableHello;
	
/**
 * Get the Approvals of the BM
 * @param event
 */
	@FXML
	void Approvals(ActionEvent event) {
		branchManagerFunctions.reload(event, "/branchManager/ApprovalsPage.fxml", "Branch manager - Approvals Page");
	}

	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	@FXML
	void editPersonalInfo(ActionEvent event) {
		branchManagerFunctions.reload(event, "/branchManager/EditPersonalInfo.fxml", "Branch manager - Edit Personal Info");
	}

	@FXML
	void createReports(ActionEvent event) {

	}

	@FXML
	void openAccount(ActionEvent event) {
		branchManagerFunctions.reload(event, "/branchManager/OpenAccountPage.fxml", "Branch manager - open account");
	}

	@FXML
	void orderFood(ActionEvent event) {
		System.out.println("Order food - Branch manager");
		CustomerPageController.client = branchManager;
		branchManagerFunctions.reload(event, "/client/ChooseRestaurant.fxml", "Branch manager- Order food");
	}

	@FXML
	void RegisterationRestaurant(ActionEvent event) {
		System.out.println("registeration & Approval Supplier - Branch manager");
		RegisterationApprovalSupplierController.isEdit = false;
		branchManagerFunctions.reload(event, "/branchManager/RegisterationApprovalSupplier.fxml", "Branch manager - registeration & Approval Supplier");
	}

	@FXML
	void viewReports(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getFirstName());

//		String[] listButtons;
//		 VBox vBoxManu;
//		Navigation_SidePanelController sidePanelController;
//
//		System.out.println("lala1");
//		listButtons = new String[7];
//		listButtons[0] = "Orde Food";
//		listButtons[1] = "View reports";
//		listButtons[2] = "Creat new report";
//		listButtons[3] = "Open new account";
//		listButtons[4] = "Register & Approval  supplier";
//		listButtons[5] = "Edit personal info";
//		listButtons[6] = "Exit";
//		JFXButton button;
//		for (int i = 0; i < listButtons.length; i++) {
//			button = new JFXButton(listButtons[i]);
//			vBoxManu.getChildren().add(button);
//		}
//		sidePanelController.setvBoxManu(vBoxManu);
//		System.out.println("lala2");
	}
}
