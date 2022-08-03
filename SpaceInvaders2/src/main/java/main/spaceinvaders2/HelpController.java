package main.spaceinvaders2;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controller of the "Help" scene
 *
 * @author Volodymyr Pavlov
 * @version 30.07.2022
 */
public class HelpController {

    /**
     * Text area with help info
     */
    @FXML
    public TextArea textArea;

    /**
     * On "Close" button click action, returns to the main menu
     */
    public void onCloseClick() {
        textArea.selectRange(0, 0);
        SpaceInvaders2App.showStartMenu();
    }
}
