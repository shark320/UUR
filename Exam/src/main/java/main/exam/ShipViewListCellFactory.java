package main.exam;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import main.exam.data.Component;

/**
 * UNUSED
 */
public class ShipViewListCellFactory implements Callback<ListView<Component>, ListCell<Component>> {

    private static Color color = Color.WHITE;

    private static Color textColor = Color.BLACK;
    private static class MyListCell extends ListCell<Component> {
        @Override
        public void updateItem(Component component, boolean empty){
            super.updateItem(component,empty);
            if (empty || component==null){
                setText(null);
                setGraphic(null);
                setStyle("-fx-background-color: #"+color.darker().toString().substring(2,8));
            }else{
                setText(component.toString());
                setGraphic(null);
                setStyle("-fx-background-color: #"+color.darker().toString().substring(2,8)
                +"; -fx-text-fill: #"+textColor.darker().toString().substring(2,8));
            }
        }
    }

    public void setMainColor (Color clr){
        color=clr;
    }

    public void setTextColor(Color clr){
        textColor=clr;
    }


    @Override
    public ListCell<Component> call(ListView<Component> param) {
        return new MyListCell();
    }
}
