package com.excilys.formation.berangere.mychat.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by berangere on 16/03/15.
 */
public class Message {
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("login")
    private String author;
    @SerializedName("message")
    private String message;


    public Message(String a, String m, String uuid) {

        this.author = a;
        this.message = m;
        this.uuid = uuid;
    }


    public String getUuid(){ return uuid;}

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUuid(String uuid) { this.uuid = uuid;}
}
