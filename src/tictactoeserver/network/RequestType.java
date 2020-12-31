/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.network;

public enum RequestType {
    
    LOGIN,   //user  username password 
    SIGNUP,   //  user  username password 
    CONNECT,   // user   username
    REQUESTGAME, //  user  username anotherplayer
    ACCEPTGAME,  //  user  username anotherplayer
    REJECTGAME,
    PLAYGAME,
    
    ENDGAME,
}
