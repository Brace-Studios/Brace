package dev.dubhe.brace.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface Guild {
    // 获取频道列表
    @Nonnull
    List<Channel> getChannels();

    // 获取用户列表
    @Nonnull
    List<User> getUsers();

    // 获取服务器ID
    @Nonnull
    Long getGuildID();

    // 通过频道ID获取频道
    @Nullable
    default Channel getChannel(@Nonnull Long id) {
        for (Channel channel : getChannels()) {
            if (channel.getChannelID().equals(id)) return channel;
        }
        return null;
    }
}
