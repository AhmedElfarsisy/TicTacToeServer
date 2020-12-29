/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.presenter.Admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import tictactoeserver.repository.ClientDao;
import tictactoeserver.repository.ClientDaoImpl;


/**
 *
 * @author yasmineghazy
 */

//
public class AdminController implements Initializable {
    boolean flag=true;
    //MARK: - Properties
    private AdminViewBase adminView;
    // create object from Dao
    
    ClientDao client=ClientDaoImpl.creatDB();
    
    //MARK: - Constructor
    public AdminController(){
        
        //create new view
        
        adminView = new AdminViewBase();
        adminView.startBtn.setOnAction((ActionEvent event) -> {
            
           
           if(flag){ 
           adminView.startBtn.setText("Stop");
           flag=!flag;
           
           
           }else{
           adminView.startBtn.setText("start");
           flag=!flag;
           
           
           
           }         
        });
        setupBieChart(100, 120, 300);
         
    }

    
    //MARK: - Implement Initializable callback method
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
   
    }  
    
    //MARK: - Getters
    public AdminViewBase getAdminView(){
        return  adminView;
    }   
    
    
    //MARK: - Methods
    public void setupBieChart(Integer offline, Integer onlineNotPlaying, Integer onlinePlaying){
        
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Offline", 13),
                new PieChart.Data("Online Not Playing", 25),
                new PieChart.Data("Online Playing", 10));
        adminView.playersPieChart.setData(pieChartData);  
    }

    
}
