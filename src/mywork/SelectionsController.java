package mywork;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
	private Button btnIncrease;

	@FXML
	private Button btnDecrease;

	@FXML
	private HBox allOptionals;

	@FXML
	private HBox optionalsSelected;

	@FXML
	private TextArea additionalInstructions;

	@FXML
	private JFXButton clearBtn;

	@FXML
	private JFXButton submitBtn;

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("IngredientsAnd.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> listOptional = new ArrayList<Button>();
		optionsSelectedHash = null;
		quantityOfItem.setText("0");
		itemName.setText(ItemsFromMenuController.itemSelected.getName());
//		designLabel(itemName);
		priceOfItem.setText(String.valueOf(ItemsFromMenuController.itemSelected.getPrice()));
		if (ItemsFromMenuController.itemSelected.getOptions() != null)
			for (int i = 0; i < ItemsFromMenuController.itemSelected.getOptions().length; i++) {
				String categotyName = ItemsFromMenuController.itemSelected.getOptions()[i].getOption_category();
				String specify = ItemsFromMenuController.itemSelected.getOptions()[i].getSpecify_option();
				double price = ItemsFromMenuController.itemSelected.getOptions()[i].getPrice();
				JFXButton categotyBtn = new JFXButton(categotyName + ":" + "\n" + specify + "-" + "$" + price);
				designButton(categotyBtn);
				categotyBtn.setOnAction(this);
				listOptional.add(categotyBtn);
			}
		allOptionals.getChildren().addAll(listOptional);
		allOptionals.setSpacing(5);
	}

//	private void designLabel(Label label) {
//		Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 16);
//		label.setFont(font);
//	}

	private void designButton(JFXButton button) {
		button.setPrefHeight(43);
		BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);
		button.setBackground(background);
		Font font = Font.font("Arial", FontWeight.BOLD, 12);
		button.setFont(font);
	}

	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = buttonRecognize(event);
		if (optionsSelectedHash == null)
			optionsSelectedHash = new HashMap<String, String>();
		if (optionsSelectedHash.containsKey(stringFromEvent)) {
		} else {
			optionsSelectedHash.put(stringFromEvent, null);
			JFXButton optionalsSelectedBtn = new JFXButton(stringFromEvent);
			designButton(optionalsSelectedBtn);
			optionalsSelected.getChildren().add(optionalsSelectedBtn);
			optionalsSelected.setSpacing(5);
		}
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
		int i = 0;
		Options optionsArr[] = new Options[optionsSelectedHash.size()];
		for (String a : optionsSelectedHash.keySet()) {
			String category = a.split(":", 0)[0];
			String specify = a.split("\n", 0)[1].split("-", 0)[0];
			double price = Double.valueOf(a.split("-", 0)[1].substring(1, a.split("-", 0)[1].length()));
			optionsArr[i] = new Options(category, specify, price, ItemsFromMenuController.itemSelected.getItemID());
			ItemsFromMenuController.itemSelected
					.setPrice(ItemsFromMenuController.itemSelected.getPrice() + (float) price);
		}
		ItemsFromMenuController.itemSelected.setOptions(optionsArr);
		ItemsFromMenuController.itemSelected.setAmount(Integer.valueOf(quantityOfItem.getText()));
		ItemsFromMenuController.itemSelected
				.setPrice(Integer.valueOf(quantityOfItem.getText()) * ItemsFromMenuController.itemSelected.getPrice());
		ItemsFromMenuController.itemsSelectedArr.add(ItemsFromMenuController.itemSelected);
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Summary.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ChooseADishController.chooseADishController.getPaneForSummary().setCenter(root);
		ChooseADishController.chooseADishController.getPaneForCourses().setTop(new Label("Choose Menu:"));
		ChooseADishController.chooseADishController.getPaneForCourses()
				.setCenter(ChooseADishController.chooseADishController.getSpMenus());
		ChooseADishController.chooseADishController.getPaneForSelections().setCenter(null);
		ChooseADishController.chooseADishController.getItemInfo().setVisible(false);
		// **************** for check only ******************
		// for (int j = 0; j < ItemsFromMenuController.itemsSelectedArr.size(); j++) {
		// System.out.println(ItemsFromMenuController.itemsSelectedArr.get(j).getName());
		// System.out.println(ItemsFromMenuController.itemsSelectedArr.get(j).getAmount());
		// }
	}

	@FXML
	void decrease(ActionEvent event) {
		int tmp;
		if (quantityOfItem.getText() != null)
			if (Integer.valueOf(quantityOfItem.getText()) > 0) {
				tmp = Integer.valueOf(quantityOfItem.getText());
				tmp--;
				quantityOfItem.setText(String.valueOf(tmp));
			}
	}

	@FXML
	void increase(ActionEvent event) {
		int tmp;
		if (quantityOfItem.getText() != null) {
			tmp = Integer.valueOf(quantityOfItem.getText());
			tmp++;
			quantityOfItem.setText(String.valueOf(tmp));
		}
	}
}
