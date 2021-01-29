package assignment2.model;

import assignment2.backend.*;
import assignment2.App;
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import assignment2.controller.CustomerAddCardController;
import assignment2.controller.CustomerBuyController;
import assignment2.controller.CustomerOverviewController;
import assignment2.controller.CustomerPayCardController;
import assignment2.controller.CustomerPayMethodController;
import assignment2.controller.CustomerPayCashController;
import assignment2.controller.CustomerPayNotEnoughController;
import assignment2.controller.CustomerChangeController;
import assignment2.controller.CustomerRecentController;
import assignment2.controller.CustomerRootLayoutController;
import assignment2.controller.CustomerNoEnoughChangeController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.application.Platform;


public class Customer {
    private static BorderPane LoggedInRootLayout;
    public Float money_inputted;
    private User user;
    public float total_price;
    private static ObservableList<Snack> itemData = FXCollections.observableArrayList();
    private ArrayList<Snack> itemBought;
    public HashMap<String, Integer> cash_type;
    public HashMap<String, Integer> change_type;
    final PauseTransition timer = new PauseTransition(Duration.minutes(1));
    // method to keep track of moeny customer inserted
    public Customer(User user) {
        this.money_inputted = (float) 0;
        this.user = user;
        this.total_price = 0;
        this.itemBought = new ArrayList<Snack>();
        this.cash_type = new HashMap<String, Integer>();
        this.change_type = new HashMap<String, Integer>();
        this.cash_type.put("50",0);
        this.cash_type.put("20",0);
        this.cash_type.put("10",0);
        this.cash_type.put("5",0);
        this.cash_type.put("2",0);
        this.cash_type.put("1",0);
        this.cash_type.put("0.5",0);
        this.cash_type.put("0.2",0);
        this.cash_type.put("0.1",0);
        this.change_type.put("50",0);
        this.change_type.put("20",0);
        this.change_type.put("10",0);
        this.change_type.put("5",0);
        this.change_type.put("2",0);
        this.change_type.put("1",0);
        this.change_type.put("0.5",0);
        this.change_type.put("0.2",0);
        this.change_type.put("0.1",0);
        itemData.clear();
        itemData.addAll(SnackDB.view("Drinks"));
        itemData.addAll(SnackDB.view("Chocolates"));
        itemData.addAll(SnackDB.view("Chips"));
        itemData.addAll(SnackDB.view("Candies"));

        /* Adds a timeout to the overview page to log the customer out after 1 min */
        timer.setOnFinished( ( ActionEvent event ) -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(App.getPrimaryStage());
            alert.setTitle("Timeout");
            alert.setHeaderText("You have been timed out");
            alert.setContentText("Please login again");
            Platform.runLater(() -> {
                alert.showAndWait();
                TransactionDB.addCancelledTransaction(new Transaction(new Timestamp(System.currentTimeMillis()) ,"Failed", this.getUser(), "Timeout"));
                this.logout();
            });
        });
    }
    public boolean get_change(){

        float tempp = this.money_inputted;
        tempp -= this.total_price;
        ArrayList<String> currency = new ArrayList<String>(Arrays.asList("50","20","10",
        "5", "2", "1", "0.5", "0.2", "0.1"));
        HashMap<String, Integer> temp = new HashMap<String, Integer>();
        for (String i : currency){
            temp.put(i,this.cash_type.get(i) + CashDB.getQuantity(i));
        }
        for (String i : currency){
            // Ad-hoc fix of comparision
            while ((float)(Math.round((tempp - Float.valueOf(i)) * 10) / 10.0) >= 0 && temp.get(i) > 0) {
                //Increment change coins
                this.change_type.put(i, this.change_type.get(i) + 1);
                //Decrement available coins
                temp.put(i, temp.get(i) - 1);
                tempp -= Float.valueOf(i);

            }
            if(tempp < 0.01){
                break;
            }
        }

        if(tempp > 0.01){
            return false;
        }
        else{
            for (String i : currency){
                CashDB.addCash(i, this.cash_type.get(i));
            }
            for (String i : currency){
                CashDB.useCash(i, this.change_type.get(i));
            }
            for (Snack i : this.itemBought){
                SnackDB.buy(i.getRollno(), i.getQuantity());
            }
            return true;
        }
    }
    public ArrayList<Snack> getItemBought() {
        return itemBought;
    }

    public void add_balance(Float balance){
        this.money_inputted += balance;
    }

    public void reset(){
        this.money_inputted = (float)0;
    }

    public User getUser() {
        return user;
    }

    public ObservableList<Snack> getItemData() {
        return itemData;
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            // Load root layout from fxml file.
            loader.setLocation(App.class.getResource("/fxml/CustomerRootLayout.fxml"));
            LoggedInRootLayout = (BorderPane) loader.load();
            // Show the scene containing the root layout.
            CustomerRootLayoutController controller = loader.getController();
            controller.setCustomer(this);

            Scene scene = new Scene(LoggedInRootLayout);
            App.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play_timer() {
        this.timer.play();
    }

    public void timer_reset() {
        this.timer.playFromStart();
    }

    public void timer_stop() {
        this.timer.stop();
    }

    public void showItemOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/fxml/CustomerOverview.fxml"));
            AnchorPane itemOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            LoggedInRootLayout.setCenter(itemOverview);

            // Give the controller access to the main app.
            CustomerOverviewController controller = loader.getController();
            controller.setCustomer(this);
            controller.setMainApp(itemData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showbuyDialog(Snack item) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/fxml/CustomerBuy.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Buy Item");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(App.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the item into the controller.
            CustomerBuyController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setItem(item);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            if (controller.bought != null) {
                this.itemBought.add(controller.bought);
                this.total_price += (controller.bought.getQuantity() * controller.bought.getPrice());
            }
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void pay_method() {
        try {
            FXMLLoader loader = new FXMLLoader();
            // Load root layout from fxml file.
            loader.setLocation(App.class.getResource("/fxml/CustomerPayMethod.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Payment Method");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(App.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            CustomerPayMethodController controller = loader.getController();
            controller.setCustomer(this);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recent_method() {
        try {
            FXMLLoader loader = new FXMLLoader();
            // Load root layout from fxml file.
            loader.setLocation(App.class.getResource("/fxml/CustomerRecent.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Recent Items");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(App.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the item into the controller.
            CustomerRecentController controller = loader.getController();
            controller.setCustomer(this);
            controller.setDialogStage(dialogStage);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pay_by_cash() {
        try {
            FXMLLoader loader = new FXMLLoader();
            // Load root layout from fxml file.
            loader.setLocation(App.class.getResource("/fxml/CustomerPayCash.fxml"));
            AnchorPane Method_page = (AnchorPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(Method_page);
            App.getPrimaryStage().setScene(scene);
            CustomerPayCashController controller = loader.getController();
            controller.setCustomer(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float get_unpaid() {
        // Change float comparison by Eric - 15/11 6pm
        final double EPSILON = 0.01;
        if ((this.total_price - this.money_inputted - 0) >= EPSILON) {
            return this.total_price - this.money_inputted;
        } else {
            return (float) 0;
        }
    }
    public void cancel_transaction(String message){
        TransactionDB.addCancelledTransaction(
            new Transaction(new Timestamp(System.currentTimeMillis())
            ,"Failed", this.user, message));
        this.itemBought = new ArrayList<Snack>();
        this.cash_type.put("50",0);
        this.cash_type.put("20",0);
        this.cash_type.put("10",0);
        this.cash_type.put("5",0);
        this.cash_type.put("2",0);
        this.cash_type.put("1",0);
        this.cash_type.put("0.5",0);
        this.cash_type.put("0.2",0);
        this.cash_type.put("0.1",0);
        this.change_type.put("50",0);
        this.change_type.put("20",0);
        this.change_type.put("10",0);
        this.change_type.put("5",0);
        this.change_type.put("2",0);
        this.change_type.put("1",0);
        this.change_type.put("0.5",0);
        this.change_type.put("0.2",0);
        this.change_type.put("0.1",0);
        this.total_price = 0;
        this.money_inputted = (float)0;
        this.itemBought = new ArrayList<Snack>();
    }
    public void complete_transaction(){
        HashMap <Snack,String> temp = new HashMap<Snack,String>();
        for(Snack i : this.itemBought){
            temp.put(i,String.valueOf(i.getQuantity()));
        }
        TransactionDB.addValidTransaction(
            new Transaction(new Timestamp(System.currentTimeMillis())
            ,"valid", this.user, "cash",(double)this.money_inputted, (double)((Math.round((this.money_inputted - this.total_price)*10)/10.0)),temp));
        // System.out.println(total_);
        // System.out.println(this.money_inputted);
        // System.out.println((double)((Math.round(this.money_inputted - this.total_price))));
        // System.out.println((double)((Math.round(this.money_inputted - this.total_price)*10)/10.0));
        // System.out.println((double)((Math.round(this.money_inputted - this.total_price)*100)/100.0));
        this.itemBought = new ArrayList<Snack>();
        this.cash_type.put("50",0);
        this.cash_type.put("20",0);
        this.cash_type.put("10",0);
        this.cash_type.put("5",0);
        this.cash_type.put("2",0);
        this.cash_type.put("1",0);
        this.cash_type.put("0.5",0);
        this.cash_type.put("0.2",0);
        this.cash_type.put("0.1",0);
        this.change_type.put("50",0);
        this.change_type.put("20",0);
        this.change_type.put("10",0);
        this.change_type.put("5",0);
        this.change_type.put("2",0);
        this.change_type.put("1",0);
        this.change_type.put("0.5",0);
        this.change_type.put("0.2",0);
        this.change_type.put("0.1",0);
        this.total_price = 0;
        this.money_inputted = (float)0;
        this.itemBought = new ArrayList<Snack>();
    }
    public void check_enough() {
        if (money_inputted < total_price) {
            try {
                FXMLLoader loader = new FXMLLoader();
                // Load root layout from fxml file.
                loader.setLocation(App.class.getResource("/fxml/CustomerPayNotEnough.fxml"));
                AnchorPane Method_page = (AnchorPane) loader.load();
                // Show the scene containing the root layout.
                Scene scene = new Scene(Method_page);
                App.getPrimaryStage().setScene(scene);
                CustomerPayNotEnoughController controller = loader.getController();
                controller.setCustomer(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            if(this.get_change()){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    // Load root layout from fxml file.
                    loader.setLocation(App.class.getResource("/fxml/CustomerChange.fxml"));
                    AnchorPane Method_page = (AnchorPane) loader.load();
                    // Show the scene containing the root layout.
                    Scene scene = new Scene(Method_page);
                    App.getPrimaryStage().setScene(scene);
                    CustomerChangeController controller = loader.getController();
                    controller.setCustomer(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    FXMLLoader loader = new FXMLLoader();
                    // Load root layout from fxml file.
                    loader.setLocation(App.class.getResource("/fxml/CustomerNoEnoughChange.fxml"));
                    AnchorPane Method_page = (AnchorPane) loader.load();
                    // Show the scene containing the root layout.
                    Scene scene = new Scene(Method_page);
                    App.getPrimaryStage().setScene(scene);
                    CustomerNoEnoughChangeController controller = loader.getController();
                    controller.setCustomer(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pay_by_card() {
        try {
            FXMLLoader loader = new FXMLLoader();

            // Load login page from fxml file.
            loader.setLocation(App.class.getResource("/fxml/CustomerPayCard.fxml"));
            AnchorPane layout = (AnchorPane) loader.load();

            Scene scene = new Scene(layout);
            App.getPrimaryStage().setScene(scene);

            CustomerPayCardController controller = loader.getController();
            controller.setCustomer(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        try {
            FXMLLoader loader = new FXMLLoader();

            // Load login page from fxml file.
            loader.setLocation(App.class.getResource("/fxml/login.fxml"));
            AnchorPane layout = (AnchorPane) loader.load();
            this.timer_stop();

            Scene scene = new Scene(layout);
            App.getPrimaryStage().setScene(scene);
        } catch (Exception e) {
        }
    }

    /**
     * 
     * @return 0, failed; 1, card accepted but not saved; 2, card saved
     */
    public int is_card_saved() {
        // If card is not saved
        if (UserDB.getCard(user) == null) {
            try {
                FXMLLoader loader = new FXMLLoader();

                // Load login page from fxml file.
                loader.setLocation(App.class.getResource("/fxml/CustomerAddCard.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add card details");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(App.getPrimaryStage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                //Set the item into the controller.
                CustomerAddCardController controller = loader.getController();
                controller.setCustomer(this);
                controller.setDialogStage(dialogStage);

                // Show the dialog and wait until the user closes it
                dialogStage.showAndWait();
                
                if (controller.isOkClicked()) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        } else {
            // Otherwise, the customer has already saved the card, just jump to success
            return 2;
        }

    }

    

}