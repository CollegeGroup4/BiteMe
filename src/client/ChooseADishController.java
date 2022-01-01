package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.Item;
import logic.Menu;
import logic.Options;
import logic.Restaurant;
import logic.item_in_menu;

public class ChooseADishController implements Initializable {

	public static ChooseADishController chooseADishController = null;
	public static Menu menuSelected;
	public static ArrayList<Menu> menusArr;
	public HashMap<Integer, Item> allItems;
	public static ArrayList<Item> itemsSelectedArr;
	public static Item itemSelected;
	public HashMap<String, Integer> categories;
	public boolean isOpen = false;
	String projectPath = System.getProperty("user.dir") + "\\src\\images\\"; // locate the Path of the current project
	// directory

	@FXML
	private BorderPane paneForSummary;

	@FXML
	private BorderPane paneForSelections;

	@FXML
	private ScrollPane scrolePaneListItems;

	@FXML
	private VBox ItemContainer;

	@FXML
	private JFXComboBox<String> comboBoxMenu;

	@FXML
	private JFXComboBox<String> comboBoxCourse;

	@FXML
	private JFXComboBox<String> comboBoxCategory;

	@FXML
	private ImageView shoppingCart;

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/client/ChooseADish.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Prepare A Dish Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeLabel.setText("Welcome, " + CustomerPageController.client.getFirstName());
		/// ---------------------------------------------
		System.out.println("path:" + System.getProperty("user.dir"));
		/// ---------------------------------------------

		requstFromServer();
		if (itemsSelectedArr == null)
			itemsSelectedArr = new ArrayList<>();
		paneForSelections.setVisible(false);
		setComboBoxMenu();// --create menu list--
		comboBoxMenu.setValue(menusArr.get(0).getName());

		setComboBoxCourses();// --create course list--
		setComboBoxCategory();// --create category list--
		setItemsInCategory();// --create item in category list--
		comboBoxCategory.setDisable(true);
		shoppingCart.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> handeleClickShoppingCart(e));

		try {
			setItemContainer();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void onClickMenu(ActionEvent event) throws FileNotFoundException {
		setComboBoxCourses();
		setComboBoxCategory();
		setItemContainer();
		comboBoxCategory.setDisable(true);
	}

	@FXML
	void onClickCourse(ActionEvent event) throws FileNotFoundException {
		setComboBoxCategory();
		setItemContainer();
		comboBoxCategory.setDisable(false);
	}

	@FXML
	void onClickCategory(ActionEvent event) throws FileNotFoundException {
		setItemContainer();
	}

	public void handeleClickShoppingCart(MouseEvent event) {
		if (isOpen) {
			paneForSummary.setCenter(null);
			paneForSummary.toBack();
		} else {
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("Summary.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			paneForSummary.setCenter(root);
			paneForSummary.toFront();
		}
		isOpen = !isOpen;
	}

	private void setItemContainer() throws FileNotFoundException {
		ArrayList<VBox> itemList = new ArrayList<>();
		String selectedCourse = comboBoxCourse.getValue();
		String selectedCategory = comboBoxCategory.getValue();

		for (int i = 0; i < menuSelected.getItems().length; i++) {
			item_in_menu itemInMenu = menuSelected.getItems()[i];
			if (selectedCourse == null || (itemInMenu.getCourse()).equals(selectedCourse)) {
				Item item = allItems.get(itemInMenu.getItemID());
				if (selectedCategory == null || (item.getCategory()).equals(selectedCategory)) {
					// ---get the img of the dish---//
					InputStream stream;
					stream = new FileInputStream(projectPath + "" + item.getPhoto());

					Image logo = new Image(stream);
					ImageView logoImage = new ImageView();
					logoImage.setImage(logo);
					logoImage.setFitWidth(80);
					logoImage.setFitHeight(80);

					// ---get the name of the dish---//
					Label name = new Label(item.getName());
					Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 15);
					name.setFont(font);

					// ---get the name of the dish---//
					String price = new DecimalFormat("##.##").format(item.getPrice()) + "$";
					Label priceLabel = new Label(price);
					priceLabel.setFont(font);

					// ---get the description of the dish---//
					JFXTextArea description = new JFXTextArea(item.getDescription());
					description.setEditable(false);
					description.setPrefHeight(50);
					description.setPrefWidth(300);

					VBox name_description = new VBox(5);
					name_description.getChildren().addAll(name, description);

					HBox itemInLine = new HBox(5);
					itemInLine.getChildren().addAll(logoImage, name_description, priceLabel);
					itemInLine.setSpacing(30);

					VBox dishes = new VBox(20);
					dishes.getChildren().add(itemInLine);
					dishes.setPadding(new Insets(10, 0, 50, 10));
					dishes.setCursor(Cursor.HAND);

					dishes.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> handeleClickDish(e, item));
					itemList.add(dishes);
					ItemContainer.getChildren().clear();
					ItemContainer.getChildren().addAll(itemList);
				}
			}
		}
	}

	private void handeleClickDish(MouseEvent event, Item item) {

		setVisibleItemContainer(false);
		itemSelected = item;
		setItemsInCategory();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Selections.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		paneForSelections.setCenter(root);
	}

	private void setComboBoxMenu() {
		String[] manuList = new String[menusArr.size()];
		for (int i = 0; i < menusArr.size(); i++)
			manuList[i] = (menusArr.get(i).getName());
		comboBoxMenu.getItems().setAll(manuList);
	}

	private void setComboBoxCourses() {
		getMenuName();// ===== get the menu name =====
		HashMap<String, Integer> courseList = new HashMap<>();
		for (int i = 0; i < menuSelected.getItems().length; i++) {
			courseList.put(menuSelected.getItems()[i].getCourse(), null);
		}
		comboBoxCourse.getItems().clear();
		for (String course : courseList.keySet()) // enter courses to the comboBox
			comboBoxCourse.getItems().add(course);
	}

	private void getMenuName() {
		for (int i = 0; i < menusArr.size(); i++) {
			if (menusArr.get(i).getName().equals(comboBoxMenu.getValue()))
				menuSelected = menusArr.get(i);
		}
	}

	private void setComboBoxCategory() {
		setCategoriesInformation();
		comboBoxCategory.getItems().clear();
		for (String category : categories.keySet()) {
			comboBoxCategory.getItems().add(category);
		}
	}

	private void setCategoriesInformation() {// find all categories for a specific menu
		categories = new HashMap<>();
		item_in_menu[] itemsInMenu = menuSelected.getItems();

		for (int i = 0; i < itemsInMenu.length; i++)
			categories.put(allItems.get(itemsInMenu[i].getItemID()).getCategory(), null);
	}

	private void setItemsInCategory() {
		ArrayList<Item> itemsInCategory = new ArrayList<>();
		for (int i = 0; i < menuSelected.getItems().length; i++) {
			if (menuSelected.getItems()[i].getCourse().equals(comboBoxCourse.getValue())) {
				Item item = allItems.get(menuSelected.getItems()[i].getItemID());

				if (item.getCategory().equals(comboBoxCategory.getValue()))
					itemsInCategory.add(item);
			}
		}
	}

	void requstFromServer() {
		menusArr = new ArrayList<Menu>();
		allItems = new HashMap<>();
		// TODO - need to get menus and items list
		setTempDatabase();
	}

	public void setVisibleItemContainer(boolean visible) {
		ItemContainer.setVisible(visible);
		scrolePaneListItems.setVisible(visible);
		paneForSelections.setVisible(!visible);
	}

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

	@FXML
	void home(ActionEvent event) {

	}

	@FXML
	void logout(ActionEvent event) {

	}

	public void setRestName() {
//		displayRestName.setText("You chose to dine at: " + ChooseRestaurantController.restaurantSelected.getName());

	}

	private void setTempDatabase() {

		Options op0[] = { new Options("Size", "Regular", 0, 0, false), new Options("Size", "Big", 15, 0, false),
				new Options("Select", "Not tomatoes", 0, 0, false), new Options("Select", "No onions", 0, 0, false) };

		Options op1[] = { new Options("Cook Size", "Medium", 0, 1, false), new Options("Cook Size", "Medium Well", 0, 1, false),
				new Options("Cook Size", "Well Done", 0, 1, false) };
//========Day menu =======//
		Item item0 = new Item("Italiano", null, 0, 0, "Regular Pizza", 40,
				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
				"regular-pizza.jpg", 5);
		Item item2 = new Item("Italiano", null, 2, 0, "Margherita Pizza", 47,
				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
				"margherita-pizza.jpg", 5);
		Item item3 = new Item("Italiano", null, 3, 0, "Special Pizza", 52,
				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
				"agg-pizza.jpeg", 5);
		Item item6 = new Item("Salads", null, 6, 0, "Greek Salad", 52,
				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
				"GreekSalad.jpg", 5);
//		Item item3 = new Item("Italiano", null, 3, 0, "Special Pizza", 52,
//				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
//				"C:\\Users\\talch\\OneDrive\\שולחן העבודה\\לימודים\\שיטות הנדסיות לפיתוח מערכות תוכנה\\פרוייקט\\חלק 2\\BiteMe\\src\\images\\agg-pizza.jpeg",
//				5);
		allItems.put(item0.getItemID(), item0);
		allItems.put(item2.getItemID(), item2);
		allItems.put(item3.getItemID(), item3);
		allItems.put(item6.getItemID(), item6);

		item_in_menu[] item_in_Daymenu = new item_in_menu[4];
		item_in_Daymenu[0] = new item_in_menu(6, 0, "Day", "First Course");
		item_in_Daymenu[1] = new item_in_menu(0, 0, "Day", "Second Course");
		item_in_Daymenu[2] = new item_in_menu(2, 0, "Day", "Second Course");
		item_in_Daymenu[3] = new item_in_menu(3, 0, "Day", "Second Course");

//		item_in_menu item_in_Daymenu= new item_in_menu(1, 0, "Day", "Second Course");

		// ========Night menu =======//
		Item item1 = new Item("Steaks", null, 1, 0, "Entrecote", 85, "A classic 300 gram slice of entrecote", null, op1,
				"meat-bar.jpg", 3);
		Item item4 = new Item("Steaks", null, 4, 0, "Hamburger", 60, "A classic 300 gram slice of entrecote", null, op1,
				"hamburger.jpg", 3);
		Item item5 = new Item("Steaks", null, 5, 0, "Fries", 15, "A classic 300 gram slice of entrecote", null, op1,
				"fries.jpg", 3);
		allItems.put(item1.getItemID(), item1);
		allItems.put(item4.getItemID(), item4);
		allItems.put(item5.getItemID(), item5);
		item_in_menu[] item_in_Nightmenu = new item_in_menu[3];
		item_in_Nightmenu[0] = new item_in_menu(1, 0, "Night", "Second Course");
		item_in_Nightmenu[1] = new item_in_menu(4, 0, "Night", "Second Course");
		item_in_Nightmenu[2] = new item_in_menu(0, 0, "Night", "Second Course");

		// items_in_menuArr.add(item_in_menu0);
		// items_in_menuArr.add(item_in_menu1);

		Menu dayMenu = new Menu("Day", 0, item_in_Daymenu);
		Menu nightMenu = new Menu("Night", 0, item_in_Nightmenu);
		menusArr = new ArrayList<Menu>();
		menusArr.add(dayMenu);
		menusArr.add(nightMenu);
	}

	public BorderPane getPaneForSummary() {
		return paneForSummary;
	}

	public BorderPane getPaneForSelections() {
		return paneForSelections;
	}
}
