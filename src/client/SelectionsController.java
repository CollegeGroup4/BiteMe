package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.Item;
import logic.Options;
/**
 * This class made for select optional ingredients and quantity
 * for each item selected
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */
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

	/**
	 * Setting initial data structures Display item's details that the user can
	 * choose
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> listOptional = new ArrayList<Button>();
		optionsSelectedHash = null;
		quantityOfItem.setText("0");
		itemName.setText(ChooseADishController.itemSelected.getName());
		priceOfItem.setText(String.valueOf(ChooseADishController.itemSelected.getPrice()));
		if (ChooseADishController.itemSelected.getOptions() != null)
			for (int i = 0; i < ChooseADishController.itemSelected.getOptions().length; i++) {
				String categotyName = ChooseADishController.itemSelected.getOptions()[i].getOption_category();
				String specify = ChooseADishController.itemSelected.getOptions()[i].getSpecify_option();
				double price = ChooseADishController.itemSelected.getOptions()[i].getPrice();
				JFXButton categotyBtn = new JFXButton(categotyName + ":" + "\n" + specify + "-" + "$" + price);
				designButton(categotyBtn);
				categotyBtn.setOnAction(this);
				listOptional.add(categotyBtn);
			}
		allOptionals.getChildren().addAll(listOptional);
		allOptionals.setSpacing(5);
	}

	/**
	 * A function that design a button
	 * 
	 * @param button
	 */
	private void designButton(JFXButton button) {
		button.setPrefHeight(43);
		BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);
		button.setBackground(background);
		Font font = Font.font("Arial", FontWeight.BOLD, 12);
		button.setFont(font);
	}

	/**
	 * An action for each button (item's option) that the user choose
	 */
	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = buttonRecognize(event);
		if (optionsSelectedHash == null)
			optionsSelectedHash = new HashMap<String, String>();
		if (optionsSelectedHash.containsKey(stringFromEvent)) {
		} else {
			if (stringFromEvent.split(":", 2)[0].equals("Select") || checkIfDuplicate(stringFromEvent)) {
				optionsSelectedHash.put(stringFromEvent, null);
				JFXButton optionalsSelectedBtn = new JFXButton(stringFromEvent);
				designButton(optionalsSelectedBtn);
				optionalsSelected.getChildren().add(optionalsSelectedBtn);
				optionalsSelected.setSpacing(5);
			}
		}
	}

	/**
	 * Checks if duplicate choose was made (for optional selections)
	 * 
	 * @param string
	 * @return
	 */
	private boolean checkIfDuplicate(String stringFromEvent) {
		String category = stringFromEvent.split(":", 2)[0];
		for (String a : optionsSelectedHash.keySet()) {
			if (a.split(":", 2)[0].equals(category)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Identifies the selected button according to the displayed text
	 * 
	 * @param event
	 * @return
	 */
	private String buttonRecognize(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent = stringFromEvent.split("'", 2)[1];
		stringFromEvent = stringFromEvent.substring(0, stringFromEvent.length() - 1);
		return stringFromEvent;
	}

	/**
	 * Clears selected options
	 * 
	 * @param event
	 */
	@FXML
	void clear(ActionEvent event) {
		optionsSelectedHash = null;
		optionalsSelected.getChildren().clear();
	}

	/**
	 * Confirm product selection and add optional components
	 * @param event
	 */
	@FXML
	void submit(ActionEvent event) {
		int i = 0;
		if (optionsSelectedHash != null) {
			Options optionsArr[] = new Options[optionsSelectedHash.size()];
			for (String a : optionsSelectedHash.keySet()) {
				String category = a.split(":", 0)[0];
				String specify = a.split("\n", 0)[1].split("-", 0)[0];
				double price = Double.valueOf(a.split("-", 0)[1].substring(1, a.split("-", 0)[1].length()));
				optionsArr[i] = new Options(category, specify, price, ChooseADishController.itemSelected.getItemID(),
						false);
				ChooseADishController.itemSelected
						.setPrice(ChooseADishController.itemSelected.getPrice() + (float) price);
			}
			ChooseADishController.itemSelected.setOptions(optionsArr);

		} else {
			ChooseADishController.itemSelected.setOptions(null);
		}
		ChooseADishController.itemSelected.setAmount(Integer.valueOf(quantityOfItem.getText()));
		ChooseADishController.itemSelected
				.setPrice(Integer.valueOf(quantityOfItem.getText()) * ChooseADishController.itemSelected.getPrice());

		addItemToCart();
		ChooseADishController.chooseADishController.getPaneForSelections().setCenter(null);
		ChooseADishController.chooseADishController.setVisibleItemContainer(true);

	}
/**
 * Adds the selected item to the shopping cart
 */
	private void addItemToCart() {
		// is the user didn't chose amount then the item doesn't add to the cart
		if (ChooseADishController.itemSelected.getAmount() == 0)
			return;
		boolean flag = true;
		for (Item item : ChooseADishController.itemsSelectedArr)
			if (item.getName().equals(ChooseADishController.itemSelected.getName())) {
				item.setAmount(item.getAmount() + ChooseADishController.itemSelected.getAmount());
				item.setPrice(item.getPrice() + ChooseADishController.itemSelected.getPrice());
				flag = false;
			}
		if (flag) // if the item isn't already in the cart then we add him.
			ChooseADishController.itemsSelectedArr.add(ChooseADishController.itemSelected);
	}
/**
 * Function for decreasing the amount of product activated by a button
 * @param event
 */
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
/**
 * Function for increasing the amount of product activated by a button
 * @param event
 */
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
