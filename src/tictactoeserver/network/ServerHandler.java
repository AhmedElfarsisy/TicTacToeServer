package tictactoeserver.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Logger;

import tictactoeserver.repository.User;

public class ServerHandler extends Thread {

    String userName;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    static Vector<ServerHandler> clientsVector = new Vector<>();
    Socket client;

    public ServerHandler(Socket cs) {
        try {
            System.out.println("Entered Server Handler");
            client = cs;
            ois = new ObjectInputStream(cs.getInputStream());
            oos = new ObjectOutputStream(cs.getOutputStream());
            //ServerHandler.clientsVector.add(this);
           
            System.out.println("Start Server Handler");
            this.start();
           
            //String s = ServerHandler.clientsVector.get(0).userName;
        } catch (IOException ex) {
            // Logger.getLogger(ChatServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        while (true) {

            try {
                // Object object = ois.readObject();
                // String className  = object.getClass().toString();

                Request<Object> request = (Request<Object>) ois.readObject();
                System.out.print(request.getType());
                handleRequest(request);
                // sendMessageToAll("found");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SocketException se) {
                try {
                    stop();
                    ois.close();
                    oos.close();
                    ServerHandler.clientsVector.remove(this);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*void sendMessageToAll(String msg) {
        for (ChatHandler ch : clientsVector) {
            ch.ps.println(client.getPort() + " : " + msg);
        }
    }*/
    private void handleRequest(Request request) {
        switch (request.getType()) {
            case LOGIN:
                handleLoginRequest((User) request.getData());
                break;
            case SIGNUP:
                handleSignupRequest((User) request.getData());
                break;
        }
    }

    private void handleLoginRequest(User user) {
        System.out.print(user.getUserName() + user.getPassword());
    }

    private void handleSignupRequest(User user) {
        System.out.print(user.getUserName() + user.getPassword());
    }
}
