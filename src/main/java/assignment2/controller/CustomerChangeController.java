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

public class CustomerChangeController {

    @FXML
    private Label fifty;

    @FXML
    private Label twenty;

    @FXML
    private Label ten;

    @FXML
    private Label five;

    @FXML
    private Label two;

    @FXML
    private Label one;

    @FXML
    private Label fiftycents;

    @FXML
    private Label twentycents;

    @FXML
    private Label tencents;

    Customer customer;
  
    public void setCustomer(Customer customer) {
        this.customer = customer;
        

        fiftycents.setText(String.format("%d", customer.change_type.get("0.5")));
        twentycents.setText(String.format("%d", customer.change_type.get("0.2")));
        tencents.setText(String.format("%d", customer.change_type.get("0.1")));
        one.setText(String.format("%d", customer.change_type.get("1")));
        two.setText(String.format("%d", customer.change_type.get("2")));
        five.setText(String.format("%d", customer.change_type.get("5")));
        ten.setText(String.format("%d", customer.change_type.get("10")));
        twenty.setText(String.format("%d", customer.change_type.get("20")));
        fifty.setText(String.format("%d", customer.change_type.get("50")));

        // Quick fix by Eric - 17/11/2020
        customer.complete_transaction();

        this.customer.total_price = 0;
        this.customer.reset();

    }

    @FXML
    void handleFinish(ActionEvent event) {
      customer.initRootLayout();
      customer.showItemOverview();
    }

    @FXML
    void handleLogout(ActionEvent event) {
      this.customer.logout();
    }

}

