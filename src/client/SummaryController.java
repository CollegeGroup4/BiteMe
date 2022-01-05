package client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import logic.Item;

/**
 * This class made for showing summary of the order
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */
public class SummaryController implements Initializable {
	public static boolean withRemoveBtn;
	@FXML
	private TableView<Item> table;

	@FXML
	private TableColumn<Item, String> colItemName;

	@FXML
	private TableColumn<Item, Integer> colAmount;

	@FXML
	private TableColumn<Item, Float> colPrice;

	@FXML
	private Button btnRemove;

	private ObservableList<Item> itemsList = FXCollections.observableArrayList();
	private ObservableList<Item> itemSelect;

	/**
	 * Initialize Table View
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		insertitemsToTbl();
		colItemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		colAmount.setCellValueFactory(new PropertyValueFactory<Item, Integer>("amount"));
		colPrice.setCellValueFactory(new PropertyValueFactory<Item, Float>("price"));
		table.setItems(itemsList);
		btnRemove.setVisible(withRemoveBtn);
	}

	/**
	 * Insert items to the table
	 */
	public void insertitemsToTbl() {
		for (int i = 0; i < ChooseADishController.itemsSelectedArr.size(); i++) {
			itemsList.add(ChooseADishController.itemsSelectedArr.get(i));
		}
	}

	/**
	 * Event for clicking on a row
	 * 
	 * @param event
	 */
	@FXML
	void ClickOnTable(MouseEvent event) {
		itemSelect = table.getSelectionModel().getSelectedItems();
	}

	/**
	 * Remove item from the shopping cart
	 * 
	 * @param event
	 */
	@FXML
	void removeItem(ActionEvent event) {
		if (itemSelect != null)
			itemsList.remove(itemSelect.get(0));

	}
}
