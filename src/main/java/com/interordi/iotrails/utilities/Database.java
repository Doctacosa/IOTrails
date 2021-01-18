package com.interordi.iotrails.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class Database {
	
	//MySQL server information
	private String database = "";


	public Database(String server, int port, String username, String password, String base) {
		database = "jdbc:mysql://" + server + ":" + port + "/" + base + "?user=" + username + "&password=" + password + "&useSSL=false";
	}


	//Initialize the database
	public boolean init() {

		//Create the required database table
		//A failure indicates that the database wasn't configured properly
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = "";
		
		try {
			conn = DriverManager.getConnection(database);
			
			pstmt = conn.prepareStatement("" +
				"CREATE TABLE IF NOT EXISTS `io__trails` (" +
				"	`uuid` varchar(36) NOT NULL, " +
				"	`trail` varchar(20) NOT NULL, " +
				"	PRIMARY KEY (`uuid`) " +
				") ENGINE=InnoDB DEFAULT CHARSET=latin1; "
			);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("Query: " + query);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}

		return true;
	}
	
	
	//Get a player's active trail, if any
	public String getPlayerTrail(UUID player) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "";
		String trail = null;
		
		try {
			conn = DriverManager.getConnection(database);
			
			pstmt = conn.prepareStatement("" +
				"SELECT trail " + 
				"FROM io__trails " +
				"WHERE uuid = ? "
			);
			
			pstmt.setString(1, player.toString());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				trail = rs.getString("trail");
			}
			rs.close();
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("Query: " + query);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return trail;
	}
	
	
	//Save a player's active trail or clear it
	public void savePlayerTrail(UUID uuid, String trail) {

		Connection conn = null;
		String query = "";
		
		try {
			conn = DriverManager.getConnection(database);
			
			PreparedStatement pstmt = null;
			
			if (trail != null && !trail.isEmpty()) {
				//Set the trail
				pstmt = conn.prepareStatement("" +
					"REPLACE INTO io__trails (uuid, trail) " +
					"VALUES (?, ?) "
				);
				pstmt.setString(1, uuid.toString());
				pstmt.setString(2, trail);
			} else {
				//Remove the active one, if any
				pstmt = conn.prepareStatement("" +
					"DELETE FROM io__trails " +
					"WHERE uuid = ? "
				);
				pstmt.setString(1, uuid.toString());
			}
			
			@SuppressWarnings("unused")
			int res = pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("Query: " + query);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
