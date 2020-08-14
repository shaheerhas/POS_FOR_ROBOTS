package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLDatabase {
	private Connection dbConn;
	private Statement stmt;

	private static MySQLDatabase dbConnection;

	public static MySQLDatabase getInstance(String url,String user,String password)
	{
		if (dbConnection == null)
		{
			dbConnection = new MySQLDatabase(url , user, password);
		}
		return dbConnection;
	}

	public static MySQLDatabase getInstance() throws Exception
	{
		if (dbConnection == null)
		{
			throw new Exception();
		}
		return dbConnection;
	}


	private MySQLDatabase(String url, String user, String password)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbConn = DriverManager.getConnection(url, user, password);
			stmt = dbConn.createStatement();
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}


	public ArrayList<ArrayList<String>> getRows(String tableName) throws SQLException
	{
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		String sqlQuery = new String();
		sqlQuery = "SELECT * FROM " + tableName;
		ResultSet rsRows = stmt.executeQuery(sqlQuery);
		while (rsRows.next()){
			rows.add(rowToColumns(rsRows));
		}
		rsRows.close();
		return rows;
	}

	public ArrayList<ArrayList<String>> getIndexValue(String tableName, String colName, String value) throws SQLException
	{
		String sqlQuery = new String();
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		sqlQuery = "Select * FROM " + tableName + " Where " + colName + " = '" + value + "'";
		System.out.println(sqlQuery);
		ResultSet rsRows = stmt.executeQuery(sqlQuery);
		while (rsRows.next()){
			rows.add(rowToColumns(rsRows));
		}
		rsRows.close();
		return rows;
	}

	public int removeRow(String tableName, String colName, String value) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "Delete FROM " + tableName + " Where " + colName + " = '" + value + "'";
		return stmt.executeUpdate(sqlQuery);
	}

	public int addEmployee(String name, String password, String phone, String address) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "INSERT INTO `employee`(`Name`, `Password`, `Phone`, `Address`) "
				+ "VALUES ('" + name + "', '" + password + "', '" + phone + "', '" + address + "' )";
		System.out.println(sqlQuery);
		return stmt.executeUpdate(sqlQuery);
	}

	public int updateEmployee(String ID, String name, String password, String phone, String address,String type) throws SQLException
	{
		String sqlQuery = new String();
		/*sqlQuery = "INSERT INTO `employee`(`Name`, `Password`, `Phone`, `Address`) "
				+ "VALUES (" + name + "', '" + password + "', '" + phone + "', '" + address + "' )";*/
		sqlQuery = "UPDATE `employee` SET `Name` = '" + name + "',"
				+ "`Type` = '" + type +"',"
				+ " `Password` = '" + password + "', `Phone` = '" + phone +"', `Address` = '" + address +"' WHERE `employee`.`ID` = "+ ID +";";

		return stmt.executeUpdate(sqlQuery);
	}


	public int addERobot(String name, String desc, String price, String qty, String item_place) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "INSERT INTO `e_robot` (`Name`, `Description`, `Price`, `Qty`, `Place`) "
				+ "VALUES ('"  + name + "', '" + desc + "', '" + price + "', '" + qty + "', '" + item_place + "')";
		System.out.println(sqlQuery);
		return stmt.executeUpdate(sqlQuery);
	}

	public int updateERobot(String ID,String name, String desc, String price, String qty, String place) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "UPDATE `e_robot` SET `Name`= '" + name
				+"' ,`Description`= '" + desc + "',`Price`= '" + price + "',`Qty`= '" + qty + "',`Place`= '" +
				place + "' WHERE `Code`=" + Integer.parseInt(ID) +";";
		System.out.println(sqlQuery);
		return stmt.executeUpdate(sqlQuery);
	}

	public int executeUpdate(String query)
	{
		try{
			return stmt.executeUpdate(query);
		}
		catch (SQLException ex){
			return -1;
		}
	}

	public int setSaleLineItemOnRobotCode(String sale_code, String ERobot_Code, String qty) throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "UPDATE `sale` SET `ERobot_Qty`=" + qty + " WHERE `Sale_Code` = " + sale_code + " AND `ERobot_Code`= " + ERobot_Code+ ";";
		return stmt.executeUpdate(sqlQuery);
	}

	public int getCurrentSale() throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "SELECT MAX(sale_code) FROM sale;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		//Cuz it points to -1th index in the begining
		rs.next();
		return rs.getInt(1);

	}

	public void addSale() throws SQLException
	{
		String sqlQuery = new String();
		sqlQuery = "INSERT INTO `sale`(`total_amount`, `payment`) VALUES (0,0)";
		stmt.executeUpdate(sqlQuery);
	}

//	public
//	{
//		String sqlQuery = new String();
//		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
//		sqlQuery = "UPDATE `sale` SET `ERobot_Qty`=" + qty + " WHERE `Sale_Code` = " + sale_code + " AND `ERobot_Code`= " + ERobot_Code+ ";";
//		return stmt.executeUpdate(sqlQuery);
//		while (rsRows.next()){
//			rows.add(rowToColumns(rsRows));
//		}
//		rsRows.close();
//		return rows;
//	}


//	public void editSaleLineItem(String sale_code)
//	{
//		try {
//			String sqlQuery = "SELECT * From SALE WHERE `Sale_Code` = '" + sale_code + "';";
//			ResultSet rs = stmt.executeQuery(sqlQuery);
//			rs.getInt(1);
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	public void runQuery(String query) throws SQLException
	{
		stmt.executeUpdate(query);
	}

	//helper
	private ArrayList<String> rowToColumns(ResultSet row) throws SQLException
	{
		ArrayList<String> rowData = new ArrayList<String>();
		try
		{
			int columnCount = row.getMetaData().getColumnCount();
			for (int j = 1; j < columnCount+1; ++j){
				rowData.add(row.getString(j));
			}
		}
		catch (SQLException e)
		{
			System.out.println("No Data found");
		}
		return rowData;
	}
}
