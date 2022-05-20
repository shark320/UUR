package main.spaceinvaders;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.spaceinvaders.datamodel.PlayersList;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Start_menu.fxml"));
        Scene scene = new Scene(new BorderPane(), 500, 800);
        stage.setTitle("Hello!");
        PlayersList pl = new PlayersList();
        pl.readFromFile();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}