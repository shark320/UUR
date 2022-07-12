package main.spaceinvaders2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.spaceinvaders2.datamodel.Player;
import main.spaceinvaders2.datamodel.PlayersList;
import main.spaceinvaders2.gamemodels.EnemyModel;
import main.spaceinvaders2.gamemodels.PlayerModel;

import java.io.IOException;
import java.net.URISyntaxException;

public class SpaceInvaders2App extends Application {

    public static Player currentPlayer ;

    public static boolean isRunningGame = false;
    public static PlayersList players = new PlayersList();

    public static StringProperty currentPlayerGreetings = new SimpleStringProperty();

    public static GameController gameController;

    public static Scene startMenu;

    public static Scene leaders;

    public static Scene changeUser;

    public static Scene createUser;

    public static Scene game;
    public static Stage primaryStage;

    private static GameEngine gameEngine;

/*    public static Parent */

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
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



    private void initScenes() throws IOException, URISyntaxException {
        startMenu = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("Start_menu.fxml")).load());
        leaders = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("Leaders.fxml")).load());
        changeUser = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("LogIn.fxml")).load());
        createUser = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("CreateUser.fxml")).load());

        FXMLLoader loader = new FXMLLoader();
        game = new Scene(loader.load(SpaceInvaders2App.class.getResource("Game.fxml").openStream()));
        gameController = loader.getController();
        gameEngine = GameEngine.getGameEngine(gameController.getPane());
    }

    public static void runGame(){
        gameEngine.init();

        long start = System.currentTimeMillis();
        AnimationTimer timer = new AnimationTimer() {

            private int count = 0;
            @Override
            public void handle(long now) {
                gameEngine.update(System.currentTimeMillis()-start);
            }
        };
        timer.start();
    };
    public static void main(String[] args) {
        launch();
    }
}