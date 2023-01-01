package dev.dubhe.brace.base;

import dev.dubhe.brace.commands.CommandSource;
import dev.dubhe.brace.utils.chat.Component;

import javax.annotation.Nonnull;

public interface User extends CommandSource {
    @Nonnull
    Long getUserID();

    // 获取私聊文字频道
    @Nonnull
    TextChannel getPrivateChannel();

    @Override
    default void sendMessage(Component component) {
        this.getPrivateChannel().sendMessage(component);
    }

    @Override
    default boolean acceptsFailure() {
        return true;
    }

    @Override
    default boolean acceptsSuccess() {
        return true;
    }
}
