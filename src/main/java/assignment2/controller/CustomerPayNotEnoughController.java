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
import javafx.scene.control.TextField;

public class CustomerPayNotEnoughController {

    @FXML
    private Label due_amount;

    @FXML
    void handle_continue(ActionEvent event) {
        customer.timer_reset();
        customer.pay_by_cash();
    }

    @FXML
    void handle_quit(ActionEvent event) {
        customer.cancel_transaction("Customer didn't pay enough");
        customer.timer_stop();
        customer.initRootLayout();
        customer.showItemOverview();
    }

    Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        due_amount.setText(String.format("%.01f",customer.get_unpaid()));
    }
}