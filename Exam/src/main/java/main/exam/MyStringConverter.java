package main.exam;

import javafx.scene.control.Alert;
import javafx.util.converter.NumberStringConverter;

/**
 * Proprietary String converter for text field with units
 *
 * @author Volodymyr Pavlov
 * @version 23.05.2022
 */
public class MyStringConverter extends NumberStringConverter {

    /**
     * old field value
     */
    private Integer oldVal = 0;

    /**
     * units to use
     */
    private final String units;

    /**
     * Class constructor
     *
     * @param units -units to use
     */
    public MyStringConverter(String units) {
        this.units = units;
    }

    @Override
    public String toString(Number object) {
        oldVal = object.intValue();
        return formatString(oldVal);
    }

    @Override
    public Number fromString(String string) {
        try {
            int newVal = Integer.parseInt(string);
            if (newVal <= 0) {
                Alert cast = new Alert(Alert.AlertType.ERROR);
                cast.setHeaderText("value must be a positive integer");

                cast.show();
            } else {
                oldVal = newVal;
            }
        } catch (NumberFormatException e) {
            Alert cast = new Alert(Alert.AlertType.ERROR);
            cast.setHeaderText("Wrong integer value");
            cast.show();
        }
        return oldVal;
    }

    /**
     * String formatter
     *
     * @param number -number to format
     * @return number with separated digits
     */
    private String formatString(int number) {
        if (number == 0) return "0 " + units;
        StringBuilder sb = new StringBuilder();
        int th;
        while ((th = number % 1000) != 0 || number != 0) {
            if (th != 0) {
                sb.insert(0, th).insert(0, ' ');
            } else sb.insert(0, "000").insert(0, ' ');
            number /= 1000;
        }
        sb.replace(0, 1, "");
        sb.append(' ').append(units);

        return sb.toString();
    }
}
