package main.spaceinvaders2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

    public static StartMenuController startMenuController;

    public static LeadersController leadersController;

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
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    players.savePlayers();
                    stop();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public static void showStartMenu(){
        isRunningGame=false;
        currentPlayerGreetings.setValue("Hello, "+currentPlayer.getNickName());
        primaryStage.setScene(startMenu);
        players.update(currentPlayer);
        leadersController.update();
        startMenuController.scoresLabel.setText("Scores: "+currentPlayer.getMaxScores());
    }



    private void initScenes() throws IOException, URISyntaxException {

        FXMLLoader loader3 = new FXMLLoader();
        startMenu = new Scene(loader3.load(SpaceInvaders2App.class.getResource("Start_menu.fxml").openStream()));
        startMenuController = loader3.getController();

        FXMLLoader loader2 = new FXMLLoader();
        leaders = new Scene(loader2.load(SpaceInvaders2App.class.getResource("Leaders.fxml").openStream()));
        leadersController=loader2.getController();
        changeUser = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("LogIn.fxml")).load());
        createUser = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("CreateUser.fxml")).load());

        FXMLLoader loader = new FXMLLoader();
        game = new Scene(loader.load(SpaceInvaders2App.class.getResource("Game.fxml").openStream()));
        gameController = loader.getController();
        gameEngine = GameEngine.getGameEngine(gameController);
    }

    public static void runGame(){
        try {
            gameEngine.init();

            long start = System.currentTimeMillis();
            AnimationTimer timer = new AnimationTimer() {

                private int count = 0;

                @Override
                public void handle(long now) {

                    gameEngine.update(System.currentTimeMillis() - start);
                    if (!isRunningGame){
                        stop();
                    }
                }
            };
            timer.start();
        }catch (Exception e){
            showError(e.getMessage());
        }
    }

    public static void showError(String info){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText(info);
        error.showAndWait();
    }
    public static void main(String[] args) {
        launch();
    }
}