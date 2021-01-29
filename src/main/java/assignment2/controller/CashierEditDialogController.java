package assignment2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import assignment2.model.*;
import assignment2.backend.*;;
/**
 * Dialog to edit details of a item.
 */
public class CashierEditDialogController {

    @FXML
    private TextField quantityField;

    private Stage dialogStage;
    private Cash item;
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
    public void setItem(Cash item) {
        this.item = item;

        quantityField.setText(Integer.toString(item.getQuantity()));
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
            item.setQuantity(Integer.parseInt(quantityField.getText()));

            // Update item details back to database
            CashDB.addCash(item.getPrice(), item.getQuantity());

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

        if (quantityField.getText() == null || quantityField.getText().length() == 0) {
            errorMessage += "No valid item quantity!\n";
        } else {
            // try to parse the quantity into an int.
            try {
                if (Integer.parseInt(quantityField.getText()) < 0) {
                    errorMessage += "Quantity must be positive!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid item quantity! (must be an integer)\n";
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
