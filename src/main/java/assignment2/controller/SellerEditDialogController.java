package assignment2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import assignment2.model.*;
import assignment2.backend.*;

/**
 * Dialog to edit details of a item.
 */
public class SellerEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField codeField;

    private Stage dialogStage;
    private Snack item;
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
    public void setItem(Snack item) {
        this.item = item;

        priceField.setText(Float.toString(item.getPrice()));
        quantityField.setText(Integer.toString(item.getQuantity()));
        nameField.setText(item.getName());
        categoryField.setText(item.getCategory());
        codeField.setText(Integer.toString(item.getCode()));

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
            item.setName(nameField.getText());
            item.setPrice(Float.parseFloat(priceField.getText()));
            item.setQuantity(Integer.parseInt(quantityField.getText()));
            item.setCategory(categoryField.getText());
            item.setCode(Integer.parseInt(codeField.getText()));

            // Update item details back to database
            SnackDB.update(item);

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

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No valid item name!\n";
        }
        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "No valid item price!\n";
        } else {
            // try to parse the price code into an float.
            try {
                if (Float.parseFloat(priceField.getText()) < 0) {
                    errorMessage += "Item price must be positive!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid item price (must be an interger or float)!\n";
            }
        }
        if (quantityField.getText() == null || quantityField.getText().length() == 0) {
            errorMessage += "No valid item quantity!\n";
        } else {
            // try to parse the quantity into an int.
            try {
                if (Integer.parseInt(quantityField.getText()) < 0 || Integer.parseInt(quantityField.getText()) > 15) {
                    errorMessage += "Item quantity must be between 0 and 15!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid item quantity! (must be an integer)\n";
            }
        }
        if (categoryField.getText() == null || categoryField.getText().length() == 0) {
            errorMessage += "No valid item category!\n";
        }
        if (codeField.getText() == null || codeField.getText().length() == 0) {
            errorMessage += "No valid item code!\n";
        } else {
            // try to parse the code into an int.
            try {
                Integer.parseInt(codeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid code (must be an integer)!\n";
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
