package assignment2.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

public class CustomerPayCardController {

    @FXML
    private TextField pay_amount;

    @FXML
    void handleContinue(ActionEvent event) {
        this.customer.timer_reset();
        
        int status = customer.is_card_saved();
        if (status == 0) { //Failed
            customer.initRootLayout();
            customer.showItemOverview();
            User user = customer.getUser();
            TransactionDB.addCancelledTransaction(
                    new Transaction(new Timestamp(System.currentTimeMillis())
                    ,"Failed", user, "Wrong card"));
        } else { //Success
            if (status == 1) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.initOwner(App.getPrimaryStage());
                alert.setTitle("Success");
                alert.setHeaderText("Card accepted but not saved, require re-enter next time");
                alert.setContentText("Processing payment...");
                alert.showAndWait();
            } else if (status == 2) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.initOwner(App.getPrimaryStage());
                alert.setTitle("Success");
                alert.setHeaderText("Card saved!");
                alert.setContentText("Processing payment...");
                alert.showAndWait();
            }
            
            // Record the changes in the database
            HashMap<Snack, String> map = new HashMap<>();
            for (Snack snack : customer.getItemBought()) {
                map.put(snack, String.valueOf(snack.getQuantity()));
            }
            
            TransactionDB.addValidTransaction(
                new Transaction(
                    new Timestamp(System.currentTimeMillis()),
                    "Success", customer.getUser(), "Card", (double)customer.total_price, 0.0, map
                )
            );

            // pay all the money
            customer.total_price = 0.0f;
            //redirect customer to the payment success page
            customer.check_enough();
        }
    }

    @FXML
    void handleBack(ActionEvent event) {
        this.customer.timer_stop();
        // Hard coded fix to restore to previous stage after cancellation
        Customer tempCustomer = new Customer(customer.getUser());
        tempCustomer.initRootLayout();
        tempCustomer.showItemOverview();

        //Add reasons to the database
        TransactionDB.addCancelledTransaction(
            new Transaction(new Timestamp(System.currentTimeMillis())
            ,"Failed", customer.getUser(), "Cancelled"));
    }   

    Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        pay_amount.setText(String.format("%.01f", customer.get_unpaid()));
    }

}
