package client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Server.Response;
import common.Request;
import guiNew.Navigation_SidePanelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * This class contain common functions
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */
public class CustomerFunctions {

	/**
	 * function that replace the pages
	 * 
	 * @param event
	 * @param resource
	 * @param title
	 */
	public void reload(ActionEvent event, String resource, String title) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource(resource).openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle(title);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * Initialize navigation bar that switch between pages
 * @param myHamburger
 * @param drawer
 */
	public void initializeNavigation_SidePanel(JFXHamburger myHamburger, JFXDrawer drawer) {
		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/guiNew/Navigation_SidePanel.fxml"));
			drawer.setSidePane(anchorPane);
		} catch (IOException e) {
			Logger.getLogger(Navigation_SidePanelController.class.getName()).log(Level.SEVERE, null, e);
		}
		// transition animation of hamburger icon
		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(myHamburger);
		drawer.setVisible(false);
		transition.setRate(-1);

		// click event - mouse click
		myHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawer.isOpened()) {
				drawer.setVisible(false);
				drawer.close(); // this will close slide pane
			} else {
				drawer.open(); // this will open slide pane
				drawer.setVisible(true);
			}
		});
	}

	/**
	 * Sending data to the database
	 * @param String path
	 * @param String method
	 * @param object
	 * @param String errorMsg
	 */
	public void sentToJson(String path, String method, Object object, String errorMsg) {
		Gson gson = new Gson();
		Request request = new Request();
		request.setPath(path);
		request.setMethod(method);
		request.setBody(gson.toJson(object));
		String jsonUser = gson.toJson(request);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println(errorMsg);
		}
	}
	
/**
 * Returning home
 * @param event
 */
	public void home(ActionEvent event) {
		reload(event, "/client/CustomerPage.fxml", "Customer - home");
	}

	public void exit(ActionEvent event) {
		System.out.println("exit client Tool");
		logoutSentToJson();
		logoutResponse();
		Request request = new Request();
		request.setPath("/exit");
		Gson gson = new Gson();
		String jsonUser = gson.toJson(request);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("Exit - new ClientController didn't work");
		}
		System.exit(0);
	}
/**
 * Logout functionalities
 * @param event
 */
	public void logout(ActionEvent event) {
		logoutSentToJson();
		logoutResponse();
		reload(event, "/guiNew/HomePage.fxml", "Home page");
	}
/**
 * Disconnect the user from the system
 */
	public void logoutSentToJson() {
		Gson gson = new Gson();
		JsonElement jsonElem = gson.toJsonTree(new Object());
		jsonElem.getAsJsonObject().addProperty("userName", CustomerPageController.client.getUserName());

		Request request = new Request();
		request.setPath("/users/logout");
		request.setMethod("GET");
		request.setBody(gson.toJson(jsonElem));
		try {
			ClientUI.chat.accept(gson.toJson(request)); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("new ClientController didn't work");
		}
	}

/**
 * Receiving response to the logout request
 */
	public void logoutResponse() {
		Response response = ChatClient.serverAns;
		System.out.println("-->>" + response.getDescription()); // Description from server
	}

}
