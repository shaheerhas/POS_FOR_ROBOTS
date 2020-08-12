package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;

import com.sun.javafx.image.impl.ByteIndexed.Getter;

import application.Main;
import Database.MySQLDatabase;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.control.PasswordField;

public class AddEmployeeController {
	@FXML
	private TextField firstField;
	@FXML
	private TextArea addressArea;
	@FXML
	private TextField lastField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField phoneField;
	@FXML
	private Label lbl;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancel;

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void handleSave(ActionEvent event)
	{
		if(validateInput())
		{
			String name = firstField.getText()+lastField.getText();
			String address = addressArea.getText();
			String phone = phoneField.getText();
			String password = passwordField.getText();
			lbl.setText("");


			// Insert Query for insertin data in to datbase
			try {
				MySQLDatabase.getInstance().addEmployee(name, password, phone, address);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Employee Registration");
				alert.setHeaderText("Registered");
				alert.setContentText("Employee account has been Registered Successfully");
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
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/ManagerScreen.fxml"));
		Scene scene = new Scene(root, 1920, 990);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		Main.Get_Stage().setMaximized(true);
	}

	public boolean validateInput() {

        String errorMessage = "";

        if (firstField.getText().equals("") || passwordField.getText().equals("")|| lastField.getText().equals("") || phoneField.getText().equals("") || addressArea.getText().equals(""))



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
