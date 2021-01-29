package assignment2.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class UserDB {

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
     * Validate the user login
     *
     * @param string username
     * @param string password
     * @return User returns a User if the user is valid. return null if user is invalid
     */
    public static User validate(String username, String password) {
        try {
            Connection con = getCon();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM User where name=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            // Dealing with anonymous case - Added by Eric 8/11/2020 12pm
            if (username.equals("anonymous")) {
                return new User(-1, "customer");
            }

            if (rs.next()) {
                int id = rs.getInt("userid");
                String type = rs.getString("userType");
                User s = new User(id, type);
                con.close();
                return s;
            } else {
                con.close();
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * add card for users
     *
     * @param Card card
     * @param User user
     */
    public static void addCard(Card card, User user) {
        try {
            Connection con = getCon();
            PreparedStatement ps = con.prepareStatement("INSERT INTO Card (userid, name, number) VALUES (?,?,?);");
            ps.setInt(1, user.getId());
            ps.setString(2, card.getName());
            ps.setString(3, card.getNum());
            int status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Card getCard(User user){
        try{
            Connection con=getCon();
            PreparedStatement ps=con.prepareStatement("SELECT * FROM Card WHERE userid =?");
            ps.setInt(1,user.getId());
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
              String name = rs.getString("name");
              String number = rs.getString("number");
              Card card = new Card(name, number);
              con.close();
              return card;
            }else{
              con.close();
            }
        }catch(Exception e){System.out.println(e);}
        return null;
    }

    /**
     * Get the specific infomation about all users expect customer, to generate summary for owner
     * @return List of users
     */
    public static List<User> getUsernameType() {
        try {
            Connection con = getCon();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM User WHERE (UserType = 'owner') OR (UserType = 'seller') OR (UserType = 'cashier')");
            ResultSet rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs.getInt("userid"), rs.getString("name"), rs.getString("UserType")));
            }
            con.close();

            return users;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static void deleteUser(User user) {
        try {
            Connection con = getCon();
            PreparedStatement ps = con.prepareStatement("DELETE FROM User WHERE userid = ?");
            ps.setInt(1, user.getId());
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Add a new user to the database
     * @param user User to be added
     * @return The user id inserted, -1 is returned if failed
     */
    public static int addUser(User user) {
        try {
            Connection con = getCon();
            PreparedStatement ps = con.prepareStatement("INSERT INTO User (name, userType, password) VALUES (?, ?, ?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getType());
            ps.setString(3, user.getPassword());
            int status = ps.executeUpdate();
            // Insertion fails
            con.close();
            if (status == 0) {
                return -1;
            } else {
                UserDB db = new UserDB();
                return db.getUserid();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return -1;
    }

    /**
     * get newly added user
     * @return The user id inserted, -1 is returned if failed
     */
    public static int getUserid() {
        int id = 0;
        try {
            Connection con = getCon();
            PreparedStatement ps = con.prepareStatement("SELECT MAX(userid) AS userid FROM User");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Return the newly inserted user_id
                id = rs.getInt("userid");
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return id;
    }

}
