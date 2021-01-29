package assignment2.controller;

import assignment2.App;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

import assignment2.model.*;
import assignment2.backend.*;

public class OwnerOverviewController {

    @FXML
    private TableView<User> itemTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> usertypeColumn;

    @FXML
    private Label useridLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label usertypeLabel;

    Owner owner;

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Initializes the controller class. This method is automatically called after
     * the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the item table with the two columns.
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        usertypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        // Clear item details.
        showItemDetails(null);

        // Listen for selection changes and show the item primary details when changed.
        itemTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showItemDetails(newValue));
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewUser() {
        User tempUser = new User();
        boolean okClicked = owner.showOwnerEditDialog(tempUser);
        if (okClicked) {
            owner.getUserData().add(tempUser);
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteUser() {
        int selectedIndex = itemTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            // Delete user from the database based on their user_id
            UserDB.deleteUser(itemTable.getItems().get(selectedIndex));
            itemTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(App.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No User Selected");
            alert.setContentText("Please select a user in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Fills all text fields to show details about the item. If the specified
     * item is null, all text fields are cleared.
     *
     * @param item the item or null
     */
    private void showItemDetails(User user) {
        if (user != null) {
            // Fill the labels with info from the user object.
            useridLabel.setText(Integer.toString(user.getId()));
            usernameLabel.setText(user.getName());
            usertypeLabel.setText(user.getType());
        } else {
            // user is null, remove all the text.
            useridLabel.setText("");
            usernameLabel.setText("");
            usertypeLabel.setText("");
        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(ObservableList<User> users) {
        itemTable.setItems(users);
    }

}
