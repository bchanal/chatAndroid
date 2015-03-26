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
import com.excilys.formation.berangere.mychat.model.User;

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
    private final String TAG = this.getClass().getSimpleName();

    private EditText msgEdit;
    private Button postMsg;
    private Button refresh;
    private PostTask postTask;
    private MessagesTask msgTask;


    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_message);

        messages = (ListView)findViewById(R.id.listMessages);
        MessagesTask task =  new MessagesTask(this);
        task.execute(User.name, User.password);

        msgEdit = (EditText) findViewById(R.id.msgEdit);
        postMsg = (Button)findViewById(R.id.postMsg);
        refresh = (Button)findViewById(R.id.refresh);

        postMsg.setOnClickListener(this);
        refresh.setOnClickListener(this);

    }


    @Override
    public void getAllMessages(List<Message> list) {
        MessagesAdapter mAdapt = new MessagesAdapter(list, this);
        messages.setAdapter(mAdapt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.postMsg: {
                Message m = new Message(User.name, msgEdit.getText().toString(), UUID.randomUUID().toString());
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
                    msgEdit.setText("");

                }
            }
            case R.id.refresh : {
                if (msgTask == null) {

                    msgTask = new MessagesTask(this);
                    msgTask.execute(User.name, User.password);
                }else if (msgTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                    //wait
                } else if (msgTask.getStatus().equals(AsyncTask.Status.PENDING)) {
                    //wait
                } else if (msgTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
                    msgTask = new MessagesTask(this);
                    msgTask.execute(User.name, User.password);
                }
            }
        }
    }
}
