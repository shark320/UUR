package main.zadani_6;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

import java.util.Objects;
import java.util.Scanner;

/**
 * main application class
 * build window with people data of people
 * with editing features
 *
 * @author Volodymyr Pavlov
 * @version 25.03.2022
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

    /**
     * people data of people
     */
    private final ListProperty<PersonData> peopleData = new SimpleListProperty<>(FXCollections.observableArrayList());

    /**
     * listview of people data
     */
    private final ListView<PersonData> peopleList = new ListView<>(peopleData);

    /**
     * editable listview to change people data / create new
     */
    private final ListView<String> currentPerson = new ListView<>();

    /**
     * show info about current selected action (editing/creating new)
     */
    private final Label currentAction = new Label("Not chosen");

    /**
     * control buttons to create new personal data
     */
    private final VBox buttonsNew = new VBox(DEF_SPACING);

    /**
     * control buttons to edit present personal data
     */
    private final VBox buttonsEdit = new VBox(DEF_SPACING);

    /**
     * box with controls with editable area (currentPerson)
     */
    private final VBox editableZone = new VBox(DEF_SPACING);

    /**
     * box of all controls actions (buttons)
     */
    private final BorderPane controls = new BorderPane();

    /**
     * create main program window
     *
     * @param stage -main program window
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Person Data");
        Parent root = createRoot();
        Scene scene = new Scene(root);
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
        readPeopleData();
        rootPane.setCenter(getPeopleList());

        rootPane.setRight(getControlPanel());
        rootPane.setTop(getMenu());

        return rootPane;
    }

    /**
     * create new menu panel
     *
     * @return menu panel
     */
    private Node getMenu() {
        MenuBar menu = new MenuBar();

        Menu program = new Menu("_Program");

        MenuItem help = new MenuItem("_Help");
        help.setOnAction(e -> showHelp());

        MenuItem exit = new MenuItem("_Exit");
        exit.setOnAction(e -> Platform.exit());

        program.getItems().addAll(help, exit);


        menu.getMenus().addAll(program);
        return menu;
    }

    /**
     * show alert message with help info
     */
    private void showHelp() {
        Scanner sc = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream("/help/help.txt")));
        StringBuilder text = new StringBuilder();
        while (sc.hasNextLine()) {
            text.append(sc.nextLine()).append('\n');
        }
        Alert helpInfo = new Alert(Alert.AlertType.INFORMATION);
        helpInfo.setHeaderText("Help information");
        helpInfo.setContentText(text.toString());
        helpInfo.getDialogPane().setPrefWidth(600);
        helpInfo.showAndWait();
    }

    /**
     * create new control panel with editable area
     *
     * @return control panel
     */
    private Node getControlPanel() {
        initEdits();
        initNew();

        currentAction.setFont(new Font("Arial", 15));
        currentAction.setPrefWidth(CONTROL_WIDTH);

        editableZone.getChildren().addAll(currentAction, currentPerson, buttonsEdit);

        controls.setTop(editableZone);
        controls.setBottom(getListInfo());
        return controls;
    }

    /**
     * create VBox with show buttons (to show list and person info)
     *
     * @return new VBox with buttons
     */
    private Node getListInfo() {
        VBox showButtons = new VBox(DEF_SPACING);

        Button showPerson = new Button("Show person info");
        showPerson.setPrefWidth(CONTROL_WIDTH);
        showPerson.setOnAction(e -> showPersonInfo());

        Button showList = new Button("Show list info");
        showList.setPrefWidth(CONTROL_WIDTH);
        showList.setOnAction(e -> showListInfo());

        showButtons.getChildren().addAll(showPerson, showList);
        return showButtons;
    }

    /**
     * show message with all people data info
     */
    private void showListInfo() {
        if (!peopleData.isEmpty()) {
            StringBuilder allInfo = new StringBuilder();

            int i = 1;
            for (PersonData person : peopleData) {
                allInfo.append(i++).append(":\n");
                allInfo.append(person.toString()).append('\n');
            }

            Alert listInfo = new Alert(Alert.AlertType.INFORMATION);
            listInfo.setHeaderText("All people data:");
            listInfo.setContentText(allInfo.toString());
            listInfo.getDialogPane().setPrefWidth(CONTROL_WIDTH);
            listInfo.getDialogPane().setPrefWidth(800);
            listInfo.showAndWait();
        } else {
            Alert noData = new Alert(Alert.AlertType.INFORMATION);
            noData.setHeaderText("People data is empty!");
            noData.showAndWait();
        }
    }

    /**
     * show message with current person info
     */
    private void showPersonInfo() {
        PersonData current = peopleList.getSelectionModel().getSelectedItem();
        if (current != null) {
            String info = current.toString().replaceAll(" , ", "\n");

            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setHeaderText(current.getFullName() + ':');
            infoAlert.setContentText(info);
            infoAlert.showAndWait();
        } else {
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
            noSelection.setHeaderText("No selected person");
            noSelection.showAndWait();
        }
    }

    /**
     * initialize buttonsNew VBox
     * create new buttons ("Create" and "Cancel")
     */
    private void initNew() {
        buttonsNew.setPrefWidth(CONTROL_WIDTH);

        //create new personal data
        Button createBtn = new Button("Create");
        createBtn.setPrefWidth(CONTROL_WIDTH);
        //cancel creating, setting editing mode
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setPrefWidth(CONTROL_WIDTH);

        createBtn.setOnAction(e -> {
            createData();
            editableZone.getChildren().remove(buttonsNew);
            editableZone.getChildren().add(buttonsEdit);
            currentAction.setText("Not chosen");
        });

        cancelBtn.setOnAction(e -> {
            editableZone.getChildren().remove(buttonsNew);
            editableZone.getChildren().add(buttonsEdit);
            currentAction.setText("Not chosen");
            currentPerson.setItems(new SimpleListProperty<>());
            currentPerson.refresh();
        });

        buttonsNew.getChildren().addAll(createBtn, cancelBtn);
    }


    /**
     * initialize buttonsEdit VBox
     * create new buttons ("Save", "Delete", "Create new")
     */
    private void initEdits() {
        buttonsEdit.setPrefWidth(CONTROL_WIDTH);

        //save all changes in selected personal data
        Button saveBtn = new Button("Save");
        saveBtn.setPrefWidth(CONTROL_WIDTH);
        //delete selected personal data
        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefWidth(CONTROL_WIDTH);
        //setting creating mode
        Button newBtn = new Button("Create new");
        newBtn.setPrefWidth(CONTROL_WIDTH);

        currentPerson.setCellFactory(TextFieldListCell.forListView(new DefaultStringConverter()));
        currentPerson.setMaxHeight(120);

        deleteBtn.setOnAction(e -> deleteData());

        saveBtn.setOnAction(e -> saveData());

        newBtn.setOnAction(e -> {
            currentAction.setText("Creating new personal data");
            currentPerson.setItems(new SimpleListProperty<String>(createEmptyList()));
            editableZone.getChildren().remove(buttonsEdit);
            editableZone.getChildren().add(buttonsNew);
        });

        buttonsEdit.getChildren().addAll(saveBtn, deleteBtn, newBtn);
    }

    /**
     * create new ObservableList with personal data template
     *
     * @return created ObservableList
     */
    private ObservableList<String> createEmptyList() {
        ListProperty<String> list = new SimpleListProperty<>(FXCollections.observableArrayList());
        list.add("Name");
        list.add("Surname");
        list.add("Birth");
        list.add("Email");
        list.add("Post");
        return list;
    }

    /**
     * create new personal data using data from the editable list
     */
    private void createData() {
        try {
            Object[] data = currentPerson.getItems().toArray();
            PersonData current = new PersonData(
                    (String) data[0],
                    (String) data[1],
                    (String) data[2],
                    (String) data[3],
                    (String) data[4]
            );
            peopleData.add(current);
            peopleList.refresh();
        } catch (IllegalArgumentException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    /**
     * delete selected item after the confirmation message
     */
    private void deleteData() {
        if (peopleList.getSelectionModel().getSelectedIndex() != -1) {
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDelete.setContentText("Are you sure you want to delete this personal data?");
            if (confirmDelete.showAndWait().get() == ButtonType.OK) {
                int index = peopleList.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    peopleData.remove(index);
                    peopleList.getSelectionModel().clearSelection();
                }
            }
        } else {
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION);
            noSelection.setHeaderText("No selected person");
            noSelection.showAndWait();
        }
    }

    /**
     * save edited personal data
     */
    private void saveData() {
        PersonData current = peopleList.getSelectionModel().getSelectedItem();
        if (current != null) {
            try {
                Object[] data = currentPerson.getItems().toArray();
                current.setName((String) data[0]);
                current.setSurname((String) data[1]);
                current.setBirth((String) data[2]);
                current.setEmail((String) data[3]);
                current.setPost((String) data[4]);
                peopleList.refresh();
            } catch (IllegalArgumentException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(exception.getMessage());
                alert.show();
            }
        }
    }

    /**
     * initialize people data ListView with people data
     *
     * @return initialized ListView
     */
    private Node getPeopleList() {
        peopleList.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(PersonData object) {
                return object.getFullName();
            }

            @Override
            public PersonData fromString(String string) {
                /*String[] fullName = string.split("\s");
                if (fullName.length!=2) throw new IllegalArgumentException("Invalid full name data");
                PersonData tmp = peopleList.getSelectionModel().getSelectedItem();
                tmp.setName(fullName[0]);
                tmp.setSurname(fullName[1]);
                return tmp;*/
                return null;
            }
        }));

        //I have tried to make peopleList editable (make possible to edit name & surname directly in the list)
        //but I've failed with saving new data (catching the exception)


/*        peopleList.setEditable(true);

        peopleList.setOnEditCommit(event -> {
            PersonData current = peopleList.getSelectionModel().getSelectedItem();
            try{
                System.out.println(current.getFullName());
                current = event.getNewValue();

            }catch (Exception exception) {
                peopleList.onEditCancelProperty();
                Alert invalidData = new Alert(Alert.AlertType.ERROR);
                invalidData.setContentText(exception.getMessage());
                invalidData.showAndWait();
            }
        });*/

        peopleList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateSelectionAction();
        });

        return peopleList;
    }

    /**
     * setting new selection action into ListView of people data
     */
    private void updateSelectionAction() {
        PersonData current = peopleList.getSelectionModel().getSelectedItem();
        if (current != null) {
            currentPerson.setItems(current.getAsList());
            currentPerson.refresh();
            currentPerson.setEditable(true);
            currentAction.setText("Selected: " + current.getFullName());
            if (!editableZone.getChildren().contains(buttonsEdit)) {
                editableZone.getChildren().remove(buttonsNew);
                editableZone.getChildren().add(buttonsEdit);
            }
        }
    }

    /**
     * reading people data form the file
     */
    private void readPeopleData() {

        Scanner sc = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream("/data/people.txt")));
        while (sc.hasNextLine()) {
            peopleData.add(new PersonData(sc.nextLine()));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}