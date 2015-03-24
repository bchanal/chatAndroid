package com.excilys.formation.berangere.mychat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.widget.ListView;


import com.excilys.formation.berangere.mychat.Task.MessagesTask;
import com.excilys.formation.berangere.mychat.model.Message;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by berangere on 16/03/15.
 */
public class MessagesActivity extends Activity implements MessagesListener {

    private ListView messages;
    private List<Message> list;
    private final String TAG = MessagesActivity.class.getSimpleName();


    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_message);

        Context context = getApplicationContext();

        messages = (ListView)findViewById(R.id.listMessages);
        MessagesTask task =  new MessagesTask(this);
//        task.execute(b.getString("login", "beran"), b.getString("pwd", "schtroumpf"));
        task.execute("beran", "schtroumpf");

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
            e.printStackTrace();
            Log.i(TAG, e.getMessage());}
    }

    @Override
    public void getAllMessages(List<Message> list) {
        MessagesAdapter mAdapt = new MessagesAdapter(list, this);
        messages.setAdapter(mAdapt);
    }
}
