package dev.dubhe.cbapi.command;

import dev.dubhe.cbapi.util.chat.Component;

public interface CommandSource {
    CommandSource NULL = new CommandSource() {
        public void sendMessage(Component component) {
        }

        public boolean acceptsSuccess() {
            return false;
        }

        public boolean acceptsFailure() {
            return false;
        }

        public boolean shouldInformAdmins() {
            return false;
        }
    };

    void sendMessage(Component msg);

    boolean acceptsSuccess();

    boolean acceptsFailure();

    boolean shouldInformAdmins();

    default boolean alwaysAccepts() {
        return false;
    }
}