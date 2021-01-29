package assignment2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import assignment2.model.*;
import assignment2.backend.*;

/**
 * Dialog to add details of a user.
 */
public class OwnerEditDialogController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField typeField;

    private Stage dialogStage;
    private User user;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the item to be edited in the dialog.
     *
     * @param item
     */
    public void setItem(User user) {
        this.user = user;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            // Id has been set in isInputValid() function
            user.setName(usernameField.getText());
            user.setType(typeField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (usernameField.getText() == null || usernameField.getText().length() == 0) {
            errorMessage += "No valid username!\n";
        }
        if (typeField.getText() == null || typeField.getText().length() == 0) {
            errorMessage += "No valid type!\n";
        } else {
            if (!(typeField.getText().equals("seller") || typeField.getText().equals("cashier") || typeField.getText().equals("owner"))) {
                errorMessage += "Type must be either seller/cashier/owner\n";
            }
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }

        if (errorMessage.length() == 0) {
            // -1 is returned is insertion fails
            int userid = UserDB.addUser(new User(usernameField.getText(), passwordField.getText(), typeField.getText()));
            if (userid == -1) {
                errorMessage += "Username and password combination already exists!\n";
            } else {
                // User id for display on the screen
                user.setid(userid);
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
