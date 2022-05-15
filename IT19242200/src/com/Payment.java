package com;

import java.sql.*;

public class Payment
{ //A common method to connect to the DB
	
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Provide the correct details: DBServer/DBName, username, password
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_assi","root", "eranda");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return con;

	}
	
	//payID, bill_ID, card_Holder, card_type, card_No, cvv, amount


public String readPay()
{
		String output = "";
		try
		{
			Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for reading."; } 
			 
			
			 output = "<table border='3' class='table table-striped'><tr><th>Bill ID</th> "+
			 "<th>Name</th>"+
			 "<th>Card Type</th>"+
			 "<th>Card No</th>"+
			 "<th>CVV</th>"+
			 "<th>Amount</th>"+
			 "<th>Update</th><th>Remove</th></tr>"; 
			 
			 String query = "select * from paymet"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
			 String payID = Integer.toString(rs.getInt("payID"));
			 String bill_ID = rs.getString("bill_ID");
			 String card_Holder = rs.getString("card_Holder");
			 String card_type = rs.getString("card_type");
			 String card_No = Integer.toString(rs.getInt("card_No"));
			 String cvv = Integer.toString(rs.getInt("cvv"));
			 String amount = Double.toString(rs.getDouble("amount"));
			 
			 
			 // Add into the html table
			 output += "<td>"+ bill_ID + "</td>";
			 output += "<td>" + card_Holder + "</td>";
			 output += "<td>" + card_type + "</td>";
			 output += "<td>" + card_No + "</td>";
			 output += "<td>" + cvv + "</td>";
			 output += "<td>" + amount + "</td>";
			 
			 //buttons
	            
			 output += "<td><input name='btnUpdate' type='button' value='Update' class=' btnUpdate btn btn-secondary' data-itemid='" + payID + "'></td>"
	            		+ "<td><input name = 'btnRemove' type='button' value = 'Remove' "
	            		+ "class = 'btnRemove btn btn-danger' data-itemid='" + payID + "'>"
	            		+"</td></tr>";
            		
		}
		
		con.close();
		
		// Complete the html table
		
		output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}


public String updatePay(String payID, String bill_ID, String card_Holder, String card_type, String card_No, String cvv, String amount)
{
		String output = "";
		try
		{
			Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for updating."; } 

			//payID, bill_ID, card_Holder, card_type, card_No, cvv, amount
			 // create a prepared statement
			 String query = "UPDATE paymet SET bill_ID=?, card_Holder=?, card_type=?,card_No=?,cvv=?,amount=? WHERE payID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 


			 // binding values 
			 preparedStmt.setString(1,bill_ID);
			 preparedStmt.setString(2, card_Holder); 
			 preparedStmt.setString(3, card_type); 
			 preparedStmt.setInt(4, Integer.parseInt(card_No)); 
			 preparedStmt.setInt(5, Integer.parseInt(cvv)); 
			 preparedStmt.setDouble(6, Double.parseDouble(amount));
			 preparedStmt.setInt(7, Integer.parseInt(payID));
				// execute the statement
				preparedStmt.execute();

				con.close();

				String newItems = readPay();
				output = "{\"status\":\"success\", \"data\": \"" +
				newItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
}
	
	
public String deletePay(String payID)
{
	String output = "";
	try
	{
		Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for deleting."; } 
		 
		//payID, bill_ID, card_Holder, card_type, card_No, cvv, amount, paymet
		 // create a prepared statement
		 String query = "delete from paymet where payID=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(payID)); 
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			
			String newItems = readPay();
			output = "{\"status\":\"success\", \"data\": \"" +
			newItems + "\"}";
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
		System.err.println(e.getMessage());
	}
	
	return output;
	}

public String insertPay(String bill_ID, String card_Holder, String card_type, String card_No, String cvv, String amount) {
	String output = "";
	try
	{
		Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for inserting."; } 
		 
		 
		 // create a prepared statement
		 String query = " insert into paymet (`payID`,`bill_ID`, `card_Holder`, `card_type`,`card_No`,`cvv`,`amount`)"+ " values (?, ?, ?, ?, ?, ?, ?)"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 
		 // binding values 
		 preparedStmt.setInt(1, 0); 
		 preparedStmt.setString(2, bill_ID);
		 //preparedStmt.setInt(2, Integer.parseInt(bill_ID));
		 preparedStmt.setString(3, card_Holder);
		 preparedStmt.setString(4, card_type);
		 preparedStmt.setInt(5, Integer.parseInt(card_No));
		 preparedStmt.setInt(6, Integer.parseInt(cvv));
		 //preparedStmt.setString(5, card_No);
		 //preparedStmt.setString(6, cvv);
		 preparedStmt.setDouble(7, Double.parseDouble(amount));
		// execute the statement

		preparedStmt.execute();
		
		con.close();
		
		String newItems = readPay();
		output = "{\"status\":\"success\", \"data\": \"" +
		        newItems + "\"}";
		
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
	}
		
	return output;
}


}