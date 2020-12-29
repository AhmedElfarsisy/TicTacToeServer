
package tictactoeserver.repository;

import java.util.ArrayList;

// interface for the DAO 
public interface ClientDao{
    
   
   public int loginPlayer(String user, String pass);
   public int signUpPlayer(String user, String pass);
   public int getScore(String user);
   public void changeScore(String user);
   public ArrayList<String> getPlayers();
}
