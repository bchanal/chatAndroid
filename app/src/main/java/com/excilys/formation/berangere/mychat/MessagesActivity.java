package com.excilys.formation.berangere.mychat;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.excilys.formation.berangere.mychat.Listener.MessagesListener;
import com.excilys.formation.berangere.mychat.Task.LoginTask;
import com.excilys.formation.berangere.mychat.Task.MessagesTask;
import com.excilys.formation.berangere.mychat.Task.PostTask;
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
import java.util.UUID;

/**
 * Created by berangere on 16/03/15.
 */
public class MessagesActivity extends Activity implements MessagesListener, View.OnClickListener {

    private ListView messages;
    private List<Message> list;
    private final String TAG = MessagesActivity.class.getSimpleName();

    private EditText msgEdit;
    private Button postMsg;
    private PostTask postTask;


    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_message);

        Context context = getApplicationContext();

        messages = (ListView)findViewById(R.id.listMessages);
        MessagesTask task =  new MessagesTask(this);
//        task.execute(b.getString("login", "beran"), b.getString("pwd", "schtroumpf"));
        task.execute("beran", "schtroumpf");

        msgEdit = (EditText) findViewById(R.id.msgEdit);
        postMsg = (Button)findViewById(R.id.postMsg);
        postMsg.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {

        Message m = new Message("beran", msgEdit.getText().toString(), UUID.randomUUID().toString());
        Toast.makeText(getApplicationContext(), "Envoyer", Toast.LENGTH_SHORT).show();
        if (postTask == null) {
            postTask = new PostTask();
            postTask.execute(m);
        } else if (postTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            //wait
        } else if (postTask.getStatus().equals(AsyncTask.Status.PENDING)) {
            //wait
        } else if (postTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            postTask = new PostTask();
            postTask.execute(m);
        }
    }
}
