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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Item;
import logic.Options;
import logic.item_in_menu;
import temporaryDatabase.myOwnDatabase;

public class SelectionsController implements Initializable, EventHandler<ActionEvent> {

	public static HashMap<String, String> optionsSelectedHash;
	public static ArrayList<Options> optionsSelectedArr = new ArrayList<>();
	@FXML
	private Label itemName;

	@FXML
	private Label priceOfItem;

	@FXML
	private TextField quantityOfItem;

	@FXML
	private HBox allOptionals;

	@FXML
	private HBox optionalsSelected;

	@FXML
	private Button clearBtn;

	@FXML
	private Button submitBtn;

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("IngredientsAnd.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> b = new ArrayList<Button>();
		optionsSelectedHash = null;
		quantityOfItem.setText("0");
		itemName.setText(ItemsFromMenuController.itemSelected.getName());
		priceOfItem.setText(String.valueOf(ItemsFromMenuController.itemSelected.getPrice()));
		if (ItemsFromMenuController.itemSelected.getOptions() != null)
			for (int i = 0; i < ItemsFromMenuController.itemSelected.getOptions().length; i++) {
				String categotyName = ItemsFromMenuController.itemSelected.getOptions()[i].getOption_category();
				String specify = ItemsFromMenuController.itemSelected.getOptions()[i].getSpecify_option();
				double price = ItemsFromMenuController.itemSelected.getOptions()[i].getPrice();
				Button temp = new Button(categotyName + ":" + "\n" + specify + "-" + "$" + price);
				temp.setOnAction(this);
				b.add(temp);
				/*
				 * String specifies[] =
				 * ItemsFromMenuController.itemSelected.getOptions()[i].getSpecify_option().
				 * split(",", 0);
				 * 
				 * for (String a : specifies) { Button temp = new Button(a);
				 * temp.setOnAction(this); b.add(temp); }
				 */
			}
		allOptionals.getChildren().addAll(b);
	}

	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = buttonRecognize(event);
		if (optionsSelectedHash == null)
			optionsSelectedHash = new HashMap<String, String>();
		if (optionsSelectedHash.containsKey(stringFromEvent)) {
		} else {
			optionsSelectedHash.put(stringFromEvent, null);
			optionalsSelected.getChildren().add(new Button(stringFromEvent));
		}

		// System.out.println(ingredientsSelectedArr.toString());

	}

	private String buttonRecognize(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent = stringFromEvent.split("'", 2)[1];
		stringFromEvent = stringFromEvent.substring(0, stringFromEvent.length() - 1);
		return stringFromEvent;
	}

	@FXML
	void clear(ActionEvent event) {
		optionsSelectedHash = null;
		optionalsSelected.getChildren().clear();

	}

	@FXML
	void submit(ActionEvent event) {
		if (ItemsFromMenuController.itemsSelectedArr == null)
			ItemsFromMenuController.itemsSelectedArr = new ArrayList<>();
		/*
		 * StringBuilder str = new StringBuilder(); boolean flag = true; for (String a :
		 * optionsSelectedHash.keySet()) { if (flag == true) { str.append(a); flag =
		 * false; } else { str.append("," + a); } }
		 */

		// Options tempOpt = new Options("", "", 0,
		// ItemsFromMenuController.itemSelected.getItemID());
		// optionsSelectedArr.add(tempOpt);
		int i = 0;
		Options optionsArr[] = new Options[optionsSelectedHash.size()];
		for (String a : optionsSelectedHash.keySet()) {
			String category = a.split(":", 0)[0];
			String specify = a.split("\n", 0)[1].split("-", 0)[0];
			double price = Double.valueOf(a.split("-", 0)[1].substring(1, a.split("-", 0)[1].length()));
			optionsArr[i] = new Options(category, specify, price, ItemsFromMenuController.itemSelected.getItemID());
			ItemsFromMenuController.itemSelected.setPrice(ItemsFromMenuController.itemSelected.getPrice()+(float)price);
		}
		ItemsFromMenuController.itemSelected.setOptions(optionsArr);
		ItemsFromMenuController.itemSelected.setAmount(Integer.valueOf(quantityOfItem.getText()));
		ItemsFromMenuController.itemSelected.setPrice(Integer.valueOf(quantityOfItem.getText())
				* ItemsFromMenuController.itemSelected.getPrice());
		ItemsFromMenuController.itemsSelectedArr.add(ItemsFromMenuController.itemSelected);
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Summary.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Hide all screens to prepare for another dish
		PrepareADishController.prepareADishController.getPaneForSummary().setCenter(root);
		PrepareADishController.prepareADishController.getPaneForSelections().setCenter(null);
		PrepareADishController.prepareADishController.getPaneForItems().setCenter(null);
		PrepareADishController.prepareADishController.getPaneForCourses().setCenter(null);

		// **************** for check only ******************
		//for (int j = 0; j < ItemsFromMenuController.itemsSelectedArr.size(); j++) {
		//	System.out.println(ItemsFromMenuController.itemsSelectedArr.get(j).getName());
			//System.out.println(ItemsFromMenuController.itemsSelectedArr.get(j).getAmount());
		//}

	}

}
