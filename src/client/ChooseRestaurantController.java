package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import Server.EchoServer;
import Server.Response;
import common.Request;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.Account;
import logic.Restaurant;

public class ChooseRestaurantController implements Initializable {

	public ArrayList<Restaurant> restaurantsFromData;
	public static Restaurant restaurantSelected;
	String projectPath = System.getProperty("user.dir") + "\\src\\images\\"; // locate the Path of the current project
																				// directory

	@FXML
	private VBox restaurantsContainer;

	@FXML
	private Button backBtn;

	@FXML
	private JFXComboBox<String> areas;

	@FXML
	private JFXComboBox<String> types;

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button btnLogout;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeLabel.setText("Welcome, " + CustomerPageController.client.getFirstName());
		
		System.out.println("aaaa");
		sentToServer();
		System.out.println("llll");
		response();
		System.out.println("bbbbb");
		
		areas.getItems().addAll("North", "South", "East", "West");
		areas.setValue(CustomerPageController.client.getArea()); 

		types.getItems().addAll("All", "Pizza", "Burgers", "Italian", "Sushi", "SteakHouse"); //TODO - function that find all the types and set hare!
		types.setValue("All");
		try {
			reSetContainer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

//		BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
//		Background background = new Background(background_fill);
//		restaurantsContainer.setBackground(background);
	}

	@FXML
	void onClickArea(ActionEvent event) {
		try {
			reSetContainer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickType(ActionEvent event) {
		try {
			reSetContainer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	void reSetContainer() throws FileNotFoundException {
		getInformation(areas.getValue(), types.getValue());
		ArrayList<HBox> restaurantList = new ArrayList<>();
		String areaSelected = areas.getValue();
		String typeSelected = types.getValue();
		for (int i = 0; i < restaurantsFromData.size(); i++) {
			if (areaSelected == restaurantsFromData.get(i).getArea()
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

	void sentToServer() {
		Gson gson = new Gson();
		Request request = new Request();
		request.setPath("/restaurants/areas");
		request.setMethod("GET");
		
		JsonElement body = gson.toJsonTree(new Object());
		body.getAsJsonObject().addProperty("area", areas.getValue());
		request.setBody(gson.toJson(body));
		String jsonUser = gson.toJson(request);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println( "get restaurants by area didn't work");
		}
	}

	private void response() {
		
		restaurantsFromData = new ArrayList<Restaurant>();
		Response response = ChatClient.serverAns; 
		if (response.getCode() != 200 && response.getCode() != 201) // TODO- if there was an error then need to print an ERORR!
			//lableErrorMag.setText(response.getDescription()); // TODO-  error massage
			System.out.println(response.getDescription()); 
		else {
//			System.out.println("-->>" + response.getDescription()); // Description from server
//			JsonElement body = EchoServer.gson.fromJson((String) response.getBody(), JsonElement.class);
//			restaurantList = EchoServer.gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant[].class);
			Restaurant[] restaurantList = EchoServer.gson.fromJson((String) response.getBody(), Restaurant[].class);
			System.out.println("restaurantList " + restaurantList );
			for (Restaurant r : restaurantList)  // update the  list of restaurant to be the response from the DB
				restaurantsFromData.add(r);
			
		}
	}
	
	private void getInformation(String area, String type) {// getting and arranging the data from the
															// server
		restaurantsFromData = new ArrayList<Restaurant>();

		Restaurant res0 = new Restaurant(0, true, 0, "McDonald's", "North", "Burgers", null, "M.png",
				"Carmiel, kalnit 1",
				"Wikipedia is a free online encyclopedia, created and edited by volunteers around the world and");
		Restaurant res1 = new Restaurant(1, true, 0, "Ruben", "North", "Burgers", null, "ruben.png",
				"Carmiel, hasalom 33",
				"Wikipedia is a free online encyclopedia, created and edited by volunteers around the world and");
		Restaurant res2 = new Restaurant(2, true, 0, "BBB", "North", "Burgers", null, "BBB.png", "Carmiel, yefe 22",
				"Wikipedia is a free online encyclopedia, created and edited by volunteers around the world and");
		restaurantsFromData.add(res0);
		restaurantsFromData.add(res1);
		restaurantsFromData.add(res2);

	}

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/client/ChooseRestaurant.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Restaurants Page");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

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
}
