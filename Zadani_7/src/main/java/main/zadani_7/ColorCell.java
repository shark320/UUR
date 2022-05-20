package main.zadani_7;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * class represents ColorPicker cell for a table column
 *
 * @author Volodymyr Pavlov
 * @version 30.03.2022
 */
public class ColorCell extends TableCell<TextFontModel, Color> {

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                           Instance variables                             //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * the instance of ColorPicker
     */
    private ColorPicker colorPicker;

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                            Override methods                              //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////


    @Override
    public void startEdit() {
        super.startEdit();

        if (colorPicker == null) {
            createColorPicker();
        }

        setGraphic(colorPicker);

        colorPicker.show();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        colorPicker.setValue(this.getItem());

        //replace ColorPicker with colored (ColorPicker color) rectangle
        setGraphic(new Rectangle(120, 20, colorPicker.getValue()));
    }

    @Override
    protected void updateItem(Color item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (colorPicker != null) {
                    colorPicker.setValue(item);
                }
                setGraphic(colorPicker);
            } else {
                //replace ColorPicker with colored (new item color) rectangle
                setGraphic(new Rectangle(120, 20, item));
            }
        }
    }

    private void createColorPicker() {
        colorPicker = new ColorPicker();

        colorPicker.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                event.consume();
            }
        });

        colorPicker.setOnAction(event -> {
            if (colorPicker.getValue() != null) {
                commitEdit(colorPicker.getValue());
            } else {
                event.consume();
                cancelEdit();
            }
        });
    }
}
