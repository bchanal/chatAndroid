package com.excilys.formation.berangere.mychat.Task;

import android.os.AsyncTask;

import com.excilys.formation.berangere.mychat.Listener.MessagesListener;
import com.excilys.formation.berangere.mychat.model.Message;
import com.excilys.formation.berangere.mychat.util.ServiceAccess;

import java.util.ArrayList;
import java.util.List;

public class MessagesTask extends AsyncTask<String, Void, List<Message>>  {

    private MessagesListener ml;

    public MessagesTask(MessagesListener listener) {
        this.ml=listener;
    }

    protected List<Message> doInBackground(String... params) {
        List<Message> list =new ArrayList<Message>();
//        String name = params[0];
//        String password = params[1];
        ServiceAccess sa = new ServiceAccess();
//        list= sa.getAllMessages(name, password);
        list= sa.getAllMessages();

        return list;

    }

    public void onPostExecute(List<Message> list) {

        ml.getAllMessages(list);
    }
}
