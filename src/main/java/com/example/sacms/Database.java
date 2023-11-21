package com.example.sacms;

import java.sql.*;

public class Database {
    final String connectionUrl;

    public Database() {
        connectionUrl = "jdbc:mysql://localhost:3306/sacms";
    }
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(connectionUrl, "root", "");
        } catch (SQLException sqlException) {
            System.out.println("Could not get SQL Connection");
            System.out.println(sqlException.getMessage());
            return null;
        }
    }

    public boolean validateLogin(String username, String password){
        String query = "SELECT * FROM member WHERE username = ? AND password = ?";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
        }catch (SQLException e){
            System.out.println("Error validating login");
            System.out.println(e.getMessage());
            return false;
        }
    }
}
