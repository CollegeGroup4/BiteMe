package branchManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DisplayReportController implements Initializable {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	@FXML
	private HBox Nav;

	@FXML
	private Label lableHello;

	@FXML
	private ScrollPane scrolePaneListItems;

	@FXML
	private VBox ItemContainer;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	@FXML
	void back(ActionEvent event) {
		branchManagerFunctions.reload(event, "/branchManager/ViewReports.fxml", "Branch Manager - View Report");
	}

	@FXML
	void home(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HBox itemInLine = new HBox(5);
		for (int i = 0; i < ViewReportsController.reports.length; i++) {
			String projectPath = System.getProperty("user.dir");
			System.out.println(ViewReportsController.reports[i].getFileName());
			try {
				System.out.println(projectPath + "\\" + ViewReportsController.reports[i].getFileName());
					InputStream stream;
					stream = new FileInputStream(projectPath + "\\" + ViewReportsController.reports[i].getFileName());
					Image logo = new Image(stream);
					ImageView logoImage = new ImageView();
					logoImage.setImage(logo);
					logoImage.setFitWidth(680);
					logoImage.setFitHeight(350);
					itemInLine.getChildren().addAll(logoImage);
					itemInLine.setSpacing(30);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ItemContainer.getChildren().addAll(itemInLine);
		}

	}
}
