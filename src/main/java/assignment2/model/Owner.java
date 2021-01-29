package assignment2.model;

import static org.junit.Assert.assertTrue;

import assignment2.backend.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import assignment2.App;
import assignment2.controller.CashierOverviewController;
import assignment2.controller.OwnerEditDialogController;
import assignment2.controller.OwnerOverviewController;
import assignment2.controller.SellerOverviewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Owner {

    private static BorderPane LoggedInRootLayout;

    private static ObservableList<Snack> snackData = FXCollections.observableArrayList();
    private static ObservableList<Cash> cashData = FXCollections.observableArrayList();
    private static ObservableList<User> userData = FXCollections.observableArrayList();

    public Owner() {

        // Make sure we don't fetch the same data over and over
        snackData.clear();
        cashData.clear();
        userData.clear();
        /**
         * Initialize seller data
         */
        snackData.addAll(SnackDB.view("Drinks"));
        snackData.addAll(SnackDB.view("Chocolates"));
        snackData.addAll(SnackDB.view("Chips"));
        snackData.addAll(SnackDB.view("Candies"));

        /**
         * Initialize cashier data
         */
        List<String> values = new ArrayList<>(Arrays.asList("50", "20", "10", "5", "2", "1", "0.5", "0.2", "0.1"));
        for (String price : values) {
            cashData.addAll(new Cash(price, CashDB.getQuantity(price)));
        }

        /**
         * Initialize owner data
         */
        userData.addAll(UserDB.getUsernameType());

    }

    public ObservableList<User> getUserData() {
        return userData;
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            // Load root layout from fxml file.
            loader.setLocation(App.class.getResource("/fxml/OwnerRootLayout.fxml"));
            LoggedInRootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(LoggedInRootLayout);
            App.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOwnerItemOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/fxml/OwnerOverview.fxml"));
            AnchorPane itemOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            LoggedInRootLayout.setCenter(itemOverview);

            // Give the controller access to the main app.
            OwnerOverviewController controller = loader.getController();
            controller.setOwner(this);

            controller.setMainApp(userData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new user. If the user clicks
     * OK, the changes are saved into the provided item object and true is
     * returned.
     *
     * @param user the item object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showOwnerEditDialog(User user) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/fxml/OwnerEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(App.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the item into the controller.
            OwnerEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setItem(user);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Shows the item overview of seller inside the root layout.
     */
    public void showSellerItemOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/fxml/SellerOverview.fxml"));
            AnchorPane itemOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            LoggedInRootLayout.setCenter(itemOverview);

            // Give the controller access to the main app.
            SellerOverviewController controller = loader.getController();
            controller.setSeller(new Seller());

            controller.setMainApp(snackData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the item overview of cashier inside the root layout.
     */
    public void showCashierItemOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/fxml/CashierOverview.fxml"));
            AnchorPane itemOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            LoggedInRootLayout.setCenter(itemOverview);

            // Give the controller access to the main app.
            CashierOverviewController controller = loader.getController();
            controller.setCashier(new Cashier());

            controller.setMainApp(cashData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Summary of all usernames and their role
     */
    public static void generate_username_role_summary() {
        File csvOutputFile = new File("summary/username_role_summary");

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("username,type");
            for (User user : userData) {
                pw.println(user); // Override toString() method in the Cash class
            }
        } catch (FileNotFoundException e) {}
        assertTrue(csvOutputFile.exists());
    }

    /**
     * Summary of cancelled transaction
     */
    public static void generate_cancelled_transaction_summary() {
        File csvOutputFile = new File("summary/cancelled_transaction_summary");

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("transaction_id,time,user_id,reason");
            List<Transaction> transactions = TransactionDB.getCancelledTransaction();
            for (Transaction tran : transactions) {
                pw.println(tran.toStringCancelled());
            }

        } catch (FileNotFoundException e) {}
        assertTrue(csvOutputFile.exists());
	}

    /**
     * Cashier Summary report
     */
    public static void generate_available_change_summary() {
        File csvOutputFile = new File("summary/available_change_summary");

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("price,quantity");
            for (Cash Cash : cashData) {
                pw.println(Cash); // Override toString() method in the Cash class
            }
        } catch (FileNotFoundException e) {}
        assertTrue(csvOutputFile.exists());
    }

    public static void generate_valid_transaction_summary() {
        File csvOutputFile = new File("summary/valid_transaction_summary");

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("transaction_id,time,user_id,payment,paid,change");
            pw.println("snack_id,snack_name,number_sold");
            List<Transaction> transactions = TransactionDB.getValidTransaction();
            for (Transaction tran : transactions) {
                pw.println(tran.toStringValid());
                pw.println(tran.toStringSnacks());
            }

        } catch (FileNotFoundException e) {}
        assertTrue(csvOutputFile.exists());
	}

     /**
     * Seller Summary report
     */
    public static void generate_item_summary() {
        File csvOutputFile = new File("summary/item_summary");

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("code,name,category,quantity,price");
            for (Snack snack : snackData) {
                pw.println(snack); // Override toString() method in the Snack class
            }
        } catch (FileNotFoundException e) {}
        assertTrue(csvOutputFile.exists());
    }

    public static void generate_sold_item_summary() {
        File csvOutputFile = new File("summary/item_sold_summary");

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("code,name,number_sold");
            HashMap<Snack, String> map = TransactionDB.getAllSoldSnack();
            for (Snack snack : map.keySet()) {
                String s = "" + Integer.toString(snack.getCode()) + "," + snack.getName() + "," + map.get(snack);
                pw.println(s);
            }
        } catch (FileNotFoundException e) {}
        assertTrue(csvOutputFile.exists());
    }

	public static void logout() {
        try {
            FXMLLoader loader = new FXMLLoader();

            // Load login page from fxml file.
            loader.setLocation(App.class.getResource("/fxml/login.fxml"));
            AnchorPane layout = (AnchorPane) loader.load();

            Scene scene = new Scene(layout);
            App.getPrimaryStage().setScene(scene);
        } catch (Exception e) {} 
	}



}
