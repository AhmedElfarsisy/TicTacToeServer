/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.network;

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
    Socket socket;

    public Server() {
        try {
            serverSocket = new ServerSocket(5006);

            while (true) {
                try {
                    socket = serverSocket.accept();
                    System.out.println("Accept");
                    new ServerHandler(socket);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
