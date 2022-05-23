package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.spaceinvaders2.datamodel.Player;

import java.io.IOException;
import java.net.URISyntaxException;

import static main.spaceinvaders2.SpaceInvaders2App.players;

public class CreateUserController {

    @FXML
    protected TextField userNameField;
    @FXML
    protected PasswordField passwordField;

    @FXML
    protected PasswordField confirmPasswordField;

    @FXML
    protected void onCreateNewUserClick()  {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        //check tabs and spaces
        if (checkSpaces(userName)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("There are spaces or tabs in the username");
            alert.show();
            return;
        }

        if (!checkUserName(userName)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("This username is already in use");
            alert.show();
            return;
        }

        if (checkSpaces(password) || checkSpaces(confirmPassword)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("There are spaces or tabs in the password");
            alert.show();
            return;
        }

        if (!password.equals(confirmPassword)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Passwords are not the same");
            alert.show();
            return;
        }

        try {
            Player player = new Player(userName,0,password.hashCode());
            players.addNewPlayer(player);
            SpaceInvaders2App.currentPlayer=player;
            passwordField.clear();
            userNameField.clear();
            confirmPasswordField.clear();
            SpaceInvaders2App.showStartMenu();
        }catch (URISyntaxException | IOException URI){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Data file is corrupted or does not exist");
            alert.show();
        }


    }

    @FXML
    protected void onAlreadyHaveClick(){
        passwordField.clear();
        userNameField.clear();
        confirmPasswordField.clear();
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.changeUser);
    }

    private boolean checkUserName(String userName){
        //System.out.println(userName);
        for (Player player:players.getPlayers()){
            if (player.getNickName().equals(userName)){
                return false;
            }
        }

        return true;
    }

    private boolean checkSpaces(String line){
        return line.indexOf(9)!=-1 || line.indexOf(32)!=-1;
    }


}
