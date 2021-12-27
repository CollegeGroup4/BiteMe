package mywork;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
		ArrayList<Button> b = new ArrayList<Button>();
		getInformation();
		for (String i : categories.keySet()) {
			Button temp = new Button(i);
			temp.setOnAction(this);
			b.add(temp);
		}
		categoriesContainer.getChildren().addAll(b);

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
		ChooseADishController.chooseADishController.getPaneForCourses().setTop(new Label("Choose Item:"));
		
	}

}
