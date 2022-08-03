package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller of the "Start menu" scene
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class StartMenuController implements Initializable {

    /**
     * Max scores label
     */
    @FXML
    public Label scoresLabel;

    /**
     * Help info link
     */
    @FXML
    public Hyperlink helpLink;

    /**
     * Label with current username
     */
    @FXML
    protected Label userLabel;

    /**
     * Start button
     */
    @FXML
    protected Button btnStart;



    /**
     * On "Start" button click action
     * Starts a new game
     */
    @FXML
    protected void onStartClick() {
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.game);
        SpaceInvaders2App.isRunningGame = true;
        SpaceInvaders2App.runGame();
    }

    /**
     * On "Leaders" button click action
     * Change current scene on the "Leaders" scene
     */
    @FXML
    protected void onLeadersClick() {
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.leaders);
    }

    /**
     * On "Change user" button click action
     * Change current scene on the "Change user" scene
     */
    @FXML
    protected void onChangeUserClick() {
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.changeUser);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLabel.setText(SpaceInvaders2App.currentPlayer.getNickName());
        userLabel.textProperty().bind(SpaceInvaders2App.currentPlayerGreetings);
    }

    @FXML
    public void helpClick() {
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.helpInfo);
    }
}