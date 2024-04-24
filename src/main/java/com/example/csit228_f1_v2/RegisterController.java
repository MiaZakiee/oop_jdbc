package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;
import sqlSample.MySqlConnection;

import java.io.IOException;
import java.sql.*;

public class RegisterController {
    @FXML
    public Pane pnRegister;
    @FXML
    public TextField txtFname;
    @FXML
    public TextField txtLname;
    @FXML
    public TextField txtUser;
    @FXML
    public PasswordField txtPass;
    @FXML
    public Label txtStatus;

    @FXML
    public void openSignin() throws IOException {
        Parent targetView = FXMLLoader.load(LoginApplication.class
                .getResource("login-view.fxml"));
        AnchorPane p = (AnchorPane) pnRegister.getParent();
        p.getChildren().remove(pnRegister);
        p.getChildren().add(targetView);
    }

    @FXML
    public void handleRegister() throws IOException {
//        checks if input is empty
        if (txtFname.getText().isEmpty() || txtPass.getText().isEmpty() || txtUser.getText().isEmpty()) {
            txtStatus.setText("Incomplete form! Please complete all fields");
            return;
        }
        boolean canRegister = false;
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT COUNT(*) FROM tblusers WHERE username = ?")) {
            statement.setString(1, txtUser.getText());
            ResultSet rs = statement.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                canRegister = true;
            }
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (canRegister) {
            try (Connection c = MySqlConnection.getConnection();
                 PreparedStatement statement = c.prepareStatement("INSERT INTO tblusers (fname,lname,username,password) VALUES (?,?,?,?)")) {

                statement.setString(1, txtFname.getText());
                statement.setString(2, txtLname.getText());
                statement.setString(3, txtUser.getText());
                statement.setString(4, txtPass.getText());
                statement.executeUpdate();
                txtStatus.setText("Register Successful!");
                c.close();
            } catch (SQLException e) {
                txtStatus.setText("Error in register try again");
                throw new RuntimeException(e);
            }
        } else {
            txtStatus.setText("User already exists,");
        }
//
    }
}
