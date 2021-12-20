package mywork;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Restaurant;
import temporaryDatabase.myOwnDatabase;

public class ChooseRestaurantController implements Initializable, EventHandler<ActionEvent> {

	public ArrayList<Restaurant> restaurantsFromData;
	public static Restaurant restaurantSelected;

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	@FXML
	private Button btnLogout;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button submitBtn;

	@FXML
	private ImageView imageBiteme1;

	@FXML
	private HBox Nav1;

	@FXML
	private Button backBtn;

	@FXML
	private ComboBox<String> areas;

	@FXML
	private ComboBox<String> types;

	@FXML
	private VBox restaurantsContainer;

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
		Pane root = loader.load(getClass().getResource("/mywork/PrepareADish.fxml").openStream());
		// PrepareADishController prepareADishController = loader.getController();
		// prepareADishController.setName();
		// prepareADishController.setRestName();
		PrepareADishController.prepareADishController = loader.getController();
		PrepareADishController.prepareADishController.setName();
		PrepareADishController.prepareADishController.setRestName();

		Scene scene = new Scene(root);
		primaryStage.setTitle("Prepare A Dish Page");

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void btnRecognize(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent = stringFromEvent.split("'", 2)[1];
		stringFromEvent = stringFromEvent.substring(0, stringFromEvent.length() - 1);
		for (int i = 0; i < restaurantsFromData.size(); i++) {
			if (restaurantsFromData.get(i).getName().equals(stringFromEvent)) {
				restaurantSelected = restaurantsFromData.get(i);
				break;
			}
		}
	}

	public void setName() {
		welcomeLabel.setText("Welcome, " + CustomerPageController.user.getName());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		areas.getItems().addAll("North", "South", "East", "West");
		areas.setPromptText("Default: North");

		types.getItems().addAll("All", "Pizza", "Burgers", "Italian", "Sushi", "SteakHouse");
		types.setPromptText("All");
	}

	@FXML
	void submit(ActionEvent event) {

		getInformation(areas.getValue(), types.getValue());
		ArrayList<Button> b = new ArrayList<Button>();
		for (int i = 0; i < restaurantsFromData.size(); i++) {
			Button temp = new Button(restaurantsFromData.get(i).getName());
			temp.setOnAction(this);
			b.add(temp);
		}
		restaurantsContainer.getChildren().clear();
		restaurantsContainer.getChildren().addAll(b);
	}

	private void getInformation(String area, String type) {// getting and arranging the data from the
															// server
		restaurantsFromData = new ArrayList<Restaurant>();
		Restaurant res0 = new Restaurant(0, true, 0, "McDonald's", "North", "Burgers", null, null, null, null);
		Restaurant res1 = new Restaurant(1, true, 0, "Ruben", "North", "Burgers", null, null, null, null);
		Restaurant res2 = new Restaurant(2, true, 0, "BBB", "North", "Burgers", null, null, null, null);
		restaurantsFromData.add(res0);
		restaurantsFromData.add(res1);
		restaurantsFromData.add(res2);

	}

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/mywork/ChooseRestaurant.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Restaurants Page");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void handle(ActionEvent event) {
		btnRecognize(event);
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/mywork/PrepareADish.fxml").openStream());
			PrepareADishController.prepareADishController = loader.getController();
			//PrepareADishController.prepareADishController.setName();  //set when will be logged into a real user
			PrepareADishController.prepareADishController.setRestName();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Prepare A Dish Page");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
