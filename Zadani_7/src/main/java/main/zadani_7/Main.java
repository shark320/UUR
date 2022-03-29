package main.zadani_7;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
 *
 * @author Volodymyr Pavlov
 * @version 29.03.2022
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

    private Stage primaryStage;

    private ObservableList<Animal> zoo = FXCollections.observableArrayList();

    private TableView<Animal> zooTable;


    /**
     * create main program window
     *
     * @param stage -main program window
     */
    @Override
    public void start(Stage stage) {
        Locale.setDefault(new Locale("cs", "CZ"));
        createData();
        stage.setTitle("Animal table");
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
        BorderPane rootPane = new BorderPane();

        rootPane.setCenter(createTablePane());
        rootPane.setBottom(createStatusBar());
        return rootPane;
    }

    private Node createStatusBar() {
        HBox statusBar = new HBox(DEF_SPACING);

        Button addBtn = new Button("Add random animal");
        addBtn.setOnAction(e -> addAnimal());

        statusBar.getChildren().add(addBtn);
        return statusBar;
    }

    private void addAnimal() {
        Random r = new Random();
        Animal animal = new Animal(r.nextDouble(100),"New"," smth",r.nextInt(8));

        zoo.add(animal);
    }

    private Node createTablePane() {
        zooTable = new TableView<>();

        zooTable.setItems(zoo);
        zooTable.setEditable(true);

        TableColumn<Animal, String> speciesColumn = new TableColumn<>("Species");
        speciesColumn.setCellValueFactory(new PropertyValueFactory<Animal, String>("species"));
        speciesColumn.setComparator(Collator.getInstance(new Locale("cs", "CZ"))::compare);
        speciesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        speciesColumn.setOnEditCommit(e -> {
            if (e.getNewValue().length() < 5) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.showAndWait();
                e.getRowValue().setSpecies(e.getOldValue());
            } else {
                e.getRowValue().setSpecies(e.getNewValue());
            }
            zooTable.refresh();
        });

        TableColumn<Animal, Double> weightColumn = new TableColumn<>("Weight");
        weightColumn.setCellValueFactory(new PropertyValueFactory<Animal, Double>("weight"));
        weightColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {

            double oldValue;

            @Override
            public String toString(Double object) {
                oldValue = object;
                return object.toString();
            }

            @Override
            public Double fromString(String string) {
                try{
                    oldValue = Double.parseDouble(string);
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.showAndWait();
                }
                return oldValue;
            }
        }));

        TableColumn<Animal, String> soundColumn = new TableColumn<>("Sound");
        soundColumn.setCellValueFactory(new PropertyValueFactory<Animal, String>("sound"));

        TableColumn<Animal, Integer> legCountColumn = new TableColumn<>("Leg Count");
        legCountColumn.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("legsCount"));

        zooTable.getColumns().addAll(speciesColumn, weightColumn, soundColumn, legCountColumn);
        zooTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        zooTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super Animal>) e->{

            showPrintDialog();
        });
        return zooTable;
    }

    private void showPrintDialog() {
        PrinterJob  job = PrinterJob.createPrinterJob();
    }

    private void createData() {
        zoo.add(new Animal(0.05, "Spider", " ", 8));
        zoo.add(new Animal(0.05, "Mutated spider", "Huuu", 12));
        zoo.add(new Animal(150, "Zebra", "ihaha", 4));
        //zoo.add(new Animal(0.2,"Činčila","fffff",4));
    }


    public static void main(String[] args) {
        launch();
    }
}