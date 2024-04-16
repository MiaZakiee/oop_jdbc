package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LoginController {
    public AnchorPane pnMain;
    public Pane pnLogin;
    public TextArea txtUser;
    public TextArea txtPassword;
    public Button btnLogin;
    public TextArea txtRegister;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void onSigninClick() throws IOException {
        Parent homeview = FXMLLoader.load(LoginApplication.class.getResource("login-view.fxml"));
        AnchorPane p = (AnchorPane) pnLogin.getParent();
        p.getChildren().remove(pnLogin);
        p.getChildren().add(homeview);
//        Parent homeview = FXMLLoader.load(HelloApplication.class
//                .getResource("home-view.fxml"));
//        AnchorPane p = (AnchorPane) pnLogin.getParent();
//        p.getChildren().remove(pnLogin);
//        p.getChildren().add(homeview);

    }
}