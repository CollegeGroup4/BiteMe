
package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import supplier.SupplierFunction;
import supplier.SupplierController;
import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Item;
import logic.Order;

public class ApproveOrderController implements Initializable {
	/**
	 * This class made for supplier order approve process
	 *
	 * @author Or Biton
	 * @author Einan Choen
	 * @author Tal Yehoshua
	 * @author Moshe Pretze;
	 * @author Tal-chen Ben-Eliyahu
	 * @version January 2022
	 * 
	 */
	@FXML
	private AnchorPane Na1;

	@FXML
	private TableView<Order> OrdersTable;

	@FXML
	private TableColumn<Order, Integer> Ordercol;

	@FXML
	private TableColumn<Order, String> Usercol;

	@FXML
	private TableColumn<Order, String> Typecol;

	@FXML
	private TableColumn<Order, String> Timecol;
	@FXML
	private TableColumn<Order, String> phonecol;

	@FXML
	private Button backbutton;

	@FXML
	private Button approvebutton;

	@FXML
	private HBox Nav;

	@FXML
	private Button LogOut;
	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;
	private SupplierFunction supplierfunction = new SupplierFunction();
	ObservableList<Order> OrderList = FXCollections.observableArrayList();
	
	/**
	 * This Method made to give us the option to get back to supplier screen
	 * 
	 * @param event
	 */

	@FXML
	void Back(ActionEvent event) {

		System.out.println("Supplier Page");//
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/SupplierPage.fxml").openStream());

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open H.R Page");
			e.printStackTrace();

		}
	}
	/**
	 * This Method made to get all the restaurant orders that not approved from DB
	 * 
	 */
	void FromJson() {

		Request request = new Request();
		request.setPath("/orders");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		body.getAsJsonObject().addProperty("restaurantID",SupplierController.resturant.getId());// String 2 is the current restaurant ID (!!!)
		

		request.setBody(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));

		Order[] orders = (gson.fromJson((String) ChatClient.serverAns.getBody(), Order[].class));

		for (Order approve : orders) {
			if(approve.isApproved()==false)
				OrderList.add(approve);

		}

	}
	/**
	 * This Method made to allow us to get back to the home screen
	 * @param action
	 */
	@FXML
	void Home(ActionEvent action) {
		supplierfunction.home(action);
	}
	/**
	 * This Method made to logout from system
	 * @param event
	 */
	@FXML
	void LogOut(ActionEvent event) {
		supplierfunction.logout(event);
	}
/**
 * This Method made to make the chosen order approve in the DB  
 * @param approve
 */
	void sendtoserver(Order approve) {

		Request request = new Request();
		request.setPath("/restaurants/approveOrder");
		request.setMethod("POST");
		Gson gson = new Gson();
		
		JsonElement j=gson.toJsonTree(new Object());
		
		j.getAsJsonObject().addProperty("orderID",approve.getOrderID());
		request.setBody(gson.toJson(j));
		ClientUI.chat.accept(gson.toJson(request));

		if (ChatClient.serverAns.getCode() != 200 && ChatClient.serverAns.getCode() != 201) {

			// Warning
		}

	}
/**
 * This Method made to allow us when approve button is pressed to change it and send to DB
 * @param event
 */
	@FXML
	void onapprove(ActionEvent event) {

		ObservableList<Order> orderstoapprove = OrdersTable.getSelectionModel().getSelectedItems();

		sendtoserver(orderstoapprove.get(0));
		OrdersTable.getItems().remove(orderstoapprove.get(0));



	}
	/**
	 * This Method made to initialize all the buttons and table requirements
	 *@param location resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/// here i will enter all orders that not approved for this supplier ///

	
		FromJson();

		supplierfunction.initializeNavigation_SidePanel(myHamburger, drawer);

		Usercol.setCellValueFactory(new PropertyValueFactory<Order, String>("userName"));
		Ordercol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
		Typecol.setCellValueFactory(new PropertyValueFactory<Order, String>("type_of_order"));
		Timecol.setCellValueFactory(new PropertyValueFactory<Order, String>("required_time"));
		phonecol.setCellValueFactory(new PropertyValueFactory<Order, String>("phone"));

		OrdersTable.setItems(OrderList);

	}

}
