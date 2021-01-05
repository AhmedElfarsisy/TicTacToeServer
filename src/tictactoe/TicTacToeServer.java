/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tictactoe.network.Server;

import tictactoe.presenter.Admin.AdminController;
import tictactoe.repository.ClientDaoImpl;

/**
 *
 * @author A.Elfarsisy
 */
public class TicTacToeServer extends Application {

    Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
         stage.setOnCloseRequest((WindowEvent e) -> {
            Platform.exit();
            System.exit(0);
        });
        Scene scene = new Scene((new AdminController()).getAdminView());
        stage.setScene(scene);
        stage.show();
        System.out.println(ClientDaoImpl.getInstance().getClientsCount());

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

//    private void goToAdmin() {
//        Task<Void> sleeper = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                Server s=new Server();
//                return null;
//            }
//        };
//        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//            @Override
//            public void handle(WorkerStateEvent event) {
//           
//             
//            }
//        });
////        new Thread(sleeper).start();
//    }
}
