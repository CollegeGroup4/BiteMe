package branchManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import guiNew.Navigation_SidePanelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OpenAccountController implements Initializable {
//	private static BranchManager branchManager;
//	private static User user;

	@FXML
	private HBox Nav;

	@FXML
	private Label lableHello;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnBackBM;

	@FXML
	private Button btnOpenPrivateAccount;

	@FXML
	private Button btnOpenBusinessAccount;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getUserName());

		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/guiNew/Navigation_SidePanel.fxml"));
			drawer.setSidePane(anchorPane);
		} catch (IOException e) {
			Logger.getLogger(Navigation_SidePanelController.class.getName()).log(Level.SEVERE, null, e);
		}

		// transition animation of hamburger icon
		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(myHamburger);
		drawer.setVisible(false);
		transition.setRate(-1);

		// click event - mouse click
		myHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawer.isOpened()) {
				drawer.setVisible(false);
				drawer.close(); // this will close slide pane
			} else {
				drawer.open(); // this will open slide pane
				drawer.setVisible(true);
			}
		});
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
			e.printStackTrace();
		}

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
		} catch (IOException e) {
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
			e.printStackTrace();
		}

	}

	@FXML
	void openPrivateAccount(ActionEvent event) {
		System.out.println("Open private account");
		try {
//			FXMLLoader loader = new FXMLLoader( getClass().getResource("/branchManager/OpenBusinessAccount.fxml"));
//			loader.setController("branchManager.EditBusinessAccountController");
			
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
			e.printStackTrace();
		}
	}

}
