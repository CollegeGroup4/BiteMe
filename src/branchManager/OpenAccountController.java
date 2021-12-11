package branchManager;

import java.io.IOException;

import client.ChatClient;
import donotenterdrinksorfood.BranchManager;
import donotenterdrinksorfood.Users;
import gui.EditOrderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OpenAccountController {
private static BranchManager branchManager;
private static Users user;

	@FXML
	private HBox Nav;

	@FXML
	private Hyperlink linkApprovals;

	@FXML
	private Button btnOpenBusinessAccount;

	@FXML
	private Button btnOpenPrivateAccount;

	@FXML
	private HBox Nav1;

	@FXML
	private Button btnBackBM;

	@FXML
	void Approvals(ActionEvent event) {

	}

	@FXML
	void backBM(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
			BranchManagerController branchManagerController = loader.getController();
			branchManagerController.initUser(user);
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void openBusinessAccount(ActionEvent event) {
		System.out.println("Open business account");
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/OpenBusinessAccount.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager - open business account");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void openPrivateAccount(ActionEvent event) {
		System.out.println("Open private account");
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/OpenPrivateAccount.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager - open private account");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
