package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import main.spaceinvaders2.datamodel.Player;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Controller of the "Start menu" scene
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class LeadersController implements Initializable {

    /**
     * Leaders ListView
     */
    @FXML
    private ListView<Player> leadersView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leadersView.setCellFactory(new LeadersCellFactory());
        leadersView.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("scrollbar.css")).toExternalForm());
        leadersView.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        leadersView.itemsProperty().bind(SpaceInvaders2App.players.playersProperty());
    }

    /**
     * Update info in the leadersView ListView
     */
    public void update() {
        leadersView.refresh();
    }

    /**
     * On "Close" button click action
     * Returns to the Main menu
     */
    @FXML
    protected void onCloseClick() {
        SpaceInvaders2App.showStartMenu();
    }
}
