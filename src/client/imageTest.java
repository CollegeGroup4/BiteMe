package client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class imageTest extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Load Image");

		StackPane sp = new StackPane();
		String img = null;
		try {
		img =  sendImage();
		}catch (Exception e) {
			System.out.println("hmmm");
		}
		Gson gson = new Gson();
		JsonElement j = gson.toJsonTree(img);
		ImageView imgage = gson.fromJson(j, ImageView.class);
		sp.getChildren().add(imgage);

//Adding HBox to the scene
		Scene scene = new Scene(sp);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public String sendImage() {
		Image i = new Image("https://thumbs.dreamstime.com/b/white-flower-beautiful-hd-pic-japantown-215486907.jpg");
		ImageView img = new ImageView(
				"https://thumbs.dreamstime.com/b/white-flower-beautiful-hd-pic-japantown-215486907.jpg");
		Gson gson = new Gson();
		return gson.toJson(i);
	}
}
