
package tictactoe.repository;

import java.util.ArrayList;
import tictactoe.model.User;
import tictactoe.network.model.NWResponse;

// interface for the DAO 
public interface ClientDao{
    
   
   public NWResponse loginPlayer(String user, String pass);
   public NWResponse signupPlayer(String user, String pass);
   public int getScore(String user);
   public void changeScore(String user);
   public ArrayList<String> getPlayers();
}
