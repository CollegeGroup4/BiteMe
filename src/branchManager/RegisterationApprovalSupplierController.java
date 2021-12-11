package branchManager;

import java.io.File;
import java.io.IOException;

import donotenterdrinksorfood.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RegisterationApprovalSupplierController {
	private Supplier supplier;
	private String photo;

	@FXML
	private TextField textFieldFirstName;

	@FXML
	private Label lblrequiredFname;

	@FXML
	private Label lblrequiredLname;

	@FXML
	private TextField textFieldLastName;

	@FXML
	private Label lblrequiredID;

	@FXML
	private TextField textFieldID;

	@FXML
	private Label lblrequiredPhonNum;

	@FXML
	private TextField textFieldPhoneNum;

	@FXML
	private Label lblrequiredEmail;

	@FXML
	private TextField textFieldEmail;

	@FXML
	private Button btnAddSupplier;

	@FXML
	private Label lblrequiredRestName;

	@FXML
	private TextField textFieldRestaurantName;

	@FXML
	private Button btnSelectFile;

	@FXML
	private ListView<String> listView;

	@FXML
	private HBox Nav1;

	@FXML
	private Button btnBackBM;

	@FXML
	private HBox Nav;

	@FXML
	private Hyperlink btnHome;
	@FXML
	private CheckBox checkBoxIsApprov;

	@FXML
	void selectFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile != null) {
			photo = selectedFile.getAbsolutePath();
			listView.getItems().add(selectedFile.getName());
			listView.getItems().add(selectedFile.getAbsolutePath());
//			Image imageForFile = new Image(file.toURI().toURL().toExternalForm());
//			listView.getItems().add(photo)
		} else
			System.out.println("File is not valid");
	}

	private boolean flag, validField;

	@FXML
	void addSupplier(ActionEvent event) {
		flag = true;
		checkTextFiled(textFieldFirstName, lblrequiredFname);
		checkTextFiled(textFieldLastName, lblrequiredLname);
		checkTextFiled(textFieldID, lblrequiredID);
		checkTextFiled(textFieldPhoneNum, lblrequiredPhonNum);
		checkTextFiled(textFieldEmail, lblrequiredEmail);
		checkTextFiled(textFieldRestaurantName, lblrequiredRestName);
		if (flag) {
			validField = true;
			checkValidFields(textFieldID, lblrequiredID);
			checkValidFields(textFieldPhoneNum, lblrequiredPhonNum);

			if (validField) {

				int id = Integer.parseInt(textFieldID.getText());
//////////////////////////////////////////////////////////////////////////////
				// this one need to add to supplier class - tall Moshe!!!!!
//////////////////////////////////////////////////////////////////////////////
				int phoneNum = Integer.parseInt(textFieldPhoneNum.getText());
				String email = textFieldEmail.getText();
				String restaurantName = textFieldRestaurantName.getText();
//////////////////////////////////////////////////////////////////////////////
				String name = textFieldFirstName.getText() + " " + textFieldLastName.getText();

				boolean isApproved = checkBoxIsApprov.isSelected();
				System.out.println("checkBoxIsApprov.isSelected(): " + isApproved);
				int branchManagerID = 1;
				String area = "a";

				supplier = new Supplier(id, isApproved, branchManagerID, name, area, photo);
//////////////////////////////////////////////////////////////////////////////
				//covert to JSON here!!!
//////////////////////////////////////////////////////////////////////////////

			}
		}
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

	@FXML
	void backBM(ActionEvent event) {
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

	@FXML
	void home(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/gui/HomePage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Home");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
		}

	}
}
