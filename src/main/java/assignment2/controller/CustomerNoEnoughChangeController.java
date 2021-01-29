package assignment2.controller;

import assignment2.model.*;
import assignment2.App;
import assignment2.backend.*;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class CustomerNoEnoughChangeController {
    @FXML
    void handleAgain(ActionEvent event) {
        this.customer.money_inputted = (float) 0;
        this.customer.cash_type.put("50", 0);
        this.customer.cash_type.put("20", 0);
        this.customer.cash_type.put("10", 0);
        this.customer.cash_type.put("5", 0);
        this.customer.cash_type.put("2", 0);
        this.customer.cash_type.put("1", 0);
        this.customer.cash_type.put("0.5", 0);
        this.customer.cash_type.put("0.2", 0);
        this.customer.cash_type.put("0.1", 0);
        this.customer.change_type.put("50", 0);
        this.customer.change_type.put("20", 0);
        this.customer.change_type.put("10", 0);
        this.customer.change_type.put("5", 0);
        this.customer.change_type.put("2", 0);
        this.customer.change_type.put("1", 0);
        this.customer.change_type.put("0.5", 0);
        this.customer.change_type.put("0.2", 0);
        this.customer.change_type.put("0.1", 0);
        this.customer.pay_by_cash();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        this.customer.timer_stop();
        Customer tempCustomer = new Customer(customer.getUser());
        tempCustomer.cancel_transaction("No change");
        tempCustomer.initRootLayout();
        tempCustomer.showItemOverview();
    }

    @FXML
    void handleLogout(ActionEvent event) {
        this.customer.cancel_transaction("logout");
        this.customer.logout();
    }

    Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
