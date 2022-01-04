package CEO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import guiNew.Navigation_SidePanelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Account;


public class CEOController implements Initializable {
	public static Account CEO;

	@FXML
	private HBox Nav;

	@FXML
	private Button btnOrderFood;

	@FXML
	private Button btnViewReports;

	@FXML
	private Button btnOpenAccount;

    @FXML
    private Button btnEditPersonalInfo;
    
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

	@FXML
	void Approvals(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/ApprovalsPage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager - Approvals Page");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void logout(ActionEvent event) {
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
	void viewReports(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/CEO/ViewReportsPage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("CEO - View Report");
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

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, "  + CEOController.CEO.getUserName());
	}
}
