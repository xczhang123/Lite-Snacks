package assignment2.controller;

import assignment2.App;
import assignment2.model.*;
import assignment2.backend.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

public class loginUI {

    @FXML
    private AnchorPane login;

    @FXML
    private Pane content_area;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField password;

    @FXML
    void open_registration(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/fxml/registration.fxml"));

        AnchorPane layout = (AnchorPane) loader.load();
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(layout);
    }

    @FXML
    void sellerLogin(ActionEvent event) throws IOException {

        User user = UserDB.validate(email.getText(), password.getText());

        if (user == null) {
            // Clear the text fields
            email.setText("");
            password.setText("");

            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(App.getPrimaryStage());
            alert.setContentText("Incorrect username or password!");
            alert.showAndWait();
        } else {
            if (user.getType().equals("seller")) {
                Seller seller = new Seller();
                seller.initRootLayout();
                seller.showItemOverview();
            } else if (user.getType().equals("cashier")) {
                Cashier cashier = new Cashier();
                cashier.initRootLayout();
                cashier.showItemOverview();
            } else if (user.getType().equals("owner")) {
                Owner owner = new Owner();
                owner.initRootLayout();
                owner.showOwnerItemOverview();
            } else if (user.getType().equals("customer")) {
                Customer customer = new Customer(user);
                customer.getUser().setName(email.getText());
                customer.initRootLayout();
                customer.showItemOverview();
            }
        }
    }
}
