package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;

import javafx.scene.layout.BorderPane;

public class InventoryManagerController {
	@FXML
	private BorderPane welcomeBorder;
	@FXML
	private Button additem;
	@FXML
	private Button viewitem;
	@FXML
	private Button edititem;
	@FXML
	private Button inventoryreport;
	@FXML
	private Button deleteitem;
	@FXML
	private Button logout;
	@FXML
	private Button demand;



	// Event Listener on Button[#additem].onAction
	@FXML
	public void AddER(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/AddRobot.fxml"));
		Scene scene = new Scene(root,1800,900);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		Main.Get_Stage().setMaximized(true);
	}
	// Event Listener on Button[#viewitem].onAction
	@FXML
	public void ViewER(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/ViewRobot.fxml"));
		Scene scene = new Scene(root, 1800,900);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		Main.Get_Stage().setMaximized(true);

	}
	// Event Listener on Button[#edititem].onAction
	@FXML
	public void EditER(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/UpdateRobot.fxml"));
		Scene scene = new Scene(root, 1800,900);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().setMaximized(true);

	}
	// Event Listener on Button[#inventoryreport].onAction
	@FXML
	public void ViewReport(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/ViewReport.fxml"));
		Scene scene = new Scene(root, 1800,900);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		Main.Get_Stage().setMaximized(true);

	}
	// Event Listener on Button[#deleteitem].onAction
	@FXML
	public void DeleteER(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/Delete_Robot.fxml"));
		Scene scene = new Scene(root, 1800,900);
		Main.Get_Stage().setScene(scene);// TODO Autogenerated
		Main.Get_Stage().setMaximized(true);

	}
	// Event Listener on Button[#logout].onAction
	@FXML
	public void Logout(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml"));
		Scene scene = new Scene(root, 1800,900);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		Main.Get_Stage().setMaximized(true);

	}

	@FXML
	public void getDemand(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/demandScreen.fxml"));
		Scene scene = new Scene(root, 1800,900);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		Main.Get_Stage().setMaximized(true);

	}

}
