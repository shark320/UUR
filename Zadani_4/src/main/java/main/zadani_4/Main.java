package main.zadani_4;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * spacing into small box zone
     */
    private final double BOX_IN_SPACING = 5;

    /**
     * spacing into big box zone (where are several boxes)
     */
    private final double BOX_OUT_SPACING = 10;

    /**
     * title font size
     */
    private final double TITLE_FONT_SIZE = 20;

    /**
     * label font size
     */
    private final double LABEL_FONT_SIZE = 15;

    /**
     * default box padding
     */
    private final double DEFAULT_PADDING = 10;

    /**
     * size of the control buttons (WASD)
     */
    private final double CTRL_BUTTON_SIZE = 70;

    /**
     * minimal window width
     */
    private final double MINIMAL_WIDTH = 770;

    /**
     * minimal window height
     */
    private final double MINIMAL_HEIGHT = 490;

    /**
     * default font
     */
    private final String FONT = "Arial";

    /**
     * create main program window
     *
     * @param stage -main program window
     */
    @Override
    public void start(Stage stage) {
        Parent root = createRoot();
        Scene scene = new Scene(root, MINIMAL_WIDTH, MINIMAL_HEIGHT);
        stage.setHeight(MINIMAL_HEIGHT);
        stage.setWidth(MINIMAL_WIDTH);
        stage.setMinWidth(MINIMAL_WIDTH);
        stage.setMinHeight(MINIMAL_HEIGHT);
        stage.setTitle("Robot");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * creating main root panel
     *
     * @return root panel
     */
    private Parent createRoot() {
        BorderPane rootPane = new BorderPane();
        rootPane.setTop(getMenu());
        rootPane.setLeft(getOptions());
        rootPane.setCenter(getMoveControl());
        rootPane.setBottom(getInfo());

        return rootPane;
    }

    /**
     * create bottom part, includes launch controls and main robot info
     *
     * @return bottom part
     */
    private Node getInfo() {
        VBox panel = new VBox(BOX_OUT_SPACING);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(DEFAULT_PADDING));

        panel.getChildren().addAll(getMainInfo(), getLaunchCtrl());

        return panel;
    }

    /**
     * create main robot info panel
     *
     * @return main info panel
     */
    private Node getMainInfo() {
        HBox info = new HBox(BOX_IN_SPACING);
        info.setAlignment(Pos.BOTTOM_CENTER);
        info.getChildren().addAll(
                new VBox(new Label("Battery, %"), new TextField("")),
                new VBox(new Label("Road, m"), new TextField("")),
                new VBox(new Label("Work time, s"), new TextField("")),
                new VBox(new Label("Work time, s"), new TextField("")),
                new CheckBox("Power")
        );

        info.getChildren().forEach(e -> {
            if (e instanceof VBox) {
                ((Label) ((VBox) e).getChildren().get(0)).setFont(new Font(FONT, LABEL_FONT_SIZE));
                ((TextField) ((VBox) e).getChildren().get(1)).setPrefSize(100_000_000, 30);
                ((VBox) e).setAlignment(Pos.CENTER);
            } else {
                ((CheckBox) e).setFont(new Font(FONT, LABEL_FONT_SIZE));
                ((CheckBox) e).setMinWidth(100);
                e.setDisable(true);
            }
        });

        return info;
    }

    /**
     * create panel with launch controls
     *
     * @return ctrl panel
     */
    private Node getLaunchCtrl() {
        HBox ctrlButtons = new HBox(BOX_IN_SPACING);
        ctrlButtons.setAlignment(Pos.CENTER);
        ctrlButtons.getChildren().addAll(
                new Button("Power off"),
                new Button("Power on"),
                new Button("Restart")
        );

        ctrlButtons.getChildren().forEach(e -> {
            Button btn = (Button) e;
            btn.setFont(new Font(FONT, LABEL_FONT_SIZE));
            btn.setPrefSize(160, 30);
        });

        return ctrlButtons;
    }

    /**
     * create center program part with move ctrl, position info and robot status
     *
     * @return center program, part
     */
    private Node getMoveControl() {
        VBox moveCtrl = new VBox(BOX_OUT_SPACING);
        moveCtrl.setAlignment(Pos.CENTER);
        moveCtrl.getChildren().addAll(getControl(), getPosition(), getStatus());

        return moveCtrl;
    }

    /**
     * create robot status panel
     *
     * @return robot status panel
     */
    private Node getStatus() {
        VBox status = new VBox(5);
        status.setAlignment(Pos.CENTER);
        Label label = new Label("Current status and warnings");
        label.setFont(new Font(FONT, LABEL_FONT_SIZE));
        status.getChildren().addAll(label, new TextArea());

        return status;
    }

    /**
     * create robot position panel (rotation and speed)
     *
     * @return robot position panel
     */
    private Node getPosition() {
        HBox info = new HBox(BOX_OUT_SPACING);

        VBox rotation = new VBox(BOX_IN_SPACING);
        TextField rotVal = new TextField("");
        rotation.getChildren().addAll(new Label("Rotation"), rotVal);

        VBox speed = new VBox(BOX_IN_SPACING);
        TextField speedVal = new TextField("");
        speed.getChildren().addAll(new Label("Speed"), speedVal);

        info.getChildren().addAll(rotation, speed);

        final double fontSize = 20;
        final double width = 50;

        info.getChildren().forEach(e -> {
            ((VBox) e).setAlignment(Pos.CENTER);
            Label label = (Label) ((VBox) e).getChildren().get(0);
            TextField text = (TextField) ((VBox) e).getChildren().get(1);
            label.setFont(new Font(FONT, fontSize));
            text.prefWidth(width);
            text.setDisable(true);
        });

        info.setAlignment(Pos.CENTER);


        return info;
    }

    /**
     * create move ctrl buttons
     *
     * @return move ctrl buttons
     */
    private Node getControl() {
        VBox ctrls = new VBox(BOX_IN_SPACING);
        ctrls.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(BOX_IN_SPACING);
        buttons.setAlignment(Pos.CENTER);

        Button backward = new Button("S");
        Button left = new Button("A");
        Button right = new Button("D");
        Button forward = new Button("W");
        forward.setAlignment(Pos.CENTER);
        forward.setMinSize(CTRL_BUTTON_SIZE, CTRL_BUTTON_SIZE);
        forward.setMaxSize(CTRL_BUTTON_SIZE, CTRL_BUTTON_SIZE);
        forward.setFont(new Font(30));
        buttons.getChildren().addAll(left, backward, right);
        buttons.getChildren().forEach(e -> {
            ((Button) e).setMinSize(CTRL_BUTTON_SIZE, CTRL_BUTTON_SIZE);
            ((Button) e).setMaxSize(CTRL_BUTTON_SIZE, CTRL_BUTTON_SIZE);
            ((Button) e).setFont(new Font(30));
        });

        ctrls.getChildren().addAll(forward, buttons);

        return ctrls;
    }

    /**
     * create left program part (options)
     *
     * @return left program part
     */
    private Node getOptions() {
        VBox options = new VBox(BOX_OUT_SPACING);
        options.getChildren().addAll(createSensors(), createEnergySrc());

        options.getChildren().forEach(e -> ((VBox) e).setBorder(new Border
                (
                        new BorderStroke(
                                Color.BLACK,
                                BorderStrokeStyle.SOLID,
                                new CornerRadii(10.0),
                                BorderWidths.DEFAULT)
                )
        ));
        options.setAlignment(Pos.CENTER);

        return options;
    }

    /**
     * create options of energy sources
     *
     * @return energy sources radio buttons panel
     */
    private Node createEnergySrc() {
        VBox energySrc = new VBox(BOX_IN_SPACING);
        Text title = new Text("Energy source");
        title.setFont(new Font(FONT, TITLE_FONT_SIZE));

        ToggleGroup energyGroup = new ToggleGroup();

        RadioButton solarSrc = new RadioButton("Solar");
        RadioButton batterySrc = new RadioButton("Battery");
        RadioButton rtgSrc = new RadioButton("Radioisotope thermoelectric generator");

        energySrc.getChildren().addAll(title, solarSrc, batterySrc, rtgSrc);
        energySrc.getChildren().filtered(e -> e instanceof RadioButton).forEach(e -> {
            ((RadioButton) e).setFont(new Font(FONT, LABEL_FONT_SIZE));
            ((RadioButton) e).setToggleGroup(energyGroup);
        });

        energySrc.setPadding(new Insets(DEFAULT_PADDING));

        return energySrc;
    }

    /**
     * create active sensors options
     *
     * @return sensors options check boxes panel
     */
    private Node createSensors() {
        VBox sensors = new VBox(BOX_IN_SPACING);
        Text title = new Text("Active sensors");
        title.setFont(new Font("Arial", TITLE_FONT_SIZE));
        CheckBox sensorAcoustic = new CheckBox("Acoustic");
        CheckBox sensorOptical = new CheckBox("Optical");
        CheckBox sensorTemp = new CheckBox("Temperature");
        CheckBox sensorPressure = new CheckBox("Pressure");
        CheckBox sensorRadar = new CheckBox("Radar");

        sensors.getChildren().addAll(title, sensorAcoustic, sensorOptical, sensorTemp, sensorPressure, sensorRadar);
        sensors.getChildren().filtered(e -> e instanceof CheckBox).forEach(e -> ((CheckBox) e).setFont(new Font(FONT, LABEL_FONT_SIZE)));
        sensors.setPadding(new Insets(DEFAULT_PADDING));
        return sensors;
    }

    /**
     * create menu
     *
     * @return menu panel
     */
    private Node getMenu() {
        MenuBar menu = new MenuBar();

        Menu settings = new Menu("_Settings");
        MenuItem download = new MenuItem("_Download data");
        MenuItem softUpdate = new MenuItem("Soft_ware update");
        MenuItem openF = new MenuItem("_Open File");

        Menu ctrlMenu = new Menu("_Control program");
        ctrlMenu.getItems().addAll(new MenuItem("_Launch program"), new MenuItem("_Quit program"));

        settings.getItems().addAll(download, softUpdate, openF);
        menu.getMenus().addAll(settings, ctrlMenu);
        return menu;
    }

    public static void main(String[] args) {
        launch();
    }
}