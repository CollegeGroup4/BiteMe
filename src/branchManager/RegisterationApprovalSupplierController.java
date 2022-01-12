package branchManager;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import Server.EchoServer;
import Server.Response;
import client.ChatClient;
import common.MyPhoto;
import common.imageUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import logic.Account;
import logic.Restaurant;

/**
 * This class is for the branch manager to Registration Supplier page. From here
 * you can open or edit restaurant Only if the supplier and moderator are
 * registered in the database of the user management system then the restaurant
 * can be opened OR if the supplier has a open restaurant for edit
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */

public class RegisterationApprovalSupplierController implements Initializable {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
	public static Boolean isEdit = false;
	public static Restaurant restaurant;
	public static Account supplierAccount;
	private String photo;
	private boolean flag, validField, moderator;
	MyPhoto m;

	@FXML
	private Label labelTitle;
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
	private Label lblrequiredRestDescription;
	@FXML
	private TextField textFieldRestaurantDescription;
	@FXML
	private Button btnSelectFile;
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
	private Label lableSuccessMsg;
	@FXML
	private Label lableErrorMsg;
	@FXML
	private AnchorPane chooseAccount;
	@FXML
	private Label lableSlectedUser;
	@FXML
	private Button btnReturnHome;
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

	/**
	 * initialize the Registration Supplier-restaurant page - initialize the
	 * navigation side panel - initialize the personal info - initialize the
	 * restaurant info if this is edit restaurant
	 * 
	 * @param URL            location
	 * @param ResourceBundle resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getUserName());
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
		btnReturnHome.setVisible(false);
		if (isEdit) {
			// if we are editing an account then
			btnUpdate.setVisible(true);
			chooseAccount.setVisible(false);
			anchorPaneRestaurantInfo.setVisible(true);
			btnUpdate.setVisible(true);
			btnCceateRestaurant.setVisible(false);
			labelTitle.setText("Edit restaurant");
			// ----set the initialize value of the restaurant
			textFieldRestaurantName.setText(restaurant.getName());
			textFieldRestaurantType.setText(restaurant.getType());
			textFieldRestaurantAddress.setText(restaurant.getAddress());
			listView.getItems().add(restaurant.getPhoto());

			textFieldSupplierUserame.setText(supplierAccount.getUserName());
			textFieldsupplierID.setText(supplierAccount.getUserID() + "");

			// TODO- after up date need to unNote them!
//			textFieldModeratorUsername.setText(restaurant.);
//			textFieldModeratorID.setText();

		} else {
			btnUpdate.setVisible(false);
			// ----set the initialize value of the user
			chooseAccount.setVisible(true);
			anchorPaneRestaurantInfo.setVisible(false);
			userList.clear();
			// sent to server
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

	/**
	 * A method that receives from the serve the list of all accounts that the
	 * branch manager can edit
	 * 
	 */
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

	/**
	 * A method that updates the selected user - userEditSelect by clicking on the
	 * table
	 * 
	 * @param event
	 */
	@FXML
	void ClickOnTable(MouseEvent event) {
		userEditSelect = tableViewUsers.getSelectionModel().getSelectedItems();
		lableSlectedUser.setText("Name: " + userEditSelect.get(0).getFirstName() + " "
				+ userEditSelect.get(0).getLastName() + ",  Role: " + userEditSelect.get(0).getRole() + ",  Status: "
				+ userEditSelect.get(0).getStatus());

	}

	/**
	 * A method that updates the table of all users according to the selected status
	 * in the combo box
	 * 
	 * @param event
	 */
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

	/**
	 * A method that sends the account to the server to get all the accounts for the
	 * selected user , when clicking on account from the table
	 * 
	 * @param event
	 */
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

	/**
	 * A method that receives all the accounts from the server of the selected user
	 * and upload the choice of account type to open
	 * 
	 * @param event
	 */
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

	/**
	 * A method Allows the user to logout from the system.
	 * 
	 * @param event
	 */
	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	/**
	 * A method that returns to the branch manager's home screen
	 * 
	 * @param event
	 */
	@FXML
	void home(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	/**
	 * A method for selecting an image and uploading from the user's computer
	 * 
	 * @param event
	 */
	@FXML
	void selectFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile != null) {
			photo = selectedFile.getName();
			MyPhoto m = new MyPhoto(selectedFile.getAbsolutePath());
			imageUtils.sender(m);
			listView.getItems().add(selectedFile.getName());
		} else
			System.out.println("File is not valid");
	}

	@FXML
	void createRestaurant(ActionEvent event) {
		flag = true;
		checkTextFiled(textFieldSupplierUserame, lblrequiredSupplierUsername);
		checkTextFiled(textFieldsupplierID, lblrequiredSupplierID);

		checkTextFiled(textFieldRestaurantName, lblrequiredRestName);
		checkTextFiled(textFieldRestaurantType, lblrequiredRestType);
		checkTextFiled(textFieldRestaurantAddress, lblrequiredRestAdderss);
		checkTextFiled(textFieldRestaurantDescription, lblrequiredRestDescription);


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
			String restaurantDescription = textFieldRestaurantDescription.getText();

			Restaurant restaurant = new Restaurant(0, true, BranchManagerController.branchManager.getUserID(),
					restaurantName, BranchManagerController.branchManager.getArea(), restaurantType, supplierUserame,
					photo, restaurantAddress, restaurantDescription);
			restaurant.setRestaurantImage(m);
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
	/**
	 * A method for create restaurant by sending it to the server that it will save
	 * the restaurant in DB
	 * 
	 * @param restaurant
	 * @param supplier
	 * @param supplierModorator
	 */
	void sentToJson(Restaurant restaurant, Account supplier, Account supplierModorator) {
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());
		JsonElement jsonElemModorator = gson.toJsonTree(new Object());
		JsonElement jsonElemSupplier = gson.toJsonTree(new Object());

		jsonElemModorator.getAsJsonObject().addProperty("userName", supplierModorator.getUserName());
		jsonElemModorator.getAsJsonObject().addProperty("userID", supplierModorator.getUserID());
		jsonElemModorator.getAsJsonObject().addProperty("supplierUserName", supplier.getUserName());
		jsonElemSupplier.getAsJsonObject().addProperty("userName", supplier.getUserName());
		jsonElemSupplier.getAsJsonObject().addProperty("userID", supplier.getUserID());

		body.getAsJsonObject().add("moderator", jsonElemModorator);
		body.getAsJsonObject().add("supplier", jsonElemSupplier);
		body.getAsJsonObject().add("restaurant", gson.toJsonTree(restaurant));
		branchManagerFunctions.sentToJson("/branch_managers/restaurants", "POST", body,
				"Open Business account - new ClientController didn't work");
	}
	/**
	 * A method that receive a response from the server to confirm that the
	 * restaurant information has indeed been created
	 * 
	 */
	void response() {
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201) {
			lableErrorMsg.setText(response.getDescription());// error massage
			lableSuccessMsg.setText("");
		} else {
			lableSuccessMsg.setText(response.getDescription());
			lableErrorMsg.setText("");
			btnReturnHome.setVisible(true);
		}
	}
	/**
	 * ** If we are in a state of edit restaurant: ** We will update the restaurant
	 * by sending it to a server that will update in DB and receive a response from
	 * the server to confirm that the information has indeed been updated
	 * 
	 * @param event
	 */
	@FXML
	void update(ActionEvent event) {
		String restaurantName = textFieldRestaurantName.getText();
		String restaurantType = textFieldRestaurantType.getText();
		String restaurantAddress = textFieldRestaurantAddress.getText();
		String restaurantDescription = textFieldRestaurantDescription.getText();
		photo = restaurant.getPhoto();

		restaurant = new Restaurant(restaurant.getId(), restaurant.isApproved(), restaurant.getBranchManagerID(),
				restaurantName, restaurant.getArea(), restaurantType, restaurant.getUserName(), photo,
				restaurantAddress, restaurantDescription);

		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());
		body.getAsJsonObject().addProperty("userName", supplierAccount.getUserName());
		body.getAsJsonObject().add("restaurant", gson.toJsonTree(restaurant));
		branchManagerFunctions.sentToJson("/branch_managers/restaurants", "PUT", body,
				"Edit restaurant - new ClientController didn't work");

		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201) {
			lableSuccessMsg.setText("");
			lableErrorMsg.setText(response.getDescription());// error massage
		} else {
			btnReturnHome.setVisible(true);
			lableErrorMsg.setText("");
			lableSuccessMsg.setText(response.getDescription());// error massage
			btnUpdate.setVisible(false);
		}
	}

	/**
	 * A method that checks that we got in a text field string if not then prints
	 * 'required'
	 * 
	 * @param textField
	 * @param lblrequired
	 */
	void checkTextFiled(TextField textField, Label lblrequired) {
		if (textField.getText().equals("")) {
			lblrequired.setText("required");
			flag = false;
		} else {
			lblrequired.setText("");
		}
	}

	/**
	 * A method that checks that we got in a text field a number if not then prints
	 * 'Enter only numbers'
	 * 
	 * @param textField
	 * @param lblrequired
	 */
	void checkValidFields(TextField textField, Label lblrequired) {
		try {
			Integer.parseInt(textField.getText());
		} catch (NumberFormatException e) {
			lblrequired.setText("Enter only numbers");
			validField = false;
		}
	}

}
