package dev.dubhe.brace.events.message;

import dev.dubhe.brace.base.TextChannel;
import dev.dubhe.brace.events.Cancellable;
import dev.dubhe.brace.events.Event;
import dev.dubhe.brace.utils.chat.Component;

public class MessageSendPrevEvent extends Event implements Cancellable {
    private Component component;
    private boolean canceled = false;

    public MessageSendPrevEvent(Component component, TextChannel channel) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }

    public void changeComponent(Component component) {
        this.component = component;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }
}
