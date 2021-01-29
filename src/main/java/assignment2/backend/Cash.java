package assignment2.backend;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cash {

    /* Primary information displayed on the LHS/RHS */
    private StringProperty price;
    private IntegerProperty quantity;

    public Cash(String price, int quantity) {
        this.price = new SimpleStringProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    @Override
    public String toString() {
        return getPrice() + ',' + getQuantity();
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public StringProperty priceProperty() {
        return price;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

}
