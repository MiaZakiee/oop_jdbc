package sqlSample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteData {
    public static void main(String[] args) {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("DELETE FROM users Where id > ?")) {
            int starting_id = 2;
            statement.setInt(1,starting_id);
            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted);
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
