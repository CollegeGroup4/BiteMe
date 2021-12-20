package mywork;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DeliveryAndTimeController implements Initializable{

	@FXML
	private Button btnBack;

	@FXML
	private Button btnNext;

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	@FXML
	private DatePicker dpDate;

	@FXML
	private ChoiceBox<String> cbTime;

	@FXML
	private ChoiceBox<String> cbType;

	@FXML
	private TextField address;

	@FXML
	private TextField name;

	@FXML
	private TextField phoneNumber;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button btnLogout;

	@FXML
	void goBack(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		PrepareADishController aFrame = new PrepareADishController();
		aFrame.start(primaryStage);
	}

	@FXML
	void next(ActionEvent event) {

	}

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/mywork/DeliveryAndTime.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Delivery And Time Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbTime.getItems().addAll("00:00","00:30","1:00","1:30","2:00","2:30","3:00","3:30","4:00"
				,"4:30","5:00","5:30","6:00","6:30","7:00","7:30","8:00","8:30","9:00","9:30","10:00"
				,"10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00"
				,"15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00"
				,"20:30","21:00","21:30","22:00","22:30","23:00","23:30");
		
	}

}
