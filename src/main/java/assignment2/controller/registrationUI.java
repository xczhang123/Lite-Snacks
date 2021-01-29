package assignment2.controller;

import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import assignment2.App;
import assignment2.backend.User;
import assignment2.backend.UserDB;
import assignment2.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class registrationUI {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField password1;
    
    @FXML
    void back_to_menu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/fxml/login.fxml"));
    
        AnchorPane layout = (AnchorPane) loader.load();
        Scene scene = new Scene(layout);

        App.getPrimaryStage().setScene(scene);
    }

    @FXML
    void register(ActionEvent event) {
        String errorMessage = "";
        if (email.getText() == null || email.getText().length() == 0) {
            errorMessage += "No username!\n";
        }
        if (password.getText() == null || password.getText().length() == 0) {
            errorMessage += "No password!\n";
        }
        if (!password.getText().equals(password1.getText())) {
            errorMessage += "Password differs!\n";
        }
        // If there is no error message
        if (errorMessage.length() == 0) {
            // -1 is returned is insertion fails
            int status = UserDB.addUser(new User(email.getText(), password.getText(), "customer"));
            if (status == -1) {
                errorMessage += "Username and password combination already exists!\n";
            }
        }

        if (errorMessage.length() == 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(App.getPrimaryStage());
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Success in registration! We will redirect you to the home page.");

            alert.showAndWait();
            
            // redirect user to login page
            try {
                User user = UserDB.validate(email.getText(), password.getText());
                Customer customer = new Customer(user);
                customer.getUser().setName(email.getText());
                customer.initRootLayout();
                customer.showItemOverview();
            } catch (Exception e) {}
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(App.getPrimaryStage());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();
        }


    }


}
