module com.example.zadani_3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zadani_3 to javafx.fxml;
    exports com.example.zadani_3;
}