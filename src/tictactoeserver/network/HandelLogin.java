package tictactoeserver.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import tictactoeserver.repository.ClientDao;
import tictactoeserver.repository.ClientDaoImpl;

public class HandelLogin {

    ServerSocket myServerSocket;
    Socket socket;
    DataInputStream dis;
    PrintStream ps;
    String message;
    ClientDao client = ClientDaoImpl.creatDB();

    public void handelLogin() {
        try {
            myServerSocket = new ServerSocket(5011);
            socket = myServerSocket.accept();
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

       new Thread() {
            public void run() {
                while (true) {

                    try {
                        message = dis.readLine();
                        //System.out.println(message);

                        // if(message==null){
                        //    }else{
                        int s = client.loginPlayer(message, "123");
                        if (s == 1) {
                            ps.println("found");
                            ps.flush();

                        } else {

                            ps.println("not found");
                            ps.flush();
                        }

                    } catch (SocketException ex) {

                        try {
                            dis.close();
                            ps.close();

                            break;
                        } catch (IOException ex1) {
                            System.out.println("SocketException");
                            // Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    } catch (IOException ex2) {
                        System.out.println("IOException");
                        // Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex2);
                    }

                }
            }

        }.start();
    }

    public HandelLogin() {
       
         try {
            myServerSocket = new ServerSocket(5011);
            socket = myServerSocket.accept();
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
             while(true){
                try {
                    message = dis.readLine();
                    //System.out.println(message);

                    /* if(message==null){
                            }else{*/
                    int s = client.loginPlayer(message, "123");
                    if (s == 1) {
                        ps.println("found");
                        ps.flush();

                    } else {

                        ps.println("not found");
                        ps.flush();
                    }

                } catch (SocketException ex) {

                    try {
                        dis.close();
                        ps.close();

                    } catch (IOException ex1) {
                        System.out.println("SocketException");
                        // Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (IOException ex2) {
                    System.out.println("IOException");
                    // Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex2);
                } }
             
            }

        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                //changeScene(new HomeController());
            }
        });
        new Thread(sleeper).start();

    }
    
}
