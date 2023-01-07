package dev.dubhe.brace.base;

import dev.dubhe.brace.commands.CommandSource;
import dev.dubhe.brace.utils.chat.Component;

import javax.annotation.Nonnull;

public interface User extends CommandSource {
    /** 获取用户ID
     *
     * @return 用户ID
     */
    @Nonnull
    Long getUserID();

    /** 获取私聊文字频道
     *
     * @return 私聊文字频道
     */
    @Nonnull
    TextChannel getPrivateChannel();

    /** 发送消息
     *
     * @param component 要发送的消息
     */
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
