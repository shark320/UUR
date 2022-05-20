package main.spaceinvaders2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.spaceinvaders2.datamodel.PlayersList;

import java.io.IOException;

public class SpaceInvaders2App extends Application {

    public static PlayersList players = new PlayersList();

    public static Scene startMenu;

    public static Scene leaders;

    public static Scene changeUser;

    public static Stage primaryStage;

/*    public static Parent */

    @Override
    public void start(Stage stage) throws IOException {
        initScenes();
        players.readFromFile();
        //players.initTest();
        stage.setTitle("SpaceInvaders");
        stage.setScene(startMenu);
        stage.show();
        primaryStage = stage;
    }

    private void initScenes() throws IOException{
        startMenu = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("Start_menu.fxml")).load());
        leaders = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("Leaders.fxml")).load());
        changeUser = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("LogIn.fxml")).load());
    }

    public static void main(String[] args) {
        launch();
    }
}