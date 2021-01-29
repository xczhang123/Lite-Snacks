package assignment2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import assignment2.model.*;
import assignment2.backend.*;

/**
 * Dialog to edit details of a item.
 */
public class CustomerBuyController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private Label available;
    private Stage dialogStage;
    private Snack item;
    public Snack bought;
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
        this.bought = null;
    }

    /**
     * Sets the item to be edited in the dialog.
     *
     * @param item
     */
    public void setItem(Snack item) {
        this.item = item;
        quantityField.setText(Integer.toString(0));
        nameField.setText(item.getName());
        available.setText("Quantity available: " + Integer.toString(item.getQuantity()));
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
          Snack snack2 = new Snack(item.getRollno());
          snack2.setQuantity(Integer.parseInt(quantityField.getText()));
          snack2.setName(item.getName());
          snack2.setPrice(item.getPrice());
          snack2.setCategory(item.getCategory());
          snack2.setCode(item.getCode());
          item.setQuantity(item.getQuantity() - snack2.getQuantity());
          this.bought = snack2;
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
        if(item.getQuantity() < Integer.parseInt(quantityField.getText())) {
          errorMessage += "Not enough stock!";
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
