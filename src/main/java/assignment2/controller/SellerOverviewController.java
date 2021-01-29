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

public class SellerOverviewController {

    @FXML
    private TableView<Snack> itemTable;
    @FXML
    private TableColumn<Snack, String> nameColumn;
    @FXML
    private TableColumn<Snack, Float> priceColumn;
    @FXML
    private TableColumn<Snack, Integer> quantityColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label codeLabel;

    Seller seller;

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    /**
     * Updates each Cell in the price column to add a dollar sign to the front
     */
    public static class PriceTableCell<S> extends TableCell<Snack, Float> {

        public PriceTableCell() {
            Label label = new Label("$");
            this.setAlignment(Pos.CENTER_LEFT);
            this.setGraphic(label);
        }

        @Override
        protected void updateItem(Float item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                this.setText(String.format("%.2f", item));
            }
        }
    }

    /**
     * Initializes the controller class. This method is automatically called after
     * the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the item table with the three columns.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
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
        Snack selectedItem = itemTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            boolean okClicked = seller.showItemEditDialog(selectedItem);
            if (okClicked) {
                showItemDetails(selectedItem);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(App.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No item Selected");
            alert.setContentText("Please select a item in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Fills all text fields to show details about the item. If the specified
     * item is null, all text fields are cleared.
     *
     * @param item the item or null
     */
    private void showItemDetails(Snack item) {
        if (item != null) {
            // Fill the labels with info from the item object.
            codeLabel.setText(Integer.toString(item.getCode()));
            priceLabel.setText("$ " + String.format("%.2f", item.getPrice()));
            quantityLabel.setText(Integer.toString(item.getQuantity()));

            nameLabel.setText(item.getName());
            categoryLabel.setText(item.getCategory());
        } else {
            // item is null, remove all the text.
            codeLabel.setText("");
            priceLabel.setText("");
            quantityLabel.setText("");

            nameLabel.setText("");
            categoryLabel.setText("");
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(ObservableList<Snack> items) {
        itemTable.setItems(items);
    }

}
