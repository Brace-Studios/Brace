package dev.dubhe.brace.commands;

import dev.dubhe.brace.utils.chat.Component;

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