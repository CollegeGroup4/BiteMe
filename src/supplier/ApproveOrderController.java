
	package supplier;

	import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.tools.javac.jvm.Items;

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
import logic.Order;

	public class ApproveOrderController implements Initializable {

	    @FXML
	    private AnchorPane Na1;

	    @FXML
	    private TableView<Order> OrdersTable;

	    @FXML
	    private TableColumn<Order,Integer> Ordercol;

	    @FXML
	    private TableColumn<Order,String> Usercol;

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

	    
	    ObservableList<Order> OrderList = FXCollections.observableArrayList();
	    Order order = new Order(30, 0, null, null, 0, "13:52", "Delivery", "Avi", "0526268887", 0,null , null, null, false, true);
	    Order order2 = new Order(31, 0, null, null, 0, "13:20", "Delivery", "Shani", "0524441117", 0,null , null, null, false, true);
	    
	    
	    @FXML
	    void Back(ActionEvent event) {
	    	System.out.println("Supplier Page");//
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			Pane root;
			try {
				root = loader.load(getClass().getResource("/supplier/H.R.fxml").openStream());
				
				Scene scene = new Scene(root);

				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				System.out.println("Erorr: Could not open H.R Page");
				e.printStackTrace();
				
			}
	    }
	    void FromJson() {
	
			Request request=new Request();
			request.setPath("/restaurants/areas");////should by orders !
			request.setMethod("GET");
			Gson gson=new Gson();
			JsonElement body=gson.toJsonTree(new Object());
	
			
			body.getAsJsonObject().addProperty("area","All");// String 2 is the current restaurant ID (!!!)
			
			request.setBody(gson.toJson(body));
			ClientUI.chat.accept(gson.toJson(request));
			OrderList.addAll(gson.fromJson((String)ChatClient.serverAns.getBody(),Order[].class));
		}
	    @FXML
	    void LogOut(ActionEvent event) {

	    }
	    void sendtoserver(Order approve) {
	    	
			Request request=new Request();
			request.setPath("/restaurants/approveOrder");
			request.setMethod("POST");
			Gson gson=new Gson();
		
			request.setBody(gson.toJson(approve));
			ClientUI.chat.accept(gson.toJson(request));
			
			if(ChatClient.serverAns.getCode()!= 200 && ChatClient.serverAns.getCode()!= 201 ) {
				
				//Warning
			}
		
			
		}
	    @FXML
	    void onapprove(ActionEvent event) {

	    	ObservableList<Order>  orderstoapprove=OrdersTable.getSelectionModel().getSelectedItems();

	    	for(Order  approve :orderstoapprove) {
	    		
	    		approve.setIsApproved(true);
	    		sendtoserver(approve);
	    		OrdersTable.getItems().remove(approve);
	    	}
	    	/// ******** need to save update into DB
	    	
	    }

	   
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			
			FromJson();
			/// here i will enter all orders that not approved for this supplier ///
			
			//OrderList.addAll(order,order2);
			
			
			
			Usercol.setCellValueFactory(new PropertyValueFactory<Order, String>("userName"));
			Ordercol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
			Typecol.setCellValueFactory(new PropertyValueFactory<Order, String>("type_of_order"));
			Timecol.setCellValueFactory(new PropertyValueFactory<Order, String>("required_time"));
			phonecol.setCellValueFactory(new PropertyValueFactory<Order, String>("phone"));
			
			
			OrdersTable.setItems(OrderList);
			
			
			
			
		}

	

}
