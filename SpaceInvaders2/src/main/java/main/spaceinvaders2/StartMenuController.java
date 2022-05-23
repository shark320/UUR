package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class StartMenuController implements Initializable {

    @FXML
    protected Label userLabel;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLabel.setText(SpaceInvaders2App.currentPlayer.getNickName());
        userLabel.textProperty().bind(SpaceInvaders2App.currentPlayerGreetings);
    }
}