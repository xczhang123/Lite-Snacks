package assignment2.controller;

import assignment2.App;
import assignment2.model.*;
import assignment2.backend.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class CashierOverviewController {

    @FXML
    private TableView<Cash> itemTable;
    @FXML
    private TableColumn<Cash, String> priceColumn;
    @FXML
    private TableColumn<Cash, Integer> quantityColumn;

    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;

    Cashier cashier;

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    /**
     * Updates each Cell in the price column to add a dollar sign to the front
     */
    public static class PriceTableCell<S> extends TableCell<Cash, String> {

        public PriceTableCell() {
            Label label = new Label("$");
            this.setAlignment(Pos.CENTER_LEFT);
            this.setGraphic(label);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                this.setText(item);
            }
        }
    }

    /**
     * Initializes the controller class. This method is automatically called after
     * the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the item table with the two columns.
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        // Adds dollar sign to price column
        priceColumn.setCellFactory(price -> new PriceTableCell<>());

        // Clear item details.
        showItemDetails(null);

        // Listen for selection changes and show the item primary details when changed.
        itemTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showItemDetails(newValue));
    }

     /**
     * If the Edit button is clicked and a item is selected, redirect users to Edit Dialogue stage;
     * otherwise, raise a warning
     */
    @FXML
    void handleEditItem(ActionEvent event) {
        Cash selectedItem = itemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            boolean okClicked = cashier.showItemEditDialog(selectedItem);
            if (okClicked) {
                showItemDetails(selectedItem);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(App.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No change Selected");
            alert.setContentText("Please select a change in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Fills all text fields to show details about the item. If the specified
     * item is null, all text fields are cleared.
     *
     * @param item the item or null
     */
    private void showItemDetails(Cash item) {
        if (item != null) {
            // Fill the labels with info from the item object.
            priceLabel.setText("$ " + item.getPrice());
            quantityLabel.setText(Integer.toString(item.getQuantity()));
        } else {
            // item is null, remove all the text.
            priceLabel.setText("");
            quantityLabel.setText("");
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(ObservableList<Cash> items) {
        itemTable.setItems(items);
    }

}
