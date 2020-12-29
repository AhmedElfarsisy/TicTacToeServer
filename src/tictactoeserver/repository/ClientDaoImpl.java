
package tictactoeserver.repository;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

public class  ClientDaoImpl implements ClientDao{
    
    
    private static ClientDaoImpl dbxo;
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
        }catch ( SQLException ex){
        
        
        }

    }

    public static ClientDaoImpl creatDB() {   //return the object of the Dao
        if (dbxo == null) {
            dbxo = new ClientDaoImpl();
        }
        return dbxo;

    }
    
    
    public int loginPlayer(String user, String pass) {   // know if the player exsist in the database return 0 if the player dosent exsist return 1 if he exsist
        int s=0;
        int result = 1;
        ResultSet r = null;
        PreparedStatement pst;
        try {
            pst = con.prepareStatement("select username from players where username=? and password=?");
            pst.setString(1, user);
            pst.setString(2, pass);
            r = pst.executeQuery();
            r.next();
            s=r.getRow();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (s==0) {

            result = 0;
        } else {
            result = 1;
        }

        return result;

    }
    
    
    public int signUpPlayer(String user, String pass) { // insert a player in database  return 1 if the insert sucssed return 0 if the username duplicated 
        int s=0;
        int result = 1;
        ResultSet r = null;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("select username from players where username=?");
            pst.setString(1, user);
            r = pst.executeQuery();
            r.next();
            s=r.getRow();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        if ( s== 0) {

            try {
                pst = con.prepareStatement("INSERT INTO players VALUES (?,?,?)");
                pst.setString(1, user);
                pst.setString(2, pass);
                pst.setInt(3, 0);
                
                pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            result = 0;

        }

        return result;

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
