package branchManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import client.Request;
import guiNew.Navigation_SidePanelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.BusinessAccount;

public class EditBusinessAccountController implements Initializable {
	public static Boolean isEdit = false;
	@FXML
	private Button btnUpdateAccount;

	@FXML
	private Label labelTitle;

	@FXML
	private TextField textFieldUsernamePersonal;

	@FXML
	private Label lblrequiredUsername;

	@FXML
	private Label lblrequiredIDPersonal;

	@FXML
	private TextField textFieldIDPersonal;

	@FXML
	private Label lblrequiredDebt;

	@FXML
	private TextField textFieldDebt;

	@FXML
	private TextField textFieldBusinessName;

	@FXML
	private Label lblrequiredBusinessName;

	@FXML
	private Label lblrequiredIDBusiness;

	@FXML
	private TextField textFieldIDBusiness;

	@FXML
	private Label lblrequiredMonthBlling;

	@FXML
	private TextField textFieldMonthBlling;

	@FXML
	private JFXComboBox<String> comboBoxStatus;

	@FXML
	private Button btnbackOpenBusinessAccount;

	@FXML
	private HBox Nav;

	@FXML
	private Label lableHello;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button btnLogout;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;
    @FXML
    private AnchorPane componnentDebt;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
//		labelTitle.setText("Edit Business Account");
		
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getUserName());
		comboBoxStatus.getItems().setAll("Active", "Frozen", "Blocked");
		componnentDebt.setVisible(isEdit);

		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/guiNew/Navigation_SidePanel.fxml"));
			drawer.setSidePane(anchorPane);
		} catch (IOException e) {
			Logger.getLogger(Navigation_SidePanelController.class.getName()).log(Level.SEVERE, null, e);
		}

		// transition animation of hamburger icon
		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(myHamburger);
		drawer.setVisible(false);
		transition.setRate(-1);

		// click event - mouse click
		myHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawer.isOpened()) {
				drawer.setVisible(false);
				drawer.close(); // this will close slide pane
			} else {
				drawer.open(); // this will open slide pane
				drawer.setVisible(true);
			}
		});
	}

	@FXML
	void homelogout(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/guiNew/HomePage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Home");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void backEditInfo(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/EditPersonalInfo.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Edit Personal Info");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean flag, validField;

	@FXML
	void updateAccount(ActionEvent event) {
		flag = true;
		checkTextFiled(textFieldDebt, lblrequiredDebt);
		checkTextFiled(textFieldBusinessName, lblrequiredBusinessName);
		checkTextFiled(textFieldMonthBlling, lblrequiredMonthBlling);

		if (flag) {
			validField = true;
			checkValidFields(textFieldDebt, lblrequiredDebt);
			checkValidFields(textFieldMonthBlling, lblrequiredMonthBlling);
			
			if (validField) {

				String personalUsename = textFieldUsernamePersonal.getText();
				int personalID = Integer.parseInt(textFieldIDPersonal.getText());
				String status = comboBoxStatus.getValue() == null ? "Active" : comboBoxStatus.getValue();
				String businessName = textFieldBusinessName.getText();
				int EmployeeID = Integer.parseInt(textFieldIDBusiness.getText());
				int monthBillingCeiling = Integer.parseInt(textFieldMonthBlling.getText());
				BusinessAccount businessAccount = new BusinessAccount(personalID, personalUsename, null, null, null, null, null,
						null, status, true, BranchManagerController.branchManager.getUserID(),
						BranchManagerController.branchManager.getArea(), 0, null, monthBillingCeiling, false,
						businessName, 0);
				sentToJson(businessAccount);
				response();
			}
		}
	}

	void sentToJson(BusinessAccount businessAccount) {
		Request request = new Request();
		request.setPath("/accounts/BusinessAccount");
		request.setMethod("Post");
		request.setBody(businessAccount);
		Gson gson = new Gson();
		JsonElement jsonUser = gson.toJsonTree(request);

		String jsonFile = gson.toJson(jsonUser);
//    	System.out.println("jsonFile : "+jsonFile);
		// client.accept(jsonFile); // in here will be DB ask for restaurant id
	}

	void response() {
//		Gson gson = new Gson();
//		Response response = gson.fromJson(ChatClient.serverAns, Response.class);
//		if (response.getCode() != 200 && response.getCode() != 201) 
//			lableErrorMag.setText(response.getDescription());// error massage
//		
//		System.out.println("-->>"+response.getDescription()); // Description from server
	}

	void checkTextFiled(TextField textField, Label lblrequired) {
		if (textField.getText().equals("")) {
			lblrequired.setText("required");
			flag = false;
		} else {
			lblrequired.setText("");
		}
	}

	void checkValidFields(TextField textField, Label lblrequired) {
		try {
			Integer.parseInt(textField.getText());
		} catch (NumberFormatException e) {
			lblrequired.setText("Enter only numbers");
			validField = false;
		}
	}

}
