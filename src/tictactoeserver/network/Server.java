/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mostafa Abdalla
 */
public class Server {

    ServerSocket serverSocket;
    public Server(){
        try {
            serverSocket = new ServerSocket(5005);
            while(true){
                Socket s = serverSocket.accept();
                new ServerHandler(s);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}


