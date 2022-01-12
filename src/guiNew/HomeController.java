package guiNew;

import java.io.IOException;
import java.net.UnknownHostException;

import branchManager.BranchManagerFunctions;
import client.CustomerFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HomeController {

	public static String role;
	@FXML
	private HBox Nav;

	@FXML
	private Hyperlink hyperLinkLogin;

	@FXML
	private Hyperlink hyperLinkAbout;

	@FXML
	private Button btnJoin;

	@FXML
	private HBox Nav1;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/guiNew/HomePage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Home");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void about(ActionEvent event) {

	}

	@FXML
	void login(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;

			root = loader.load(getClass().getResource("/guiNew/Login.fxml").openStream());

			Scene scene = new Scene(root);
			primaryStage.setTitle("Login");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
		}
	}

	@FXML
	void Exit(ActionEvent event) throws UnknownHostException {
		
	switch (role) {
		case "Branch Manager":
			System.out.println("exit manager");
			BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
			branchManagerFunctions.exit(event);
			break;
		case "Client":
			System.out.println("exit client");
			CustomerFunctions customerFunctions = new CustomerFunctions();
			customerFunctions.exit(event);
			break;
		default:
			Navigation_SidePanelController.role = "";
			System.out.println("defult - error DB the user does not have a file");
		}
	}
}
