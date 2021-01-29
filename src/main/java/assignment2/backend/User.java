package assignment2.backend;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty type;
    private String password;

    public User() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.password = "";
    }

    public User(int id, String type) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty("anonymous");
        this.type = new SimpleStringProperty(type);
    }

    public User(int id, String name, String type) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
    }

    public User(String name, String password, String type) {
        this.name = new SimpleStringProperty(name);
        this.password = password;
        this.type = new SimpleStringProperty(type);
    }

    public String toString() {
        return getName() + ',' + getType();
    }

    public int getId() {
        return id.get();
    }

    public void setid(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }


}
