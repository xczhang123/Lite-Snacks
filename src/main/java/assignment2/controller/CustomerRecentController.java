package assignment2.controller;

import java.util.List;

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
import javafx.scene.control.TextField;

public class CustomerRecentController {

    @FXML
    private Label nameone;

    @FXML
    private Label quantityone;

    @FXML
    private Label nametwo;

    @FXML
    private Label quantitytwo;

    @FXML
    private Label namethree;

    @FXML
    private Label quantitythree;

    @FXML
    private Label namefour;

    @FXML
    private Label quantityfour;

    @FXML
    private Label namefive;

    @FXML
    private Label quantityfive;
    Customer customer;
    private Stage dialogStage;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    @FXML
    void handleOk(ActionEvent event) {
        this.customer.timer_reset();
        dialogStage.close();
    }

     /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;

        List<Snack> snacks = SnackDB.getRecent(customer.getUser().getId());

        nameone.setText("None");
        quantityone.setText("None");
        nametwo.setText("None");
        quantitytwo.setText("None");
        namethree.setText("None");
        quantitythree.setText("None");
        namefour.setText("None");
        quantityfour.setText("None");
        namefive.setText("None");
        quantityfive.setText("None");
        
        switch (snacks.size()) {
            case 5:
                namefive.setText(snacks.get(4).getName());
                quantityfive.setText(String.valueOf(snacks.get(4).getQuantity()));
            case 4:
                namefour.setText(snacks.get(3).getName());
                quantityfour.setText(String.valueOf(snacks.get(3).getQuantity()));
            case 3:
                namethree.setText(snacks.get(2).getName());
                quantitythree.setText(String.valueOf(snacks.get(2).getQuantity()));
            case 2:
                nametwo.setText(snacks.get(1).getName());
                quantitytwo.setText(String.valueOf(snacks.get(1).getQuantity()));
            case 1:
                nameone.setText(snacks.get(0).getName());
                quantityone.setText(String.valueOf(snacks.get(0).getQuantity()));           
        }
    }
}
