package assignment2.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CashDB {

    public static Connection getCon() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://database-1.cim2a1ghafx3.ap-southeast-2.rds.amazonaws.com:3306/Assignment2", "admin",
                    "soft2412");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    /**
     * Get the quantity of specified cash
     *
     * @param string cash type
     * @return int quantity
     */
    public static int getQuantity(String cash) {
        int quantity = 0;
        try {
            Connection con = getCon();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Cash WHERE price=?;");
            // String price = Double.toString(cash);
            ps.setString(1, cash);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt("quantity");
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return quantity;
    }

    /**
     * Reduce the specified cash from database
     *
     * @param string cash type
     * @param int    quantity that has been used
     */
    public static void useCash(String cash, int quantity) {
        try {
            Connection con = getCon();
            // int quantityNew = db.getQuantity(cash) - quantity;
            PreparedStatement ps = con.prepareStatement("UPDATE Cash SET quantity = quantity -? WHERE price = ?");
            ps.setInt(1, quantity);
            ps.setString(2, cash);
            int status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Add the specified cash from database
     *
     * @param double cash type
     * @param int    quantity that has been used
     */
    public static void addCash(String cash, int quantity) {
        try {
            Connection con = getCon();
            CashDB db = new CashDB();
            PreparedStatement ps = con.prepareStatement("UPDATE Cash SET quantity = quantity + ? WHERE price = ?");
            ps.setInt(1, quantity);
            ps.setString(2, cash);
            int status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
