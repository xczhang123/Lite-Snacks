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
public class CustomerAddCardController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField numberField;

    private Stage dialogStage;
    private Customer customer;
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
        this.customer.timer_reset();
        if (isInputValid()) {
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks okSave.
     */
    @FXML
    private void handleOkSave() {
        this.customer.timer_reset();
        if (isInputValid()) {
            Card card = new Card(nameField.getText(), numberField.getText());
            System.out.println(card.getName() + ", " + card.getNum());
            UserDB.addCard(card, customer.getUser());
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        this.customer.timer_reset();
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        // Validate card details
        JSON json = new JSON();
        if (json.validate(nameField.getText(), numberField.getText()) != null) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Invalid card name or number");

            alert.showAndWait();

            return false;
        }
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
