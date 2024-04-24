package sqlSample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateData {
    public static void main(String[] args) {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("UPDATE users SET name=? where id=?")) {
            String new_name = "Nino Rey S. Cabiltes";
            int id = 1;
            statement.setString(1, new_name);
            statement.setInt(2,id);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
