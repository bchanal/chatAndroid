package com.excilys.formation.berangere.mychat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.excilys.formation.berangere.mychat.model.Message;

import java.util.List;

/**
 * Created by berangere on 16/03/15.
 */
public class MessagesAdapter extends BaseAdapter {

    private List<Message> messages;
    private LayoutInflater lInflater;


    public MessagesAdapter(List<Message> list, Context context) {

        this.messages = list;
        this.lInflater = LayoutInflater.from(context);
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
            convertView = lInflater.inflate(R.layout.single_message, null);
        }

        TextView authorView = (TextView) convertView.findViewById(R.id.author);
        TextView messageView = (TextView) convertView.findViewById(R.id.message);

        authorView.setText(messages.get(position).getAuthor());
        messageView.setText(messages.get(position).getMessage());

        return convertView;
    }
}
