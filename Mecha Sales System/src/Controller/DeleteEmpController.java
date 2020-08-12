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

import javafx.scene.control.PasswordField;

public class DeleteEmpController {
	@FXML
	private TextField IDFeild;
	@FXML
	private TextArea addressArea;
	@FXML
	private TextField name;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField phoneField;
	@FXML
	private Button find;
	@FXML
	private Label lbl;
	@FXML
	private Button DltBtn;
	@FXML
	private Button Back;
	MySQLDatabase DB;


	// Event Listener on Button[#find].onAction
	@FXML
	public void FindInfo(ActionEvent event) throws SQLException
	{

		if(validateInput())
		{
			String d = IDFeild.getText();
			System.out.println(d);
			try {
				DB = MySQLDatabase.getInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<ArrayList<String>> data = DB.getIndexValue("employee", "ID", d);

			if(data.size()>0 && data.get(0).size()>0)
			{
				lbl.setText("");
				System.out.println(data.get(0).get(0));
				System.out.println(data.get(0).get(1));
				System.out.println(data.get(0).get(2));
				System.out.println(data.get(0).get(3));
				name.setText(data.get(0).get(1));
				passwordField.setText(data.get(0).get(2));
				phoneField.setText(data.get(0).get(3));
				addressArea.setText(data.get(0).get(4));
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Invalid Employee ID");
        		alert.setHeaderText(null);
        		alert.setContentText("No Information Found! Please Enter Employee Valid Employee ID");
        		alert.showAndWait();
        	}
		}

	}
	// Event Listener on Button[#DltBtn].onAction
	@FXML
	public void DeleteEmp(ActionEvent event) throws IOException
	{
		if(validateInput())
		{
			lbl.setText("");
		//Query to delete Employee
		try {
			DB.removeRow("employee", "ID", IDFeild.getText());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Employee Delete");
			alert.setHeaderText(null);
			alert.setContentText("Employee account has been removed successfully");
			alert.showAndWait();
			handleCancel(event);
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Invalid Employee ID");
			alert.setHeaderText("Invalid");
			alert.setContentText("No Information Found! Please Enter Employee Valid Employee ID");
			alert.showAndWait();
		}
		}


	}
	// Event Listener on Button[#Back].onAction
	@FXML
	public void handleCancel(ActionEvent event) throws IOException {
		System.out.println("Authenticate");
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/ManagerScreen.fxml"));
		Scene scene = new Scene(root, 1880, 940);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		Main.Get_Stage().setMaximized(true);

	}
	public boolean validateInput()
	{

        String errorMessage = "";

        if (IDFeild.getText() == null || IDFeild.getText().length()<=0) {
            errorMessage += "Please enter Employee ID!\n";
            lbl.setText(errorMessage);
            return false;

        }

        else
        {
            return true;
        }     }
}
