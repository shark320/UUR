package com.example.test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {

    private static final int INIT = 0;
    private final int MIN = 0;
    private final int MAX = 255;


    DataModel myModel = new DataModel(0, 0, 0);

    @Override
    public void start(Stage stage) {
        stage.setTitle("Color sliders");
        stage.setScene(new Scene(getRootPane(), 500, 500));
        stage.show();
    }

    private Parent getRootPane() {
        BorderPane rootPane = new BorderPane();
        rootPane.setTop(getMenu());
        rootPane.setLeft(getControls());
        rootPane.setRight(getColorRectangle());
        return rootPane;
    }

    private Node getColorRectangle() {
        Rectangle rectangle = new Rectangle();
        return rectangle;
    }

    private Node getControls() {
        VBox controls = new VBox(10);

        Label redComp = new Label("Red");
        redComp.setFont(new Font("Arial", 20));
        Slider redSlider = new Slider(MIN, MAX, INIT);
        ObservingTextField redText = new ObservingTextField();
        myModel.addPropertyChangeListener(redText);
        redText.setEditable(false);

        redSlider.setShowTickLabels(true);
        redSlider.setShowTickMarks(true);
        redSlider.setMajorTickUnit(15);
        redSlider.valueProperty().bindBidirectional(myModel.redIntegerDataProperty());

        controls.getChildren().addAll(redComp, redSlider, redText);

        Label greenComp = new Label("Green");
        greenComp.setFont(new Font("Arial", 20));
        Slider greenSlider = new Slider(MIN, MAX, INIT);
        ObservingTextField greenText = new ObservingTextField();
        myModel.addPropertyChangeListener(greenText);
        greenText.setEditable(false);

        greenSlider.setShowTickLabels(true);
        greenSlider.setShowTickMarks(true);
        greenSlider.setMajorTickUnit(15);
        greenSlider.valueProperty().bindBidirectional(myModel.greenIntegerDataProperty());

        controls.getChildren().addAll(greenComp, greenSlider, greenText);

        Label blueComp = new Label("Blue");
        blueComp.setFont(new Font("Arial", 20));
        Slider blueSlider = new Slider(MIN, MAX, INIT);
        ObservingTextField blueText = new ObservingTextField();
        myModel.addPropertyChangeListener(blueText);
        blueText.setEditable(false);

        blueSlider.setShowTickLabels(true);
        blueSlider.setShowTickMarks(true);
        blueSlider.setMajorTickUnit(15);
        blueSlider.valueProperty().bindBidirectional(myModel.blueIntegerDataProperty());

        controls.getChildren().addAll(blueComp, blueSlider, blueText);

        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(0, 0, 0, 20));
        return controls;
    }

    private MenuBar getMenu() {
        MenuBar menuBar = new MenuBar();

        Menu app = new Menu("_Application");
        MenuItem exitApp = new MenuItem("_Exit");
        exitApp.setOnAction(event -> Platform.exit());

        app.getItems().add(exitApp);

        menuBar.getMenus().add(app);

        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}