package main.spaceinvaders2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import main.spaceinvaders2.datamodel.Player;

/**
 * Proprietary CellFactory for the Leaders ListView
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class LeadersCellFactory implements Callback<ListView<Player>, ListCell<Player>> {
    /**
     * Background color
     */
    private static final String BACKGROUND_COLOR = "-fx-background-color: #3a0ca3";

    /**
     * Default Insets
     */
    private static final Insets INSETS = new Insets(10, 20, 10, 20);

    /**
     * Inner private cell class
     */
    private static class LeadersCell extends ListCell<Player> {

        @Override
        public void updateItem(Player player, boolean empty) {
            super.updateItem(player, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
                setStyle(BACKGROUND_COLOR);
            } else if (player != null) {
                setText(null);
                BorderPane borders = new BorderPane();

                Label nick = new Label(player.getNickName());
                nick.setTextFill(Color.valueOf("#fca311"));
                nick.setAlignment(Pos.CENTER_LEFT);
                nick.setFont(new Font(20));
                nick.setPadding(INSETS);

                Label scores = new Label(player.getMaxScores() + "");
                scores.setTextFill(Color.valueOf("#fca311"));
                scores.setAlignment(Pos.CENTER_RIGHT);
                scores.setFont(new Font(20));
                scores.setPadding(INSETS);

                borders.setLeft(nick);
                borders.setRight(scores);
                setGraphic(borders);
                setStyle(BACKGROUND_COLOR);
            } else {
                setStyle(BACKGROUND_COLOR);
                setText(null);
                setGraphic(null);
            }
        }
    }

    @Override
    public ListCell<Player> call(ListView<Player> param) {
        return new LeadersCell();
    }
}
