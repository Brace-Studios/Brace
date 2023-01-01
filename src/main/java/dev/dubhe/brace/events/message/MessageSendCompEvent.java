package dev.dubhe.brace.events.message;

import dev.dubhe.brace.base.TextChannel;
import dev.dubhe.brace.base.Message;

public class MessageSendCompEvent extends MessageEvent {
    public MessageSendCompEvent(Message message, TextChannel channel) {
        super(message, channel);
    }
}
