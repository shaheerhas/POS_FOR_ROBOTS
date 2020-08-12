package application;



import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import database.MySQLDatabase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class Main extends Application {
	static boolean minimized=false;
	public static Stage s;
	static Parent root;

	public static void changeRoot(Parent r){
		root=r;
	}

	static void  initialize(){

		System.out.println("Initialized");
		Thread thread = new Thread(new Runnable() {

			@Override
        public void run() {
            Runnable updater = new Runnable() {

                @Override
                public void run() {
                	//s.maxi
                	s.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
                	    @Override
                		public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                	        System.out.println("minimized:" + t1.booleanValue());
                	        minimized = t1.booleanValue();
                	    }
                	});
                	if (minimized){
                		screenSizeSet(1900,990);
                	}



                }
            };
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                }
                // UI update is run on the Application thread
                Platform.runLater(updater);
            }
        }
    });
    // don't let thread prevent JVM shutdown
    thread.setDaemon(true);
    thread.start();
	}

	@Override
	public void start(Stage primaryStage)
	{

		try {
			MySQLDatabase.getInstance("jdbc:mysql://localhost/mechaSalesSystem", "root", "tiger");
			root = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml"));
			primaryStage.setTitle("Welcome");
			s = new Stage();
  			Scene scene = new Scene(root, 1880, 940);
  			primaryStage.setScene(scene);
  			primaryStage.show();
  			primaryStage.setMaximized(true);
  			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("logo.jpeg")));
			s=primaryStage;

			/*s.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
        	    @Override
        		public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
        	        System.out.println("minimized:" + t1.booleanValue());
        	        minimized = t1.booleanValue();
        	    }
        	});
			if (minimized){
				screenSizeSet(1900,990);
			}*/
			//checkScreenForResolutionChange();
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
		//initialize();
		launch(args);
	}

	public static void screenSizeSet(double wi,double he){
		Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
		double width = resolution.getWidth();
		double height = resolution.getHeight();
		System.out.println(Double.toString(width)+"  height "+Double.toString(height));
		double w = width/1920;  // your window width
		double h = height/990;  // your window height
		Scale scale = new Scale(w, h, 0, 0);
		root.getTransforms().add(scale);
	}

	//converting object to list
	public static List<?> convertObjectToList(Object obj) {
	    List<?> list = new ArrayList<>();
	    if (obj.getClass().isArray()) {
	        list = Arrays.asList((Object[])obj);
	    } else if (obj instanceof Collection) {
	        list = new ArrayList<>((Collection<?>)obj);
	    }
	    return list;
	}

}
