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
import javafx.stage.Stage;

public class CustomerPayMethodController {
    Customer customer;
    private Stage dialogStage;

    @FXML
    void handleCash(ActionEvent event) {
        this.customer.timer_reset();
        dialogStage.close();
        customer.pay_by_cash();
    }

    @FXML
    void handleCard(ActionEvent event) {
        this.customer.timer_reset();
        dialogStage.close();
        customer.pay_by_card();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
