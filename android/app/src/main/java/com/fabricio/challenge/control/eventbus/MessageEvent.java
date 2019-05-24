package com.fabricio.challenge.control.eventbus;

/**
 * Message event controller. Each message must have a MessageCode and any number of arguments needed.
 * Each argument must be well defined and parsed properly in each callback.
 * @author Fabricio Godoi
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
