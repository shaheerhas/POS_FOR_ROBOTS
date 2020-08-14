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

import application.Main;
import Database.MySQLDatabase;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.control.PasswordField;

public class AddRobotController {

	@FXML
	private TextArea desc;
	@FXML
	private TextField name;
	@FXML
	private TextField qty;
	@FXML
	private TextField price;
	@FXML
	private Label lbl;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancel;
	@FXML
	private TextField place;

		@FXML
		public void handleSave(ActionEvent event)
		{
			if(validateInput())
			{	lbl.setText("");


				// Insert Query for inserting data into the database
				try {
					MySQLDatabase.getInstance().addERobot( name.getText(), desc.getText(), price.getText(), qty.getText(), place.getText());
					//MySQLDatabase.getInstance().addEmployee(name, password, phone, address);
					// Querty To Add Data
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("E Robot Add");
					alert.setHeaderText("Added");
					alert.setContentText("Electronic Robot has been added successfully");
					alert.showAndWait();
					handleCancel(event);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// Event Listener on Button[#cancel].onAction
		@FXML
		public void handleCancel(ActionEvent event) throws IOException {
			System.out.println("Authenticate");
			Parent root = FXMLLoader.load(getClass().getResource("/GUI/InventoryManagerScreen.fxml"));
			Scene scene = new Scene(root, 1920, 990);
			Main.Get_Stage().setScene(scene);
			Main.Get_Stage().show();
			Main.Get_Stage().setMaximized(true);
		}

		public boolean validateInput() {

	        String errorMessage = "";

	        if ( name.getText().equals("")|| price.getText().equals("") || qty.getText().equals("") || desc.getText().equals(""))
	            {
	        		errorMessage += "Please enter Credentials!\n";
	            }
	        if (errorMessage.length() == 0)
	        {
	            return true;
	        } else
	        {
	            lbl.setText(errorMessage);
	            return false;
	        }
	    }
	}
