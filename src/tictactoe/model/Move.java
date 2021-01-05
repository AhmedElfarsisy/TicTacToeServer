/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.model;

import java.io.Serializable;

/**
 *
 * @author yasmineghazy
 */
public class Move implements Serializable{
    private int x;
    private int y;

    public Move() {
        x = -1;
        y = -1;
    }
    
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    } 
}
