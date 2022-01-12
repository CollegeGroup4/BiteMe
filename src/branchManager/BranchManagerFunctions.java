package branchManager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Server.Response;
import client.ChatClient;
import client.ClientUI;
import common.Request;
import guiNew.IsendToJson;
import guiNew.Navigation_SidePanelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class is for the branch manager generic functions.
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */

public class BranchManagerFunctions implements IsendToJson{

	/**
	 * function that replace the pages
	 * 
	 * @param event
	 * @param resource
	 * @param title    - title of the page
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
	 * A generic method that sends to a server based on the information it receives,
	 * Convert to json and send to server
	 * 
	 * @param String path
	 * @param String method
	 * @param Object body
	 * @param String errorMsg
	 */
	public void sentToJson(String path, String method, Object body, String errorMsg) {
		Gson gson = new Gson();
		Request request = new Request();
		request.setPath(path);
		request.setMethod(method);
		request.setBody(gson.toJson(body));
		String jsonUser = gson.toJson(request);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println(errorMsg);
		}
	}

	/**
	 * A method that loads the initializeNavigation_SidePanel and inserts an event
	 * on each click on myHamburger
	 * 
	 * @param myHamburger
	 * @param drawer
	 */
	@SuppressWarnings({ "restriction", "restriction", "restriction", "restriction" })
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
	 * A method that returns to the branch manager's home screen
	 * 
	 * @param event
	 */
	public void home(ActionEvent event) {
		reload(event, "/branchManager/BranchManagerPage.fxml", "Branch manager - home");
	}

	/**
	 * A method that logout and exit the application
	 * 
	 * @param event
	 */
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
	 * A method Allows the user to logout from the system.
	 * 
	 * @param event
	 */
	public void logout(ActionEvent event) {
		logoutSentToJson();
		logoutResponse();
		reload(event, "/guiNew/HomePage.fxml", "Home page");
	}

	/**
	 * A method that Sends to serve the username of the branch manager in order to
	 * logout from the application
	 * 
	 */
	public void logoutSentToJson() {
		Gson gson = new Gson();
		JsonElement jsonElem = gson.toJsonTree(new Object());
		jsonElem.getAsJsonObject().addProperty("userName", BranchManagerController.branchManager.getUserName());

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
	 * A method that gets the response from server for logout
	 */
	public void logoutResponse() {
		Response response = ChatClient.serverAns;
		System.out.println("-->>" + response.getDescription()); // Description from server
	}

}
