package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
		Label chooseCategory = new Label("Choose Category:");
		designLabel(chooseCategory);
		ChooseADishController.chooseADishController.getPaneForCourses().setTop(chooseCategory);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> listCourses = new ArrayList<Button>();
		getInformation();
		for (String i : courses.keySet()) {
			JFXButton CourseBtn = new JFXButton(i);
			designButton(CourseBtn);
			CourseBtn.setOnAction(this);
			listCourses.add(CourseBtn);
		}
		coursesContainer.getChildren().addAll(listCourses);
		
	}
	private void designLabel(Label label) {
		Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 16);
		label.setFont(font);
	}

	private void designButton(JFXButton button) {
		button.setPrefWidth(160);
//		button
		BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(background_fill);
		button.setBackground(background);
		Font font = Font.font("Arial", FontWeight.BOLD, 15);
		button.setFont(font);
	}
	private void getInformation() {
		courses = new HashMap<>();
		for (int i = 0; i < ChooseADishController.menuSelected.getItems().length; i++) {
			courses.put(ChooseADishController.menuSelected.getItems()[i].getCourse(), null);
		}
	
	}
}
