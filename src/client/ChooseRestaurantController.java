package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;

import Server.EchoServer;
import common.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.Restaurant;
/**
 * This class made for display and select restaurants
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */
public class ChooseRestaurantController implements Initializable {

	public ArrayList<Restaurant> restaurantsFromData;
	public static Restaurant restaurantSelected;
	private CustomerFunctions customerFunctions = new CustomerFunctions();
	String projectPath = System.getProperty("user.dir") + "\\src\\images\\"; // locate the Path of the current project// director
	@FXML
	private VBox restaurantsContainer;

	@FXML
	private JFXComboBox<String> areas;

	@FXML
	private JFXComboBox<String> types;

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	@FXML
	private Button btnLogout;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private Label lableErrorMag;

/**
 * Setting initial display values for the page
 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeLabel.setText("Welcome, " + CustomerPageController.client.getFirstName());
		customerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
		areas.getItems().addAll("North", "South", "East", "West");
		areas.setValue(CustomerPageController.client.getArea());
		sentToServer();
		response();
		try {
			reSetContainer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
/**
 * A function that is activated when selecting the restaurant area
 * @param event
 */
	@FXML
	void onClickArea(ActionEvent event) {
		try {
			reSetContainer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
/**
 * A function that is activated when selecting the restaurant type
 * @param event
 */
	@FXML
	void onClickType(ActionEvent event) {
		try {
			reSetContainer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
/**
 * Creates the restaurants display component
 * @throws FileNotFoundException
 */
	void reSetContainer() throws FileNotFoundException {
		ArrayList<HBox> restaurantList = new ArrayList<>();
		String areaSelected = areas.getValue();
		String typeSelected = types.getValue();
		for (int i = 0; i < restaurantsFromData.size(); i++) {
			if (areaSelected.equals(restaurantsFromData.get(i).getArea())
					&& (typeSelected.equals("All") || typeSelected.equals(restaurantsFromData.get(i).getType()))) {
				Label name = new Label(restaurantsFromData.get(i).getName());
				Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 20);
				name.setFont(font);
				Label type = new Label(restaurantsFromData.get(i).getType());
				Label address = new Label(restaurantsFromData.get(i).getAddress());
				address.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
				InputStream stream = new FileInputStream(projectPath + "" + restaurantsFromData.get(i).getPhoto());
				
				Image logo = new Image(stream);
				ImageView logoImage = new ImageView();
				logoImage.setImage(logo);
				logoImage.setFitWidth(100);
				logoImage.setFitHeight(100);
				
				Circle circle = new Circle(50, 50, 60);
				circle.setStroke(Color.BLACK);
				logoImage.setClip(circle);

				JFXTextArea description = new JFXTextArea(restaurantsFromData.get(i).getDescription());
				description.setEditable(false);
				description.setPrefHeight(50);
				description.setPrefWidth(300);

				VBox restaurantInfo = new VBox(10);
				restaurantInfo.getChildren().addAll(name, type, address, description);

				HBox restaurant = new HBox(5);
				restaurant.getChildren().addAll(logoImage, restaurantInfo);
				restaurant.setSpacing(30);
				// -----------new Insets (top, left, bottom, right)
				restaurant.setPadding(new Insets(10, 10, 10, 10));
				restaurant.setAlignment(Pos.CENTER);
				BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
				Background background = new Background(background_fill);
				restaurant.setBackground(background);
				restaurant.setCursor(Cursor.HAND);
				restaurantSelected = restaurantsFromData.get(i);
				restaurant.addEventHandler(MouseEvent.MOUSE_CLICKED,
						(e) -> handeleClick(e, restaurantSelected.getName()));
				restaurantList.add(restaurant);

			}
		}
		restaurantsContainer.getChildren().clear();
		restaurantsContainer.setPadding(new Insets(10, 10, 10, 10));
		restaurantsContainer.setSpacing(20);
		restaurantsContainer.getChildren().addAll(restaurantList);
	}
/**
 * The function is activated when clicking on a restaurant.
 * The purpose of the function is to save the selected restaurant and move on to the dish selection process
 * @param event
 * @param name
 */
	private void handeleClick(MouseEvent event, String name) {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		sentToServer();
		response();
		try {
			root = loader.load(getClass().getResource("/client/ChooseADish.fxml").openStream());
			ChooseADishController.chooseADishController = loader.getController();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Prepare A Dish Page");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
/**
 * Sends the type and area of the restaurant to the server, in order to get the appropriate restaurants
 */
	void sentToServer() {
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());
		body.getAsJsonObject().addProperty("area", areas.getValue());
		customerFunctions.sentToJson("/restaurants/areas", "GET", body, "get restaurants by area didn't work");	
	}
/**
 * Received restaurants information from the server and arrangements for the data structures
 */
	private void response() {
		restaurantsFromData = new ArrayList<Restaurant>();
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201) // if there was an error then need to print an error
			lableErrorMag.setText(response.getDescription()); // TODO- error massage
		else {
			Restaurant[] restaurantList = EchoServer.gson.fromJson((String) response.getBody(), Restaurant[].class);
			types.getItems().add("All");
			for (Restaurant r : restaurantList) { // update the list of restaurant to be the response from the DB
				restaurantsFromData.add(r);
				types.getItems().add(r.getType());
			}
			types.setValue("All");
		}
	}

/**
 * Loading the previous screen (CustomerPage)
 * @param event
 */
	@FXML
	void goBack(ActionEvent event) {
		customerFunctions.reload(event, "CustomerPage.fxml", "Customer Page");
	}
/**
 * Loading home page
 * @param event
 */
	@FXML
	void home(ActionEvent event) {
		customerFunctions.home(event);
	}
/**
 * Disconnect the user from the system
 * @param event
 */
	@FXML
	void logout(ActionEvent event) {
		customerFunctions.logout(event);
	}
}
