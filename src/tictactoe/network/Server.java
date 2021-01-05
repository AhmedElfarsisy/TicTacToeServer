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
    boolean isRunning = false;
    Thread th;
    public Server() {
        try {
            serverSocket = new ServerSocket(5006);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startServer() {
        th = new Thread(() -> {
            isRunning = true;
            while (isRunning) {
                try {
                    socket = serverSocket.accept();
                    System.out.println("Accept");
                    new ServerHandler(socket);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        th.start();
    }

    public void stopServer() {
        isRunning = false;
        try {
            ServerHandler.clientsVector.forEach((cl) -> {
//                cl.closeConnection();
                cl.currentUser=null;
            });
            ServerHandler.clientsVector.clear();
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
