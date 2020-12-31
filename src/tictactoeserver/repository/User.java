/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.repository;

import java.io.Serializable;


/**
 *
 * @author A.Elfarsisy
 */
public class User implements Serializable {
    String userName; 
    String password; 
    int score; 
    int state; 

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, int score,int state) {
        this.userName = userName;
        this.password = password;
        this.score = score;
        this.state = state;
    }
    public User(String userName, int score) {
        this.userName = userName;
        this.score = score;
        
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
