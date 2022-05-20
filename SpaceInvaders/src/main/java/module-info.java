module main.spaceinvaders {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.spaceinvaders to javafx.fxml;
    exports main.spaceinvaders;
}