package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of the game scene
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class GameController implements Initializable {

    /**
     * HBox of the top section
     */
    @FXML
    public HBox topPane;

    /**
     * Main scene pane
     */
    @FXML
    public BorderPane root;

    /**
     * Game pane
     */
    @FXML
    public Pane gamePane;

    /**
     * HBox of the life visualization
     */
    @FXML
    public HBox lifeBox;

    /**
     * HBox of the scores visualization
     */
    @FXML
    public HBox scoresBox;

    /**
     * Scores text field
     */
    @FXML
    public Text scores;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Group topElements = new Group();
        topElements.getChildren().add(topPane);
        root.getChildren().add(topElements);
    }
}
