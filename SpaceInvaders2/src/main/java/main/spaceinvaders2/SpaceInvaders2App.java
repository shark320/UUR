package main.spaceinvaders2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import main.spaceinvaders2.datamodel.Player;
import main.spaceinvaders2.datamodel.PlayersList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * Main application class
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class SpaceInvaders2App extends Application {

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                             Static variables                             //
    //                                                                          //
    //--------------------------------------------------------------------------//
    /**
     * Current logged in player
     */
    public static Player currentPlayer;

    /**
     * Is game running
     */
    public static boolean isRunningGame = false;

    /**
     * List of all exist players
     */
    public static PlayersList players = new PlayersList();

    /**
     * Greetings to the current player
     */
    public static StringProperty currentPlayerGreetings = new SimpleStringProperty();

    /**
     * Game engine (Only one instance)
     */
    private static GameEngine gameEngine;

    /**
     * Main application stage
     */
    public static Stage primaryStage;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                         Static FXML controllers                          //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Game controller
     */
    public static GameController gameController;

    /**
     * Start menu controller
     */
    public static StartMenuController startMenuController;

    /**
     * Leaders controller
     */
    public static LeadersController leadersController;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                         Static scenes variables                          //
    //                                                                          //
    //--------------------------------------------------------------------------//

    /**
     * Start menu scene
     */
    public static Scene startMenu;

    /**
     * Leaders scene
     */
    public static Scene leaders;

    /**
     * Change user scene
     */
    public static Scene changeUser;

    /**
     * Create user scene
     */
    public static Scene createUser;

    /**
     * Game scene
     */
    public static Scene game;

    //--------------------------------------------------------------------------//
    //                                                                          //
    //                              Instance methods                            //
    //                                                                          //
    //--------------------------------------------------------------------------//


    /**
     * Shows main menu, updates all data on the "Start menu" scene
     */
    public static void showStartMenu() {
        isRunningGame = false;
        currentPlayerGreetings.setValue("Hello, " + currentPlayer.getNickName());
        primaryStage.setScene(startMenu);
        players.update(currentPlayer);
        leadersController.update();
        startMenuController.scoresLabel.setText("Scores: " + currentPlayer.getMaxScores());
    }

    /**
     * Initializes all scenes from FXML files
     *
     * @throws IOException - if FXML file does not exist
     */
    private void initScenes() throws IOException {

        FXMLLoader loader3 = new FXMLLoader();
        startMenu = new Scene(loader3.load(Objects.requireNonNull(SpaceInvaders2App.class.getResource("Start_menu.fxml")).openStream()));
        startMenuController = loader3.getController();

        FXMLLoader loader2 = new FXMLLoader();
        leaders = new Scene(loader2.load(Objects.requireNonNull(SpaceInvaders2App.class.getResource("Leaders.fxml")).openStream()));
        leadersController = loader2.getController();
        changeUser = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("LogIn.fxml")).load());
        createUser = new Scene(new FXMLLoader(SpaceInvaders2App.class.getResource("CreateUser.fxml")).load());

        FXMLLoader loader = new FXMLLoader();
        game = new Scene(loader.load(Objects.requireNonNull(SpaceInvaders2App.class.getResource("Game.fxml")).openStream()));
        gameController = loader.getController();
        gameEngine = GameEngine.getGameEngine(gameController);
    }

    /**
     * Run the game
     */
    public static void runGame() {
        try {
            gameEngine.init();

            long start = System.currentTimeMillis();
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {

                    gameEngine.update(System.currentTimeMillis() - start);
                    if (!isRunningGame) {
                        stop();
                    }
                }
            };
            timer.start();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    /**
     * Shows error alert with an information message
     *
     * @param info - information to show
     */
    public static void showError(String info) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText(info);
        error.showAndWait();
    }

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        players.readFromFile();
        currentPlayer = players.getDefaultPlayer();
        initScenes();
        stage.setTitle("SpaceInvaders");
        primaryStage = stage;
        showStartMenu();
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(event -> {
            try {
                players.savePlayers();
                stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Main method - application entry point
     *
     * @param args -   in this application isn't used
     */
    public static void main(String[] args) {
        launch();
    }
}