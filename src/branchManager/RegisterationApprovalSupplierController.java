package branchManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Server.EchoServer;
import Server.Response;
import client.ChatClient;
import client.ClientUI;
import common.Request;
import guiNew.Navigation_SidePanelController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.Account;
import logic.BusinessAccount;
import logic.PrivateAccount;
import logic.Restaurant;

public class RegisterationApprovalSupplierController implements Initializable {
	public static Boolean isEdit = false;
	public static Restaurant restaurant;
	public static Account supplierAccount;

	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	private String photo;

	@FXML
	private Label labelTitle;

	@FXML
	private Label lableErrorMag;

	@FXML
	private Button btnUpdate;

	@FXML
	private AnchorPane anchorPaneRestaurantInfo;

	@FXML
	private TextField textFieldSupplierUserame;

	@FXML
	private Label lblrequiredSupplierUsername;

	@FXML
	private Label lblrequiredSupplierID;

	@FXML
	private TextField textFieldsupplierID;

	@FXML
	private Label lblrequiredRestName;

	@FXML
	private TextField textFieldRestaurantName;

	@FXML
	private Label lblrequiredRestType;

	@FXML
	private TextField textFieldRestaurantType;

	@FXML
	private Label lblrequiredRestAdderss;

	@FXML
	private TextField textFieldRestaurantAddress;

	@FXML
	private Button btnSelectFile;

	@FXML
	private Label lblrequiredModeratorUsername;

	@FXML
	private TextField textFieldModeratorUsername;

	@FXML
	private Label lblrequiredModeratorID;

	@FXML
	private TextField textFieldModeratorID;

	@FXML
	private Button btnCceateRestaurant;
	@FXML
	private AnchorPane chooseAccount;
	@FXML
	private ListView<String> listView;
	@FXML
	private TableView<Account> tableViewUsers;
	@FXML
	private TableColumn<Account, String> tableColRole;
	@FXML
	private TableColumn<Account, String> tableColID;
	@FXML
	private TableColumn<Account, String> tableColFirstname;
	@FXML
	private TableColumn<Account, String> tableColLastName;
	@FXML
	private TableColumn<Account, String> tableColArea;
	@FXML
	private TableColumn<Account, String> tableColPhonenum;
	@FXML
	private TableColumn<Account, String> tableColStatus;
	@FXML
	private JFXComboBox<String> coboBoxStatus;

	@FXML
	private Label lableSlectedUser;

	@FXML
	private Label lableErrorMsg;

	@FXML
	private HBox Nav;

	@FXML
	private Label lableHello;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	private ObservableList<Account> userList = FXCollections.observableArrayList();
	private ObservableList<Account> usersFilter = FXCollections.observableArrayList();

	private ObservableList<Account> userEditSelect;
	private HashMap<String, Account> allUsres = new HashMap<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getUserName());
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
		if (isEdit) {
			chooseAccount.setVisible(false);
			anchorPaneRestaurantInfo.setVisible(true);
			btnUpdate.setVisible(true);
			btnCceateRestaurant.setVisible(false);
			labelTitle.setText("Edit restaurant");
			// ----set the initialize value of the restaurant
			textFieldRestaurantName.setText(restaurant.getName());
			textFieldRestaurantType.setText(restaurant.getType());
			textFieldRestaurantAddress.setText(restaurant.getAddress());

			textFieldSupplierUserame.setText(supplierAccount.getUserName());
			textFieldsupplierID.setText(supplierAccount.getUserID() + "");

		} else {
			// ----set the initialize value of the user
			chooseAccount.setVisible(true);
			anchorPaneRestaurantInfo.setVisible(false);
			userList.clear();
			Gson gson = new Gson();
			JsonElement jsonElem = gson.toJsonTree(new Object());
			jsonElem.getAsJsonObject().addProperty("branchManagerID",
					BranchManagerController.branchManager.getUserID());
			branchManagerFunctions.sentToJson("/accounts", "GET", jsonElem,
					"Edit personal info - new ClientController didn't work");

			responseAccounts();

			// create the table
			tableColRole.setCellValueFactory(new PropertyValueFactory<Account, String>("role"));
			tableColID.setCellValueFactory(new PropertyValueFactory<Account, String>("userName"));
			tableColFirstname.setCellValueFactory(new PropertyValueFactory<Account, String>("firstName"));
			tableColLastName.setCellValueFactory(new PropertyValueFactory<Account, String>("lastName"));
			tableColArea.setCellValueFactory(new PropertyValueFactory<Account, String>("area"));
			tableColPhonenum.setCellValueFactory(new PropertyValueFactory<Account, String>("phone"));
			tableColStatus.setCellValueFactory(new PropertyValueFactory<Account, String>("status"));
			usersFilter = userList;
			tableViewUsers.setItems(usersFilter);

			coboBoxStatus.getItems().setAll("All", "active", "frozen", "blocked");
			coboBoxStatus.setValue("All");

		}

	}

	void responseAccounts() {
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableErrorMsg.setText(response.getDescription());// error massage
		else {
			Account[] account = EchoServer.gson.fromJson((String) response.getBody(), Account[].class);
			for (Account a : account) { // update the list of users to be the response from the DB
				if (a.getRole().equals("Supplier")) {
					userList.add(a);
					allUsres.put(a.getUserName(), a);
				}
			}
		}

	}

	@FXML
	void ClickOnTable(MouseEvent event) {
		userEditSelect = tableViewUsers.getSelectionModel().getSelectedItems();
		lableSlectedUser.setText("Name: " + userEditSelect.get(0).getFirstName() + " "
				+ userEditSelect.get(0).getLastName() + ",  Role: " + userEditSelect.get(0).getRole() + ",  Status: "
				+ userEditSelect.get(0).getStatus());

	}

	@FXML
	void onChengedStatus(ActionEvent event) {
		String status = coboBoxStatus.getValue();

		usersFilter = FXCollections.observableArrayList();
		if (status.equals("All"))
			usersFilter = userList;
		else
			for (Account user : userList) {
				if (status.equals("All"))
					usersFilter.add(user);
				if (user.getStatus().equals(status))
					usersFilter.add(user);
			}

		tableViewUsers.setItems(usersFilter);
	}

	@FXML
	void onClickcontinue(ActionEvent event) {
		chooseAccount.setVisible(false);
		anchorPaneRestaurantInfo.setVisible(true);

		if (userEditSelect.get(0) != null) {
			branchManagerFunctions.sentToJson("/accounts/getAccount", "GET", userEditSelect.get(0),
					"Edit personal info - new ClientController didn't work");

			responseGetAccountUsername();
		}
	}

	void responseGetAccountUsername() {
		Gson gson = new Gson();
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableErrorMsg.setText(response.getDescription());
		else {
			JsonElement j = gson.fromJson((String) response.getBody(), JsonElement.class);
			supplierAccount = gson.fromJson(j.getAsJsonObject().get("account"), Account.class);
			restaurant = gson.fromJson(j.getAsJsonObject().get("restaurant"), Restaurant.class);

			// ----set the initialize value of the restaurant
			textFieldSupplierUserame.setText(supplierAccount.getUserName());
			textFieldsupplierID.setText(supplierAccount.getUserID() + "");

		}
	}

	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	@FXML
	void homeANDback(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	@FXML
	void selectFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile != null) {
			photo = selectedFile.getName();
			listView.getItems().add(selectedFile.getName());
//			listView.getItems().add(selectedFile.getAbsolutePath());
//			Image imageForFile = new Image(file.toURI().toURL().toExternalForm());
//			listView.getItems().add(photo)
		} else
			System.out.println("File is not valid");
	}

	private boolean flag, validField, moderator;

	@FXML
	void createRestaurant(ActionEvent event) {
		flag = true;
		checkTextFiled(textFieldSupplierUserame, lblrequiredSupplierUsername);
		checkTextFiled(textFieldsupplierID, lblrequiredSupplierID);

		checkTextFiled(textFieldRestaurantName, lblrequiredRestName);
		checkTextFiled(textFieldRestaurantType, lblrequiredRestType);
		checkTextFiled(textFieldRestaurantAddress, lblrequiredRestAdderss);

		if (textFieldModeratorUsername.getText() != null) { // if the user decided to add moderator
			checkTextFiled(textFieldModeratorUsername, lblrequiredModeratorUsername);
			checkTextFiled(textFieldModeratorID, lblrequiredModeratorID);
			moderator = true;
		}

		if (flag) {
			int supplierID = Integer.parseInt(textFieldsupplierID.getText());
			String supplierUserame = textFieldSupplierUserame.getText();
			String moderatorUsername = textFieldModeratorUsername.getText();
			int moderatorID = Integer.parseInt(textFieldModeratorID.getText());
			String restaurantName = textFieldRestaurantName.getText();
			String restaurantType = textFieldRestaurantType.getText();
			String restaurantAddress = textFieldRestaurantAddress.getText();

			Restaurant restaurant = new Restaurant(0, false, BranchManagerController.branchManager.getUserID(),
					restaurantName, BranchManagerController.branchManager.getArea(), restaurantType, supplierUserame,
					photo, restaurantAddress, "");
			Account supplier = new Account(supplierID, supplierUserame, null, null, null, null, "Supplier", null,
					"status", false, BranchManagerController.branchManager.getUserID(),
					BranchManagerController.branchManager.getArea(), 0);
			Account supplierModorator = new Account(moderatorID, moderatorUsername, null, null, null, null, "Modorator",
					null, "status", false, BranchManagerController.branchManager.getUserID(),
					BranchManagerController.branchManager.getArea(), 0);

			sentToJson(restaurant, supplier, supplierModorator);
			response();
		}
	}

	@FXML
	void update(ActionEvent event) {

	}

	void sentToJson(Restaurant restaurant, Account supplier, Account supplierModorator) {
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());
		JsonElement jsonElemModorator = gson.toJsonTree(new Object());
		JsonElement jsonElemSupplier = gson.toJsonTree(new Object());

		jsonElemModorator.getAsJsonObject().addProperty("userName", supplierModorator.getUserName());
		jsonElemModorator.getAsJsonObject().addProperty("userID", supplierModorator.getUserID());
		jsonElemSupplier.getAsJsonObject().addProperty("userName", supplier.getUserName());
		jsonElemSupplier.getAsJsonObject().addProperty("userID", supplier.getUserID());

		body.getAsJsonObject().add("moderator", jsonElemModorator);
		body.getAsJsonObject().add("supplier", jsonElemSupplier);
		body.getAsJsonObject().add("restaurant", gson.toJsonTree(restaurant));
		branchManagerFunctions.sentToJson("/branch_managers/restaurants", "POST", body,
				"Open Business account - new ClientController didn't work");
	}

	void response() {
		Response response = ChatClient.serverAns;
//		if (response.getCode() != 200 && response.getCode() != 201)
		System.out.println("response.getDescription()" + response.getDescription());
		lableErrorMag.setText(response.getDescription());// error massage
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
