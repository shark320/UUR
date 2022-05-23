package main.exam;


import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import main.exam.data.Component;

/**
 * Proprietary list cell factory
 *
 * @author Volodymyr Pavlov
 * @version 23.05.2022
 */
public class MyListViewCellFactory implements Callback<ListView<Component>, ListCell<Component>> {

    /**
     * Proprietary list cell class
     */
    private static class MyListCell extends ListCell<Component> {


        @Override
        public void updateItem(Component item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);

            } else {
                setText(item.getProducerAndName());
                setGraphic(null);
            }
        }
    }

    @Override
    public ListCell<Component> call(ListView<Component> param) {
        return new MyListCell();
    }
}
