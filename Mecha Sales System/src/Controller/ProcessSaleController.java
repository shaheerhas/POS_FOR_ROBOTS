package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.sql.Statement;

import application.Main;
import Database.MySQLDatabase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class ProcessSaleController implements Initializable {
	@FXML
	public TableView tableview;
	@FXML
	private Button EndSale;
	@FXML
	private Button AddSaleLineItem;
	@FXML
	private Button RemoveSaleLineIem;
	@FXML
	private Button MakePayment;
	@FXML
	private Button GenerateReceipt;
	@FXML
	private Button Edit;
	@FXML
	private Button cancel;
	private ObservableList<ObservableList> data;
	static public int SaleNo=3;
	public void initialize(URL location, ResourceBundle resources) {
		if(SaleNo>=0)
		{
		tableview.getColumns().clear();
		build(SaleNo);
		}
		}
	public void build(int SID)
	{
		if(tableview!=null)
		{
			tableview.getColumns().clear();
		}
		 data = null;
         data = FXCollections.observableArrayList();
         try{
	         ResultSet rs = MySQLDatabase.getInstance().getSaleInfo(MySQLDatabase.getInstance().getCurrentSale());

	         /**********************************
	          * TABLE COLUMN ADDED DYNAMICALLY *
	          **********************************/
	         for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
	        	 //We are using non property style for making dynamic table
	        	 final int j = i;
	        	 TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
	        	 col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
	        		 public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
	        			 return new SimpleStringProperty(param.getValue().get(j).toString());
	        		 }
	        	 });

	        	 tableview.getColumns().addAll(col);

	        	 System.out.println("Column ["+i+"] ");
	         }

	         /********************************
	          * Data added to ObservableList *
	          ********************************/
	         while(rs.next()){
	        	 //Iterate Row
	        	 ObservableList<String> row = FXCollections.observableArrayList();
	        	 for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++)
	        	 {
	        		 //Iterate Column
	        		 row.add(rs.getString(i));
	        	 }
	        	// System.out.println("Row [1] added "+row );
	        	 data.add(row);


           }

           //FINALLY ADDED TO TableView
           tableview.setItems(data);
         }catch(Exception e){
             e.printStackTrace();
             System.out.println("Error on Building Data");
         }
	}

	// Event Listener on Button[#EndSale].onAction
	@FXML
	public void EndSale(ActionEvent event) throws SQLException, Exception
	{
		ArrayList<ArrayList<String>> info = MySQLDatabase.getInstance().getIndexValue("sale", "sale_code", Integer.toString(SaleNo));
		if(PaymentController.np==null || PaymentController.np.length()<=0
				|| PaymentController.tot==null || PaymentController.tot.length()<=0
				|| PaymentController.rt==null || PaymentController.rt.length()<=0
				|| info.size()<0)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Make Payment First");
			alert.setHeaderText("No Sale Rocord Found");
			alert.setContentText("Payment Or Sale Missing");
			alert.showAndWait();
		}
		else
		{
			Parent root = FXMLLoader.load(getClass().getResource("/GUI/CashierScreen.fxml"));
			Scene scene = new Scene(root,1920, 990);
			Main.Get_Stage().setScene(scene);
			Main.Get_Stage().setTitle("Process Sale");
			Main.Get_Stage().show();

		}
		}

	// Event Listener on Button[#AddSaleLineItem].onAction
	@FXML
	public void ADDSaleLineItem(ActionEvent event) throws IOException
	{
		System.out.println("Authenticate");
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/AddSaleLineItem.fxml"));
		Main.changeRoot(root);

		Scene scene = new Scene(root, 1920, 990);
		Main.screenSizeSet(1920, 990);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
	}
	// Event Listener on Button[#RemoveSaleLineIem].onAction

	@FXML
	public void RemoveSaleLineItem(ActionEvent event) throws SQLException, Exception {
		 //System.out.println("selectionmodel");
		 //System.out.println(tableview.getSelectionModel().getSelectedItem());
			if(!data.isEmpty() &&tableview.getSelectionModel().getSelectedItem()!=null){
				 ArrayList a = (ArrayList) Main.convertObjectToList(tableview.getSelectionModel().getSelectedItem());

			tableview.getColumns().remove(tableview.getSelectionModel().getSelectedItem());
			data.remove(tableview.getSelectionModel().getSelectedItem() );
			String indx = (a.get(2).toString());
			MySQLDatabase.getInstance().removeSaleLineItem(String.valueOf(MySQLDatabase.getInstance().getCurrentSale()),
			indx ) ;

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Item Removed");
			alert.setHeaderText(null);
			alert.setContentText("Sale Line Robot Successfully Removed!");
			alert.showAndWait();


		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No Item selected for Removal");
			alert.setHeaderText(null);
			alert.setContentText("Select an Item to remove");
			alert.showAndWait();

		}


	}
	@FXML
	public void EditSaleLineItem(ActionEvent event) throws IOException {
		System.out.println("Authenticate");
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/EditSaleLineItem.fxml"));
		Scene scene = new Scene(root, 1366, 768);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		//Main.changeRoot(root);
		//Main.screenSizeSet(1900, 990);

	}
	// Event Listener on Button[#MakePayment].onAction
	@FXML
	public void MakePayment(ActionEvent event) throws SQLException, Exception
	{
		ArrayList<ArrayList<String>> info = MySQLDatabase.getInstance().getIndexValue("sale_line_item", "sale_code", String.valueOf(MySQLDatabase.getInstance().getCurrentSale()));
		if(info.size()>0)
		{
			Parent root = FXMLLoader.load(getClass().getResource("/GUI/Payment.fxml"));
			Scene scene = new Scene(root, 1920, 990);
			Main.Get_Stage().setScene(scene);
			Main.Get_Stage().show();
			Main.changeRoot(root);
			Main.screenSizeSet(1900, 990);
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Make Sale First");
			alert.setHeaderText("No Sale Rocord Found");
			alert.setContentText("Sale Line Items Missing");
			alert.showAndWait();
		}
	}
	// Event Listener on Button[#GenerateReceipt].onAction
	@FXML
	public void Get_GenerateReciept(ActionEvent event) throws SQLException, Exception
	{

		if(PaymentController.np==null || PaymentController.np.length()<=0
				|| PaymentController.tot==null || PaymentController.tot.length()<=0
				|| PaymentController.rt==null || PaymentController.rt.length()<=0)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Make Payment First");
			alert.setHeaderText("No Sale Rocord Found");
			alert.setContentText("Payment Or Sale Missing");
			alert.showAndWait();
		}
		else
		{
			String dat="ERobot_Code   Description   Robot Name  Price          Quantity   Sub Total   Var  Sale No\n"
					+ " ---------------------------------------------------------------------------------------------------\n";
			ArrayList<ArrayList<String>> info = MySQLDatabase.getInstance().getIndexValue("sale_line_item","sale_code",String.valueOf(MySQLDatabase.getInstance().getCurrentSale()));
			 for(int i=0;i<info.size();i++)
			 {
				 for(int j=0;j<info.get(i).size();j++)
				 {
					 if(j==0)
					 {

					 }
					 else
					 {
						 dat+=info.get(i).get(j);
						 dat+="              ";
					 }
				}
				 dat+="\n";
			 }
			 dat =dat+ " \n---------------------------------------------------------------------------------------------------\n"+"\n"+ "Total = "+
			 PaymentController.tot+"\n"+"Payment = "+PaymentController.np+"\n"+"Returns = "+PaymentController.rt;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Purchase Details");
			alert.setHeaderText("Receipt                                                                                                                  ");
			alert.setContentText(dat);
			alert.showAndWait();
			}
	}

	public void cancelsale(ActionEvent e) throws SQLException, Exception
	{
		System.out.println("Authenticate");
		MySQLDatabase.getInstance().removeSale(MySQLDatabase.getInstance().getCurrentSale());
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/CashierScreen.fxml"));
		Scene scene = new Scene(root, 1920, 990);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().setTitle("Process Sale");
		Main.Get_Stage().show();
		Main.changeRoot(root);
		Main.screenSizeSet(1900, 990);
	}
}
