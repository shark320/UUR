package main.zadani_5;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * data model, represents colors
 *
 * @version 21.03.2022
 * @author Volody,yr Pavlov
 */
public class ColorModel {

    /**
     * green color component
     */
    private final IntegerProperty green = new SimpleIntegerProperty();

    /**
     * red color component
     */
    private final IntegerProperty red = new SimpleIntegerProperty();

    /**
     * blue color component
     */
    private final IntegerProperty blue = new SimpleIntegerProperty();

    /**
     * hex code (HTML-style) of the color
     */
    private final StringProperty hexCode = new SimpleStringProperty();

    /**
     * brightness of the color
     */
    private final DoubleProperty brightness = new SimpleDoubleProperty();

    /**
     * saturation of the color
     */
    private final DoubleProperty saturation = new SimpleDoubleProperty();

    /**
     * current color
     */
    private Color color;

    /**
     * property change listener
     */
    private final PropertyChangeSupport listenerManager = new PropertyChangeSupport(this);

    /**
     * color model constructor
     *
     * @param red        -red component, 0 to 255
     * @param green      -green component, 0 to 255
     * @param blue       -blue component, 0 to 255
     * @param brightness -default brightness, 0.0 to 1.0
     */
    public ColorModel(int red, int green, int blue, double brightness) {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255 || brightness < 0 || brightness > 1) {
            throw new IllegalArgumentException("invalid initial parameters");
        }

        this.red.set(red);
        this.green.set(green);
        this.blue.set(blue);
        color = Color.rgb(red, green, blue);
        color = Color.hsb(color.getHue(), color.getSaturation(), brightness);
        this.brightness.set(color.getBrightness());
        this.saturation.set(color.getSaturation());
        setHexCode();

        //red listener
        this.red.addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.intValue() < 0 || newValue.intValue() > 255) {
                        throw new IllegalArgumentException("red must be in range [0,255]");
                    }
                    listenerManager.firePropertyChange("red_value", oldValue, newValue);
                    color = Color.rgb(newValue.intValue(), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
                    this.brightness.set(color.getBrightness());
                    setHexCode();
                    //System.out.println("Red: "+getRed());
                }
        );

        //green listener
        this.green.addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.intValue() < 0 || newValue.intValue() > 255) {
                        throw new IllegalArgumentException("green must be in range [0,255]");
                    }
                    listenerManager.firePropertyChange("green_value", oldValue, newValue);
                    color = Color.rgb((int) (color.getRed() * 255), newValue.intValue(), (int) (color.getBlue() * 255));
                    this.brightness.set(color.getBrightness());
                    setHexCode();
                    //System.out.println("Green: "+getGreen());
                }
        );

        //blue listener
        this.blue.addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.intValue() < 0 || newValue.intValue() > 255) {
                        throw new IllegalArgumentException("blue must be in range [0,255]");
                    }
                    listenerManager.firePropertyChange("blue_value", oldValue, newValue);
                    color = Color.rgb((int) (color.getRed() * 255), (int) (color.getGreen() * 255), newValue.intValue());
                    this.brightness.set(color.getBrightness());
                    setHexCode();
                    //System.out.println("BLue: "+getBlue());
                }
        );

        //brightness listener
        this.brightness.addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.doubleValue() < 0 || newValue.doubleValue() > 1) {
                        throw new IllegalArgumentException("brightness must be in range [0,1]");
                    }
                    listenerManager.firePropertyChange("brightness", oldValue, newValue);
                    color = Color.hsb(color.getHue(), color.getSaturation(), newValue.doubleValue());
                    this.red.set((int) (color.getRed() * 255));
                    this.green.set((int) (color.getGreen() * 255));
                    this.blue.set((int) (color.getBlue() * 255));
                    setHexCode();
                    //System.out.println("Brightness: "+getBrightness());
                }
        );

        //saturation listener
        this.saturation.addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.doubleValue() < 0 || newValue.doubleValue() > 1) {
                        throw new IllegalArgumentException("saturation must be in range [0,1]");
                    }
                    listenerManager.firePropertyChange("saturation", oldValue, newValue);
                    color = Color.hsb(color.getHue(), newValue.doubleValue(), color.getBrightness());
                    this.red.set((int) (color.getRed() * 255));
                    this.green.set((int) (color.getGreen() * 255));
                    this.blue.set((int) (color.getBlue() * 255));
                    setHexCode();
                    //System.out.println("Brightness: "+getBrightness());
                }
        );

        //hex code listener
        this.hexCode.addListener(
                (observable, oldValue, newValue) -> listenerManager.firePropertyChange("hex_value", oldValue, newValue)
        );
    }

    /**
     * hex code setter (represents RGB into hex code)
     */
    private void setHexCode() {
        hexCode.set(
                String.format("#%02X%02X%02X", red.get(), green.get(), blue.get())
        );
    }

    /**
     * adding property change listener
     *
     * @param listener property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        System.out.println("Registering " + listener);
        listenerManager.addPropertyChangeListener(listener);
    }

    /**
     * removing property change listener
     *
     * @param listener -listener to add
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listenerManager.removePropertyChangeListener(listener);
    }

    /**
     * green value getter
     *
     * @return green value
     */
    public int getGreen() {
        return green.get();
    }

    /**
     * green property getter
     *
     * @return green property
     */
    public IntegerProperty greenProperty() {
        return green;
    }

    /**
     * red value getter
     *
     * @return red value
     */
    public int getRed() {
        return red.get();
    }

    /**
     * red property getter
     *
     * @return red property
     */
    public IntegerProperty redProperty() {
        return red;
    }

    /**
     * blue value getter
     *
     * @return blue value
     */
    public int getBlue() {
        return blue.get();
    }

    /**
     * blue property getter
     *
     * @return blue property
     */
    public IntegerProperty blueProperty() {
        return blue;
    }

    /**
     * hex code getter
     *
     * @return hex code
     */
    public String getHexCode() {
        return hexCode.get();
    }

    /**
     * hex code property getter
     *
     * @return hex code property
     */
    public StringProperty hexCodeProperty() {
        return hexCode;
    }

    /**
     * brightness value getter
     *
     * @return brightness value
     */
    public double getBrightness() {
        return brightness.get();
    }

    /**
     * brightness property getter
     *
     * @return brightness property
     */
    public DoubleProperty brightnessProperty() {
        return brightness;
    }

    /**
     * saturation value getter
     *
     * @return saturation value
     */
    public double getSaturation() {
        return saturation.get();
    }

    /**
     * saturation property getter
     *
     * @return saturation property
     */
    public DoubleProperty saturationProperty() {
        return saturation;
    }

}
