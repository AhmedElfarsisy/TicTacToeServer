/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.network.model;

import java.io.Serializable;
import tictactoe.model.Move;
import tictactoe.model.User;

/**
 *
 * @author A.Elfarsisy
 */
public class GameModel implements Serializable {

    private User player1;
    private User player2;
    private Move move;

    public GameModel(User player1, User player2, Move move) {
        this.player1 = player1;
        this.player2 = player2;
        this.move = move;
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public boolean isPlayer(User user) {
        return user.getUserName().equals(player1.getUserName()) || user.getUserName().equals(player2.getUserName());
    }

}
