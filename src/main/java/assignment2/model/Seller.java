package assignment2.model;

import static org.junit.Assert.assertTrue;

import assignment2.backend.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import assignment2.App;
import assignment2.controller.SellerEditDialogController;
import assignment2.controller.SellerOverviewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
    As a seller, I wish to inspect the current inventory, so that I can fill/modify the item details correctly.

    Level of satisfaction:
    - Correctly display the right amount of goods in the inventory.
    - Able to fill in the existing goods
    - Able to modify the item details
    - Able to generate a summary that includes items codes, item names and the total number of quantity sold for each item (e.g. "1001; Mineral Water; 12", "1002; Mars; 1", etc.).
*/
public class Seller {

    private static BorderPane LoggedInRootLayout;
    /**
     * The data as an observable list of Persons.
     */
    private static ObservableList<Snack> itemData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public Seller() {
        itemData.clear();
        itemData.addAll(SnackDB.view("Drinks"));
        itemData.addAll(SnackDB.view("Chocolates"));
        itemData.addAll(SnackDB.view("Chips"));
        itemData.addAll(SnackDB.view("Candies"));
    }

    /**
     * Returns the data as an observable list of Persons.
     *
     * @return
     */
    public ObservableList<Snack> getItemData() {
        return itemData;
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            // Load root layout from fxml file.
            loader.setLocation(App.class.getResource("/fxml/SellerRootLayout.fxml"));
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
            loader.setLocation(App.class.getResource("/fxml/SellerOverview.fxml"));
            AnchorPane itemOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            LoggedInRootLayout.setCenter(itemOverview);

            // Give the controller access to the main app.
            SellerOverviewController controller = loader.getController();
            controller.setSeller(this);
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
    public boolean showItemEditDialog(Snack item) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/fxml/SellerEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Item");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(App.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the item into the controller.
            SellerEditDialogController controller = loader.getController();
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

    public static void generate_item_summary() {
        File csvOutputFile = new File("summary/item_summary");

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("code,name,category,quantity,price");
            for (Snack snack : itemData) {
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
