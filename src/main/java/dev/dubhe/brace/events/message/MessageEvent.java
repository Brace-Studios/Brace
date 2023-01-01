package dev.dubhe.brace.events.message;

import dev.dubhe.brace.base.TextChannel;
import dev.dubhe.brace.base.Message;
import dev.dubhe.brace.events.Event;

public class MessageEvent extends Event {
    protected final Message message;
    protected final TextChannel channel;

    public MessageEvent(Message message, TextChannel channel) {
        this.message = message;
        this.channel = channel;
    }

    public Message getMessage() {
        return message;
    }
}
