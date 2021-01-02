/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.network.model;

import java.io.Serializable;

/**
 *
 * @author A.Elfarsisy
 */
public class NWResponse implements Serializable{

    private Object Data;
    private ResponseStatus status;
    private String message;

    public NWResponse() {
    }

    public NWResponse(Object Data, ResponseStatus status, String message) {
        this.Data = Data;
        this.status = status;
        this.message = message;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object Data) {
        this.Data = Data;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
