package CEO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

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

public class ViewReportsController implements Initializable {
private String typeReport;
private CEOFunctions ceoFunctions = new CEOFunctions();
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
    private JFXHamburger myHamburger;

    @FXML
    private JFXDrawer drawer;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	oneReportNext.setVisible(false);
    	twoReportNext.setVisible(false);
    	
    	comboBoxbranch.getItems().setAll("North", "South", "Center");
    	comboBoxFBranch.getItems().setAll("North", "South", "Center");
    	comboBoxSBranch.getItems().setAll("North", "South", "Center");
    	
    	comboBoxFQuarter.getItems().setAll("I", "II", "III", "IV");
    	comboBoxSQuarter.getItems().setAll("I", "II", "III", "IV");
    	
    	ceoFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
	}
    
    @FXML
    void ReportQuarterlyPDF(ActionEvent event) {
    	typeReport = "ReportQuarterlyPDF";
    	oneReportNext.setVisible(true);
    	twoReportNext.setVisible(false);
    }

    @FXML
    void RevenueReport(ActionEvent event) {
    	typeReport = "RevenueReport";
    	oneReportNext.setVisible(true);
    	twoReportNext.setVisible(false);
    }

    @FXML
    void ViewOneRoport(ActionEvent event) { //button view
    	String branch = comboBoxbranch.getValue();
    	branch = branch == null ? "North" : branch;
    	ceoFunctions.reload(event, "/CEO/DisplayReport.fxml", "CEO- Dispaly report");
    	//type of report is in 'typeReport'
    }

    @FXML
    void ViewReportsBranchManager(ActionEvent event) {
    	typeReport = "ViewReportsBranchManager";
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
    void ViewTwoReport(ActionEvent event) { //button view
    	String firstQuarter = comboBoxFQuarter.getValue();
    	firstQuarter = firstQuarter==null? "I" :firstQuarter;
    	String secondQuarter = comboBoxSQuarter.getValue();
    	secondQuarter = secondQuarter==null? "I" :secondQuarter;
    	String firstBranch = comboBoxbranch.getValue();
    	firstBranch = firstBranch == null ? "North" : firstBranch;
    	String secondBranch = comboBoxbranch.getValue(); // it myte be null because the second branch is optional
    	//type of report is in 'typeReport'
    	ceoFunctions.reload(event, "/CEO/DisplayReport.fxml", "CEO- Dispaly report");
    }

    @FXML
    void backCEO(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/CEO/CEOPage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("CEO main page");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void home(ActionEvent event) {
    	try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/CEO/CEOPage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("CEO main page");
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

	

}
