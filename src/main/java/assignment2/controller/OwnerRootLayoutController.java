package assignment2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import assignment2.model.*;
import assignment2.backend.*;

public class OwnerRootLayoutController {
    @FXML
    void switch_to_seller_view(ActionEvent event) {
        new Owner().showSellerItemOverview();
    }

    @FXML
    void switch_to_cashier_view(ActionEvent event) {
        new Owner().showCashierItemOverview();
    }

    @FXML
    void switch_to_owner_view(ActionEvent event) {
        new Owner().showOwnerItemOverview();
    }

    @FXML
    void handle_click_username_role_summary(ActionEvent event) {
        Owner.generate_username_role_summary();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Username role summary generated.");
        alert.showAndWait();
    }

    @FXML
    void handle_click_cancelled_transaction_summary(ActionEvent event) {
        Owner.generate_cancelled_transaction_summary();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Calcelled transaction summary generated.");
        alert.showAndWait();
    }

    @FXML
    void handle_click_item_summary(ActionEvent event) {
        Owner.generate_item_summary();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Item summary report generated.");
        alert.showAndWait();
    }

    @FXML
    void handle_click_sold_item_summary(ActionEvent event) {
        Owner.generate_sold_item_summary();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Item sold summary report generated.");
        alert.showAndWait();
    }

    @FXML
    void handle_click_change_summary(ActionEvent event) {
        Owner.generate_available_change_summary();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Available change summary generated.");
        alert.showAndWait();
    }

    @FXML
    void handle_click_valid_transaction_summary(ActionEvent event) {
        Owner.generate_valid_transaction_summary();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Valid transaction summary generated.");
        alert.showAndWait();
    }

    @FXML
    void handle_logout(ActionEvent event){
        Owner.logout();
    }

}
