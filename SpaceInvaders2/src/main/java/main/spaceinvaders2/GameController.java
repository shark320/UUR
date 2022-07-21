package main.spaceinvaders2;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.spaceinvaders2.gamemodels.AbstractModel;
import main.spaceinvaders2.gamemodels.EnemyModel;
import main.spaceinvaders2.gamemodels.PlayerModel;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static main.spaceinvaders2.SpaceInvaders2App.createUser;

public class GameController implements Initializable{

    private static final double PANE_WIDTH = 417.2;

    private static final double PANE_HEIGHT = 619.2;

    @FXML
    public HBox topPane;

    @FXML
    public BorderPane root;

    @FXML
    public Pane gamePane;
    public HBox lifeBox;


    public void test() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.show();

        //gamePane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

    }


    public Pane getPane(){
        return gamePane;
    }

    public HBox getLifeBox(){
        return lifeBox;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Group topElements = new Group();
        topElements.getChildren().add(topPane);
        root.getChildren().add(topElements);
    }
}
