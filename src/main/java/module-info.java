module com.example.csit228_f1_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.csit228_f1_v2 to javafx.fxml;
    exports com.example.csit228_f1_v2;
    exports sqlSample;
    opens sqlSample to javafx.fxml;
}