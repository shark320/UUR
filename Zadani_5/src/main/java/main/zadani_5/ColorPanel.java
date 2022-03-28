package main.zadani_5;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * rectangle filled with specified color
 * depends on outsource properties
 *
 * @author Volodymyr Pavlov
 * @version 21.03.2022
 */
public class ColorPanel extends BorderPane implements PropertyChangeListener {

    /**
     * rectangle to fill with color
     */
    private final Rectangle rect;

    /**
     * constructor
     *
     * @param width  -width of the rectangle
     * @param height -height of the rectangles
     */
    public ColorPanel(double width, double height) {
        super();
        this.setPrefSize(width, height);
        this.setMinSize(width, height);
        rect = new Rectangle(width - 5, height - 5, Color.BLACK);
        setCenter(rect);
        super.setBorder(
                new Border
                        (new BorderStroke(
                                Color.BLACK,
                                BorderStrokeStyle.SOLID,
                                new CornerRadii(0),
                                new BorderWidths(5))
                        )
        );
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            if (evt.getPropertyName() == "hex_value") {
                rect.setFill(Paint.valueOf((String) evt.getNewValue()));
            }
        } catch (ClassCastException cast) {
            System.err.println(cast);
        }
    }
}
