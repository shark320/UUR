package main.spaceinvaders2;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.spaceinvaders2.datamodel.Player;
import main.spaceinvaders2.datamodel.PlayersList;

import java.io.IOException;

public class SpaceInvaders2App extends Application {

    public static Player currentPlayer ;
    public static PlayersList players = new PlayersList();

    public static StringProperty currentPlayerGreetings = new SimpleStringProperty();

    private static Scene startMenu;

    public static Scene leaders;

    public static Scene changeUser;

    public static Scene createUser;

    public static Stage primaryStage;

/*    public static Parent */

    @Override
    public void start(Stage stage) throws IOException {
        players.readFromFile();
        currentPlayer= players.getDefaultPlayer();
        //players.initTest();
        initScenes();
        stage.setTitle("SpaceInvaders");
        primaryStage = stage;
        showStartMenu();
        stage.show();
    }

    public static void showStartMenu(){
        currentPlayerGreetings.setValue("Hello, "+currentPlayer.getNickName());
        primaryStage.setScene(startMenu);
    }



    private void initScenes() throws IOException{
        startMenu = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("Start_menu.fxml")).load());
        leaders = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("Leaders.fxml")).load());
        changeUser = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("LogIn.fxml")).load());
        createUser = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("CreateUser.fxml")).load());
    }

    public static void main(String[] args) {
        launch();
    }
}