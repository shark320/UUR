package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import main.spaceinvaders2.datamodel.Player;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.spaceinvaders2.SpaceInvaders2App.players;

public class CreateUserController implements Initializable {
    @FXML
    public BorderPane createUserPanel;
    @FXML
    protected TextField userNameField;
    @FXML
    protected PasswordField passwordField;

    @FXML
    protected PasswordField confirmPasswordField;

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

    @FXML
    protected void onAlreadyHaveClick() {
        passwordField.clear();
        userNameField.clear();
        confirmPasswordField.clear();
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.changeUser);
    }

    private boolean checkUserName(String userName) {
        //System.out.println(userName);
        for (Player player : players.getPlayers()) {
            if (player.getNickName().equals(userName)) {
                return false;
            }
        }

        return true;
    }

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
