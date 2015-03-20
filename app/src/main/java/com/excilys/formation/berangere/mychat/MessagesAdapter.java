package com.excilys.formation.berangere.mychat;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by berangere on 16/03/15.
 */
public class MessagesAdapter extends BaseAdapter {

    private List<Message> messages;
    private Context contexte;


    public MessagesAdapter(List<Message> list, Context context) {

        this.messages = list;
        this.contexte = context;
    }


    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(contexte)
                    .inflate(R.layout.single_message, parent, false);
        }

        TextView authorView = (TextView) convertView.findViewById(R.id.author);
        TextView messageView = (TextView) convertView.findViewById(R.id.message);

        authorView.setText(messages.get(position).getAuthor());
        messageView.setText(messages.get(position).getMessage());

        return convertView;
    }
}
