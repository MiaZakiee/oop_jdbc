package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sqlSample.MySqlConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotController {
    @FXML
    public Pane pnForgot;
    @FXML
    public TextField txtUser;
    @FXML
    public TextField txtPass1;
    @FXML
    public PasswordField txtPass2;
    @FXML
    public Label txtStatus;
    @FXML
    public void openSignin() throws IOException {
        Parent targetView = FXMLLoader.load(LoginApplication.class
                .getResource("login-view.fxml"));
        AnchorPane p = (AnchorPane) pnForgot.getParent();
        p.getChildren().remove(pnForgot);
        p.getChildren().add(targetView);
    }

    @FXML
    public void handleReset() throws IOException {
        if (txtUser.getText().isEmpty() || txtPass1.getText().isEmpty() || txtPass2.getText().isEmpty()) {
            txtStatus.setText("Incomplete form! Please complete all fields");
            return;
        }

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT password FROM tblusers WHERE username = ?")) {
            statement.setString(1, txtUser.getText());
            ResultSet rs = statement.executeQuery();

            // User valid
            if (rs.next()) {
//                pass is same
                if (txtPass1.getText().equals(txtPass2.getText())) {
                    PreparedStatement changePass = c.prepareStatement("UPDATE tblusers SET password = ? WHERE username = ?");
                    changePass.setString(1,txtPass1.getText());
                    changePass.setString(2,txtUser.getText());

                    changePass.executeUpdate();
                    txtStatus.setText("Password changed successfully!");
                }
            } else {
                txtStatus.setText("User does not exist.");
            }
        } catch (SQLException e) {
            txtStatus.setText("Login failed due to system error.");
            throw new RuntimeException(e);
        }
    }
}
