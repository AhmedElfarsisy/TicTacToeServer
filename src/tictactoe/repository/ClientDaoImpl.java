package tictactoe.repository;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;
import tictactoe.model.User;
import tictactoe.network.model.NWResponse;
import tictactoe.network.model.ResponseStatus;

public class ClientDaoImpl implements ClientDao {

    private static ClientDaoImpl shared;
    ResultSet rs;
    Connection con;
    PreparedStatement pst;

    private ClientDaoImpl() {

        try {

            DriverManager.registerDriver(new ClientDriver());

            con = DriverManager.getConnection("jdbc:derby://localhost:1527/dao", "dao", "dao");
            //  jdbc:derby:C:/Users/Mostafa Abdalla/AppData/Roaming/NetBeans/Derby/dao;create=true
        } catch (SQLNonTransientConnectionException ex) {
            // Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {

        }

    }

    public static ClientDaoImpl getInstance() {   //return the object of the Dao
        if (shared == null) {
            shared = new ClientDaoImpl();
        }
        return shared;

    }

    public NWResponse loginPlayer(String userName, String pass) {   // know if the player exsist in the database return 0 if the player dosent exsist return 1 if he exsist
        int s = 0;
        User user;
        NWResponse response;
        int result = 1;
        ResultSet r = null;
        PreparedStatement pst;
        try {

            pst = con.prepareStatement("select * from players where username=? and password=?");
            pst.setString(1, userName);
            pst.setString(2, pass);
            r = pst.executeQuery();
            r.next();
            s = r.getRow();
        } catch (SQLException ex) {
            response = new NWResponse(null, ResponseStatus.FAILURE, ex.getMessage());
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (s == 0) {
            response = new NWResponse(null, ResponseStatus.FAILURE, "User Not Exist");

        } else {
            try {
                user = new User(userName, r.getInt("score"));
                response = new NWResponse(user, ResponseStatus.SUCCESS, "Logged in successfully");

            } catch (SQLNonTransientConnectionException ex) {
                response = new NWResponse(null, ResponseStatus.FAILURE, "something went wrong try again later");

            } catch (SQLException ex) {
                response = new NWResponse(null, ResponseStatus.FAILURE, "something went wrong try again later");
            }
        }
        return response;
    }

    public NWResponse signupPlayer(String userName, String pass) { // insert a player in database  return 1 if the insert sucssed return 0 if the username duplicated 
        User user;
        NWResponse response;
        int s = 0;
        int result = 1;
        ResultSet r = null;
        PreparedStatement pst = null;

        try {
            pst = con.prepareStatement("INSERT INTO players VALUES (?,?,?)");
            pst.setString(1, userName);
            pst.setString(2, pass);
            pst.setInt(3, 0);
            pst.executeUpdate();
            user = new User(userName, 0);
            response = new NWResponse(user, ResponseStatus.SUCCESS, "Registered Successfully");

        } catch (SQLIntegrityConstraintViolationException ex) {
            //ex.printStackTrace();
            response = new NWResponse(null, ResponseStatus.FAILURE, "Duplicated User Name");

        } catch (SQLNonTransientConnectionException ex) {
            response = new NWResponse(null, ResponseStatus.FAILURE, "something went wrong try again later");

        } catch (SQLException ex) {
            response = new NWResponse(null, ResponseStatus.FAILURE, "something went wrong try again later");
        }

        return response;

    }

    public int getScore(String user) {   // return the score of the player 

        ResultSet r = null;
        PreparedStatement pst;
        int s = 0;
        try {
            pst = con.prepareStatement("select score from players where username=?");
            pst.setString(1, user);
            r = pst.executeQuery();
            r.next();
            s = r.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return s;

    }

    public void changeScore(String user) {   // increace the score by 1 

        int i = getScore(user);
        i++;

        PreparedStatement pst;

        try {
            pst = con.prepareStatement("update players set Score =? where username=?");
            pst.setInt(1, i);
            pst.setString(2, user);

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<String> getPlayers() {

        ArrayList<String> arr = new ArrayList<String>();
        ResultSet r = null;
        PreparedStatement pst;
        try {
            pst = con.prepareStatement("select username from players ");
            r = pst.executeQuery();

            while (r.next()) {

                arr.add(r.getString(1));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;
    }

}
