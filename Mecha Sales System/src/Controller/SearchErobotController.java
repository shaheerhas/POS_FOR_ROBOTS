package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Main;
import Database.MySQLDatabase;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

public class SearchErobotController
{
	@FXML
	private TextField CodeFeild;
	@FXML
	private TextArea DescriArea;
	@FXML
	private TextField nameField;
	@FXML
	private TextField QTYField;
	@FXML
	private TextField priceField;
	@FXML
	private TextField placeField;
	@FXML
	private Button find;
	@FXML
	private Label lbl;
	@FXML
	private Button Back;
	@FXML
	private Button cancel;

	// Event Listener on Button[#find].onAction
	@FXML
	public void FindInfo(ActionEvent event) throws Exception
	{

		if(validateInput())
		{
			String d = CodeFeild.getText();
			MySQLDatabase DB = MySQLDatabase.getInstance();

			ArrayList<ArrayList<String>> data = DB.getIndexValue("E_Robot", "Code", d);

			if(data.size()>0 && data.get(0).size()>0)
			{
				lbl.setText("");
				System.out.println(data.get(0).get(0));
				System.out.println(data.get(0).get(1));
				System.out.println(data.get(0).get(2));
				System.out.println(data.get(0).get(3));
				System.out.println(data.get(0).get(5));

				nameField.setText(data.get(0).get(1));
				priceField.setText(data.get(0).get(3));
				DescriArea.setText(data.get(0).get(2));
				QTYField.setText(data.get(0).get(4));
				placeField.setText(data.get(0).get(5));
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Invalid E Robot Code");
        		alert.setHeaderText(null);
        		alert.setContentText("No Information Found! Please Enter Valid Robot Code");
        		alert.showAndWait();
        		nameField.clear();
				QTYField.clear();
				priceField.clear();
				DescriArea.clear();
				placeField.clear();

        	}
		}

	}

	@FXML
	public void handleback(ActionEvent event) throws IOException
	{


		Parent root = FXMLLoader.load(getClass().getResource("/GUI/CustomerScreen.fxml"));
		Scene scene = new Scene(root, 1920, 990);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
	}
	public boolean validateInput()
	{

        String errorMessage = "";

        if (CodeFeild.getText() == null || CodeFeild.getText().length()<=0) {
            errorMessage += "Please enter Credentials!\n";
            lbl.setText(errorMessage);
            nameField.clear();
			QTYField.clear();
			priceField.clear();
			DescriArea.clear();
			placeField.clear();

            return false;

        }

        else
        {
            return true;
        }     }
}
