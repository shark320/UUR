package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import main.spaceinvaders2.datamodel.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeUserController implements Initializable {

    @FXML
    public BorderPane logInPane;
    @FXML
    protected TextField userNameField;

    @FXML
    protected PasswordField passwordField;



    @FXML
    protected void onLoginClick(){
        Player player = checkUsername(userNameField.getText());
        if (player ==null){
            SpaceInvaders2App.showError("This user does not exist");
            return;
        }

        if (passwordField.getText().hashCode() != player.getPassHash()){
            SpaceInvaders2App.showError("Incorrect password");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInPane.setOnKeyPressed(e->{
            if (e.getCode()== KeyCode.ENTER){
                onLoginClick();
            }
        });
    }
}
