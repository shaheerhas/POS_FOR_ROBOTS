package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import java.sql.Statement;

import application.Main;
import Database.MySQLDatabase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class SaleReportController implements Initializable {
	@FXML
	private TableView tableview;
	@FXML
	private Button cancel;
	@FXML
	private Label lba;
	@FXML
	private Label lbb;
	@FXML
	private Label lbc;

	private ObservableList<ObservableList> data;


	public void build()
	{
		if(tableview!=null)
		{


		tableview.getColumns().clear();
		}
		 data = null;
         data = FXCollections.observableArrayList();
         try{
        	String url1 = "jdbc:mysql://localhost/mechasalessystem";
     		String user = "root";
     		String password = "tiger";
     		Class.forName("com.mysql.jdbc.Driver");
			Connection c1;
     		c1 = DriverManager.getConnection(url1, user, password);
			Statement stmt = (Statement) c1.createStatement();
	         String SQL = "SELECT * from sale_line_item";
	         ResultSet rs = c1.createStatement().executeQuery(SQL);

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

               data.add(row);

           }

           //FINALLY ADDED TO TableView
           tableview.setItems(data);
         }catch(Exception e){
             e.printStackTrace();
             System.out.println("Error on Building Data");
         }
	}

	// Event Listener on Button[#cancel].onAction
	@FXML
	public void cancelsale(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/ManagerScreen.fxml"));
		Scene scene = new Scene(root, 1920, 990);

		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().setTitle("Sales Report");
		Main.Get_Stage().show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		build();
		ArrayList<ArrayList<String>> info = null;
		int tot_sales=0,tot_quantity=0,tot_amount=0;
		 try {
			info= MySQLDatabase.getInstance().getRows("sale_line_item");
		} catch (Exception e) {
			e.printStackTrace();
		}
		tot_sales = info.size();
		System.out.println(info.get(0).get(6));
		for(int i=0;i<info.size();i++)
		 {
			tot_quantity+=Integer.parseInt(info.get(i).get(6));
			tot_amount+=Integer.parseInt(info.get(i).get(5));

		 }
		lba.setText("Total Sales: "+Integer.toString(tot_sales));
		lbb.setText("Total Quantity: "+Integer.toString(tot_quantity));
		lbc.setText("Total Amount: " + Integer.toString(tot_amount));

	}
}
