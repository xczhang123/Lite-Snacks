package assignment2.controller;

import java.sql.Timestamp;

import assignment2.App;
import assignment2.model.*;
import assignment2.backend.*;
import javafx.application.Platform;
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
import javafx.scene.image.ImageView;

public class CustomerOverviewController {

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
    @FXML
    private Label totalpricelabel;
    @FXML
    private ImageView snack_image;
    private Label nameone;

    @FXML
    private Label quantityone;

    @FXML
    private Label name;

    @FXML
    private Label nametwo;

    @FXML
    private Label quantitytwo;

    @FXML
    private Label namethree;

    @FXML
    private Label quantitythree;

    @FXML
    private Label namefour;

    @FXML
    private Label quantityfour;

    @FXML
    private Label namefive;

    @FXML
    private Label quantityfive;

    Customer customer;
    Snack snack;

    public void setCustomer(Customer customer) {
        this.snack = null;
        this.customer = customer;
        name.setText(this.customer.getUser().getName());
        this.customer.play_timer();
    }

    public Snack get_snack(){
        return this.snack;
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
        totalpricelabel.setText("$0.00");
        // Clear item details.
        showItemDetails(null);

        // Listen for selection changes and show the item primary details when changed.
        itemTable.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> showItemDetails(newValue));
    }
    
    @FXML
    void handlebuy(ActionEvent event) {
        Snack selectedItem = itemTable.getSelectionModel().getSelectedItem();
        this.customer.timer_reset();
        if (selectedItem != null) {
            boolean okClicked = customer.showbuyDialog(selectedItem);
            if (okClicked) {
                showItemDetails(selectedItem);
                totalpricelabel.setText(String.format("$%.2f", customer.total_price));
            }
            
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(App.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No item Selected");
            alert.setContentText("Please select an item in the table.");
            alert.showAndWait();
        }
    }
    
    @FXML
    void handle_recent(ActionEvent event) {
        this.customer.timer_reset();
        customer.recent_method();
    }

    @FXML
    void handlecheckout(ActionEvent event) {
        this.customer.timer_reset();
        if (totalpricelabel.getText() == "$0.00") {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(App.getPrimaryStage());
            alert.setTitle("Buy items");
            alert.setHeaderText("No item selected");
            alert.setContentText("Please buy something from the table before checking out");
            alert.showAndWait();
        } else {
            customer.pay_method();
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
            snack_image.setImage(item.getImage());
        } else {
            // item is null, remove all the text.
            codeLabel.setText("");
            priceLabel.setText("");
            quantityLabel.setText("");

            nameLabel.setText("");
            categoryLabel.setText("");
            snack_image.setImage(null);
        }
    }

    /**
     * Handles the category button
     * @param event
     */
    @FXML
    void handle_all(ActionEvent event) {
        showItemDetails(null);
        ObservableList<Snack> snacks = SnackDB.view("");
        itemTable.setItems(snacks);
        itemTable.refresh();
        this.customer.timer_reset();
    }
    
    /**
     * Handles the category button
     * @param event
     */
    @FXML
    void handle_candies(ActionEvent event) {
        showItemDetails(null);
        ObservableList<Snack> snacks = SnackDB.view("Candies");
        itemTable.setItems(snacks);
        itemTable.refresh();
        this.customer.timer_reset();
    }
    
    /**
     * Handles the category button
     * @param event
     */
    @FXML
    void handle_chips(ActionEvent event) {
        showItemDetails(null);
        ObservableList<Snack> snacks = SnackDB.view("Chips");
        itemTable.setItems(snacks);
        itemTable.refresh();
        this.customer.timer_reset();
    }

    /**
     * Handles the category button
     * @param event
     */
    @FXML
    void handle_chocolates(ActionEvent event) {
        showItemDetails(null);
        ObservableList<Snack> snacks = SnackDB.view("Chocolates");
        itemTable.setItems(snacks);
        itemTable.refresh();
        this.customer.timer_reset();
    }

    /**
     * Handles the category button
     * @param event
     */
    @FXML
    void handle_drinks(ActionEvent event) {
        showItemDetails(null);
        ObservableList<Snack> snacks = SnackDB.view("Drinks");
        itemTable.setItems(snacks);
        itemTable.refresh();
        this.customer.timer_reset();
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
