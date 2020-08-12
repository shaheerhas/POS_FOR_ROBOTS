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

public class AddSaleLineItemController
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

				nameField.setText(data.get(0).get(1));
				priceField.setText(data.get(0).get(3));
				DescriArea.setText(data.get(0).get(2));
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

        	}
		}

	}
	// Event Listener on Button[#UpdateButton].onAction
	// Event Listener on Button[#Back].onAction
	@FXML
	public void handleCancel(ActionEvent event) throws NumberFormatException, SQLException, Exception
	{
		if(validateInput())
		{

		if(QTYField.getText().length()<=0 || QTYField.getText()=="")
		{
			lbl.setText("Please Enter required quantity");
		}
		else
		{

		lbl.setText("");
		System.out.println("QTY"+ QTYField.getText());
		int subtotal = Integer.parseInt(QTYField.getText())*Integer.parseInt(priceField.getText());
		System.out.println(String.valueOf(MySQLDatabase.getInstance().getCurrentSale()));
		MySQLDatabase.getInstance().addSale(String.valueOf(MySQLDatabase.getInstance().getCurrentSale()) , CodeFeild.getText(), nameField.getText(), DescriArea.getText(),  Integer.parseInt(priceField.getText()), Integer.parseInt(QTYField.getText()), subtotal);
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProcessSale.fxml"));
		Scene scene = new Scene(root, 1920, 990);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		//Main.changeRoot(root);
		//Main.screenSizeSet(1900, 990);
		}
		}
	//	/else
	//	{
	//		Alert alert = new Alert(AlertType.INFORMATION);
	//		alert.setTitle("Sale Line Item Not Added");
	//		alert.setHeaderText(null);
	//		alert.setContentText("Please Try Again");
	//		alert.showAndWait();
	//	}
	}

	@FXML
	public void handleback(ActionEvent event) throws IOException
	{


		Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProcessSale.fxml"));
		Scene scene = new Scene(root, 1920, 990);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		//Main.changeRoot(root);
		//Main.screenSizeSet(1900, 990);
		//Main.screenSizeSet();
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

            return false;

        }

        else
        {
            return true;
        }     }
}
