package assignment2.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SnackDB {

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
     * Update snack details using the unique rollno
     *
     * @param Snack snack
     * @return int (means nothing)
     */
    public static int update(Snack s) {
        int status = 0;
        try {
            Connection con = getCon();
            String sql = "UPDATE Snacks SET name=?, category=?, price=?, quantity=?, code = ? WHERE rollno=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getCategory());
            ps.setFloat(3, s.getPrice());
            ps.setInt(4, s.getQuantity());
            ps.setInt(5, s.getCode());
            ps.setInt(6, s.getRollno());
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    /**
     * get a list of particular category of snacks
     *
     * @param String category
     * @return List<Snack> a list of snacks
     */
    public static ObservableList<Snack> view(String category) {
        ObservableList<Snack> list = FXCollections.observableArrayList();
        try {
            Connection con = getCon();
            String sql = "";
            PreparedStatement ps;
            if (category.equals("")) {
                sql = "SELECT * FROM Snacks WHERE category!='Test'";
                ps = con.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM Snacks WHERE category=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, category);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Snack s = new Snack(rs.getInt("rollno"));
                s.setName(rs.getString("name"));
                s.setCategory(rs.getString("category"));
                s.setPrice(rs.getFloat("price"));
                s.setQuantity(rs.getInt("quantity"));
                s.setImage(rs.getString("image"));
                s.setCode(rs.getInt("code"));
                list.add(s);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    /**
     * reduce the quantity of a snack
     *
     * @param int the rollno of the snack
     * @param int the quantity of bought snacks
     * @return int (no meaning)
     */
    public static int buy(int rollno, int buy) {
        int status = 0;
        try {
            Connection con = getCon();
            String sql = "UPDATE Snacks SET quantity=quantity-? WHERE rollno=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, buy);
            ps.setInt(2, rollno);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    /**
     * get the quantity of a snack
     *
     * @param int the rollno of the snack
     * @return int the quantity of the snack
     */
    public static int getQuantity(int rollno) {
        int quantity = 0;
        try {
            Connection con = getCon();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Snacks WHERE rollno=?");
            ps.setInt(1, rollno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt("quantity");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return quantity;
    }

    /**
     * get the price of a snack
     *
     * @param int the rollno of the snack
     * @return float the price of the snack
     */
    public static float getPrice(int rollno) {
        float price = 0;
        try {
            Connection con = getCon();
            PreparedStatement ps = con.prepareStatement("SELECT price FROM Snacks WHERE rollno=?");
            ps.setInt(1, rollno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                price = rs.getFloat("price");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return price;
    }

    /**
     * get the most recent bought snack for user
     *
     * @param int the user id
     * @return List<Snack> most resent bought snacks
     */
    public static List<Snack> getRecent(int id) {
    	List<Snack> recent = new ArrayList();
    	try {
    	    Connection con = getCon();
    	    PreparedStatement ps = con.prepareStatement("SELECT * FROM (BoughtSnack JOIN Snacks USING(rollno)) JOIN Transaction USING(id) WHERE userid = ? ORDER BY date DESC LIMIT 5");
    	    ps.setInt(1, id);
    	    ResultSet rs = ps.executeQuery();
          while (rs.next()) {
              Snack s = new Snack(rs.getInt("rollno"));
              s.setName(rs.getString("name"));
              s.setCategory(rs.getString("category"));
              s.setPrice(rs.getFloat("price"));
              s.setQuantity(rs.getInt("BoughtSnack.quantity"));
              s.setCode(rs.getInt("code"));
              recent.add(s);
          }
    	    con.close();
    	} catch (Exception e) {
    	    System.out.println(e);
    	}
    	return recent;
    }

    public static String getName(int rollno) {
    	String name = "";
    	try {
    	    Connection con = getCon();
    	    PreparedStatement ps = con.prepareStatement("SELECT name FROM Snacks WHERE rollno=?");
    	    ps.setInt(1, rollno);
    	    ResultSet rs = ps.executeQuery();
    	    if (rs.next()) {
    		      name = rs.getString("name");
    	    }
    	    con.close();
    	} catch (Exception e) {
    	    System.out.println(e);
    	}
    	return name;
    }
}
