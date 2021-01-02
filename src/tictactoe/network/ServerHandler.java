package tictactoe.network;

import tictactoe.network.model.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoe.model.User;
import tictactoe.network.model.NWResponse;
import tictactoe.network.model.RequestType;
import tictactoe.network.model.ResponseStatus;
import tictactoe.repository.ClientDaoImpl;

public class ServerHandler extends Thread {

    ObjectInputStream ois;
    ObjectOutputStream oos;
    User user;
    static Vector<ServerHandler> clientsVector = new Vector<>();
    Socket client;

    public ServerHandler(Socket sc) {
        try {
            client = sc;
            oos = new ObjectOutputStream(sc.getOutputStream());
            ois = new ObjectInputStream(sc.getInputStream());
            ServerHandler.clientsVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Boolean isClientExist(String userName) {
        Boolean isExist = false;
        for (ServerHandler cl : ServerHandler.clientsVector) {
            if (cl.user != null && cl.user.getUserName().equals(userName)) {
                isExist = true;
            }
        }
        return isExist;
    }

    private void setUser(User user) {
        if (!isClientExist(user.getUserName())) {
            this.user = user;
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
                System.out.println("trying to log in ");
                response = dao.loginPlayer(user.getUserName(), user.getPassword());
//                
//                System.out.println(response.getStatus());

                break;
            case SIGNUP:
                user = (User) request.getData();
                response = dao.signupPlayer(user.getUserName(), user.getPassword());
                break;
            case GETONLINEPLAYERS:
                user = (User) request.getData();
                setUser(user);
                response = new NWResponse(getOnlinePlayers(), ResponseStatus.SUCCESS, "");
                break;
            case LOGOUT:
                removeClient();
                break;
        }
        try {
            if (request.getType() != RequestType.LOGOUT) {
                oos.writeObject(response);
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<User> getOnlinePlayers() {
        ArrayList<User> onlinePlayers = new ArrayList<>();
        clientsVector.forEach((cl) -> {
            if (cl.user != null && cl.user != this.user) {
                onlinePlayers.add(cl.user);
            }
        });
        return onlinePlayers;
    }

    private void removeClient() {
        try {
            NWResponse response = new NWResponse(null, ResponseStatus.SUCCESS, "Logged out");
            oos.writeObject(response);
//            oos.close();
//            ois.close();
//            client.close(); 
//            stop();
            ServerHandler.clientsVector.remove(this);
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
