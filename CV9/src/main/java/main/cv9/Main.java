package main.cv9;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.text.Collator;
import java.util.Locale;
import java.util.Random;

/**
 * main application class
 * classwork
 *
 * @author Volodymyr Pavlov
 * @version 12.04.2022
 */
public class Main extends Application {

    /**
     * Spacing between elements in the boxes
     */
    private static final int DEF_SPACING = 10;

    /**
     * width of the control panel
     */
    private static final double CONTROL_WIDTH = 300;

    private TreeView<String> treeView;

    private BorderPane rootPane;

    private Stage primaryStage;

    /*private Button random = new Button("Random");*/


    /**
     * create main program window
     *
     * @param stage -main program window
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Tree View");
        Parent root = createRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        primaryStage = stage;
        stage.show();
    }

    /**
     * creating main root panel
     *
     * @return root panel
     */
    private Parent createRoot() {
        rootPane = new BorderPane();

        rootPane.setCenter(createTreePane());
        rootPane.setBottom(createControlPanel());
        return rootPane;
    }

    private Node createStatusBar() {
        HBox statusBar = new HBox(DEF_SPACING);

        return statusBar;
    }

    private Node createTreePane(){
        treeView = new TreeView<>();

/*        TreeItem<String> rootItem = new TreeItem<>("Root");
        TreeItem<String> childItem = new TreeItem<>("Child 1");
        TreeItem<String> childItem2 = new TreeItem<>("Child 2");

        rootItem.getChildren().add(childItem);
        childItem.getChildren().add(childItem2);


        treeView.setRoot(rootItem);*/

        return treeView;
    }


    private Node createControlPanel() {
        HBox controlPanel = new HBox();

        Button createData = new Button("Create Data");
        createData.setOnAction(e->createDataModel());

        controlPanel.getChildren().add(createData);

        return controlPanel;
    }

    private void createDataModel() {
        /*TreeItem<String> root = new TreeItem<>();

        root.setValue(rootPane.getClass().getSimpleName());

        rootPane.getChildrenUnmodifiable().forEach(ch -> {
            TreeItem<String> child = new TreeItem<>(ch.getClass().getSimpleName());
            root.getChildren().add(child);
        });*/

        treeView.setRoot(processNode(rootPane));
    }

    private TreeItem<String> processNode (Node data){
        if (data==null){
            return null;
        }

        TreeItem<String> node = new TreeItem<>();
        node.setValue(data.getClass().getSimpleName());

        if (data instanceof Parent parent) {
            parent.getChildrenUnmodifiable().forEach(ch->{
                node.getChildren().add(processNode(ch));
            });
        }

        return node;
    }


    public static void main(String[] args) {
        launch();
    }
}