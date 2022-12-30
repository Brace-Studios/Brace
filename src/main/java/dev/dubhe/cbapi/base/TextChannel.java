package dev.dubhe.cbapi.base;

import dev.dubhe.cbapi.util.chat.Component;

import javax.annotation.Nonnull;
import java.io.File;

public interface TextChannel extends Channel {
    // 发送消息
    void sendMessage(@Nonnull Component msg);

    // 发送消息
    void sendMessage(@Nonnull String msg);

    // 发送文件
    void sendFile(@Nonnull File file);
}
