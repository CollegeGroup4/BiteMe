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

public class CoursesController implements Initializable, EventHandler<ActionEvent> {
	
	public static HashMap<String, Integer> courses;
	public static String courseSelected;
	

	@FXML
	private VBox coursesContainer;

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Courses.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent = stringFromEvent.split("'", 2)[1];
		courseSelected = stringFromEvent.substring(0, stringFromEvent.length() - 1);
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Categories.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ChooseADishController.chooseADishController.getPaneForCourses().setCenter(root);
		ChooseADishController.chooseADishController.getPaneForCourses().setTop(new Label("Choose Category:"));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> b = new ArrayList<Button>();
		getInformation();
		for (String i : courses.keySet()) {
			Button temp = new Button(i);
			temp.setOnAction(this);
			b.add(temp);
		}
		coursesContainer.getChildren().addAll(b);

	}

	private void getInformation() {
		courses = new HashMap<>();
		for (int i = 0; i < ChooseADishController.menuSelected.getItems().length; i++) {
			courses.put(ChooseADishController.menuSelected.getItems()[i].getCourse(), null);
		}
	
	}
}
