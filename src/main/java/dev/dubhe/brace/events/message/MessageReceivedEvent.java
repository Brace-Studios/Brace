package dev.dubhe.brace.events.message;

import dev.dubhe.brace.base.TextChannel;
import dev.dubhe.brace.base.Message;

public class MessageReceivedEvent extends MessageEvent {
    public MessageReceivedEvent(Message message, TextChannel channel) {
        super(message, channel);
    }
}
