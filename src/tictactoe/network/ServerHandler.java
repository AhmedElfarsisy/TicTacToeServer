package tictactoe.network;

import tictactoe.network.model.Request;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoe.network.model.RequestType;
import tictactoe.model.User;
import tictactoe.network.model.NWResponse;
import tictactoe.network.model.ResponseStatus;
import tictactoe.repository.ClientDaoImpl;

public class ServerHandler extends Thread {

    ObjectInputStream ois;
    ObjectOutputStream oos;
    static Vector<ServerHandler> ClientsVector = new Vector<>();
    Socket client;

    public ServerHandler(Socket sc) {
        try {
            client = sc;
            oos = new ObjectOutputStream(sc.getOutputStream());
            ois = new ObjectInputStream(sc.getInputStream());
            ServerHandler.ClientsVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Request<User> request = (Request<User>) ois.readObject();
                handleRequest(request);
            } catch (IOException ex) {
                try {
                    stop();
                    ois.close();
                    oos.close();
                    client.close();
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex1) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void handleRequest(Request request) {
        ClientDaoImpl dao = ClientDaoImpl.getInstance();
        User user;
        NWResponse response = null;

        switch (request.getType()) {

            case LOGIN:
                user = (User) request.getData();
                response = dao.loginPlayer(user.getUserName(), user.getPassword());
                break;
            case SIGNUP:
                user = (User) request.getData();
                response = dao.signupPlayer(user.getUserName(), user.getPassword());
                break;
        }
        try {
            oos.writeObject(response);
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
