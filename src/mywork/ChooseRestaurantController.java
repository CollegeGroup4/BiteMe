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
import logic.Restaurant;
import temporaryDatabase.myOwnDatabase;

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

	public void setName() {
		welcomeLabel.setText("Welcome, " + CustomerPageController.user.getName());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		areas.getItems().addAll("North", "South", "East", "West");
		areas.setValue("North"); // TODO - need to be CustomerPageController.user.getArea() -> user need to be
									// account as I sent in login!

		types.getItems().addAll("All", "Pizza", "Burgers", "Italian", "Sushi", "SteakHouse");
		types.setValue("All");
		try {
			reSetContainer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// need to set the scroll pane of the list to be the default area and type.

//		BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
//		Background background = new Background(background_fill);
//		restaurantsContainer.setBackground(background);
	}

	@FXML
	void onClickArea(ActionEvent event) {
		try {
			reSetContainer();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void onClickType(ActionEvent event) {
		try {
			reSetContainer();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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

		// TODO - need to sent restaurantSelected.getId()
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
		Parent root = FXMLLoader.load(getClass().getResource("/mywork/ChooseRestaurant.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Restaurants Page");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
