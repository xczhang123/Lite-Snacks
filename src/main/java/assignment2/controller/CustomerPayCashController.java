package assignment2.controller;

import assignment2.App;
import assignment2.model.*;
import assignment2.backend.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
public class CustomerPayCashController {

    @FXML
    private TextField pay_amount;

    // Change display format by Eric - 15/11 6pm

    @FXML
    void fifty(ActionEvent event) {
        this.customer.timer_reset();
        customer.add_balance((float) 50);
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
        customer.cash_type.put("50",customer.cash_type.get("50") + 1);
    }

    @FXML
    void fiftycents(ActionEvent event) {
        this.customer.timer_reset();
        customer.add_balance((float) 0.5);
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
        customer.cash_type.put("0.5",customer.cash_type.get("0.5") + 1);
    }

    @FXML
    void five(ActionEvent event) {
        this.customer.timer_reset();
        customer.add_balance((float) 5);
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
        customer.cash_type.put("5",customer.cash_type.get("5") + 1);
    }

    @FXML
    void handleContinue(ActionEvent event) {
        this.customer.timer_reset();
        customer.check_enough();
    }

    @FXML
    void handleLogout(ActionEvent event) {
        this.customer.timer_reset();
        this.customer.logout();
    }

    @FXML
    void handleCancel(ActionEvent event) {
        this.customer.timer_stop();
        Customer tempCustomer = new Customer(customer.getUser());
        tempCustomer.cancel_transaction("stopped");
        tempCustomer.initRootLayout();
        tempCustomer.showItemOverview();
    }

    @FXML
    void one(ActionEvent event) {
        this.customer.timer_reset();
        customer.add_balance((float) 1);
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
        customer.cash_type.put("1",customer.cash_type.get("1") + 1);
    }

    @FXML
    void ten(ActionEvent event) {
        this.customer.timer_reset();
        customer.add_balance((float) 10);
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
        customer.cash_type.put("10",customer.cash_type.get("10") + 1);
    }

    @FXML
    void tencents(ActionEvent event) {
        this.customer.timer_reset();
        customer.add_balance((float) 0.1);
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
        customer.cash_type.put("0.1",customer.cash_type.get("0.1") + 1);
    }

    @FXML
    void twenty(ActionEvent event) {
        this.customer.timer_reset();
        customer.add_balance((float) 20);
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
        customer.cash_type.put("20",customer.cash_type.get("20") + 1);
    }

    @FXML
    void twentycents(ActionEvent event) {
        this.customer.timer_reset();
        customer.add_balance((float) 0.2);
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
        customer.cash_type.put("0.2.",customer.cash_type.get("0.2") + 1);
    }

    @FXML
    void two(ActionEvent event) {
        this.customer.timer_reset();
        customer.add_balance((float) 2);
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
        customer.cash_type.put("2",customer.cash_type.get("2") + 1);
    }
    Customer customer;
    public void setCustomer(Customer customer) {
        this.customer = customer;
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
    }

}
