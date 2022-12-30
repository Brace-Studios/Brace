package dev.dubhe.cbapi.base;

import javax.annotation.Nonnull;

public interface User {
    @Nonnull
    Long getUserID();

    // 获取私聊文字频道
    @Nonnull
    TextChannel getPrivateChannel();
}
