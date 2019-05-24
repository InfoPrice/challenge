package com.fabricio.challenge.control.eventbus;

/**
 * Created by Alisson Godoi on 17/10/2017.
 * Updated by Fabricio Godoi on 04/03/2019.
 */

public class MessageEvent {

    public final MessageCode what;
    public final Object[] args;

    /**
     * Send a message to the activity based in the code description
     * @param what unique value that represents a action to be executed in the activity, see MessageCode.java for more information
     * @param args one or more arguments used in the message
     */
    public MessageEvent(MessageCode what, Object... args) {
        this.what = what;
        this.args = args;
    }
}
