package com.excilys.formation.berangere.mychat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.widget.ListView;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by berangere on 16/03/15.
 */
public class MessagesActivity extends Activity {

    private ListView messages;
    private List<Message> list;
    private final String TAG = MessagesActivity.class.getSimpleName();


    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_message);

        Context context = getApplicationContext();

        messages.findViewById(R.id.activity_message);
        messages.setAdapter(new MessagesAdapter(list, context));

        List<Message> staticMessages = Arrays.asList(
                new Message("beran", "ça va trop viiiite !"),
                new Message("beran", "bonjour")
        );

    }


    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            String name = uri[0];
            String password = uri[1];

            response = httpclient.execute(new HttpGet("http://training.loicortola.com/parlez-vous-android/connect/" + name + "/" + password));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                Log.i(TAG, response.toString());
                responseString = out.toString();
                out.close();
            } else {
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (IOException e) {
            //TODO Handle problems..
        }
        Log.i(TAG, responseString);
        return responseString;
    }

    protected void envoyerMessage(Message m) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            String name = m.getAuthor();
            String message = m.getMessage();
            String password="schtroumpf";
            response = httpclient.execute(new HttpPut("http://training.loicortola.com/parlez-vous-android/message/" + name + "/" + password));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else {
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (IOException e) {
            //TODO Handle problems..
        }
    }

}
