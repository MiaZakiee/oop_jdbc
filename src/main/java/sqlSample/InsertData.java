package sqlSample;

import java.nio.file.FileAlreadyExistsException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {
    public static void main(String[] args) {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("INSERT INTO tbltodo (todo_id,user_id,task,isFinished) VALUES (?,?,?,?)")) {
            int tid = 1;
            int uid = 1;
            String task = "pakamatay";
            boolean finished = false;

            statement.setInt(1, tid);
            statement.setInt(2, uid);
            statement.setString(3, task);
            statement.setBoolean(4, finished);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows Inserted: " + rowsInserted);
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
