/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.network.model;

import java.io.Serializable;

public enum RequestType implements Serializable {
    
    LOGIN,   //user  username password 
    SIGNUP,   //  user  username password 
    CONNECT,   // user   username
    REQUESTGAME, //  user  username anotherplayer
    ACCEPTGAME,  //  user  username anotherplayer
    REJECTGAME,
    PLAYGAME,
    
    ENDGAME,
}
