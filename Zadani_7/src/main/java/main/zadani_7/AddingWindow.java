package main.zadani_7;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.Random;

/**
 * One-instance class of a dialog window
 * creating new text font
 *
 * @author Volodymyr Pavlov
 * @version 30.03.2022
 */
public class AddingWindow extends Stage {

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                            Static constants                              //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * default elements spacing
     */
    private static final double DEF_SPACING = 20;

    /**
     * default elements padding
     */
    private static final double DEF_PADDING = 30;

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                             Static variables                             //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * the only one instance of this class
     */
    private static AddingWindow instance;

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                           Instance variables                             //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * current font model to edit
     */
    private TextFontModel font = new TextFontModel();

    /**
     * font name combobox
     */
    private ComboBox<String> fontName;

    /**
     * font size combobox
     */
    private ComboBox<Integer> fontSize;

    /**
     * font weight combobox
     */
    private ComboBox<FontWeight> fontWeight;

    /**
     * font posture combobox
     */
    private ComboBox<FontPosture> fontPosture;

    /**
     * font color combobox
     */
    private ColorPicker fontColor;

    /**
     * font visibility combobox
     */
    private ComboBox<String> fontVisibility;

    /**
     * example text, using editing font
     */
    private final Text example = new Text("Here is an example text");

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                              Constructors                                //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * private constructor
     * set onClose action
     */
    private AddingWindow() {
        super();
        this.setOnCloseRequest(event -> closeAction());
    }

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                             Static methods                               //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * static instance method
     *
     * @return the only one instance of this class
     */
    public static AddingWindow getInstance() {
        if (instance == null) {
            instance = new AddingWindow();
        }
        return instance;
    }

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                             Instance methods                             //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * setting font as null, if the window is closing without commit font
     */
    private void closeAction() {
        instance.font = null;
        this.close();
    }

    /**
     * build window of this class
     *
     * @return new font or null, if application was closed without cont commit
     */
    public TextFontModel display() {
        font = new TextFontModel();
        if (instance == null) {
            instance = new AddingWindow();
        }
        instance.font = font;
        instance.setTitle("Adding new font");
        instance.setScene(new Scene(getRoot()));
        instance.showAndWait();
        return font;
    }

    /**
     * build new scene root
     *
     * @return scene root pane
     */
    private Parent getRoot() {
        BorderPane root = new BorderPane();
        root.setCenter(createControls());
        root.setBottom(createButtons());
        root.setTop(createExampleText());
        return root;
    }

    /**
     * creating new VBox with an example text
     *
     * @return VBox with text
     */
    private Node createExampleText() {
        VBox border = new VBox(example);
        border.setAlignment(Pos.CENTER);
        border.setMinHeight(300);
        refreshExample();

        return border;
    }

    /**
     * creating control buttons
     *
     * @return HBox with buttons
     */
    private Node createButtons() {
        HBox buttons = new HBox(DEF_SPACING);
        buttons.setPadding(new Insets(DEF_PADDING));
        buttons.setAlignment(Pos.CENTER);

        Button create = new Button("Create");
        create.setOnAction(e -> instance.close());

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> closeAction());

        Button random = new Button("Random");
        random.setOnAction(e -> generateRandom());

        buttons.getChildren().addAll(create, random, cancel);
        return buttons;
    }

    /**
     * generate random font parameters
     */
    private void generateRandom() {
        Random r = new Random();
        FontWeight[] weights = FontWeight.values();
        FontPosture[] postures = FontPosture.values();
        String[] families = Font.getFamilies().toArray(new String[0]);
        Color color = new Color(r.nextDouble(1.0), r.nextDouble(1.0), r.nextDouble(1.0), r.nextDouble(1.0));
        font = new TextFontModel(families[r.nextInt(families.length)], 6 + 2 * r.nextInt(20), weights[r.nextInt(weights.length)], postures[r.nextInt(postures.length)], color, true);
        refreshExample();
    }

    /**
     * create comboboxes with font parameters
     *
     * @return HBox, that contains 3 VBoxes with 2 HBoxes with controls
     */
    private Node createControls() {

        HBox controlsBoxes = new HBox(DEF_SPACING);
        controlsBoxes.setPadding(new Insets(DEF_PADDING));
        controlsBoxes.setAlignment(Pos.CENTER);

        HBox fontNameBox = createFontNameBox();

        HBox fontSizeBox = createFontSizeBox();

        HBox fontWeightBox = createFontWeightBox();

        HBox fontPostureBox = createFontPostureBox();

        HBox fontColorBox = createColorBox();

        HBox fontVisibilityBox = createFontVisibilityBox();

        VBox first = new VBox(DEF_SPACING, fontNameBox, fontSizeBox);

        VBox second = new VBox(DEF_SPACING, fontWeightBox, fontPostureBox);

        VBox third = new VBox(DEF_SPACING, fontColorBox, fontVisibilityBox);

        controlsBoxes.getChildren().addAll(first, second, third);

        controlsBoxes.getChildren().forEach(vbox -> ((VBox) vbox).getChildren().forEach(e -> ((HBox) e).getChildren().forEach(ctrl -> {
            if (ctrl instanceof Label) {
                ((Control) ctrl).setPrefWidth(70);
            } else {
                ((Control) ctrl).setPrefWidth(150);
            }
        })));

        return controlsBoxes;
    }

    /**
     * create ComboBox of visibility with label
     *
     * @return HBox with ComboBox and Label
     */
    private HBox createFontVisibilityBox() {
        fontVisibility = new ComboBox<>(FXCollections.observableArrayList("VISIBLE", "INVISIBLE"));
        fontVisibility.setValue(font.isVisibility() ? "VISIBLE" : "INVISIBLE");
        fontVisibility.setOnAction(e -> {
            switch (fontVisibility.getValue()) {
                case "VISIBLE" -> font.setVisibility(true);
                case "INVISIBLE" -> font.setVisibility(false);
            }
            refreshExample();
        });

        return new HBox(DEF_SPACING, new Label("Visibility:"), fontVisibility);
    }

    /**
     * create ColorPicker with label
     *
     * @return HBox with ComboBox and Label
     */
    private HBox createColorBox() {
        fontColor = new ColorPicker();
        fontColor.setValue(font.getColor());
        fontColor.setOnAction(e -> {
            font.setColor(fontColor.getValue());
            refreshExample();
        });
        fontColor.setBackground(new Background(new BackgroundFill(font.getColor(), new CornerRadii(0), new Insets(0))));

        return new HBox(DEF_SPACING, new Label("Font color:"), fontColor);
    }

    /**
     * create ComboBox of font posture with label
     *
     * @return HBox with ComboBox and Label
     */
    private HBox createFontPostureBox() {
        fontPosture = new ComboBox<>(FXCollections.observableArrayList(FontPosture.values()));
        fontPosture.setValue(font.getFontPosture());
        fontPosture.setOnAction(e -> {
            font.setFontPosture(fontPosture.getValue());
            refreshExample();
        });

        return new HBox(DEF_SPACING, new Label("Font Posture:"), fontPosture);
    }

    /**
     * create ComboBox of font weight with label
     *
     * @return HBox with ComboBox and Label
     */
    private HBox createFontWeightBox() {
        fontWeight = new ComboBox<>(FXCollections.observableArrayList(FontWeight.values()));
        fontWeight.setValue(font.getFontWeight());
        fontWeight.setOnAction(e -> {
            font.setFontWeight(fontWeight.getValue());
            refreshExample();
        });

        return new HBox(DEF_SPACING, new Label("Font weight:"), fontWeight);
    }

    /**
     * create ComboBox of visibility with label
     *
     * @return HBox with ComboBox and Label
     */
    private HBox createFontSizeBox() {
        Integer[] sizes = new Integer[20];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = 6 + i * 2;
        }
        fontSize = new ComboBox<>(FXCollections.observableArrayList(sizes));
        fontSize.setValue(font.getSize());
        fontSize.setOnAction(e -> {
            font.setSize(fontSize.getValue());
            refreshExample();
        });

        return new HBox(DEF_SPACING, new Label("Font size:"), fontSize);
    }

    /**
     * create ComboBox of font family names with label
     *
     * @return HBox with ComboBox and Label
     */
    private HBox createFontNameBox() {
        fontName = new ComboBox<>(FXCollections.observableArrayList(Font.getFamilies()));
        fontName.setValue(font.getFontName());
        fontName.setOnAction(e -> {
            font.setFontName(fontName.getValue());
            refreshExample();
        });

        return new HBox(DEF_SPACING, new Label("Font name:"), fontName);
    }

    /**
     * update all data in controls and example text
     */
    private void refreshExample() {
        example.setFont(Font.font(font.getFontName(), font.getFontWeight(), font.getFontPosture(), font.getSize()));
        example.setVisible(font.isVisibility());
        example.setFill(font.getColor());
        fontName.setValue(font.getFontName());
        fontSize.setValue(font.getSize());
        fontWeight.setValue(font.getFontWeight());
        fontPosture.setValue(font.getFontPosture());
        fontColor.setValue(font.getColor());
        fontVisibility.setValue(font.isVisibility() ? "VISIBLE" : "INVISIBLE");
        fontColor.setBackground(
                new Background(
                        new BackgroundFill(
                                font.getColor(),
                                new CornerRadii(3),
                                new Insets(0)
                        )
                )
        );
        fontColor.setBorder(
                new Border(
                        new BorderStroke(
                                Color.BLACK,
                                BorderStrokeStyle.SOLID,
                                new CornerRadii(2),
                                new BorderWidths(1)
                        )
                )
        );
    }

}
