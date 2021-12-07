package mywork;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChooseMenuController {

    @FXML
    private HBox Nav;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button btnLogout;

    @FXML
    private Hyperlink BtnHome;

    @FXML
    private ImageView imageBiteme1;

    @FXML
    private Label displayRestName;

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

	@FXML
	void goBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		ChooseRestaurantController aFrame = new ChooseRestaurantController();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setName() {
		welcomeLabel.setText("Welcome, " + CustomerPageController.user.getName());
		
	}

	public void setRestName() {
		displayRestName.setText("You chose to dine at: "+ChooseRestaurantController.restName);
		
	}

}
