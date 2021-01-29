package assignment2.backend;

import java.sql.Timestamp;
import java.util.HashMap;

public class Transaction {

     /*
     * These are basic attributes for all transactions
     */
    private Timestamp time;
    private String status;
    private int id;
    private User user;

     /**
      * Attributes that are needed for cancelled transactions
      */
    private String reason;

     /**
      * Attributes that are needed for valid transactions
      */
    private String payment;
    private double paid;
    private double change;
    private HashMap<Snack,String> snacks;


     /**
      * Constructor for cancelled Transactions to add new transaction(no id needed)
      * basically init by classes in controller package
      * and used as parameter to TransactionDB.java methods
      *
      * @param time current system time
      * @param status cancelled or valid
      * @param user
      * @param reason why the transaction is cancelled
      */
    public Transaction(Timestamp time, String status, User user, String reason) {
        this.time = time;
        this.status = status;
        this.user = user;
        this.reason = reason;
    }

     /**
      * Constructor for valid Transactions to add new transaction(no id needed)
      * basically init by classes in controller package
      * and used as parameter to TransactionDB.java methods
      *
      * @param time current system time
      * @param status cancelled or valid
      * @param user
      * @param payment how this transaction is paid
      * @param paid how much thses transacrion is paid(i.e. total amount of inserted cash)
      * @param change probably 0 when using card
      * @param snacks a hash map that use snack as key and its quantity as a string value
      */
    public Transaction(Timestamp time, String status, User user, String payment, double paid, double change, HashMap<Snack,String> snacks) {
        this.time = time;
        this.status = status;
        this.user = user;
        this.payment = payment;
        this.paid = paid;
        this.change = change;
        this.snacks = snacks;
    }

    /**
     * Constructor for valid Transactions with id
     * basically init and returned by methods in TransactionDB.java
     * and used to print report
     *
     * @param id the id of the transaction
     * @param time current system time
     * @param status cancelled or valid
     * @param user
     * @param payment how this transaction is paid
     * @param paid how much thses transacrion is paid(i.e. total amount of inserted cash)
     * @param change probably 0 when using card
     * @param snacks a hash map that use snack as key and its quantity as a string value
     */
    public Transaction(int id, Timestamp time, String status, User user, String payment, double paid, double change, HashMap<Snack,String> snacks) {
        this.id = id;
        this.time = time;
        this.status = status;
        this.user = user;
        this.payment = payment;
        this.paid = paid;
        this.change = change;
        this.snacks = snacks;
    }

    /**
     * Constructor for cancelled Transactions with id
     * basically init and returned by methods in TransactionDB.java
     * and used to print report
     *
     * @param id the id of the transaction
     * @param time current system time
     * @param status cancelled or valid
     * @param user
     * @param reason why the transaction is cancelled
     */
    public Transaction(int id, Timestamp time, String status, User user, String reason) {
        this.id = id;
        this.time = time;
        this.status = status;
        this.user = user;
        this.reason = reason;
    }

    /**
     * function to get the string for a cancelled transaction
     */
   public String toStringCancelled() {
       String s;
       if (!Integer.toString(user.getId()).equals("-1")) {
            s = Integer.toString(id) + "," +
                    time.toString() + "," +
                    Integer.toString(user.getId()) + "," +
                    reason;
       } else {
            s = Integer.toString(id) + "," +
                time.toString() + "," +
                "anonymous" + "," +
                reason;
       }
       return s;
   }

     /**
      * function to get the string for a valid transaction
      * only contains basic information
      * to get the list of snack involved with thistransaction, needs to use method toStringSnacks()
      */
    public String toStringValid() {
        String s;
        if (!Integer.toString(user.getId()).equals("-1")) {
            s = Integer.toString(id) + "," +
                        time.toString() + "," +
                        Integer.toString(user.getId()) + "," +
                        payment + "," +
                        Double.toString(paid) + "," +
                        Double.toString(change);
        } else {
            s = Integer.toString(id) + "," +
                        time.toString() + "," +
                        "anonymous" + "," +
                        payment + "," +
                        Double.toString(paid) + "," +
                        Double.toString(change);
        }
        return s;
    }

     /**
      * function to get the string of a list of snack invoived with this transaction
      */
    public String toStringSnacks() {
        String s = "";

        for (Snack snack : snacks.keySet()){
          s = s + Integer.toString(snack.getCode()) + "," + snack.getName() + "," + snacks.get(snack) + "\n";
        }
        return s;
    }

     /**
      * getter for basic info
      */
    public Timestamp getTime() {
        return time;
    }

    public int getid() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getPayment() {
        return payment;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public double getPaid() {
        return paid;
    }

    public double getChange() {
        return change;
    }

    public HashMap<Snack,String> getSnacks() {
        return snacks;
    }
}
