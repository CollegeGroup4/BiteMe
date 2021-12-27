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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OpenAccountController implements Initializable {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	@FXML
	private HBox Nav;

	@FXML
	private Label lableHello;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;
	@FXML
	private BorderPane borderPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getFirstName());
		
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
	}

	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	@FXML
	void homeANDback(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	@FXML
	void openBusinessAccount(ActionEvent event) {
		System.out.println("Open business account");
		branchManagerFunctions.reload(event, "/branchManager/OpenBusinessAccount.fxml",
				"Branch manager - open business account");
	}

	@FXML
	void openPrivateAccount(ActionEvent event) {
		System.out.println("Open private account");
		branchManagerFunctions.reload(event, "/branchManager/OpenPrivateAccount.fxml",
				"Branch manager - open private account");
	}

}
