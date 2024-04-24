package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sqlSample.MySqlConnection;

import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractCollection;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public static int accountID;
    public static String fname;
    public static String lname;

    @FXML
    public Label txtWelcome;
    @FXML
    public VBox pnData;
    @FXML
    public VBox lastChild = pnData;
    @FXML
    public TextArea lastTextArea;
    @FXML
    public Pane pnMain;

    @FXML
    protected void displayAllData() {
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement("SELECT task, isFinished FROM tbltodo WHERE user_id = ?")) {
            statement.setInt(1, accountID);
            ResultSet rs = statement.executeQuery();

            // Clear existing content in the pane
            pnData.getChildren().clear();

            // Iterate over the result set and create TextArea and CheckBox for each entry
            while (rs.next()) {
                String task = rs.getString("task");
                boolean isFinished = rs.getBoolean("isFinished");

                // Create a TextArea for the task
                TextArea textArea = new TextArea(task);
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setPrefWidth(295.0); // Adjust the width as needed
                textArea.setPrefHeight(38.0); // Adjust the width as needed

                // Create a CheckBox for the isFinished status
                CheckBox checkBox = new CheckBox("");
                checkBox.setSelected(isFinished);
                checkBox.setDisable(false); // Make the CheckBox non-editable

//                choice boxxx
                ChoiceBox choiceBox = new ChoiceBox();
                choiceBox.setPrefWidth(10.0);
                choiceBox.getItems().add("Edit");
                choiceBox.getItems().add("Delete");

                // Create a VBox to hold TextArea and CheckBox for each entry
                HBox entryBox = new HBox(5); // Spacing between TextArea and CheckBox
                entryBox.setAlignment(Pos.CENTER);
                entryBox.setPrefWidth(390.0);
                entryBox.getChildren().addAll(checkBox, textArea,choiceBox);

                // Create a VBox to hold the entry HBox
                VBox vbox = new VBox();
                vbox.getChildren().add(entryBox);

                lastChild = vbox;
                lastTextArea = textArea;

                // Add the entry VBox to the pane
                pnData.getChildren().add(vbox);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        }
    }

    @FXML
    public void addTodo () {
        if (lastTextArea.getText().isEmpty()) {
            if (!(lastChild == pnData)) {
                return;
            }
        }

        // Create a TextArea for the task
        TextArea textArea = new TextArea();
        textArea.setEditable(true);
        textArea.setWrapText(true);
        textArea.setPrefWidth(295.0); // Adjust the width as needed
        textArea.setPrefHeight(38.0); // Adjust the width as needed

        // Create a CheckBox for the isFinished status
        CheckBox checkBox = new CheckBox("");
        checkBox.setSelected(false);
        checkBox.setDisable(false); // Make the CheckBox non-editable

        // Create a ChoiceBox for options
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setPrefWidth(10.0);
        choiceBox.getItems().addAll("Edit", "Delete");

        // Create an HBox to hold TextArea, CheckBox, and ChoiceBox
        HBox entryBox = new HBox(5); // Spacing between TextArea and CheckBox
        entryBox.setAlignment(Pos.CENTER);
        entryBox.setPrefWidth(390.0);
        entryBox.getChildren().addAll(checkBox, textArea, choiceBox);

        // Add the entry HBox to the lastChild VBox
        lastChild.getChildren().add(entryBox);
    }

    @FXML
    public void signout() throws IOException {
        Parent targetView = FXMLLoader.load(LoginApplication.class
                .getResource("login-view.fxml"));
        AnchorPane p = (AnchorPane) pnMain.getParent();
        p.getChildren().remove(pnMain);
        p.getChildren().add(targetView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayAllData();
        txtWelcome.setText("Welcome " + fname + lname);
    }
}
