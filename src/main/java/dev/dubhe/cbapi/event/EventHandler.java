package dev.dubhe.cbapi.event;

import dev.dubhe.cbapi.base.TextMessage;

public class EventHandler {
    public static void register() {
        Events.NEW_MESSAGE.inter(EventHandler::newMsg);
    }

    public static void newMsg(TextMessage msg) {
        msg.getMsg().getString();
    }
}
