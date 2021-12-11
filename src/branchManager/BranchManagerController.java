package branchManager;

import java.io.IOException;

import donotenterdrinksorfood.BranchManager;
import donotenterdrinksorfood.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BranchManagerController {
	public static Users user;
	public static BranchManager branchManager;

	@FXML
	private HBox Nav;

	@FXML
	private Button btnOrderFood;

	@FXML
	private Button btnViewReports;

	@FXML
	private Button btnOpenAccount;

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

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/branchManager/BranchManagerPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("BranchManager");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void Approvals(ActionEvent event) {

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
		} catch (IOException e) {}

	}

	@FXML
	void createReports(ActionEvent event) {

	}

	@FXML
	void openAccount(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/OpenAccountPage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager - open account");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {}
	}

	@FXML
	void orderFood(ActionEvent event) {
		System.out.println("Order food - Branch manager");
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/gui/MainScreen.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager- Order food");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {}
	}

	@FXML
	void registerationApprovalSupplier(ActionEvent event) {
		System.out.println("registeration & Approval Supplier - Branch manager");
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader
					.load(getClass().getResource("/branchManager/RegisterationApprovalSupplier.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager - registeration & Approval Supplier ");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {}
	}

	@FXML
	void viewReports(ActionEvent event) {

	}

	public void initUser(Users user) {
		BranchManagerController.user = user;
		lableHello.setText(user.getName());

	}
	public void initBranchManager(BranchManager branchManager) {
		BranchManagerController.branchManager = branchManager;
		lableHello.setText(branchManager.getName());

	}
}
