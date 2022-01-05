package branchManager;

import java.net.URL;
import java.util.ResourceBundle;

import client.CustomerPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import logic.Account;

/**
 * This class is for the branch manager main page. From here you can get to all
 * the functionality of the branch manager
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */

public class BranchManagerController implements Initializable {
	public static Account branchManager;
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	@FXML
	private HBox Nav;

	@FXML
	private Label lableHello;

	@FXML
	private Hyperlink linkApprovals;

	/**
	 * A method for initialize the label of the user name
	 * 
	 * @param URL            location
	 * @param ResourceBundle resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getFirstName());
	}

	/**
	 * The method receiving notifications for the branch manager, and he can approve
	 * them.
	 * 
	 * @param event
	 */
	@FXML
	void Approvals(ActionEvent event) {
		branchManagerFunctions.reload(event, "/branchManager/ApprovalsPage.fxml", "Branch manager - Approvals Page");
	}

	/**
	 * A method Allows the user to logout from the system.
	 * 
	 * @param event
	 */
	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	/**
	 * A method that allow to the branch manager to edit user info: edit private
	 * account, edit business account and edit restaurant
	 * 
	 * @param event
	 */
	@FXML
	void editPersonalInfo(ActionEvent event) {
		branchManagerFunctions.reload(event, "/branchManager/EditPersonalInfo.fxml",
				"Branch manager - Edit Personal Info");
	}

	/**
	 * A method that creates a report
	 * 
	 * @param event
	 */
	@FXML
	void createReports(ActionEvent event) {
//TODO
	}

	/**
	 * A method for the branch manager to view reports
	 * 
	 * @param event
	 */
	@FXML
	void viewReports(ActionEvent event) {
//TODO
	}

	/**
	 * A method that loads a page to select which type of account to open
	 * 
	 * @param event
	 */
	@FXML
	void openAccount(ActionEvent event) {
		branchManagerFunctions.reload(event, "/branchManager/OpenAccountPage.fxml", "Branch manager - open account");
	}

	/**
	 * A method for order food in the branch manager account
	 * 
	 * @param event
	 */
	@FXML
	void orderFood(ActionEvent event) {
		System.out.println("Order food - Branch manager");
		CustomerPageController.client = branchManager;
		branchManagerFunctions.reload(event, "/client/ChooseRestaurant.fxml", "Branch manager- Order food");
	}

	/**
	 * A method for creating a new supplier
	 * 
	 * @param event
	 */
	@FXML
	void RegisterationRestaurant(ActionEvent event) {
		System.out.println("registeration & Approval Supplier - Branch manager");
		RegisterationApprovalSupplierController.isEdit = false;
		branchManagerFunctions.reload(event, "/branchManager/RegisterationApprovalSupplier.fxml",
				"Branch manager - registeration & Approval Supplier");
	}
}
