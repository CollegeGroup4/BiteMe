package mywork;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Item;
import logic.Menu;
import logic.Options;
import logic.item_in_menu;
import temporaryDatabase.myOwnDatabase;

public class PrepareADishController implements Initializable, EventHandler<ActionEvent> {

	public static PrepareADishController prepareADishController = null;
	public static Menu menuSelected;
	public static ArrayList<item_in_menu> items_in_menuArr;
	public static ArrayList<Menu> menusArr;
	public static ArrayList<Item> itemsArr;
	public static HashMap<String, Integer> courses;

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
	private VBox Menus;

	@FXML
	private BorderPane paneForItems;
	
    @FXML
    private BorderPane paneForSummary;

	public BorderPane getPaneForSummary() {
		return paneForSummary;
	}

	public void setPaneForSummary(BorderPane paneForSummary) {
		this.paneForSummary = paneForSummary;
	}

	public BorderPane getPaneForItems() {
		return paneForItems;
	}

	public void setPaneForItems(BorderPane paneForItems) {
		this.paneForItems = paneForItems;
	}

	@FXML
	private BorderPane paneForSelections;

	@FXML
	private BorderPane paneForCourses;

	public BorderPane getPaneForCourses() {
		return paneForCourses;
	}

	public void setPaneForCourses(BorderPane paneForCourses) {
		this.paneForCourses = paneForCourses;
	}

	public BorderPane getPaneForSelections() {
		return paneForSelections;
	}

	public void setPaneForSelections(BorderPane paneForSelections) {
		this.paneForSelections = paneForSelections;
	}

	@FXML
	private HBox Nav1;
	@FXML
	private Button btnBack;
	
    @FXML
    private Button btnNext;

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
		displayRestName.setText("You chose to dine at: " + ChooseRestaurantController.restaurantSelected.getName());

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> b = new ArrayList<Button>();
		items_in_menuArr = new ArrayList<item_in_menu>();
		itemsArr = new ArrayList<Item>();
		menusArr = new ArrayList<Menu>();
		setTempDatabase();
		for (int i = 0; i < menusArr.size(); i++) {
			Button temp = new Button(menusArr.get(i).getName());
			temp.setOnAction(this);
			b.add(temp);

		}
		Menus.getChildren().addAll(b);
	}

	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent = stringFromEvent.split("'", 2)[1];
		stringFromEvent = stringFromEvent.substring(0, stringFromEvent.length() - 1);
		courses = new HashMap<>();
		for (int i = 0; i < items_in_menuArr.size(); i++) {
			if (items_in_menuArr.get(i).getMenu_name().equals(stringFromEvent))
				courses.put(items_in_menuArr.get(i).getCourse(), null);
		}

		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Courses.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		paneForCourses.setCenter(root);
	}

	private void setTempDatabase() {

		Options op0[] = { new Options("Size", "Regular", 0,0),new Options("Size", "Big", 15,0),
				new Options("Select", "Not tomatoes", 0,0),new Options("Select", "No onions", 0,0) };
		Item item0 = new Item("Italiano", "Pizza", 0, 0, "Regular Pizza", 47,
				"High-quality mozzarella pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
				null, 5);
		itemsArr.add(item0);

		item_in_menu item_in_menu0 = new item_in_menu(0, 0, "Italiano", "Pizza");
		item_in_menu item_in_menu1 = new item_in_menu(1, 0, "Main", "Burgers");
		item_in_menu item_in_menu2 = new item_in_menu(2, 0, "First", "Fries");
		item_in_menu item_in_menu3 = new item_in_menu(3, 0, "Main", "Pizza");
		items_in_menuArr.add(item_in_menu0);
		items_in_menuArr.add(item_in_menu1);
		items_in_menuArr.add(item_in_menu2);
		items_in_menuArr.add(item_in_menu3);

		Menu temp0 = new Menu("Italiano", 0,new item_in_menu[]{item_in_menu0});
		Menu temp1 = new Menu("Burgers", 0,new item_in_menu[]{item_in_menu1});
		menusArr = new ArrayList<Menu>();
		menusArr.add(temp0);
		menusArr.add(temp1);
		/*
		 * item_in_menu item0 = new item_in_menu(0, 0, "Drinks", "Coca-Cola", 7, null,
		 * null); item_in_menu item1 = new item_in_menu(1, 0, "Drinks", "Fanta", 7,
		 * null, null); item_in_menu item2 = new item_in_menu(2, 0, "Burgers", "BigMc",
		 * 35, "Regular Burger", new ArrayList<String>(Arrays.asList("Tomato", "Onion",
		 * "Pickels")));
		 * 
		 * item_in_menu item3 = new item_in_menu(3, 0, "Salads", "Halumi", 40,
		 * "Leaf Salad with Halumi cheese", new
		 * ArrayList<String>(Arrays.asList("Tomato", "Onion", "Cucumber", "Herbs",
		 * "peanuts")));
		 * 
		 * item_in_menu item4 = new item_in_menu(4, 0, "First course", "Fries", 18,
		 * "Plate of fries with sauces", new ArrayList<String>(Arrays.asList("Salt",
		 * "Mayonnaise", "Ketchup")));
		 * 
		 * Menu temp0 = new Menu("First course", 0, new
		 * ArrayList<item_in_menu>(Arrays.asList(item4))); Menu temp1 = new
		 * Menu("Burgers", 1, new ArrayList<item_in_menu>(Arrays.asList(item2))); Menu
		 * temp2 = new Menu("Salad", 2, new
		 * ArrayList<item_in_menu>(Arrays.asList(item3))); Menu temp3 = new
		 * Menu("Drinks", 4, new ArrayList<item_in_menu>(Arrays.asList(item0, item1)));
		 * myOwnDatabase.MenusArrayList = new ArrayList<Menu>();
		 * myOwnDatabase.MenusArrayList.add(temp0);
		 * myOwnDatabase.MenusArrayList.add(temp1);
		 * myOwnDatabase.MenusArrayList.add(temp2);
		 * myOwnDatabase.MenusArrayList.add(temp3);
		 */
	}

    @FXML
    void next(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
		DeliveryAndTimeController aFrame = new DeliveryAndTimeController();
		try {
			aFrame.start(new Stage());
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/mywork/PrepareADish.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("DPrepare A Dish Page");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
