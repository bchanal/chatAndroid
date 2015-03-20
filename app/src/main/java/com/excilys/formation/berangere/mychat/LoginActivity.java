package com.excilys.formation.berangere.mychat;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;


public class LoginActivity extends ActionBarActivity implements LoginListener, View.OnClickListener {

    private final String TAG = LoginActivity.class.getSimpleName();
    private EditText name;
    private EditText password;
    private Button cancel;
    private Button send;
    private LoginTask loginTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        UUID.randomUUID();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i(TAG, "onCreate");

        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        cancel = (Button) findViewById(R.id.vider);
        send = (Button) findViewById(R.id.envoyer);

        cancel.setOnClickListener(this);
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
//            }
//        });

        send.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onLoginSuccess() {
        Toast.makeText(getApplicationContext(), "connecté", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFail() {
        Toast.makeText(getApplicationContext(), "echec de connection", Toast.LENGTH_SHORT).show();

    }

    public void onProgressUpdate() {
        Toast.makeText(getApplicationContext(), "running", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.envoyer: {
                Toast.makeText(getApplicationContext(), "Envoyer", Toast.LENGTH_SHORT).show();
                if (loginTask == null) {
                    loginTask = new LoginTask(this);
                    loginTask.execute(name.getText().toString(), password.getText().toString());
                } else if (loginTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
                    //wait
                } else if (loginTask.getStatus().equals(AsyncTask.Status.PENDING)) {
                    //wait
                } else if (loginTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
                    loginTask = new LoginTask(this);
                    loginTask.execute();
                }

                new LoginTask(this).execute();

                startActivity(new Intent(getApplicationContext(), MessagesActivity.class));
            }
            case R.id.vider: {

                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();

            }

        }

    }
}

