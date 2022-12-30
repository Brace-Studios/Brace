package dev.dubhe.cbapi.command;

import dev.dubhe.cbapi.ChatBot;
import dev.dubhe.cbapi.base.TextMessage;
import dev.dubhe.cbapi.util.chat.Component;

public class CommandSourceStack {
    final ChatBot server;
    final CommandSource commandSource;
    final TextMessage message;
    final int permissionLevel;

    public CommandSourceStack(ChatBot server, CommandSource commandSource, TextMessage message, int permissionLevel) {
        this.server = server;
        this.commandSource = commandSource;
        this.message = message;
        this.permissionLevel = permissionLevel;
    }

    public void sendSuccess(Component message, boolean allowLogging) {
        this.message.reply(message);
        if (allowLogging) server.getLogger().info(message.getString());
    }

    public void sendFailure(Component message) {
        this.message.reply(message);
        server.getLogger().warn(message.getString());
    }

    public boolean hasPermission(int permissionLevel) {
        return this.permissionLevel <= permissionLevel;
    }

    public CommandSource getCommandSource() {
        return this.commandSource;
    }
}
