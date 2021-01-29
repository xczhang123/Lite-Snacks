package assignment2.model;

import static org.junit.Assert.assertTrue;

import assignment2.backend.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import assignment2.App;
import assignment2.controller.CashierEditDialogController;
import assignment2.controller.CashierOverviewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Cashier {

    private static BorderPane LoggedInRootLayout;
    /**
     * The data as an observable list of Cash.
     */
    private static ObservableList<Cash> itemData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public Cashier() {
        itemData.clear();
        List<String> values = new ArrayList<>(Arrays.asList("50", "20", "10", "5", "2", "1", "0.5", "0.2", "0.1"));
        for (String price : values) {
            itemData.addAll(new Cash(price, CashDB.getQuantity(price)));
        }
    }

    /**
     * Returns the data as an observable list of Cash.
     *
     * @return
     */
    public ObservableList<Cash> getItemData() {
        return itemData;
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            // Load root layout from fxml file.
            loader.setLocation(App.class.getResource("/fxml/CashierRootLayout.fxml"));
            LoggedInRootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(LoggedInRootLayout);
            App.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the item overview inside the root layout.
     */
    public void showItemOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/fxml/CashierOverview.fxml"));
            AnchorPane itemOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            LoggedInRootLayout.setCenter(itemOverview);

            // Give the controller access to the main app.
            CashierOverviewController controller = loader.getController();
            controller.setCashier(this);
            controller.setMainApp(itemData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified item. If the user clicks
     * OK, the changes are saved into the provided item object and true is
     * returned.
     *
     * @param item the item object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showItemEditDialog(Cash item) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/fxml/CashierEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Item");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(App.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the item into the controller.
            CashierEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setItem(item);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void generate_available_change_summary() {
        File csvOutputFile = new File("summary/available_change_summary");

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("price,quantity");
            for (Cash Cash : itemData) {
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
