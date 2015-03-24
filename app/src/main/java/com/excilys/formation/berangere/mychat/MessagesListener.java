package com.excilys.formation.berangere.mychat;

import com.excilys.formation.berangere.mychat.model.Message;

import java.util.List;

/**
 * Created by berangere on 24/03/15.
 */
public interface MessagesListener {

    public void getAllMessages(List<Message> list);

}
