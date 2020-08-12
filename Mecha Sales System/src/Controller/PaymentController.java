package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Main;
import Database.MySQLDatabase;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class PaymentController implements Initializable {
	@FXML
	private TextField total;
	@FXML
	private TextField netpay;
	@FXML
	private TextField returns;
	@FXML
	private Button payment;
	@FXML
	private TextField discount;
	@FXML
	private Label lbl;
	@FXML
	private Button cancel;
	static public String tot, np, rt;

	// Event Listener on Button[#payment].onAction
	@FXML
	public void makepayment(ActionEvent event) throws SQLException, Exception {
		try {
			int final_amount = Integer.parseInt(netpay.getText()) - Integer.parseInt(total.getText())
					+ Integer.parseInt(discount.getText());
			if (final_amount < 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Payment Unsuccessful");
				alert.setHeaderText(null);
				alert.setContentText("Payment Unsuccessfull!!");
				alert.showAndWait();
			} else {
				System.out.println("returns" + final_amount);
				tot = total.getText();
				np = netpay.getText();
				rt = Integer.toString(final_amount);
				returns.setText(Integer.toString(final_amount));
				MySQLDatabase.getInstance().setSale(MySQLDatabase.getInstance().getCurrentSale(), total.getText(),
						netpay.getText());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Payment Details");
				alert.setHeaderText(null);
				alert.setContentText("Payment Successfully Done!!");
				alert.showAndWait();
				handleCancel(event);
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Discount field can't be empty");
			alert.setHeaderText(null);
			alert.setContentText("Give desired discount or just write zero");
			alert.showAndWait();
			// handleCancel(event);

		}
	}

	// Event Listener on Button[#cancel].onAction
	@FXML
	public void handleCancel(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProcessSale.fxml"));
		Scene scene = new Scene(root, 1920, 990);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
		Main.changeRoot(root);
		Main.screenSizeSet(1900, 990);
	}

	public boolean validateInput() {

		String errorMessage = "";

		if (total.getText() == null || total.getText().length() <= 0 || netpay.getText() == null
				|| netpay.getText().length() <= 0) {
			errorMessage += "Please enter Credentials!\n";
			lbl.setText(errorMessage);
			return false;

		}

		else {
			return true;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			total.setText(String
					.valueOf(MySQLDatabase.getInstance().getTotalAmount(MySQLDatabase.getInstance().getCurrentSale())));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
