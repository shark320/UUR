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

import static main.spaceinvaders2.SpaceInvaders2App.players;

/**
 * Controller of the "Create user" scene
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class CreateUserController implements Initializable {

    /**
     * Main scene pane
     */
    @FXML
    public BorderPane createUserPanel;

    /**
     * Username text field
     */
    @FXML
    protected TextField userNameField;

    /**
     * Password text field
     */
    @FXML
    protected PasswordField passwordField;


    /**
     * Confirm password text field
     */
    @FXML
    protected PasswordField confirmPasswordField;


    /**
     * On "Create new user" button click action
     * Checks all inputted data
     * If all is right, creates new user, logins this user and return to the Main menu
     */
    @FXML
    protected void onCreateNewUserClick() {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        //check tabs and spaces
        if (checkSpaces(userName)) {
            SpaceInvaders2App.showError("There are spaces or tabs in the username");
            return;
        }

        if (!checkUserName(userName)) {
            SpaceInvaders2App.showError("This username is already in use");
            return;
        }

        if (checkSpaces(password) || checkSpaces(confirmPassword)) {
            SpaceInvaders2App.showError("There are spaces or tabs in the password");
            return;
        }

        if (!password.equals(confirmPassword)) {
            SpaceInvaders2App.showError("Passwords are not the same");
            return;
        }

        Player player = new Player(userName, 0, password.hashCode());
        players.addNewPlayer(player);
        SpaceInvaders2App.currentPlayer = player;
        passwordField.clear();
        userNameField.clear();
        confirmPasswordField.clear();
        SpaceInvaders2App.showStartMenu();


    }

    /**
     * On "Already have" hyperlink click action
     * Changes current scene on the "Change user" scene
     */
    @FXML
    protected void onAlreadyHaveClick() {
        passwordField.clear();
        userNameField.clear();
        confirmPasswordField.clear();
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.changeUser);
    }

    /**
     * Checks if this username is free
     *
     * @param userName -username to check
     * @return true - if it's free, false - if user with this name is already exists
     */
    private boolean checkUserName(String userName) {
        for (Player player : players.getPlayers()) {
            if (player.getNickName().equals(userName)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if there are spaces in the string
     *
     * @param line - string to check
     * @return false if there are no spaces in the string, true - if there are at least one space
     */
    private boolean checkSpaces(String line) {
        return line.indexOf(9) != -1 || line.indexOf(32) != -1;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createUserPanel.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onCreateNewUserClick();
            }
        });
    }
}
