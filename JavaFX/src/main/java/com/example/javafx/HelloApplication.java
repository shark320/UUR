package com.example.javafx;

import Zaadni_2.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Hello!");
        stage.setScene(new Scene(getRoot(),300,250));
        stage.show();
    }

    private Parent getRoot() {
        VBox mainPanel = new VBox();
        mainPanel.getChildren().addAll(getDataPanel(),getControlPanel());
        
        return mainPanel;
    }

    private Node getDataPanel() {
        AbstractAnimal animal = new Eagle(0,0,100,"Eagle");
        VBox infoPanel = new VBox();
        Label animalName = new Label(animal.getName());
        HBox animalStats = new HBox();
        VBox animalX = new VBox(new Label("X"),new Label(String.valueOf(animal.getX())));
        VBox animalY = new VBox(new Label("Y"),new Label(String.valueOf(animal.getY())));
        VBox animalEnergy = new VBox(new Label("Energy"),new Label(String.valueOf(animal.getEnergy())));
        animalStats.getChildren().addAll(animalX,animalY,animalEnergy);
        infoPanel.getChildren().addAll(animalName,animalStats);

        return infoPanel;
    }

    public static void main(String[] args) {
        launch();
    }
}