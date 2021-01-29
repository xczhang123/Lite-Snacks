package assignment2.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Timestamp;
import java.util.HashMap;


import java.util.ArrayList;
import java.util.List;

public class TransactionDB {

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
     * add new transaction to the system
     * should be called by Customer
     *
     * @param Transaction transaction that needed to be added
     */
    public static void addValidTransaction(Transaction trans) {
        int status = 0;
        try {
            Connection con = getCon();
            String sql = "INSERT INTO Transaction(status, userid, date) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, trans.getStatus());
            ps.setInt(2, trans.getUser().getId());
            ps.setTimestamp(3, trans.getTime());
            status = ps.executeUpdate();

            TransactionDB db = new TransactionDB();
            int id = db.getLatestTrans();
            db.addValidTrans(id, trans);

            for (Snack s : trans.getSnacks().keySet()){
              db.addBoughtSnack(id, s, trans.getSnacks().get(s));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * add new cancelled transaction to the system
     * should be called by Customer
     *
     * @param Transaction transaction that needed to be added
     */
    public static void addCancelledTransaction(Transaction trans) {
        int status = 0;
        try {
            Connection con = getCon();
            String sql = "INSERT INTO Transaction(status, userid, date) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, trans.getStatus());
            ps.setInt(2, trans.getUser().getId());
            ps.setTimestamp(3, trans.getTime());
            status = ps.executeUpdate();

            TransactionDB db = new TransactionDB();
            int id = db.getLatestTrans();
            db.addCancelledTrans(id, trans);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

     /**
      * for valid transaction report
      * get a list of valid transactions
      *
      * @return List<Transaction>
      */
    public static List<Transaction> getValidTransaction() {
        List<Transaction> trans = new ArrayList();
        try {
            Connection con = getCon();
            String sql = "SELECT * FROM Transaction JOIN ValidTransaction USING (id) WHERE status!='test'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
              int id = rs.getInt("id");
              Timestamp time = rs.getTimestamp("date");
              String status = rs.getString("status");
              User user = new User();
              user.setid(rs.getInt("userid"));
              String payment = rs.getString("payment");
              double paid = (double)rs.getFloat("paid");
              double change = (double)rs.getFloat("change");
              HashMap<Snack,String> snacks = getSnacks(id);

              Transaction t = new Transaction(id, time, status, user, payment, paid, change, snacks);

              trans.add(t);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return trans;
    }

    /**
     * for cancelled transaction report
     * get a list of cencelled transactions
     *
     * @return List<Transaction>
     */
   public static List<Transaction> getCancelledTransaction() {
       List<Transaction> trans = new ArrayList();
       try {
           Connection con = getCon();
           String sql = "SELECT * FROM Transaction JOIN CancelledTransaction USING (id) WHERE status!='test'";
           PreparedStatement ps = con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();

           while(rs.next()){
             int id = rs.getInt("id");
             Timestamp time = rs.getTimestamp("date");
             String status = rs.getString("status");
             User user = new User();
             user.setid(rs.getInt("userid"));
             String reason = rs.getString("reason");

             Transaction t = new Transaction(id, time, status, user, reason);

             trans.add(t);
           }
           con.close();
       } catch (Exception e) {
           System.out.println(e);
       }
       return trans;
   }

    /**
     * for snack report
     * get a list of snack with their sold quantity as string
     *
     * @return HashMap<Snack,String>
     */
    public static HashMap<Snack,String> getAllSoldSnack() {
        HashMap<Snack,String> snacks = new HashMap<Snack,String>();
        try {
            Connection con = getCon();
            String sql = "SELECT rollno, code, name, SUM(BoughtSnack.quantity) as count " +
                          "FROM Snacks JOIN " +
                          "BoughtSnack USING (rollno)" +
                          "WHERE rollno!=17" +" GROUP BY rollno, name ORDER BY rollno;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
              int rollno = rs.getInt("rollno");
              Snack s = new Snack(rollno);
              s.setCode(rs.getInt("code"));
              s.setName(rs.getString("name"));
              String quantity = Integer.toString(rs.getInt("count"));
              snacks.put(s,quantity);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return snacks;
    }




    /*
    * under here are all HELPER FUNCTIONS
    * no need to understand to use this database
    */

    /**
     * helper function
     * get the id of newly added transaction
     *
     * @return int the id of latest added transaction
     */
    public static int getLatestTrans() {
        int id = 0;
        try {
            Connection con = getCon();
            String sql = "SELECT MAX(id) AS id FROM Transaction";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
              id = rs.getInt("id");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }

    /**
     * helper function
     * add new valid transaction to the system
     *
     * @param int the id of latest added transaction
     * @param trans the transaction that needed to be added
     */
    public static void addValidTrans(int id, Transaction trans) {
        int status = 0;
        try {
            Connection con = getCon();
            String sql = "INSERT INTO ValidTransaction VALUES (?,?,?,?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, trans.getPayment());
            float paid = (float)trans.getPaid();
            float change = (float)trans.getChange();
            ps.setFloat(3, paid);
            ps.setFloat(4, change);
            status = ps.executeUpdate();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * helper function
     * add new cancelled transaction to the system
     *
     * @param int the id of latest added transaction
     * @param trans the transaction that needed to be added
     */
    public static void addCancelledTrans(int id, Transaction trans) {
        int status = 0;
        try {
            Connection con = getCon();
            String sql = "INSERT INTO CancelledTransaction VALUES (?,?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, trans.getReason());
            status = ps.executeUpdate();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * helper function
     * add bought snacks to system
     *
     * @param int the id of latest added transaction
     * @param Snack the snack bought by user
     * @param trans the quantity of bought snack
     */
    public static void addBoughtSnack(int id, Snack s, String quantity) {
        int status = 0;
        try {
            Connection con = getCon();
            String sql = "INSERT INTO BoughtSnack(id, rollno, quantity) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, s.getRollno());
            ps.setInt(3, Integer.parseInt(quantity));
            status = ps.executeUpdate();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        // return status;
    }

    /**
     * return the snacks involved with each transaction
     *
     * @return HashMap<Snack, String>
     */
    public static HashMap<Snack, String> getSnacks(int id) {
        HashMap<Snack, String> map = new HashMap<Snack, String>();
        try {
            Connection con = getCon();
            String sql = "SELECT * FROM BoughtSnack JOIN Snacks USING(rollno) WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
              Snack s = new Snack(rs.getInt("rollno"));
              s.setName(rs.getString("name"));
              s.setCategory(rs.getString("category"));
              s.setPrice(rs.getFloat("price"));
              s.setQuantity(rs.getInt("Snacks.quantity"));
              s.setCode(rs.getInt("code"));
              int quantity = rs.getInt("BoughtSnack.quantity");
              map.put(s, Integer.toString(quantity));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return map;
    }


}
