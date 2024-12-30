package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginImpl {

	    // Method to check if the user exists and determine the role based on the name
	    public String getUserRole(String username, String password) {
	        String role = null;
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;

	        try {
	            // Get connection to the database
	            connection = Connexion.getConnexion();
	            if (connection == null || connection.isClosed()) {
	                connection = Connexion.getConnexion();  // Re-establish connection if closed
	            }

	            // SQL query to check if the username and password match
	            String query = "SELECT * FROM login WHERE name = ? AND password = ?";
	            preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, password);
	            resultSet = preparedStatement.executeQuery();

	            // If the username and password match, assign the role
	            if (resultSet.next()) {
	                String userNameFromDb = resultSet.getString("name");

	                // Determine the role based on the username
	                if (userNameFromDb.equals("Directeur") || userNameFromDb.equals("RH1")) {
	                    role = "Admin"; // Assign admin role to Directeur and RH1
	                } else if (userNameFromDb.equals("User")) {
	                    role = "User"; // Assign user role to User
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (resultSet != null) resultSet.close();
	                if (preparedStatement != null) preparedStatement.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        return role;
	    }
	}
