package assignment2.backend;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Snack {

    /* Primary information displayed on the LHS */
    private StringProperty name;
    private FloatProperty price;
    private IntegerProperty quantity;

    /* Detailed infomarion displayed on the RHS */
    private StringProperty category;
    private IntegerProperty code;

    /* Non-displayed, cannot be changed */
    private final IntegerProperty rollno;

    /* Image for the snack to be displayed */
    private Image snackImage;

    /*
        Primary info
    */
    public Snack(int rollno) {
        this.name = new SimpleStringProperty();
        this.price = new SimpleFloatProperty();
        this.quantity = new SimpleIntegerProperty();
        this.category = new SimpleStringProperty();
        this.code = new SimpleIntegerProperty();
        this.rollno = new SimpleIntegerProperty(rollno);
        this.snackImage = null;
	}

    /**
     * Construtor
     * @param rollno    Primary key
     * @param name
     * @param category
     * @param quantity
     * @param price
     * @param code
     */
    public Snack(int rollno, String name, String category, int quantity, float price, int code, String imageURL) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleFloatProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);

        this.category = new SimpleStringProperty(category);
        this.code = new SimpleIntegerProperty(code);

        this.rollno = new SimpleIntegerProperty(rollno);

        if (imageURL.equals("")){
          this.snackImage = null;
        }else{
          this.snackImage = new Image(imageURL);
        }
    }

    @Override
    public String toString() {
        return Integer.toString(getCode()) + ',' + getName() + ',' + getCategory() + ',' + getQuantity() + ',' + getPrice();
    }

	public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Image getImage() {
        return this.snackImage;
    }

    public void setImage(String imageURL) {
        if (imageURL.equals("")){
          this.snackImage = null;
        }else{
          this.snackImage = new Image(imageURL);
        }
    }

    public StringProperty nameProperty() {
        return name;
    }

    public float getPrice() {
        return price.get();
    }

    public void setPrice(float price) {
        this.price.set(price);
    }

    public FloatProperty priceProperty() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    /*
        Detailed info
    */

    public String getCategory() {
        return this.category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public StringProperty categoryProperty() {
        return this.category;
    }

    public int getCode() {
        return code.get();
    }

    public void setCode(int code) {
        this.code.set(code);
    }

    public IntegerProperty codeProperty() {
        return code;
    }

    public int getRollno() {
        return rollno.get();
    }

    public IntegerProperty rollnoProperty() {
        return rollno;
    }
}
