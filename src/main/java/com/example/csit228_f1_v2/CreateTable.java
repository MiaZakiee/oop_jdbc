package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        Connection c = MySqlConnection.getConnection();
        String query = "CREATE TABLE tblOopAct (" +
        "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(50) NOT NULL," +
                "password VARCHAR(50) NOT NULL)";

        try {
            Statement statement = c.createStatement();
            statement.execute(query);
            System.out.println("Table created successfully");
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
