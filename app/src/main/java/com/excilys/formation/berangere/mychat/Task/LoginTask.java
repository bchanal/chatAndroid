package com.excilys.formation.berangere.mychat.Task;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import com.excilys.formation.berangere.mychat.LoginListener;
import com.excilys.formation.berangere.mychat.MessagesActivity;
import com.excilys.formation.berangere.mychat.model.Connection;
import com.excilys.formation.berangere.mychat.util.ServiceAccess;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by berangere on 16/03/15.
 */
public class LoginTask extends AsyncTask<String, Void, Boolean> {

    private LoginListener ll;

    private final String TAG = MessagesActivity.class.getSimpleName();


    public LoginTask(LoginListener listener) {
        this.ll=listener;
    }

    protected Boolean doInBackground(String... params) {
        Connection c =null;
        String name = params[0];
        String password = params[1];
        ServiceAccess sa = new ServiceAccess();
        try{
            c= sa.getConnection(name, password);
        }
        catch(Exception e){
            Log.i("loginTask", e.getMessage());
            e.printStackTrace();
        }
        if(c==null){
            Log.i(TAG, "connection null");
            return false;
        } else if(c.getStatus()==200){
            Log.i(TAG, "connection fail");
            return true;
        } else
            return false;

    }

    public void onPostExecute(Boolean aboolean) {

        if (aboolean)
            ll.onLoginSuccess();
        else
            ll.onLoginFail();
    }
}