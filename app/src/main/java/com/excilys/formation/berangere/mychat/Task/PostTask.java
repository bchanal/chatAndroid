package com.excilys.formation.berangere.mychat.Task;

import android.os.AsyncTask;
import android.util.Log;

import com.excilys.formation.berangere.mychat.model.Connection;
import com.excilys.formation.berangere.mychat.model.Message;
import com.excilys.formation.berangere.mychat.util.ServiceAccess;

/**
 * Created by berangere on 26/03/15.
 */
public class PostTask extends AsyncTask<Message, Void, Boolean> {

    private final String TAG = this.getClass().getSimpleName();


    @Override
    protected Boolean doInBackground(Message... params) {
        ServiceAccess sa = new ServiceAccess();
        Boolean success =  sa.postMessage(params[0]);
        return success;

    }
}
