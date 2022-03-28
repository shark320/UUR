package com.example.zadani_3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    /**
     * is buttons visible now
     */
    private boolean isVisible = false;

    /**
     * default window title
     */
    private String titleDefault = "Default";

    /**
     * new window title
     */
    private String newTitle = "Updated";

    /**
     * main application stage
     */
    private Stage mainStage;

    /**
     * created labels
     */
    private VBox labels;

    /**
     * button box
     */
    private VBox buttons;

    /**
     * show all buttons
     *
     * @param actionEvent
     */
    private void showButtons(ActionEvent actionEvent) {
        if (!isVisible) {
            isVisible = true;
            buttons.getChildren().get(1).setVisible(true);
        }
    }

    /**
     * create all buttons
     */
    private void createButtons() {
        //includes all disappearing buttons
        VBox box = new VBox();
        box.setVisible(false);
        //print text to the console
        Button printText = new Button("Print Text");
        printText.setOnAction(this::printText);
        //change window title
        Button changeTitle = new Button("Change Title");
        changeTitle.setOnAction(this::changeTitle);
        //create new label with text
        Button createLabel = new Button("Create Label");
        createLabel.setOnAction(this::createLabel);
        //hide all disappearing buttons
        Button hideButtons = new Button("Hide Buttons");
        hideButtons.setOnAction(this::hideButtons);
        box.getChildren().addAll(printText, changeTitle, createLabel, hideButtons);
        buttons.getChildren().add(box);
    }

    /**
     * hide all disappearing buttons
     *
     * @param actionEvent
     */
    private void hideButtons(ActionEvent actionEvent) {
        buttons.getChildren().get(1).setVisible(false);
        isVisible = false;
    }

    /**
     * create new label with text
     *
     * @param actionEvent
     */
    private void createLabel(ActionEvent actionEvent) {
        Label label = new Label("Some label");
        labels.setVisible(true);
        label.setAlignment(Pos.BOTTOM_LEFT);
        labels.getChildren().add(label);
    }

    /**
     * print text to the console
     *
     * @param actionEvent
     */
    private void printText(ActionEvent actionEvent) {
        System.out.println("Something");
    }

    /**
     * change main window title
     *
     * @param actionEvent
     */
    private void changeTitle(ActionEvent actionEvent) {
        if (mainStage.getTitle() == titleDefault) {
            mainStage.setTitle(newTitle);
        } else {
            mainStage.setTitle(titleDefault);
        }
    }

    /**
     * create new Scene
     *
     * @return
     */
    private Parent getRoot() {
        FlowPane mainPanel = new FlowPane(Orientation.VERTICAL, 20, 20);
        labels = new VBox(10);
        buttons = new VBox(10);
        labels.setAlignment(Pos.TOP_RIGHT);
        labels.setVisible(false);
        labels.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        mainPanel.getChildren().add(new HBox(labels, buttons));
        Button button1 = new Button("Show other");
        buttons.getChildren().add(button1);
        mainPanel.setAlignment(Pos.BASELINE_CENTER);
        button1.setOnAction(this::showButtons);
        createButtons();
        return mainPanel;
    }

    /**
     * create new stage
     * show it
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle(titleDefault);
        stage.setScene(new Scene(getRoot(), 500, 300));
        mainStage = stage;
        stage.show();
    }

    /**
     * main method - program entry point
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}