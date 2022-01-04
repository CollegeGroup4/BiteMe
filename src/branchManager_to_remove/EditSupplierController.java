package branchManager_to_remove;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import branchManager.BranchManagerController;
import branchManager.BranchManagerFunctions;
import common.Request;
import guiNew.Navigation_SidePanelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.Account;
import logic.BusinessAccount;
import logic.Restaurant;

public class EditSupplierController implements Initializable {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	private String photo;

	@FXML
	private TextField textFieldSupplierUserame;

	@FXML
	private Label lblrequiredSupplierUsername;

	@FXML
	private Label lblrequiredSupplierID;

	@FXML
	private TextField textFieldsupplierID;

	@FXML
	private Button btnUpdateRestaurant;

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
	private ListView<String> listView;
	@FXML
	private Button btnBackBM;

	@FXML
	private Label lblrequiredModeratorUsername;

	@FXML
	private TextField textFieldModeratorUsername;

	@FXML
	private Label lblrequiredModeratorID;

	@FXML
	private TextField textFieldModeratorID;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getUserName());
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
	}


	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	@FXML
	void home(ActionEvent event) {
		branchManagerFunctions.home(event);
	}


	@FXML
	void selectFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile != null) {
			photo = selectedFile.getAbsolutePath();
			listView.getItems().add(selectedFile.getName());
//			listView.getItems().add(selectedFile.getAbsolutePath());
//			Image imageForFile = new Image(file.toURI().toURL().toExternalForm());
//			listView.getItems().add(photo)
		} else
			System.out.println("File is not valid");
	}

	private boolean flag;

	@FXML
	void updateSupplier(ActionEvent event) {
		flag = true;

		checkTextFiled(textFieldRestaurantName, lblrequiredRestName);
		checkTextFiled(textFieldRestaurantType, lblrequiredRestType);
		checkTextFiled(textFieldRestaurantAddress, lblrequiredRestAdderss);
		if (flag) {
			String supplierUserame = textFieldSupplierUserame.getText();
			String moderatorUsername = textFieldModeratorUsername.getText();
			String restaurantName = textFieldRestaurantName.getText();
			String restaurantType = textFieldRestaurantType.getText();
			String restaurantAddress = textFieldRestaurantAddress.getText();

			Restaurant restaurant = new Restaurant(0, false, BranchManagerController.branchManager.getUserID(),
					restaurantName, BranchManagerController.branchManager.getArea(), restaurantType, supplierUserame,
					photo, restaurantAddress, "");
			Account supplier = new Account(0, supplierUserame, null, null, null, null, "Supplier", null, "status",
					false, BranchManagerController.branchManager.getUserID(),
					BranchManagerController.branchManager.getArea(), 0);
			Account supplierModorator = new Account(0, moderatorUsername, null, null, null, null, "SupplierModorator",
					null, "status", false, BranchManagerController.branchManager.getUserID(),
					BranchManagerController.branchManager.getArea(), 0);

			sentToJson(restaurant, supplier, supplierModorator);
			response();
		}
	}

	void sentToJson(Restaurant restaurant, Account supplier, Account supplierModorator) {
		Request request = new Request();
		request.setPath("/branch_managers/restaurants");
		request.setMethod("POST");
		Gson gson = new Gson();
		JsonElement jsonElem = gson.toJsonTree(new Object());
		jsonElem.getAsJsonObject().add("restaurant", gson.toJsonTree(restaurant));
		jsonElem.getAsJsonObject().add("supplier", gson.toJsonTree(supplier));
		jsonElem.getAsJsonObject().add("supplierModerator", gson.toJsonTree(supplierModorator));
		request.setBody(jsonElem);

		String jsonFile = gson.toJson(request);
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

	@FXML
	void backEditInfo(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
		}
	}

}
