package assignment2.controller;

import assignment2.model.*;
import assignment2.App;
import assignment2.backend.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;


public class CashierRootLayoutController {
    @FXML
    void handle_click_change_summary(ActionEvent event) {
        Cashier.generate_available_change_summary();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Available change summary generated.");
        alert.showAndWait();
    }

    @FXML
    void handle_click_valid_transaction_summary(ActionEvent event) {
        Cashier.generate_valid_transaction_summary();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Valid transaction summary generated.");
        alert.showAndWait();
    }

    @FXML
    void handle_logout(ActionEvent event) throws Exception {
        Cashier.logout();
    }




}
