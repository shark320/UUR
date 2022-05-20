module main.spaceinvaders2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens main.spaceinvaders2 to javafx.fxml;
    exports main.spaceinvaders2;
}