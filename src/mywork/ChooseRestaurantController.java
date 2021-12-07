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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChooseRestaurantController implements Initializable {

	@FXML
	private HBox Nav;

    @FXML
    private Label welcomeLabel;

	@FXML
	private Button btnLogout;

	@FXML
	private Hyperlink BtnHome;

    @FXML
    private Button rest0;

    @FXML
    private Button rest1;

    @FXML
    private Button rest2;

    @FXML
    private Button rest3;

	@FXML
	private ImageView imageBiteme1;

	@FXML
	private HBox Nav1;

	@FXML
	private ImageView imageBiteme2;

	@FXML
	private ImageView imageFacebook;

	@FXML
	private ImageView imageInstergram;

	@FXML
	private ImageView imageWhatsapp;

	@FXML
	private Button btnBack;
	
	public static String restName;

	@FXML
	void goBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		CustomerPageController aFrame = new CustomerPageController();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void ChooseRest(ActionEvent event) throws IOException {
		btnRecognize(event);
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/mywork/ChooseMenu.fxml").openStream());
		ChooseMenuController chooseMenuController = loader.getController();
		chooseMenuController.setName();
		chooseMenuController.setRestName();

		Scene scene = new Scene(root);
		primaryStage.setTitle("Choose Menu Page");

		primaryStage.setScene(scene);
		primaryStage.show();

	}
	private void btnRecognize(ActionEvent event) {
		String stringFromEvent=event.getSource().toString();
		restName=stringFromEvent.substring(36,stringFromEvent.length()-1);
		//System.out.println(restName);
	}

	public void setName() {
		welcomeLabel.setText("Welcome, " + CustomerPageController.user.getName());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/mywork/ChooseRestaurant.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Restaurants Page");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	

}
