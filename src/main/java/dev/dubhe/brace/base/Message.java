package dev.dubhe.brace.base;

import dev.dubhe.brace.utils.chat.Component;

import javax.annotation.Nonnull;

public interface Message {
    /**
     * 获取消息内容
     *
     * @return 消息内容
     */
    @Nonnull
    Component getMsg();

    /**
     * 获取消息所处的频道
     *
     * @return 消息所处的频道
     */
    @Nonnull
    TextChannel getChannel();

    /**
     * 获取消息发送者
     *
     * @return 消息发送者
     */
    @Nonnull
    User getSender();

    /**
     * 回复消息
     *
     * @param msg 要回复的消息
     */
    void reply(@Nonnull Component msg);

    /**
     * 回复消息
     *
     * @param msg 要回复的消息
     */
    void reply(@Nonnull String msg);
}
