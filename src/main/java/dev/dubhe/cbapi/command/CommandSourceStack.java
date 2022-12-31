package dev.dubhe.cbapi.command;

import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import dev.dubhe.cbapi.ChatBot;
import dev.dubhe.cbapi.base.TextMessage;
import dev.dubhe.cbapi.util.chat.Component;
import org.jetbrains.annotations.Nullable;

public class CommandSourceStack {
    final ChatBot server;
    final CommandSource commandSource;
    final TextMessage message;
    final int permissionLevel;
    @Nullable
    private final ResultConsumer<CommandSourceStack> consumer;

    public CommandSourceStack(ChatBot server, CommandSource commandSource, TextMessage message, int permissionLevel) {
        this(server, commandSource, message, permissionLevel, (context, success, result) -> {
        });
    }

    public CommandSourceStack(ChatBot server, CommandSource commandSource, TextMessage message, int permissionLevel, @Nullable ResultConsumer<CommandSourceStack> consumer) {
        this.server = server;
        this.commandSource = commandSource;
        this.message = message;
        this.permissionLevel = permissionLevel;
        this.consumer = consumer;
    }

    public void sendSuccess(Component message, boolean allowLogging) {
        this.message.reply(message);
        if (allowLogging) server.getLogger().info(message.getString());
    }

    public void sendFailure(Component message) {
        this.message.reply(message);
        server.getLogger().warn(message.getString());
    }

    public ChatBot getServer() {
        return server;
    }

    public boolean hasPermission(int permissionLevel) {
        return this.permissionLevel <= permissionLevel;
    }

    public CommandSource getCommandSource() {
        return this.commandSource;
    }

    public void onCommandComplete(CommandContext<CommandSourceStack> context, boolean success, int result) {
        if (this.consumer != null) {
            this.consumer.onCommandComplete(context, success, result);
        }
    }
}
