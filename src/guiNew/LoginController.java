package guiNew;

import java.io.IOException;

import branchManager.BranchManagerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import myDB.DB;

public class LoginController {
	private DB db = new DB(5555);
	@FXML
	private TextField textFieldUsername;

	@FXML
	private TextField textFieldPassword;

	@FXML
	private Button btnLogin;

	@FXML
	private Label lableErrorMag;

	@FXML
	private HBox Nav1;

	@FXML
	private HBox Nav;
	private boolean flag;

	@FXML
	void Login(ActionEvent event) {
		System.out.println("Logged in");
		flag = true;
		checkTextFiled(textFieldUsername, lableErrorMag);
		checkTextFiled(textFieldPassword, lableErrorMag);
		if (flag) {
			try {
				String username = textFieldUsername.getText();
				String password = textFieldPassword.getText();
				int role = db.isin(username, password);
				if (role != -1) {
					FXMLLoader loader = new FXMLLoader();
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
					Stage primaryStage = new Stage();
					AnchorPane root = null;
					// root =
					// loader.load(getClass().getResource("/gui/HomePage.fxml").openStream());
					switch (role) {
					case 0:
						System.out.println("go to barnch manager");
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						BranchManagerController branchManagerController = loader.getController();
						branchManagerController.initUser(db.getUser(password));
						break;
					case 1:
						System.out.println("go to supplier");
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case 2:
						System.out.println("go to ordinary user");
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					default:
						System.out.println("defult - error DB the user does not have a file");
					}
					lableErrorMag.setText("");
					Scene scene = new Scene(root);
					primaryStage.setTitle("Home");
					primaryStage.setScene(scene);
					primaryStage.show();
				} else
					lableErrorMag.setText("username or password incorrect");
			} catch (IOException e) {
			}
		}
	}

	void checkTextFiled(TextField textField, Label lblrequired) {
		if (textField.getText().equals("")) {
			lblrequired.setText("Please fill all the fields");
			flag = false;
		} else {
			lblrequired.setText("");
		}
	}

}
