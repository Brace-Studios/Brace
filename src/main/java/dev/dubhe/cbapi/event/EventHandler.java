package dev.dubhe.cbapi.event;

public class EventHandler {
    public static void register() {
        Events.NEW_MESSAGE.inter(EventHandler::newMsg);
    }

    public static void newMsg(Events.MessageContext context) {
        context.msg().getMsg().getString();
    }
}
