package main.zadani_7;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * class that represents font data model
 *
 * @author Volodymyr Pavlov
 * @version 29.03.2022
 */
public class TextFontModel {

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                            Static constants                              //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * font default size, specified in points
     */
    private static final int DEF_SIZE = 18;

    /**
     * font default color
     */
    private static final Color DEF_COLOR = Color.BLACK;

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                           Instance variables                             //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * font family name property
     */
    private final StringProperty fontName = new SimpleStringProperty();

    /**
     * font size property, specified in points
     */
    private final IntegerProperty size = new SimpleIntegerProperty();

    /**
     * font weight property
     */
    private final ObjectProperty<FontWeight> fontWeight = new SimpleObjectProperty<>();

    /**
     * font posture property
     */
    private final ObjectProperty<FontPosture> fontPosture = new SimpleObjectProperty<>();

    /**
     * font visibility property
     */
    private final BooleanProperty visibility = new SimpleBooleanProperty();

    /**
     * font color property
     */
    private final ObjectProperty<Color> color = new SimpleObjectProperty<>();

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                              Constructors                                //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * Construct a font using specified parameters
     *
     * @param fontName    -font family name
     * @param size        -font size (in points)
     * @param fontWeight  -font weight
     * @param fontPosture -font posture
     * @param color       -font color
     * @param visibility  -font visibility
     */
    public TextFontModel(String fontName, int size, FontWeight fontWeight, FontPosture fontPosture, Color color, boolean visibility) {
        setFontName(fontName);
        setSize(size);
        setFontWeight(fontWeight);
        setFontPosture(fontPosture);
        setVisibility(visibility);
        setColor(color);
    }

    /**
     * Construct a font using default posture "REGULAR" and weight "NORMAL", is visible
     *
     * @param fontName -font family name
     * @param size     -font size (int points)
     * @param color    -font color
     */
    public TextFontModel(String fontName, int size, Color color) {
        this(fontName, size, FontWeight.NORMAL, FontPosture.REGULAR, color, true);
    }

    /**
     * Construct a font using default posture "REGULAR", weight "NORMAL" and color "DEF_COLOR", is visible
     *
     * @param fontName -font family name
     * @param size     -font size (int points)
     */
    public TextFontModel(String fontName, int size) {
        this(fontName, size, DEF_COLOR);
    }

    /**
     * Construct a font using default posture "REGULAR", weight "NORMAL", color "DEF_COLOR"  and size "DEF_SIZE"
     * is visible
     *
     * @param fontName -font family name
     */
    public TextFontModel(String fontName) {
        this(fontName, DEF_SIZE);
    }

    /**
     * Construct a font using default system font name,
     * posture "REGULAR", weight "NORMAL", color "DEF_COLOR"  and size "DEF_SIZE"
     * is visible
     */
    public TextFontModel() {
        this(Font.getDefault().getName(), DEF_SIZE);
    }

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                           Getters and setters                            //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    public String getFontName() {
        return fontName.get();
    }

    public StringProperty fontNameProperty() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName.set(Font.font(fontName).getName());
    }

    public int getSize() {
        return size.get();
    }

    public IntegerProperty sizeProperty() {
        return size;
    }

    public void setSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size of the font must be a positive integer");
        }
        this.size.set(size);
    }

    public FontWeight getFontWeight() {
        return fontWeight.get();
    }

    public ObjectProperty<FontWeight> fontWeightProperty() {
        return fontWeight;
    }

    public void setFontWeight(FontWeight fontWeight) {
        this.fontWeight.set(fontWeight);
    }

    public FontPosture getFontPosture() {
        return fontPosture.get();
    }

    public ObjectProperty<FontPosture> fontPostureProperty() {
        return fontPosture;
    }

    public void setFontPosture(FontPosture fontPosture) {
        this.fontPosture.set(fontPosture);
    }

    public boolean isVisibility() {
        return visibility.get();
    }

    public BooleanProperty visibilityProperty() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }
}
