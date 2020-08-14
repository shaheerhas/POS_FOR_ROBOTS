package application;

import Database.MySQLDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage s;
	@Override
	public void start(Stage primaryStage)
	{

		try {
			MySQLDatabase.getInstance("jdbc:mysql://localhost/mechaSalesSystem", "root", "tiger");
			Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml"));
			primaryStage.setTitle("Welcome");
			s = new Stage();
  			Scene scene = new Scene(root, 1880, 940);
  			primaryStage.setScene(scene);
  			primaryStage.show();
  			primaryStage.setMaximized(true);
  			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("logo.jpeg")));
			s=primaryStage;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static  Stage Get_Stage()
	{
		return s;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
