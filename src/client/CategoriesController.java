package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.Category;
import logic.Item;
import logic.item_in_menu;

public class CategoriesController implements Initializable, EventHandler<ActionEvent> {
	
	public static HashMap<String,Integer>categories;
	public static String categorySelected;

	@FXML
	private VBox categoriesContainer;
	
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Categories.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> listCategories = new ArrayList<Button>();
		getInformation();
		for (String i : categories.keySet()) {
			JFXButton CategoryBtn = new JFXButton(i);
			designButton(CategoryBtn);
			CategoryBtn.setOnAction(this);
			listCategories.add(CategoryBtn);
		}
		categoriesContainer.getChildren().addAll(listCategories);
		Label chooseManu = new Label("Choose Category:");
		designLabel(chooseManu);
	}
	private void designLabel(Label label) {
		Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 16);
		label.setFont(font);
	}

	private void designButton(JFXButton button) {
		button.setPrefWidth(160);
//		button
		BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);
		button.setBackground(background);
		Font font = Font.font("Arial", FontWeight.BOLD, 15);
		button.setFont(font);
	}
	private void getInformation() {// find all categories for a specific menu
		categories= new HashMap<>();
		item_in_menu[] itemsInMenu=ChooseADishController.menuSelected.getItems();
		ArrayList<Item> allItems=ChooseADishController.itemsArr;
	
		for(int i=0;i<itemsInMenu.length;i++) {
			for(int j=0;j<allItems.size();j++) {
				if(itemsInMenu[i].getItemID()==allItems.get(j).getItemID())
					categories.put(allItems.get(j).getCategory(), null);
			}
		} 

	}

	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent = stringFromEvent.split("'", 2)[1];
		categorySelected = stringFromEvent.substring(0, stringFromEvent.length() - 1);
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("ItemsFromMenu.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ChooseADishController.chooseADishController.getPaneForCourses().setCenter(root);
		Label chooseItem = new Label("Choose Item:");
		designLabel(chooseItem);
		ChooseADishController.chooseADishController.getPaneForCourses().setTop(chooseItem);
		
	}

}
