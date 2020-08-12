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

public class CustomDemandController
{
	@FXML
	private TextField customerName;
	@FXML
	private TextField customerPhoneNo;
	@FXML
	private TextField itemQty;
	@FXML
	private TextField itemName;
	@FXML
	private TextArea itemDesc;
//	@FXML
//	private Button addDemand;
	@FXML
	private Button back;
	@FXML
	private Label lbl;



	// Event Listener on Button[#find].onAction
	@FXML
	public void addDemand(ActionEvent event) throws Exception
	{

		if(validateInput())
		{
//			String c_name = customerName.getText();
//			String c_phone_no = customerPhoneNo.getText();
//			String qty = itemQty.getText();
//			String item_name = itemName.getText();
//			String desc = itemDesc.getText();

			MySQLDatabase DB = MySQLDatabase.getInstance();

//			ArrayList<ArrayList<String>> data = DB.getIndexValue("E_Robot", "Code", d);

//			if(data.size()>0 && data.get(0).size()>0)
			{
				lbl.setText("");
//				System.out.println(c_name);
//				System.out.println(c_phone_no);
//				System.out.println(qty);
//				System.out.println(itemName);
//				System.out.println(itemDesc);
				DB.addCustomDemand(itemQty.getText(), customerName.getText(), customerPhoneNo.getText(), itemName.getText(), itemDesc.getText());
				Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Demand Added");
        		alert.setHeaderText("Demand Added");
        		alert.setContentText(null);
        		alert.showAndWait();
        		handleback(event);
        		}
//			else
			{
//				Alert alert = new Alert(AlertType.INFORMATION);
//        		alert.setTitle("InvalCode E Robot Code");
//        		alert.setHeaderText(null);
//        		alert.setContentText("No Information Found! Please Enter Valid Robot Code");
//        		alert.showAndWait();
//        		nameField.clear();
//				QTYField.clear();
//				priceField.clear();
//				DescriArea.clear();

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

        if (customerName.getText() == null || customerName.getText().length()<=0 && customerPhoneNo.getText() == null &&  customerPhoneNo.getText().length()<=0 || itemName.getText() == null || itemName.getText().length()<=0 ) {
            errorMessage += "Please enter Credentials!\n";
            lbl.setText(errorMessage);
            customerName.clear();
			customerPhoneNo.clear();
			itemQty.clear();
			itemDesc.clear();
			itemName.clear();

            return false;

        }

        else
        {
            return true;
        }     }
}
