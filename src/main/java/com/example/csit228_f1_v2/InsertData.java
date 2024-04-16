package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {
    public static void main(String[] args) {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("INSERT INTO users (name,email) VALUES (?,?)")) {
            String name = "Cherry Lyn Bossing";
            String email = "goat@gmail.com";
            statement.setString(1, name);
            statement.setString(2, email);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows Inserted: " + rowsInserted);
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
