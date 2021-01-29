package assignment2.controller;

import assignment2.model.*;
import assignment2.App;
import assignment2.backend.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CustomerRootLayoutController {

    Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @FXML
    void handle_logout(ActionEvent event) {
       this.customer.logout();
    }

}
