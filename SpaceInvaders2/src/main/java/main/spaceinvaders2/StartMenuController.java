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
    public Label scoresLabel;
    @FXML
    protected Label userLabel;

    @FXML
    protected Button btnStart;
    @FXML
    protected void onStartClick(){
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.game);
        SpaceInvaders2App.isRunningGame = true;
        SpaceInvaders2App.runGame();
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