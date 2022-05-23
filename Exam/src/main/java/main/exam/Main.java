package main.exam;

import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
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
import main.exam.data.AvailableComponents;
import main.exam.data.Component;
import main.exam.data.ComponentType;
import main.exam.data.SpaceShip;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * main application class
 * ship components editor
 *
 * @author Volodymyr Pavlov
 * @version 23.05.2022
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
     * Instance of SpaceShip
     */
    private final SpaceShip ship = new SpaceShip();

    /**
     * ListView with all available components
     */
    private ListView<Component> componentView;

    /**
     * Current selected component to edit/add
     */
    private Component currentComponent;

    /**
     * Root BorderPane component
     */
    private BorderPane root;

    /**
     * VBox with ship data
     */
    private VBox shipBox;

    /**
     * Power shortage warning label
     */
    private Label powerShortage;

    /**
     * TextField to edit weight of the component
     */
    private TextField weight;

    /**
     * TextField to edit power of the component
     */
    private TextField power;


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
        stage.setMinWidth(1400);
        stage.setMinHeight(800);
    }

    /**
     * Creating main root panel
     *
     * @return root panel
     */
    private Parent createRoot() {
        BorderPane rootPane = new BorderPane();
        rootPane.setLeft(createLeft());
        rootPane.setCenter(createNotChosen());
        rootPane.setRight(createRight());
        root = rootPane;
        return rootPane;
    }

    /**
     * Creating pane with available components view
     *
     * @return VBox with available components view and label
     */
    private Node createComponentView() {

        ListView<Component> components = new ListView<>();
        components.setCellFactory(new MyListViewCellFactory());

        ListProperty<Component> list = new SimpleListProperty<>(FXCollections.observableArrayList());
        list.addAll(AvailableComponents.getComponents());
        components.setItems(list);
        components.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateSelectionActionComponents();
        });

        componentView = components;

        Label label = new Label("Available components");
        label.setFont(new Font(20));

        VBox componentViewBox = new VBox(label, components);

        componentViewBox.setPadding(new Insets(DEF_PADDING));

        componentViewBox.setSpacing(DEF_SPACING);

        return componentViewBox;
    }

    /**
     * Creating pane with color picker elements
     *
     * @return VBox with color pickers
     */
    private Node createColorPickers() {
        VBox colorPickerBox = new VBox();

        VBox mainBox = new VBox(DEF_SPACING);
        mainBox.setPadding(new Insets(DEF_PADDING));

        //main color ColorPicker
        ColorPicker mainPicker = new ColorPicker(ship.getMainColor());
        Label mainLabel = new Label("Main ship color");
        mainLabel.setFont(new Font(15));
        mainBox.getChildren().addAll(mainLabel, mainPicker);

        mainPicker.setOnAction(e -> {
            ship.setMainColor(mainPicker.getValue());
            setColors(shipBox);
        });

        //Additional color ColorPicker
        VBox additionalBox = new VBox(DEF_SPACING);
        additionalBox.setPadding(new Insets(DEF_PADDING));

        ColorPicker additionalPicker = new ColorPicker(ship.getAdditionalColor());
        Label additionalLabel = new Label("Additional ship color");
        additionalLabel.setFont(new Font(15));
        additionalBox.getChildren().addAll(additionalLabel, additionalPicker);

        additionalPicker.setOnAction(e -> {
            ship.setAdditionalColor(additionalPicker.getValue());
            setColors(shipBox);
        });

        colorPickerBox.getChildren().addAll(mainBox, additionalBox);
        return colorPickerBox;
    }

    /**
     * Creating left part of the window
     *
     * @return VBox with components view and color pickers
     */
    private Node createLeft() {
        VBox leftBox = new VBox(DEF_SPACING);
        leftBox.setPadding(new Insets(DEF_PADDING));
        leftBox.setAlignment(Pos.TOP_CENTER);

        leftBox.getChildren().addAll(createComponentView(), createColorPickers());

        return leftBox;
    }

    /**
     * Pane with information about that there is no selection yet
     *
     * @return VBox with info label
     */
    private Node createNotChosen() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Label label = new Label("No Component selected");
        label.setFont(new Font(40));
        label.setTextFill(Color.RED);
        vBox.getChildren().add(label);

        return vBox;
    }

    /**
     * Creating center pane with editing controls
     * @return  VBox of controls to edit components
     */
    private Node createCenter() {
        VBox centerBox = new VBox(DEF_SPACING);
        centerBox.setPadding(new Insets(DEF_PADDING));
        centerBox.setAlignment(Pos.CENTER);

        centerBox.getChildren().addAll(createTextFields(), createAddButton());


        return centerBox;
    }

    /**
     * Creating pane with editable text fields
     *
     * @return  VBox with editable TextFields
     */
    private Node createTextFields() {
        VBox fieldsBox = new VBox(DEF_SPACING);
        fieldsBox.setAlignment(Pos.CENTER);

        Label title = new Label("Editing component " + currentComponent);
        title.setFont(new Font(20));

        VBox weightBox = new VBox(DEF_SPACING);
        weightBox.setAlignment(Pos.CENTER);

        Label weightTitle = new Label("Weight");
        weightTitle.setFont(new Font(15));
        weightTitle.setAlignment(Pos.CENTER);

        weight = new TextField();
        weight.textProperty().bindBidirectional(currentComponent.weightProperty(), new MyStringConverter("kg"));
        weight.setAlignment(Pos.CENTER);

        weightBox.getChildren().addAll(weightTitle, weight);


        VBox powerBox = new VBox(DEF_SPACING);
        powerBox.setAlignment(Pos.CENTER);


        Label powerTitle = new Label();
        if (currentComponent.getType() == ComponentType.REACTOR) {
            powerTitle.setText("Reactor power");
        } else {
            powerTitle.setText("Power consumption");
        }
        powerTitle.setAlignment(Pos.CENTER);
        powerTitle.setFont(new Font(15));

        power = new TextField();
        power.setAlignment(Pos.CENTER);
        power.textProperty().bindBidirectional(currentComponent.powerProperty(), new MyStringConverter("MW"));

        powerBox.getChildren().addAll(powerTitle, power);

        fieldsBox.getChildren().addAll(title, weightBox, powerBox);

        return fieldsBox;
    }

    /**
     * Creating Add button
     * @return Add button
     */
    private Node createAddButton() {
        Button addButton = new Button("Save and add component to the ship");
        addButton.setFont(new Font(15));

        AtomicBoolean flag = new AtomicBoolean(true);

        addButton.setOnAction(e -> {
            if (ship.isPresentComponent(currentComponent)) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setHeaderText("This component type is already present on the ship, replace it?");
                if (confirm.showAndWait().get() != ButtonType.OK) {
                    flag.set(false);
                }
            }

            if (flag.get()) {
                ship.addComponent(currentComponent);
                powerShortage.setVisible(ship.isPowerShortage());
                if (ship.isPowerShortage()) {
                    Alert shortage = new Alert(Alert.AlertType.WARNING);
                    shortage.setHeaderText("There is power shortage on the ship!");
                    shortage.show();
                }
            }

        });

        return addButton;
    }

    /**
     * Creating right pane of the window
     * @return pane with spaceship info
     */
    private Node createRight() {
        AtomicReference<Component> selectedShipComponent = new AtomicReference<>(null);
        shipBox = new VBox(DEF_SPACING);
        shipBox.setAlignment(Pos.TOP_CENTER);
        shipBox.setPadding(new Insets(DEF_PADDING));
        shipBox.setMinWidth(400);

        Label title = new Label("Space ship info");
        title.setFont(new Font(40));

        //name pane
        VBox nameBox = new VBox(DEF_SPACING);
        nameBox.setPadding(new Insets(DEF_PADDING));
        nameBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Ship name");
        nameLabel.setFont(new Font(15));
        TextField nameField = new TextField();
        nameField.setAlignment(Pos.CENTER);
        nameField.textProperty().bindBidirectional(ship.nameProperty());
        nameBox.getChildren().addAll(nameLabel, nameField);

        //id pane
        VBox idBox = new VBox(DEF_SPACING);
        idBox.setPadding(new Insets(DEF_PADDING));
        idBox.setAlignment(Pos.CENTER);
        Label idLabel = new Label("Ship ID");
        idLabel.setFont(new Font(15));
        TextField idField = new TextField();
        idField.setAlignment(Pos.CENTER);
        idField.textProperty().bindBidirectional(ship.idProperty(), new MyStringConverter(""));
        idBox.getChildren().addAll(idLabel, idField);

        //weight pane
        VBox weightBox = new VBox(DEF_SPACING);
        weightBox.setPadding(new Insets(DEF_SPACING));
        weightBox.setAlignment(Pos.CENTER);
        Label weightLabel = new Label("Ship weight");
        weightLabel.setFont(new Font(15));
        TextField weightField = new TextField();
        weightField.textProperty().bindBidirectional(ship.totalWeightProperty(), new MyStringConverter("kg"));
        weightField.setEditable(false);
        weightField.setAlignment(Pos.CENTER);
        weightBox.getChildren().addAll(weightLabel, weightField);

        //power production pane
        VBox powerBox = new VBox(DEF_SPACING);
        powerBox.setPadding(new Insets(DEF_SPACING));
        powerBox.setAlignment(Pos.CENTER);
        Label powerLabel = new Label("Ship power production");
        powerLabel.setFont(new Font(15));
        TextField powerField = new TextField();
        powerField.textProperty().bindBidirectional(ship.totalPowerProperty(),new MyStringConverter("MW"));
        powerField.setEditable(false);
        powerField.setAlignment(Pos.CENTER);
        powerBox.getChildren().addAll(powerLabel, powerField);

        //power consumption pane
        VBox consumptionBox = new VBox(DEF_SPACING);
        consumptionBox.setPadding(new Insets(DEF_PADDING));
        consumptionBox.setAlignment(Pos.CENTER);
        Label consumptionLabel = new Label("Ship power consumption");
        consumptionLabel.setFont(new Font(15));
        consumptionLabel.setAlignment(Pos.CENTER);
        TextField consumptionField = new TextField();
        consumptionField.textProperty().bindBidirectional(ship.totalPowerConsumptionProperty(), new MyStringConverter("MW"));
        consumptionField.setEditable(false);
        consumptionField.setAlignment(Pos.CENTER);
        consumptionBox.getChildren().addAll(consumptionLabel, consumptionField);

        //power shortage warning label
        powerShortage = new Label("Power Shortage!!!");
        powerShortage.setAlignment(Pos.CENTER);
        powerShortage.setTextFill(Color.RED);
        powerShortage.setFont(new Font(20));
        powerShortage.setVisible(ship.isPowerShortage());

        //ship components view
        ListView<Component> shipView = new ListView(ship.componentsProperty());
        shipView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedShipComponent.set(shipView.getSelectionModel().getSelectedItem());
        });

        //deleting component from the ship
        Button deleteBtn = new Button("Delete component");
        deleteBtn.setFont(new Font(15));
        deleteBtn.setOnAction(e -> {
            if (selectedShipComponent.get() == null) {
                Alert noSelection = new Alert(Alert.AlertType.WARNING);
                noSelection.setHeaderText("No item Selected!");
                noSelection.show();
            } else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setHeaderText("Are you sure you want to delete this component: " + selectedShipComponent.get());
                if (confirm.showAndWait().get() == ButtonType.OK) {
                    ship.deleteComponent(selectedShipComponent.get());
                    powerShortage.setVisible(ship.isPowerShortage());
                }
            }

        });

        shipBox.getChildren().addAll(title, nameBox, idBox, weightBox, powerBox, consumptionBox, powerShortage, shipView, deleteBtn);
        setColors(shipBox);
        return shipBox;
    }

    /**
     * Selected components in the Components View
     */
    private void updateSelectionActionComponents() {
        currentComponent = componentView.getSelectionModel().getSelectedItem();
        if (currentComponent != null) {
            root.setCenter(createCenter());
        }
    }

    /**
     * Recolor all ship data pane with ship colors
     * @param pane ship data pane
     */
    private void setColors(Pane pane) {
        pane.setBackground(new Background(new BackgroundFill(ship.getMainColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.getChildren().forEach(e -> {
            if (e instanceof TextField) {
                ((TextField) e).setBackground(new Background(new BackgroundFill(ship.getMainColor().darker(), CornerRadii.EMPTY, Insets.EMPTY)));
                ((TextField) e).setStyle(String.format("-fx-text-inner-color: #%s;", ship.getAdditionalColor().toString().substring(2, 8)));
            } else {
                if (e instanceof Pane) {
                    setColors((Pane) e);
                }
                if (e instanceof Region) {
                    if (!(e instanceof Button))
                        ((Region) e).setBackground(new Background(new BackgroundFill(ship.getMainColor(), CornerRadii.EMPTY, Insets.EMPTY)));
                }
                if (e instanceof Labeled) {
                    if (!((Labeled) e).getText().equals("Power Shortage!!!"))
                        ((Labeled) e).setTextFill(ship.getAdditionalColor());
                }
            }
        });
    }

    /**
     * Main application class, program entry point
     * @param args  -cmd start arguments (unused)
     */
    public static void main(String[] args) {
        launch();
    }
}