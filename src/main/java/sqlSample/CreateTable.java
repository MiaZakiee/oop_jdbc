package sqlSample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        Connection c = MySqlConnection.getConnection();
        String query = "CREATE TABLE tblTodo (" +
                "todo_id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT," +  // Foreign key reference to tblusers
                "task VARCHAR(100)," +
                "isFinished BOOLEAN," +
                "FOREIGN KEY (user_id) REFERENCES tblusers(id)" +
                ")";


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
