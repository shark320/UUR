package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.spaceinvaders2.datamodel.Player;

public class ChangeUserController {

    @FXML
    protected TextField userNameField;

    @FXML
    protected PasswordField passwordField;

    @FXML
    protected void onLoginClick(){
        Player player = checkUsername(userNameField.getText());
        if (player ==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("This user does not exist");
            alert.show();
            return;
        }

        if (passwordField.getText().hashCode() != player.getPassHash()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect password");
            alert.show();
            return;
        }

        userNameField.clear();
        passwordField.clear();

        SpaceInvaders2App.currentPlayer = player;
        SpaceInvaders2App.showStartMenu();
    }

    @FXML
    protected void onCreateNewClick(){
        SpaceInvaders2App.primaryStage.setScene(SpaceInvaders2App.createUser);
    }

    private Player checkUsername(String username){
        for (Player player: SpaceInvaders2App.players.getPlayers()){
            if (player.getNickName().equals(username)){
                return player;
            }
        }

        return null;
    }

}
