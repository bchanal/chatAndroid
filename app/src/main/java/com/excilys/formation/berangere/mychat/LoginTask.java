package com.excilys.formation.berangere.mychat;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import com.excilys.formation.berangere.mychat.LoginListener;

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
            try {
                Thread.sleep(10);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response;
                String responseString = null;
                try {
                    String name = params[0];
                    String password = params[1];

                    response = httpclient.execute(new HttpGet("http://training.loicortola.com/parlez-vous-android/connect/" + name + "/" + password));
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
                Log.i(TAG, responseString);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return true;

    }

    public void onPostExecute(Boolean aboolean) {

        if (aboolean)
            ll.onLoginSuccess();
        else
            ll.onLoginFail();
    }
}
