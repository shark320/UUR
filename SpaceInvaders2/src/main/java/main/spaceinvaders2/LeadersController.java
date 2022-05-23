package main.spaceinvaders2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import main.spaceinvaders2.datamodel.Player;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;



public class LeadersController implements Initializable {

    @FXML
    private ListView<Player> leadersView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leadersView.setCellFactory(new LeadersCellFactory());
        //System.out.println(getClass().getClassLoader().getResource("scrollbar.css"));
        leadersView.getStylesheets().add(getClass().getClassLoader().getResource("scrollbar.css").toExternalForm());
        leadersView.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        leadersView.itemsProperty().bind(SpaceInvaders2App.players.playersProperty());
    }

    @FXML
    protected void onCloseClick() throws IOException, URISyntaxException {
        SpaceInvaders2App.showStartMenu();
    }
}
