package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import main.spaceinvaders2.datamodel.Player;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the "Change user" scene
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class ChangeUserController implements Initializable {

    /**
     * Main scene pane
     */
    @FXML
    public BorderPane logInPane;

    /**
     * Username text field
     */
    @FXML
    protected TextField userNameField;

    /**
     * Password field
     */
    @FXML
    protected PasswordField passwordField;


    /**
     * Action on the login button click
     * Checks all inputted data, if all is clear - login user and returns to the Main menu
     */
    @FXML
    protected void onLoginClick() {
        Player player = checkUsername(userNameField.getText());
        if (player == null) {
            SpaceInvaders2App.showError("This user does not exist");
            return;
        }

        if (passwordField.getText().hashCode() != player.getPassHash()) {
            SpaceInvaders2App.showError("Incorrect password");
            return;
        }

        userNameField.clear();
        passwordField.clear();

        SpaceInvaders2App.currentPlayer = player;
        SpaceInvaders2App.showStartMenu();
    }

    /**
     * Action on "Create new" button click
     * Changes current scene on "Create user" scene
     */
    @FXML
    protected void onCreateNewClick() {
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.createUser);
    }

    /**
     * Checking if this username is exists
     *
     * @param username -username to check
     * @return Player instance if it's match, null  - if there is no user with this name
     */
    private Player checkUsername(String username) {
        for (Player player : SpaceInvaders2App.players.getPlayers()) {
            if (player.getNickName().equals(username)) {
                return player;
            }
        }

        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInPane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onLoginClick();
            }
        });
    }
}
