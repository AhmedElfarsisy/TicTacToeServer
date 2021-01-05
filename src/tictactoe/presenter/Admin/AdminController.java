/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.presenter.Admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import tictactoe.network.Server;
import tictactoe.network.ServerHandler;
import tictactoe.repository.ClientDao;
import tictactoe.repository.ClientDaoImpl;

/**
 *
 * @author yasmineghazy
 */
//
public class AdminController implements Initializable {

    boolean isActive = false;
    //MARK: - Properties
    private AdminViewBase adminView;
    // create object from Dao

    ClientDao client = ClientDaoImpl.getInstance();
    static ObservableList<PieChart.Data> pieChartData;
    static PieChart.Data offlineData;
    static PieChart.Data onlineData;
    private Server server;

    //MARK: - Constructor
    public AdminController() {

        //create new view
        adminView = new AdminViewBase();
        server = new Server();
        adminView.startBtn.setOnAction((ActionEvent event) -> {
            isActive = !isActive;
            if (isActive) {
                adminView.startBtn.setText("Stop");
                server.startServer();
            } else {
                adminView.startBtn.setText("Start");
                server.stopServer();
            }
        });

        initPieChart();
        updatePieChart();
        adminView.playersPieChart.setData(pieChartData);
    }

    public void initPieChart() {
        offlineData = new PieChart.Data("Offline ", 0);
        onlineData = new PieChart.Data("Online ", 0);

        pieChartData = FXCollections.observableArrayList(offlineData, onlineData);
    }

    //MARK: - Implement Initializable callback method
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    //MARK: - Getters
    public AdminViewBase getAdminView() {
        return adminView;
    }

    //MARK: - Methods
    public static void updatePieChart() {
        int allPlayersCount = ClientDaoImpl.getInstance().getClientsCount();
        int onlinePlayersCount = ServerHandler.getOnlinePlayersCount();
        int offlinePlayersCount = allPlayersCount - onlinePlayersCount;
        offlineData.setPieValue(offlinePlayersCount);
        onlineData.setPieValue(onlinePlayersCount);

    }

}
