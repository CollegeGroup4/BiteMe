package mywork;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Item;
import logic.Options;
import logic.Order;

public class SummaryController implements Initializable {

	@FXML
	private TableView<Item> table;

	@FXML
	private TableColumn<Item, String> colItemName;

	@FXML
	private TableColumn<Item, Integer> colItemID;

	@FXML
	private TableColumn<Item, Integer> colAmount;

	@FXML
	private TableColumn<Item, String> colOptionals;

	@FXML
	private TableColumn<Item, Float> colPrice;

	private ObservableList<Item> itemsList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		insertitemsToTbl();
		colItemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		colItemID.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemID"));
		colAmount.setCellValueFactory(new PropertyValueFactory<Item, Integer>("amount"));
		colOptionals.setCellValueFactory(new PropertyValueFactory<Item, String>("options"));
		colPrice.setCellValueFactory(new PropertyValueFactory<Item, Float>("price"));
		table.setItems(itemsList);

	}

	public void insertitemsToTbl() {
		for (int i = 0; i < ItemsFromMenuController.itemsSelectedArr.size(); i++) {
			// System.out.println(ItemsFromMenuController.itemsSelectedArr.get(i).getName());
			itemsList.add(ItemsFromMenuController.itemsSelectedArr.get(i));

		}
	}

}
