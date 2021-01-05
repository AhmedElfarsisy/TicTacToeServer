package tictactoe.network;

import tictactoe.network.model.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoe.model.User;
import tictactoe.network.model.NWResponse;
import tictactoe.network.model.ResponseStatus;
import tictactoe.repository.ClientDaoImpl;
import tictactoe.network.model.GameModel;
import tictactoe.network.model.RequestType;
import tictactoe.presenter.Admin.AdminController;

public class ServerHandler extends Thread {

    ObjectInputStream ois;
    ObjectOutputStream oos;
    User currentUser;
    static Vector<ServerHandler> clientsVector = new Vector<>();
    static Vector<GameModel> gamesVector = new Vector<>();
    Socket client;

    public ServerHandler(Socket sc) {
        try {
            client = sc;

            oos = new ObjectOutputStream(sc.getOutputStream());
            ois = new ObjectInputStream(sc.getInputStream());
            ServerHandler.clientsVector.add(this);
            start();
        } catch (IOException ex) {
            closeConnection();
        }

    }

    Boolean isClientExist(String userName) {
        Boolean isExist = false;
        for (ServerHandler cl : ServerHandler.clientsVector) {
            if (cl.currentUser != null && cl.currentUser.getUserName().equals(userName)) {
                isExist = true;
            }
        }
        return isExist;
    }

    private void setCurrentUser(User currentUser) {
        if (!isClientExist(currentUser.getUserName())) {
            this.currentUser = currentUser;
            updatePlayers();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request<User> request = (Request<User>) ois.readObject();
                handleRequest(request);
            } catch (SocketException se) {
                System.out.println("Socket 1   " + se.getMessage());
                closeConnection();
            } catch (IOException ex) {
                System.out.println("IO Exep   " + ex.getMessage());
                closeConnection();
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void handleRequest(Request request) {
        ClientDaoImpl dao = ClientDaoImpl.getInstance();
        User user;
        NWResponse response;
        System.out.println("\n\n\n================================\n"
                + "SERVER : request recieved: "
                + request.getType() + ""
                + ""
                + "\n==============================\n");
        try {
            switch (request.getType()) {
                case LOGIN:
                    user = (User) request.getData();
                    System.out.println("trying to log in ");
                    response = dao.loginPlayer(user.getUserName(), user.getPassword());
                    System.out.println(response.getStatus());
                    setCurrentUser((User) response.getData());
                    oos.writeObject(response);
                    oos.flush();
                    break;
                case SIGNUP:
                    user = (User) request.getData();
                    response = dao.signupPlayer(user.getUserName(), user.getPassword());
                    setCurrentUser((User) response.getData());
                    oos.writeObject(response);
                    oos.flush();
                    break;
                case GETONLINEPLAYERS:
                    updatePlayers();
                    break;
                case LOGOUT:
                    currentUser = null;
                    break;
                case PLAYGAME:
                    handlePlayGame(request);
                    break;
                case ACCEPTGAME:
                    GameModel game = new GameModel((User) request.getData(), currentUser, null);
                    System.out.println("Player1: " + currentUser.getUserName() + "Player2: " + ((User) request.getData()).getUserName());
                    gamesVector.add(game);
                    updatePlayers();

                case REQUESTGAME:
                case REJECTGAME:
                    User player = (User) request.getData();
                    ServerHandler cl = getHandler(player);
                    Request req = new Request(request.getType(), currentUser);
                    cl.sendRequest(req);
                    break;
                case ENDGAME:
                    Integer state = (Integer) request.getData();
                    if (state != -1) {
                        ClientDaoImpl.getInstance().changeScore(currentUser.getUserName());
                        int index = clientsVector.indexOf(this);
                        clientsVector.get(index).currentUser.setScore(currentUser.getScore()+1);
                    }
                    //Remove game from game array
                    removeGame();
                    updatePlayers();
                    break;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            closeConnection();
        }
    }

    private void removeGame() {
        int index = -1;
        for (int i = 0; i < gamesVector.size(); i++) {
            if (gamesVector.get(i).isPlayer(currentUser)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            gamesVector.remove(index);
        }

    }

    private ArrayList<User> getGamePlayers() {
        ArrayList<User> gamePlayers = new ArrayList<>();
        gamesVector.forEach((game) -> {
            gamePlayers.add(game.getPlayer1());
            gamePlayers.add(game.getPlayer2());
        });
        return gamePlayers;
    }

    private ArrayList<User> getOnlinePlayers() {
        ArrayList<User> onlinePlayers = new ArrayList<>();
        clientsVector.forEach((cl) -> {
            if (cl.currentUser != null) {
                //!cl.currentUser.getUserName().equals(this.currentUser.getUserName())
                System.out.println(cl.currentUser.getUserName());
                onlinePlayers.add(cl.currentUser);
            }
        });
        return onlinePlayers;
    }

    private ArrayList<User> getAvailablePlayers() {
        ArrayList available = new ArrayList();
        //available.removeAll(getGamePlayers());
        for (User onlinePlayer : getOnlinePlayers()) {
            boolean found = false;
            for (User gamePlayer : getGamePlayers()) {

                if (gamePlayer.getUserName().equals(onlinePlayer.getUserName())) {
                    found = true;
                }

            }
            if (!found) {
                available.add(onlinePlayer);
            }
        }
        return available;
    }

    public static int getOnlinePlayersCount() {
        int count = 0;
        for (ServerHandler cl : clientsVector) {
            if (cl.currentUser != null) {
                count++;
            }
        }
        return count;

    }

    private void handlePlayGame(Request request) {
        ServerHandler cl;
        GameModel gameModel = (GameModel) request.getData();
        if (currentUser.getUserName().equals(gameModel.getPlayer1().getUserName())) {
            cl = getHandler(gameModel.getPlayer2());
        } else {
            cl = getHandler(gameModel.getPlayer1());
        }
        cl.sendRequest(request);

    }

    private ServerHandler getHandler(User user) {

        for (ServerHandler cl : clientsVector) {
            if (cl.currentUser.getUserName().equals(user.getUserName())) {
                return cl;
            }
        }
        return null;
    }

    public void sendRequest(Request request) {
        try {
            oos.writeObject(request);
            oos.flush();

        } catch (SocketException se) {
            System.out.println(se.getMessage());
            closeConnection();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            closeConnection();
        }
    }

    public void closeConnection() {
        System.out.println("SERVER: Client connection closed");
        try {
            ois.close();
            oos.close();
            client.close();
            currentUser = null;
            boolean remove = clientsVector.remove(this);
            System.out.println(" is Client Removerd :: " + remove);
            updatePlayers();
            stop();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updatePlayers() {

        AdminController.updatePieChart();

        ArrayList list = getAvailablePlayers();
        clientsVector.forEach((cl) -> {
            if (cl.currentUser != null) {
                try {
                    ArrayList l = (ArrayList) list.clone();
                    l.remove(cl.currentUser);
                    Request request = new Request<>(RequestType.UPDATEONLINEPLAYERS, l);
                    cl.oos.writeObject(request);
                    cl.oos.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

    }
}
