package dev.dubhe.brace.base;

import dev.dubhe.brace.utils.chat.Component;

import javax.annotation.Nonnull;
import java.io.File;

public interface TextChannel extends Channel {
    /** 发送消息
     *
     * @param msg 要发送的消息
     */
    void sendMessage(@Nonnull Component msg);

    /** 发送消息
     *
     * @param msg 要发送的消息
     */
    void sendMessage(@Nonnull String msg);

    /** 发送文件
     *
     * @param file 要发送的文件
     */
    void sendFile(@Nonnull File file);
}
