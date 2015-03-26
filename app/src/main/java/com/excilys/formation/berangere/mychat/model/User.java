package com.excilys.formation.berangere.mychat.model;

/**
 * Created by berangere on 26/03/15.
 */
public class User {

    public static String name;
    public static String password;

    public User(String name, String pwd){
        this.name=name;
        this.password = pwd;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String pwd){
        this.password = pwd;
    }
}
