package dev.dubhe.cbapi.base;

import dev.dubhe.cbapi.command.CommandSource;
import dev.dubhe.cbapi.util.chat.Component;

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
