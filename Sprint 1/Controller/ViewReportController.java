package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ViewReportController {
	@FXML
	private TableView tableview;
	@FXML
	private Button back;
	@FXML
	private Button logout;

	// Event Listener on Button[#back].onAction
	private ObservableList<ObservableList> data;
	public void build()
	{
		data = null;
         data = FXCollections.observableArrayList();
         try{
       	String url1 = "jdbc:mysql://localhost/mechaSalessystem";
     		String user = "root";
     		String password = "tiger";
     		Class.forName("com.mysql.jdbc.Driver");
			Connection c1;
     		c1 = DriverManager.getConnection(url1, user, password);
			Statement stmt = (Statement) c1.createStatement();
           //SQL FOR SELECTING ALL OF Robots
           String SQL = "SELECT * from e_robot";
           //ResultSet
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
               System.out.println("Column ["+i+"] ");
           }

           /********************************
            * Data added to ObservableList *
            ********************************/
           while(rs.next()){
               //Iterate Row
               ObservableList<String> row = FXCollections.observableArrayList();
               for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                   //Iterate Column
                   row.add(rs.getString(i));
               }
               System.out.println("Row [1] added "+row );
               data.add(row);

           }

           //FINALLY ADDED TO TableView
           tableview.setItems(data);
           Scene s = new Scene(tableview,1920, 990);
           Main.Get_Stage().setScene(s);
           Main.Get_Stage().show();
         }catch(Exception e){
             e.printStackTrace();
             System.out.println("Error on Building Data");
         }
	}

	// Event Listener on Button[#back].onAction
	@FXML
	public void BackAction(ActionEvent event) throws IOException
	{
		build();
	}
	@FXML
	public void logoutH(ActionEvent event) throws IOException {
		System.out.println("Authenticate");
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/InventoryManagerScreen.fxml"));
		Scene scene = new Scene(root, 1800,900);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		Main.Get_Stage().setMaximized(true);
	}

}
