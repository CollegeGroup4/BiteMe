package guiNew;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import branchManager.BranchManagerFunctions;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import supplier.SupplierFunction;

public class Navigation_SidePanelController extends Application implements Initializable {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
	private SupplierFunction suplier=new SupplierFunction();
	public static String role;
	@FXML
	private VBox vBoxManu;

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/branchManager/OpenAccountPage.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		switch (role) {
		case "CEO":
			initSidePanelCEO(); // not ready!!!
			break;
		case "Branch Manager":
			initSidePanelBranchManager();
			break;
		case "Supplier":
			initSidePanelSupplier(); // not ready!!!
			break;
		case "Moderator":
			initSidePanelModerator(); // not ready!!!
			break;
		case "HR":
			initSidePanelHR(); // not ready!!!
			break;
		case "Client":
			initSidePanelClient();
			break;
		}
	}

	private void initSidePanelCEO() {
		JFXButton[] buttons = new JFXButton[7];
		buttons[0] = new JFXButton("Order food");
		createButton(buttons[0], "/gui/MainScreen.fxml", "Branch manager - Order food");

		buttons[1] = new JFXButton("Edit personal info");
		createButton(buttons[1], "/branchManager/EditPersonalInfo.fxml", "Branch manager - Edit Personal Info");

		buttons[2] = new JFXButton("View reports");
		createButton(buttons[2], "/gui/MainScreen.fxml", "Branch manager - Order food");

		buttons[3] = new JFXButton("Create new report");
		createButton(buttons[3], "/gui/MainScreen.fxml", "Branch manager - Order food");

		buttons[4] = new JFXButton("open account");
		createButton(buttons[4], "/branchManager/OpenAccountPage.fxml", "Branch manager - open account");

		buttons[5] = new JFXButton("Registeration Restaurant");
		createButton(buttons[5], "/branchManager/RegisterationApprovalSupplier.fxml",
				"Branch manager - registeration & Approval Supplier");
		buttons[6] = new JFXButton("Exit");
		Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 20);
		buttons[6].setFont(font);
		buttons[6].addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.exit(e);
		});
		vBoxManu.getChildren().addAll(buttons);
	}

	private void initSidePanelBranchManager() {
		JFXButton[] buttons = new JFXButton[7];
		buttons[0] = new JFXButton("Order food");
		createButton(buttons[0], "/gui/MainScreen.fxml", "Branch manager - Order food");

		buttons[1] = new JFXButton("Edit personal info");
		createButton(buttons[1], "/branchManager/EditPersonalInfo.fxml", "Branch manager - Edit Personal Info");

		buttons[2] = new JFXButton("View reports");
		createButton(buttons[2], "/branchManager/OpenAccountPage.fxml", "Branch manager - open account");

		buttons[3] = new JFXButton("Create new report");
		createButton(buttons[3], "/branchManager/OpenAccountPage.fxml", "Branch manager - open account");
		buttons[4] = new JFXButton("open account");
		createButton(buttons[4], "/branchManager/OpenAccountPage.fxml", "Branch manager - open account");

		buttons[5] = new JFXButton("Registeration Restaurant");
		createButton(buttons[5], "/branchManager/RegisterationApprovalSupplier.fxml",
				"Branch manager - registeration & Approval Supplier");
		buttons[6] = new JFXButton("Exit");
		Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 20);
		buttons[6].setFont(font);
		buttons[6].addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.exit(e);
		});
		vBoxManu.getChildren().addAll(buttons);
	}

	private void initSidePanelSupplier() {
		JFXButton[] buttons = new JFXButton[9];
		buttons[0] = new JFXButton("All items");
		buttons[0].addEventHandler(ActionEvent.ACTION, (e) -> {
			suplier.reload(e, "/supplier/ALL_Items.fxml",
					"supplier all item table");;
		});
		buttons[1] = new JFXButton("Create Menu");
		buttons[1].addEventHandler(ActionEvent.ACTION, (e) -> {
			suplier.reload(e, "/supplier/CreatAndEditMenu.fxml",
					"supplier creat menu ");
		});
		buttons[2] = new JFXButton("Create Item");
		buttons[2].addEventHandler(ActionEvent.ACTION, (e) -> {
			suplier.reload(e, "/supplier/CreatItem.fxml",
					"supplier create item ");
		});
		buttons[3] = new JFXButton("Update Menu");
		buttons[3].addEventHandler(ActionEvent.ACTION, (e) -> {
			suplier.reload(e, "/supplier/UpdateMenuPage.fxml",
					"supplier Update Menu ");
		});
		buttons[4] = new JFXButton("Update Item");
		buttons[4].addEventHandler(ActionEvent.ACTION, (e) -> {
			suplier.reload(e, "/supplier/UpdateItemTable.fxml", "Update Items Table");
		});
		buttons[5] = new JFXButton("Approve Order");
		buttons[5].addEventHandler(ActionEvent.ACTION, (e) -> {
			suplier.reload(e, "/supplier/ApproveOrder.fxml",
					"Approve order by Supplier");
		});
		
		buttons[6] = new JFXButton("Order");
		buttons[6].addEventHandler(ActionEvent.ACTION, (e) -> {
			suplier.exit(e);
		});
		buttons[7] = new JFXButton("Update");
		buttons[7].addEventHandler(ActionEvent.ACTION, (e) -> {
			suplier.reload(e, "/supplier/UpdateItem.fxml",
					"Update Item Screen");
		});
		
		buttons[8] = new JFXButton("Exit");
		buttons[8].addEventHandler(ActionEvent.ACTION, (e) -> {
			suplier.exit(e);
		});
		vBoxManu.getChildren().addAll(buttons);
	}

	private void initSidePanelModerator() {
		JFXButton[] buttons = new JFXButton[7];
		buttons[0] = new JFXButton("Order food");
		buttons[0].addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.reload(e, "/gui/MainScreen.fxml", "Branch manager - Order food");
		});
		buttons[1] = new JFXButton("Edit personal info");
		buttons[1].addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.reload(e, "/branchManager/EditPersonalInfo.fxml",
					"Branch manager - Edit Personal Info");
		});
		buttons[2] = new JFXButton("View reports");
		buttons[2].addEventHandler(ActionEvent.ACTION, (e) -> {
			///////
		});
		buttons[3] = new JFXButton("Create new report");
		buttons[3].addEventHandler(ActionEvent.ACTION, (e) -> {
			//////
		});
		buttons[4] = new JFXButton("open account");
		buttons[4].addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.reload(e, "/branchManager/OpenAccountPage.fxml", "Branch manager - open account");
		});
		buttons[5] = new JFXButton("Registeration Restaurant");
		buttons[5].addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.reload(e, "/branchManager/RegisterationApprovalSupplier.fxml",
					"Branch manager - registeration & Approval Supplier");
		});
		buttons[6] = new JFXButton("Exit");
		buttons[6].addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.exit(e);
		});
		vBoxManu.getChildren().addAll(buttons);
	}

	private void initSidePanelHR() {
		JFXButton[] buttons = new JFXButton[3];
		buttons[0] = new JFXButton("approve");
		buttons[0].addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.reload(e, "/supplier/BusinessApprove.fxml", "H.R manager - Approve Business");
		});
		buttons[1] = new JFXButton("employeebutton");
		buttons[1].addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.reload(e, "/supplier/EmployeeRegister.fxml",
					"H.R manager - Employee Register page");
		});
		buttons[2] = new JFXButton("Exit");
		buttons[2].addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.exit(e);
		});
		vBoxManu.getChildren().addAll(buttons);
	}

	private void initSidePanelClient() {
		JFXButton[] buttons = new JFXButton[3];

		buttons[0] = new JFXButton("Order food");
		createButton(buttons[0], "/gui/MainScreen.fxml", "Branch manager - Order food");

		buttons[1] = new JFXButton("My Orders");
		createButton(buttons[1], "/branchManager/EditPersonalInfo.fxml", "Branch manager - Edit Personal Info");

		buttons[3] = new JFXButton("Exit");
		createExitButton(buttons[3]);
		
		vBoxManu.getChildren().addAll(buttons);
	}
	private void createButton(JFXButton button, String path, String titleStage) {
		Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 20);
		button.setFont(font);
		button.addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.reload(e, path, titleStage);
		});
	}
	private void createExitButton(JFXButton button) {
		Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 20);
		button.setFont(font);
		button.addEventHandler(ActionEvent.ACTION, (e) -> {
			branchManagerFunctions.exit(e);
		});
	}
}
