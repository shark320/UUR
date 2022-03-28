package main.zadani_5;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * main application class
 * build window with color controls and color panel
 *
 * @author Volodymyr Pavlov
 * @version 21.03.2022
 */
public class Main extends Application {

    /**
     * color model to show on screen
     */
    ColorModel colorModel = new ColorModel(0, 0, 0, 0);

    /**
     * maximal color value
     */
    private final int MAX_COLOR = 255;

    /**
     * minimal color value
     */
    private final int MIN_COLOR = 0;

    /**
     * maximal double value (brightness)
     */
    private final double MAX_DOUBLE_VAL = 1.0;

    /**
     * minimal double value (brightness)
     */
    private final double MIN_DOUBLE_VAL = 0.0;

    /**
     * create main program window
     *
     * @param stage -main program window
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Color panel");
        Parent root = createRoot();
        Scene scene = new Scene(root, 800, 700);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * creating main root panel
     *
     * @return root panel
     */
    private Parent createRoot() {
        BorderPane rootPane = new BorderPane();

        rootPane.setLeft(getControls());
        rootPane.setRight(getStatus());

        return rootPane;
    }

    /**
     * create panel with colored square and hex-code
     *
     * @return created panel
     */
    private Node getStatus() {
        VBox panel = new VBox(30);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.CENTER);
        ColorPanel colorPanel = new ColorPanel(300, 300);
        colorModel.addPropertyChangeListener(colorPanel);


        Label hexCode = new Label(colorModel.getHexCode());
        hexCode.setPrefSize(150, 30);
        hexCode.setAlignment(Pos.CENTER);
        hexCode.textProperty().bind(colorModel.hexCodeProperty());
        hexCode.setFont(new Font("Arial", 30));
        hexCode.setBorder(new Border
                (
                        new BorderStroke(
                                Color.BLACK,
                                BorderStrokeStyle.SOLID,
                                new CornerRadii(10.0),
                                BorderWidths.DEFAULT)
                ));
        panel.getChildren().addAll(colorPanel, hexCode);
        return panel;
    }

    /**
     * create panel with color controls
     *
     * @return created panel
     */
    private Node getControls() {
        VBox panel = new VBox(0);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.CENTER);

        VBox redCtrl = new VBox(10);
        redCtrl.setPadding(new Insets(20));
        redCtrl.setAlignment(Pos.CENTER);
        redCtrl.getChildren().addAll(getRedText(), getRedSlider());

        VBox greenCtrl = new VBox(10);
        greenCtrl.setPadding(new Insets(20));
        greenCtrl.setAlignment(Pos.CENTER);
        greenCtrl.getChildren().addAll(getGreenText(), getGreenSlider());

        VBox blueCtrl = new VBox(10);
        blueCtrl.setPadding(new Insets(20));
        blueCtrl.setAlignment(Pos.CENTER);
        blueCtrl.getChildren().addAll(gerBlueText(), getBlueSlider());

        VBox brightnessCtrl = new VBox(10);
        brightnessCtrl.setPadding(new Insets(20));
        brightnessCtrl.setAlignment(Pos.CENTER);
        brightnessCtrl.getChildren().addAll(gerBrightnessText(), getBrightnessSlider());

        /*VBox saturCtrl = new VBox(10);
        saturCtrl .setPadding(new Insets(20));
        saturCtrl .setAlignment(Pos.CENTER);
        saturCtrl .getChildren().addAll(gerSaturText(), getSaturSlider());*/

        panel.getChildren().addAll(redCtrl, greenCtrl, blueCtrl, brightnessCtrl);

        panel.getChildren().stream().forEach(Main::changeFonts);

        return panel;
    }

    /**
     * create saturation slider [0.0,1.0] (now is not used)
     *
     * @return created slider
     */
    private Node getSaturSlider() {
        Slider slider = new Slider(MIN_DOUBLE_VAL, MAX_DOUBLE_VAL, colorModel.getBrightness());
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.1);
        slider.valueProperty().bindBidirectional(colorModel.saturationProperty());
        return slider;
    }

    /**
     * create saturation text field (now is not used)
     *
     * @return created text field
     */
    private Node gerSaturText() {
        TextField text = new TextField(String.valueOf(colorModel.getSaturation()));
        HBox clrField = new HBox(10);
        text.textProperty().bindBidirectional(colorModel.saturationProperty(), getDblConverter(MIN_DOUBLE_VAL, MAX_DOUBLE_VAL, text));
        clrField.getChildren().addAll(new Label("Saturation:"), text);
        return clrField;
    }

    /**
     * create brightness slider [0.0,1.0]
     *
     * @return created slider
     */
    private Node getBrightnessSlider() {
        Slider slider = new Slider(MIN_DOUBLE_VAL, MAX_DOUBLE_VAL, colorModel.getBrightness());
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.1);
        slider.valueProperty().bindBidirectional(colorModel.brightnessProperty());
        return slider;
    }

    /**
     * create brightness text field
     *
     * @return created text field
     */
    private Node gerBrightnessText() {
        TextField text = new TextField(String.valueOf(colorModel.getBrightness()));
        HBox clrField = new HBox(10);
        text.textProperty().bindBidirectional(colorModel.brightnessProperty(), getDblConverter(MIN_DOUBLE_VAL, MAX_DOUBLE_VAL, text));
        clrField.getChildren().addAll(new Label("Brightness:"), text);
        return clrField;

    }

    /**
     * Changing fonts in all text fields
     *
     * @param node node with fields
     */
    private static void changeFonts(Node node) {
        if (node instanceof VBox) {
            ((VBox) node).getChildren().stream().forEach(Main::changeFonts);
        } else {
            if (node instanceof HBox) {
                ((HBox) node).getChildren().stream().forEach(e -> {
                    if (e instanceof Label) {
                        ((Label) e).setFont(new Font("Arial", 18));
                    } else if (e instanceof TextField) {
                        ((TextField) e).setFont(new Font("Arial", 18));
                    }
                });
            }
        }
    }

    /**
     * create blue slider [0,255]
     *
     * @return created slider
     */
    private Node getBlueSlider() {
        Slider slider = new Slider(MIN_COLOR, MAX_COLOR, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(64);
        slider.valueProperty().bindBidirectional(colorModel.blueProperty());
        return slider;
    }

    /**
     * create blue text field
     *
     * @return created text field
     */
    private Node gerBlueText() {
        HBox clrField = new HBox(10);
        TextField text = new TextField(String.valueOf(colorModel.getBlue()));
        text.textProperty().bindBidirectional(colorModel.blueProperty(), getIntConverter(MIN_COLOR, MAX_COLOR, text));
        clrField.getChildren().addAll(new Label("Blue:"), text);
        return clrField;
    }

    /**
     * create green slider [0,255]
     *
     * @return created slider
     */
    private Node getGreenSlider() {
        Slider slider = new Slider(MIN_COLOR, MAX_COLOR, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(64);
        slider.valueProperty().bindBidirectional(colorModel.greenProperty());

        return slider;
    }

    /**
     * create green text field
     *
     * @return created text field
     */
    private Node getGreenText() {
        HBox clrField = new HBox(10);
        TextField text = new TextField(String.valueOf(colorModel.getGreen()));
        text.textProperty().bindBidirectional(
                colorModel.greenProperty(),
                getIntConverter(MIN_COLOR, MAX_COLOR, text)
        );
        clrField.getChildren().addAll(new Label("Green:"), text);
        return clrField;
    }

    /**
     * create red slider [0,255]
     *
     * @return created slider
     */
    private Node getRedSlider() {
        Slider slider = new Slider(MIN_COLOR, MAX_COLOR, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(64);
        slider.valueProperty().bindBidirectional(colorModel.redProperty());

        return slider;
    }

    /**
     * create red text field
     *
     * @return created text field
     */
    private Node getRedText() {
        HBox clrField = new HBox(10);
        TextField text = new TextField(String.valueOf(colorModel.getRed()));
        text.textProperty().bindBidirectional(colorModel.redProperty(), getIntConverter(MIN_COLOR, MAX_COLOR, text));
        clrField.getChildren().addAll(new Label("Red:"), text);
        return clrField;
    }

    /**
     * creating new instance of special StringConverter String<->Integer
     *
     * @param minVal -minimal acceptable value
     * @param maxVal -maximal acceptable value
     * @param text   -text field
     * @return new instance of StringConverter<Number>
     */
    private StringConverter<Number> getIntConverter(Integer minVal, Integer maxVal, TextField text) {
        return new StringConverter<Number>() {

            Integer oldVal = 0;

            @Override
            public String toString(Number object) {
                oldVal = object.intValue();
                return object.toString();
            }


            @Override
            public Number fromString(String string) {
                if (string.length() == 0) {
                    oldVal = minVal;
                } else {
                    try {
                        Integer value;
                        value = Integer.valueOf(string);
                        if (value.compareTo(minVal) >= 0 && value.compareTo(maxVal) <= 0) {
                            oldVal = value;
                        } else if (value.compareTo(minVal) < 0) {
                            oldVal = minVal;
                        } else {
                            oldVal = maxVal;
                        }
                    } catch (NumberFormatException e) {
                        Alert cast = new Alert(Alert.AlertType.ERROR);
                        cast.setContentText("As value must be integer value");
                        cast.show();
                    }
                }
                text.setText(String.valueOf(oldVal));
                return oldVal;
            }
        };
    }

    /**
     * creating new instance of special StringConverter String<->Double
     *
     * @param minVal -minimal acceptable value
     * @param maxVal -maximal acceptable value
     * @param text   -text field
     * @return new instance of StringConverter<Number>
     */
    private StringConverter<Number> getDblConverter(Double minVal, Double maxVal, TextField text) {
        return new StringConverter<Number>() {

            Double oldVal = 0.0;

            @Override
            public String toString(Number object) {
                oldVal = object.doubleValue();
                return String.format("%.3f", oldVal);
            }


            @Override
            public Number fromString(String string) {
                if (string.length() == 0) {
                    oldVal = minVal;
                } else {
                    try {
                        Double value;
                        value = Double.valueOf(string);
                        if (value.compareTo(minVal) >= 0 && value.compareTo(maxVal) <= 0) {
                            oldVal = value;
                        } else if (value.compareTo(minVal) < 0) {
                            oldVal = minVal;
                        } else {
                            oldVal = maxVal;
                        }
                    } catch (NumberFormatException e) {
                        Alert cast = new Alert(Alert.AlertType.ERROR);
                        cast.setContentText("As value must be integer value");
                        cast.show();
                    }
                }
                text.setText(String.valueOf(oldVal));
                return oldVal;
            }
        };
    }


    public static void main(String[] args) {
        launch();
    }
}