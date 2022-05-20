package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class StartMenuController {

    @FXML
    protected Button btnStart;
    @FXML
    protected void onStartClick(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AAAAAAAAAAA");
        alert.show();
    }

    @FXML
    protected void onLeadersClick(){
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.leaders);
    }

    @FXML
    protected void onChangeUserClick(){
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.changeUser);
    }
}