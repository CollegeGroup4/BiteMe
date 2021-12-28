package mywork;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.Restaurant;
import temporaryDatabase.myOwnDatabase;

public class ChooseRestaurantController implements Initializable {

	public ArrayList<Restaurant> restaurantsFromData;
	public static Restaurant restaurantSelected;

	@FXML
	private VBox restaurantsContainer;

	@FXML
	private Button backBtn;

	@FXML
	private JFXComboBox<String> areas;

	@FXML
	private JFXComboBox<String> types;

	@FXML
	private JFXButton submitBtn;

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button btnLogout;

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

//	@FXML
//	void ChooseRest(ActionEvent event) throws IOException {
//		btnRecognize(event);
//		FXMLLoader loader = new FXMLLoader();
//		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//		Stage primaryStage = new Stage();
//		Pane root = loader.load(getClass().getResource("/mywork/PrepareADish.fxml").openStream());
//		ChooseADishController.chooseADishController = loader.getController();
//		ChooseADishController.chooseADishController.setName();
//		ChooseADishController.chooseADishController.setRestName();
//
//		Scene scene = new Scene(root);
//		primaryStage.setTitle("Prepare A Dish Page");
//
//		primaryStage.setScene(scene);
//		primaryStage.show();
//
//	}
//
//	private void btnRecognize(ActionEvent event) {
//		String stringFromEvent = event.getSource().toString();
//		stringFromEvent = stringFromEvent.split("'", 2)[1];
//		stringFromEvent = stringFromEvent.substring(0, stringFromEvent.length() - 1);
//		for (int i = 0; i < restaurantsFromData.size(); i++) {
//			if (restaurantsFromData.get(i).getName().equals(stringFromEvent)) {
//				restaurantSelected = restaurantsFromData.get(i);
//				break;
//			}
//		}
//	}

	public void setName() {
		welcomeLabel.setText("Welcome, " + CustomerPageController.user.getName());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		areas.getItems().addAll("North", "South", "East", "West");
		areas.setPromptText("Default: North");

		types.getItems().addAll("All", "Pizza", "Burgers", "Italian", "Sushi", "SteakHouse");
		types.setPromptText("All");
		// need to set the scroll pane of the list to be the default area and type.
	}

	private void handeleClick(MouseEvent event, String name) {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		sentToServer();
		try {
			root = loader.load(getClass().getResource("/mywork/ChooseADish.fxml").openStream());
			ChooseADishController.chooseADishController = loader.getController();

			Scene scene = new Scene(root);
			primaryStage.setTitle("Prepare A Dish Page");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void sentToServer() {

		//TODO - need to sent restaurantSelected.getId()
	}

	@FXML
	void submit(ActionEvent event) throws IOException {

		getInformation(areas.getValue(), types.getValue());
		ArrayList<VBox> restaurantList = new ArrayList<>();
		for (int i = 0; i < restaurantsFromData.size(); i++) {

			Label name = new Label(restaurantsFromData.get(i).getName());
			Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 20);
			name.setFont(font);

			InputStream stream = new FileInputStream(restaurantsFromData.get(i).getPhoto());
			Image logo = new Image(stream);
			ImageView logoImage = new ImageView();
			logoImage.setImage(logo);
			logoImage.setFitWidth(100);
			logoImage.setFitHeight(100);
			JFXTextArea description = new JFXTextArea(restaurantsFromData.get(i).getDescription());
			description.setEditable(false);
			description.setPrefHeight(100);
			description.setPrefWidth(300);
			HBox hbox = new HBox(5);
			hbox.getChildren().addAll(logoImage, description);
			hbox.setSpacing(30);

			VBox restaurant = new VBox(20);
			restaurant.getChildren().addAll(name, hbox);
			restaurant.setPadding(new Insets(10, 0, 50, 10));
			restaurant.setCursor(Cursor.HAND);
			restaurantSelected = restaurantsFromData.get(i);
			restaurant.addEventHandler(MouseEvent.MOUSE_CLICKED,
					(e) -> handeleClick(e, restaurantSelected.getName()));
			restaurantList.add(restaurant);
		}
		restaurantsContainer.getChildren().clear();
		restaurantsContainer.getChildren().addAll(restaurantList);
	}

	private void getInformation(String area, String type) {// getting and arranging the data from the
															// server
		restaurantsFromData = new ArrayList<Restaurant>();
		Restaurant res0 = new Restaurant(0, true, 0, "McDonald's", "North", "Burgers", null,
				"C:\\Users\\talch\\OneDrive\\שולחן העבודה\\לימודים\\שיטות הנדסיות לפיתוח מערכות תוכנה\\פרוייקט\\חלק 2\\BiteMe\\src\\images\\M.png",
				null, "Wikipedia is a free online encyclopedia, created and edited by volunteers around the world and");
		Restaurant res1 = new Restaurant(1, true, 0, "Ruben", "North", "Burgers", null,
				"C:\\Users\\talch\\OneDrive\\שולחן העבודה\\לימודים\\שיטות הנדסיות לפיתוח מערכות תוכנה\\פרוייקט\\חלק 2\\BiteMe\\src\\images\\ruben.png",
				null, "Wikipedia is a free online encyclopedia, created and edited by volunteers around the world and");
		Restaurant res2 = new Restaurant(2, true, 0, "BBB", "North", "Burgers", null,
				"C:\\Users\\talch\\OneDrive\\שולחן העבודה\\לימודים\\שיטות הנדסיות לפיתוח מערכות תוכנה\\פרוייקט\\חלק 2\\BiteMe\\src\\images\\BBB.png",
				null, "Wikipedia is a free online encyclopedia, created and edited by volunteers around the world and");
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

//	@Override
//	public void handle(ActionEvent event) {
////		btnRecognize(event);
//		FXMLLoader loader = new FXMLLoader();
//		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//		Stage primaryStage = new Stage();
//		Pane root;
//		try {
//			root = loader.load(getClass().getResource("/mywork/ChooseADish.fxml").openStream());
//			ChooseADishController.chooseADishController = loader.getController();
//
//			Scene scene = new Scene(root);
//			primaryStage.setTitle("Prepare A Dish Page");
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
