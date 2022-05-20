package main.zadani_7;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.*;

/**
 * main application class
 * table with editable font properties
 *
 * @author Volodymyr Pavlov
 * @version 29.03.2022
 */
public class Main extends Application {

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                            Static constants                              //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * Spacing between elements in the boxes
     */
    private static final int DEF_SPACING = 10;

    /**
     * Padding between elements
     */
    private static final int DEF_PADDING = 10;

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                           Instance variables                             //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * List of current fonts
     */
    private final ObservableList<TextFontModel> fonts = FXCollections.observableArrayList();

    /**
     * Table of the fonts
     */
    private TableView<TextFontModel> fontTable;

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //                             Instance methods                             //
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////

    /**
     * Creates main program window
     *
     * @param stage -main program window
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Font table");
        Parent root = createRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * Creating main root panel
     *
     * @return root panel
     */
    private Parent createRoot() {
        createRandomFonts();
        BorderPane rootPane = new BorderPane();
        rootPane.setCenter(createTablePane());
        rootPane.setTop(createButtons());
        return rootPane;
    }

    /**
     * Creates buttons pane
     *
     * @return HBox with "Add new font" and "Delete" buttons
     */
    private Node createButtons() {
        HBox buttons = new HBox(DEF_SPACING);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(DEF_PADDING));

        Button createBtn = new Button("Add new font");
        createBtn.setOnAction(e -> addNewFont());

        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> deleteFont());

        buttons.getChildren().addAll(createBtn, deleteBtn);
        buttons.getChildren().forEach(btn -> {
            ((Button) btn).setPrefSize(200, 50);
            ((Button) btn).setAlignment(Pos.CENTER);
            ((Button) btn).setFont(new Font(25));
        });
        return buttons;
    }

    /**
     * Deletes selected font
     */
    private void deleteFont() {
        int index = fontTable.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("No selection");
            error.showAndWait();
        } else {
            fonts.remove(index);
            fontTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * add new font to the table
     * creates dialog window
     */
    private void addNewFont() {
        TextFontModel newFont;
        AddingWindow dialog = AddingWindow.getInstance();
        newFont = dialog.display();
        if (newFont != null) {
            int index = fontTable.getSelectionModel().getSelectedIndex();
            if (index == -1) {
                fonts.add(newFont);
            } else {
                fonts.add(index, newFont);
            }
            fontTable.refresh();
        }
    }

    /**
     * Creates table of the fonts
     *
     * @return created table
     */
    private Node createTablePane() {
        fontTable = new TableView<>();
        fontTable.setItems(fonts);
        fontTable.setEditable(true);

        //creating new columns

        TableColumn<TextFontModel, String> fontName = createNameColumn();

        TableColumn<TextFontModel, Integer> fontSize = createSizeColumn();

        TableColumn<TextFontModel, FontWeight> fontWeight = createWeightColumn();

        TableColumn<TextFontModel, FontPosture> fontPosture = createPostureColumn();

        TableColumn<TextFontModel, Color> fontColor = createColorColumn();

        TableColumn<TextFontModel, Boolean> visibility = createVisibilityColumn();

        TableColumn<TextFontModel, Text> fontExample = createExampleColumn();


        fontTable.getColumns().addAll(fontName, fontSize, fontWeight, fontPosture, fontColor, visibility, fontExample);
        fontTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return fontTable;
    }

    /**
     * Creates font name column with ComboBox
     *
     * @return font name column
     */
    private TableColumn<TextFontModel, String> createNameColumn() {
        TableColumn<TextFontModel, String> fontName = new TableColumn<>("Font name");
        fontName.setCellValueFactory(e -> e.getValue().fontNameProperty());
        fontName.setCellFactory(ComboBoxTableCell.forTableColumn(Font.getFamilies().toArray(new String[0])));
        fontName.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setFontName(event.getNewValue());
            event.getTableView().refresh();
        });

        return fontName;
    }

    /**
     * Creates font size column with ComboBox
     *
     * @return font size column
     */
    private TableColumn<TextFontModel, Integer> createSizeColumn() {
        TableColumn<TextFontModel, Integer> fontSize = new TableColumn<>("Font size");

        Integer[] sizes = new Integer[20];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = 6 + i * 2;
        }

        fontSize.setCellValueFactory(e -> e.getValue().sizeProperty().asObject());
        fontSize.setCellFactory(ComboBoxTableCell.forTableColumn(sizes));
        fontSize.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSize(event.getNewValue());
            event.getTableView().refresh();
        });

        return fontSize;
    }

    /**
     * Creates font weight column with ComboBox
     *
     * @return font weight column
     */
    private TableColumn<TextFontModel, FontWeight> createWeightColumn() {
        TableColumn<TextFontModel, FontWeight> fontWeight = new TableColumn<>("Font weight");
        fontWeight.setCellValueFactory(e -> e.getValue().fontWeightProperty());
        fontWeight.setCellFactory(ChoiceBoxTableCell.forTableColumn(FontWeight.values()));
        fontWeight.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setFontWeight(event.getNewValue());
            event.getTableView().refresh();
        });

        return fontWeight;
    }

    /**
     * Creates font color column with ColorPicker
     *
     * @return font color column
     */
    private TableColumn<TextFontModel, Color> createColorColumn() {
        TableColumn<TextFontModel, Color> fontColor = new TableColumn<>("Font color");
        fontColor.setCellValueFactory(e -> e.getValue().colorProperty());
        fontColor.setCellFactory(call -> new ColorCell());
        fontColor.setOnEditCommit(event -> {
            event.getRowValue().setColor(event.getNewValue());
            event.getTableView().refresh();
        });

        return fontColor;
    }

    /**
     * Creates font posture column with ComboBox
     *
     * @return font posture column
     */
    private TableColumn<TextFontModel, FontPosture> createPostureColumn() {
        TableColumn<TextFontModel, FontPosture> fontPosture = new TableColumn<>("Font posture");
        fontPosture.setCellValueFactory(e -> e.getValue().fontPostureProperty());
        fontPosture.setCellFactory(ChoiceBoxTableCell.forTableColumn(FontPosture.values()));
        fontPosture.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setFontPosture(event.getNewValue());
            event.getTableView().refresh();
        });

        return fontPosture;
    }

    /**
     * Creates font visibility column with ComboBox
     *
     * @return font visibility column
     */
    private TableColumn<TextFontModel, Boolean> createVisibilityColumn() {
        TableColumn<TextFontModel, Boolean> visibility = new TableColumn<>("Visibility");
        visibility.setCellValueFactory(e -> e.getValue().visibilityProperty());
        visibility.setCellFactory(ChoiceBoxTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Boolean object) {
                return object ? "VISIBLE" : "INVISIBLE";
            }

            @Override
            public Boolean fromString(String string) {
                return null;
            }
        }, new Boolean[]{true, false}));
        visibility.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setVisibility(event.getNewValue());
            event.getTableView().refresh();
        });

        return visibility;
    }

    /**
     * Creates font example column with Text
     *
     * @return font example column
     */
    private TableColumn<TextFontModel, Text> createExampleColumn() {
        TableColumn<TextFontModel, Text> fontExample = new TableColumn<>("Example");
        fontExample.setCellValueFactory(e -> {
            ObjectProperty<Text> textProperty = new SimpleObjectProperty<>();
            Text text = new Text("Here is an example text");
            text.setFont(Font.font(e.getValue().getFontName(), e.getValue().getFontWeight(), e.getValue().getFontPosture(), e.getValue().getSize()));
            text.setFill(e.getValue().getColor());
            text.setVisible(e.getValue().isVisibility());
            textProperty.set(text);
            return textProperty;
        });
        fontExample.setMinWidth(400);
        fontExample.setEditable(false);

        return fontExample;
    }

    /**
     * creates 20 random fonts
     */
    private void createRandomFonts() {
        Random rd = new Random();
        String[] families = Font.getFamilies().toArray(new String[0]);
        for (int i = 0; i < 20; i++) {
            TextFontModel font = new TextFontModel(families[rd.nextInt(families.length)]);
            fonts.add(font);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}