package tictactoe.model;




import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author A.Elfarsisy
 */
public class User implements Serializable {
   private String userName; 
    private String password; 
    private Integer score; 

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, Integer score) {
        this.userName = userName;
        this.password = password;
        this.score = score;
    }
    public User(String userName, Integer score) {
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
