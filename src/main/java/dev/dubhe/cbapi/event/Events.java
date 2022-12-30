package dev.dubhe.cbapi.event;

import dev.dubhe.cbapi.base.TextMessage;

public class Events {
    public static final Event<MessageContext> NEW_MESSAGE = new Event<>();

    public record MessageContext(TextMessage msg) {}
}
