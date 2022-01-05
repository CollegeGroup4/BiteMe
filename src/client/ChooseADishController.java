package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;

import common.Response;
import common.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.Item;
import logic.Menu;
import logic.item_in_menu;
/**
 * This class made for the dish chosen process
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */
public class ChooseADishController implements Initializable {

	public static ChooseADishController chooseADishController = null;
	private CustomerFunctions customerFunctions = new CustomerFunctions();
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
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private Label welcomeLabel;
	@FXML
	private Label lableErrorMag;

	/**
	 * Setting initial display values and data structures for the page
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeLabel.setText("Welcome, " + CustomerPageController.client.getFirstName());
		customerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
		/// ---------------------------------------------
		System.out.println("path:" + System.getProperty("user.dir"));
		/// ---------------------------------------------
		sentToServer();
		response();
		if (itemsSelectedArr == null)
			itemsSelectedArr = new ArrayList<>();
		paneForSelections.setVisible(false);
		setComboBoxMenu();// --create menu list--
		comboBoxMenu.setValue(menusArr.get(0).getName());
		setComboBoxCourses();// --create course list--
		setComboBoxCategory();// --create category list--
		comboBoxCategory.setDisable(true);
		shoppingCart.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> handeleClickShoppingCart(e));
		SummaryController.withRemoveBtn = true;
		try {
			setItemContainer();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * A function that is activated when selecting a menu
	 * 
	 * @param event
	 * @throws FileNotFoundException
	 */
	@FXML
	void onClickMenu(ActionEvent event) throws FileNotFoundException {
		setComboBoxCourses();
		setComboBoxCategory();
		setItemContainer();
		comboBoxCategory.setDisable(true);
	}

	/**
	 * A function that is activated when selecting a course
	 * 
	 * @param event
	 * @throws FileNotFoundException
	 */
	@FXML
	void onClickCourse(ActionEvent event) throws FileNotFoundException {
		setComboBoxCategory();
		setItemContainer();
		comboBoxCategory.setDisable(false);
	}

	/**
	 * A function that is activated when selecting category
	 * 
	 * @param event
	 * @throws FileNotFoundException
	 */
	@FXML
	void onClickCategory(ActionEvent event) throws FileNotFoundException {
		setItemContainer();
	}

	/**
	 * Displays the order summary for the selected items
	 * 
	 * @param event
	 */
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

	/**
	 * Creates the items display component
	 * 
	 * @throws FileNotFoundException
	 */
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

					dishes.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> handeleClickDish(e, item.getItemID()));
					itemList.add(dishes);
					ItemContainer.getChildren().clear();
					ItemContainer.getChildren().addAll(itemList);
				}
			}
		}
	}

	/**
	 * Activate when clicking on item, in order to select item details
	 * 
	 * @param event
	 * @param itemID
	 */
	private void handeleClickDish(MouseEvent event, int itemID) {
		setVisibleItemContainer(false);
		itemSelected = allItems.get(itemID);
		Item temp = new Item(itemSelected.getCategory(), itemSelected.getSubcategory(), itemID,
				itemSelected.getRestaurantID(), itemSelected.getName(), itemSelected.getPrice(),
				itemSelected.getDescription(), itemSelected.getIngrediants(), itemSelected.getOptions(),
				itemSelected.getPhoto(), itemSelected.getAmount());
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Selections.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		paneForSelections.setCenter(root);
		allItems.put(itemID, temp);
	}

	/**
	 * Insert menus options to a ComboBox
	 */
	private void setComboBoxMenu() {
		String[] manuList = new String[menusArr.size()];
		for (int i = 0; i < menusArr.size(); i++)
			manuList[i] = (menusArr.get(i).getName());
		comboBoxMenu.getItems().setAll(manuList);
	}

	/**
	 * insert courses options to a ComboBox
	 */
	private void setComboBoxCourses() {
		getMenuName();// ===== get the menu name =====
		HashMap<String, Integer> courseList = new HashMap<>();
		for (int i = 0; i < menuSelected.getItems().length; i++) {
			courseList.put(menuSelected.getItems()[i].getCourse(), null);
		}
		comboBoxCourse.getItems().clear();
		for (String course : courseList.keySet())
			comboBoxCourse.getItems().add(course);
	}

	/**
	 * Search for the selected menu from all the menus
	 */
	private void getMenuName() {
		for (int i = 0; i < menusArr.size(); i++) {
			if (menusArr.get(i).getName().equals(comboBoxMenu.getValue()))
				menuSelected = menusArr.get(i);
		}
	}

	/**
	 * insert categories options to a ComboBox
	 */
	private void setComboBoxCategory() {
		setCategoriesInformation();
		comboBoxCategory.getItems().clear();
		for (String category : categories.keySet()) {
			comboBoxCategory.getItems().add(category);
		}
	}

	/**
	 * Find all categories for a specific menu
	 */
	private void setCategoriesInformation() {
		categories = new HashMap<>();
		item_in_menu[] itemsInMenu = menuSelected.getItems();

		for (int i = 0; i < itemsInMenu.length; i++)
			categories.put(allItems.get(itemsInMenu[i].getItemID()).getCategory(), null);
	}

	/**
	 * Sends the restaurantID to the server, to get its items and menus
	 */
	void sentToServer() {
		Gson gson = new Gson();
		Request request = new Request();
		request.setPath("/restaurants/menus");
		request.setMethod("GET");
		JsonElement body = gson.toJsonTree(new Object());
		body.getAsJsonObject().addProperty("restaurantID", ChooseRestaurantController.restaurantSelected.getId());
		request.setBody(gson.toJson(body));
		String jsonUser = gson.toJson(request);
		try {
			ClientUI.chat.accept(jsonUser);
		} catch (NullPointerException e) {
			System.out.println("get menus by restaurand ID didn't work");
		}
	}

	/**
	 * Received restaurant's data (items,menus) the divide into data structures
	 */
	private void response() {
		Gson gson = new Gson();
		menusArr = new ArrayList<Menu>();
		allItems = new HashMap<>();
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableErrorMag.setText(response.getDescription());
		else {
			JsonElement a = gson.fromJson((String) response.getBody(), JsonElement.class);
			Menu[] menuList = gson.fromJson(a.getAsJsonObject().get("menues"), Menu[].class);
			Item[] itemList = gson.fromJson(a.getAsJsonObject().get("items"), Item[].class);
			for (Menu r : menuList)
				menusArr.add(r);
			for (Item i : itemList) {
				allItems.put(i.getItemID(), i);
			}
		}
	}

	/**
	 * Turn on/off items container
	 * 
	 * @param boolean variable
	 */
	public void setVisibleItemContainer(boolean visible) {
		ItemContainer.setVisible(visible);
		scrolePaneListItems.setVisible(visible);
		paneForSelections.setVisible(!visible);
	}

	/**
	 * Loading the previous screen (Choose Restaurant)
	 * 
	 * @param event
	 */
	@FXML
	void goBack(ActionEvent event) {
		customerFunctions.reload(event, "ChooseRestaurant.fxml", "Choose Restaurant");
	}

	/**
	 * Loading the next screen (DeliveryAndTime)
	 * 
	 * @param event
	 */
	@FXML
	void next(ActionEvent event) {
		SummaryController.withRemoveBtn = false;
		customerFunctions.reload(event, "DeliveryAndTime.fxml", "Delivery And Time");
	}

	/**
	 * Loading home page
	 * 
	 * @param event
	 */
	@FXML
	void home(ActionEvent event) {
		customerFunctions.home(event);
	}

	/**
	 * Disconnect the user from the system
	 * 
	 * @param event
	 */
	@FXML
	void logout(ActionEvent event) {
		customerFunctions.logout(event);
	}

	/**
	 * Returns the Border Pane that displays the summary table
	 * 
	 * @return
	 */
	public BorderPane getPaneForSummary() {
		return paneForSummary;
	}

	/**
	 * Returns the Border Pane that displays the item's options to choose
	 * 
	 * @return
	 */
	public BorderPane getPaneForSelections() {
		return paneForSelections;
	}
}
