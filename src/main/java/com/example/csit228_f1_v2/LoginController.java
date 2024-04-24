package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sqlSample.MySqlConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
//    main pane
    @FXML
    public Pane pnLogin;
//    leading panes
    @FXML
    public AnchorPane pnRegister;
    @FXML
    public AnchorPane pnMain;
    @FXML
    public AnchorPane pnForgot;

    @FXML
    public TextArea txtUser;
    @FXML
    public PasswordField txtPass;
    @FXML
    public Button btnLogin;
    @FXML
    public Hyperlink btnRegister;
    @FXML
    public Hyperlink btnForgot;
    @FXML
    public Label txtStatus;

    @FXML
    protected void loginHandle() {
        // Ensure the fields are not empty
        if (txtUser.getText().isEmpty() || txtPass.getText().isEmpty()) {
            txtStatus.setText("Credentials incomplete! Please complete the form.");
            return;
        }

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT password,fname,lname FROM tblusers WHERE username = ?")) {
            statement.setString(1, txtUser.getText());
            ResultSet rs = statement.executeQuery();

            // Check if a row was returned
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(txtPass.getText())) {
                    // Load the main view if password matches
                    MainController.accountID = rs.getInt("id");
                    MainController.fname = rs.getString("fname");
                    MainController.lname = rs.getString("lname");
                    Parent targetView = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                    AnchorPane p = (AnchorPane) pnLogin.getParent();
                    p.getChildren().remove(pnLogin);
                    p.getChildren().add(targetView);
                } else {
                    txtStatus.setText("Password incorrect.");
                }
            } else {
                txtStatus.setText("User does not exist.");
            }
        } catch (SQLException | IOException e) {
            txtStatus.setText("Login failed due to system error.");
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void openRegister() throws IOException {
        Parent targetView = FXMLLoader.load(getClass().getResource("register-view.fxml"));
        AnchorPane p = (AnchorPane) pnLogin.getParent();
        p.getChildren().remove(pnLogin);
        p.getChildren().add(targetView);
    }
    @FXML
    protected void openForgot() throws IOException {
        Parent targetView = FXMLLoader.load(ForgotApplication.class
                .getResource("forgot-view.fxml"));
        AnchorPane p = (AnchorPane) pnLogin.getParent();
        p.getChildren().remove(pnLogin);
        p.getChildren().add(targetView);
    }
}

//pnHome is login
//pnMain is destination(main)