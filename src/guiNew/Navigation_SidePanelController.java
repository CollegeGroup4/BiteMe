package guiNew;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Navigation_SidePanelController extends Application {

	@FXML
	private VBox vBoxManu;
	@FXML
	private JFXButton btnExit;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/branchManager/OpenAccountPage.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void setvBoxManu(VBox vBoxManu) {
		this.vBoxManu = vBoxManu;
	}

	@FXML
	void editPersonalInfo(ActionEvent event) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void RegisterationRestaurant(ActionEvent event) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void viewReports(ActionEvent event) {

	}

	@FXML
	void Exit(ActionEvent event) throws Exception {
		System.out.println("exit client Tool");
		String[] ipHostName = new String[3];
		ipHostName[0] = "EXIT";
		ipHostName[1] = InetAddress.getLocalHost().getHostName();
		ipHostName[2] = InetAddress.getLocalHost().getHostAddress();
		try {
			ClientUI.chat.accept(ipHostName);
		} catch (NullPointerException e) {
			System.out.println("new ClientController didn't work");
		}
		System.exit(0);
	}
}
