package com.excilys.formation.berangere.mychat.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.excilys.formation.berangere.mychat.model.Connection;
import com.excilys.formation.berangere.mychat.model.Message;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

/**
 * Created by berangere on 24/03/15.
 */
public class ServiceAccess {

    Gson gson;

    public ServiceAccess() {
        this.gson = new Gson();
    }

    public Connection getConnection(String login, String pwd) {
        InputStream is;
        Connection c = new Connection();

        HttpClient client = new DefaultHttpClient();
        HttpGet requete = new HttpGet("http://training.loicortola.com/parlez-vous-android/connect/" + login + "/" + pwd);
        try {
            HttpResponse response = client.execute(requete);
            is = response.getEntity().getContent();
            if (is != null) {
                InputStreamReader reader = new InputStreamReader(is);
                c = gson.fromJson(reader, Connection.class);
            } else {
                c.setStatus(400);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return c;
    }

    public List<Message> getAllMessages() {
        // anciennement : param login et pwd
        List<Message> list = new ArrayList<Message>();
        InputStream is;

        HttpClient client = new DefaultHttpClient();
//        HttpGet requete = new HttpGet("http://training.loicortola.com/parlez-vous-android/messages/"+login+"/"+password);
        HttpGet requete = new HttpGet("http://training.loicortola.com/parlez-vous-android/message/beran/schtroumpf");

        try {
            HttpResponse response = client.execute(requete);
            is = response.getEntity().getContent();
            if (is != null) {
                InputStreamReader reader = new InputStreamReader(is);
                Type type = new TypeToken<List<Message>>() {
                }.getType();
                list = gson.fromJson(reader, type);
            } else {
                list.add(1, new Message("beran", "message loading failed !", UUID.randomUUID().toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Boolean postMessage(Message m) {

        Boolean success = false;

        HttpClient client = new DefaultHttpClient();
        HttpPut requete = new HttpPut("http://training.loicortola.com/parlez-vous-android/message/beran/schtroumpf");
        requete.setHeader(HTTP.CONTENT_TYPE, "application/json");

        try {
            StringEntity stringEntity = new StringEntity(gson.toJson(m));
            requete.setEntity(stringEntity);
            HttpResponse response = client.execute(requete);
            if (response != null) {
                Log.i("beran", "message envoyé");
                success= true;
            } else {
                Log.i("beran", "message non envoyé");
                success =  false;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }
}
