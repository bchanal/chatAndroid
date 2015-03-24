package com.excilys.formation.berangere.mychat.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by berangere on 24/03/15.
 */
public class Connection {

    // récupère le message de connexion$

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
