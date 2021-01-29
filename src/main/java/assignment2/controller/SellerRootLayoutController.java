package assignment2.controller;

import assignment2.model.*;
import assignment2.App;
import assignment2.backend.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SellerRootLayoutController {

    @FXML
    void handle_click_item_summary(ActionEvent event) {
        Seller.generate_item_summary();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Item summary report generated.");
        alert.showAndWait();
    }

    @FXML
    void handle_click_sold_item_summary(ActionEvent event) {
        Seller.generate_sold_item_summary();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Item sold summary report generated.");
        alert.showAndWait();
    }

    @FXML
    void handle_logout(ActionEvent event) {
       Seller.logout();
    }



}
