package dev.dubhe.cbapi.base;

import dev.dubhe.cbapi.util.chat.Component;

import javax.annotation.Nonnull;

public interface TextMessage {
    // 获取消息内容
    @Nonnull
    Component getMsg();

    // 获取消息所处的频道
    @Nonnull
    TextChannel getChannel();

    // 获取消息发送者
    @Nonnull
    User getSender();

    // 回复消息
    void reply(@Nonnull Component msg);

    // 回复消息
    void reply(@Nonnull String msg);
}
