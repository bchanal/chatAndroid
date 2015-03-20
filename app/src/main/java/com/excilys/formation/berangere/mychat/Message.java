package com.excilys.formation.berangere.mychat;

/**
 * Created by berangere on 16/03/15.
 */
public class Message {

    private String author;
    private String message;

    public Message(String a, String m) {

        this.author = a;
        this.message = m;
    }

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
}
