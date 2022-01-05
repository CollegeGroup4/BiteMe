package branchManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import Server.EchoServer;
import Server.QueryConsts;
import Server.Response;
import client.ChatClient;
import client.ClientUI;
import common.MyPhoto;
import common.Request;
import common.imageUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Account;

public class ViewReportsController implements Initializable {
	private String typeReport;
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
	public static MyPhoto[] reports = null;
	@FXML
	private HBox Nav;

	@FXML
	private Label lableHello;

	@FXML
	private AnchorPane oneReportNext;

	@FXML
	private AnchorPane twoReportNext;

	@FXML
	private JFXComboBox<String> comboBoxbranch;

	@FXML
	private JFXComboBox<String> comboBoxFQuarter;

	@FXML
	private JFXComboBox<String> comboBoxSQuarter;

	@FXML
	private JFXComboBox<String> comboBoxFBranch;

	@FXML
	private JFXComboBox<String> comboBoxSBranch;
    
	@FXML
    private JFXComboBox<String> comboBoxSBranchYearSecond;

    @FXML
    private JFXComboBox<String> comboBoxSBranchYearFirst;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		oneReportNext.setVisible(false);
		twoReportNext.setVisible(false);

		comboBoxbranch.getItems().setAll("Performance", "Items", "Sales");
		comboBoxbranch.setValue("Performance");

		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
	}

	@FXML
	void ReportQuarterlyPDF(ActionEvent event) {
		typeReport = "ReportQuarterlyPDF";
		oneReportNext.setVisible(true);
		twoReportNext.setVisible(false);
	}

	@FXML
	void ViewReportsTwoQuarters(ActionEvent event) {
		typeReport = "ViewReportsTwoQuarters";
		twoReportNext.setVisible(true);
		oneReportNext.setVisible(false);
	}
	
	@FXML
	void ViewOneRoport(ActionEvent event) {
		typeReport = "ViewReportsTwoQuarters";
		twoReportNext.setVisible(true);
		oneReportNext.setVisible(false);
	}

	@FXML
	void ViewTwoReport(ActionEvent event) { // button view
		String secondBranch = comboBoxbranch.getValue(); // it myte be null because the second branch is optional
		Gson gson = new Gson();
		JsonElement j = ChatClient.gson.toJsonTree(new Object());
		j.getAsJsonObject().addProperty("branchManagerID", BranchManagerController.branchManager.getUserID());
		j.getAsJsonObject().addProperty("type", comboBoxbranch.getValue());
		Request req = new Request();
		req.setPath("/branch_manager/reports");
		req.setMethod("GET");
		req.setBody(gson.toJson(j));
		try {
			ClientUI.chat.accept(gson.toJson(req)); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("new ClientController didn't work");
		}
		response();
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/DisplayReport.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("branchManager- Dispaly report");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//ceoFunctions.reload(event, "/CEO/DisplayReport.fxml", "CEO- Dispaly report");
	}
	
	void response() {
		Gson gson = new Gson();
		Response response = ChatClient.serverAns;
		if (response.getCode() == 200 || response.getCode() == 201) {
			reports = gson.fromJson((String) response.getBody(), MyPhoto[].class);
			for (MyPhoto myPhoto : reports) {
				imageUtils.receiver(myPhoto, "");
			}	
		}
	}

	@FXML
	void backCEO(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	@FXML
	void home(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

}
