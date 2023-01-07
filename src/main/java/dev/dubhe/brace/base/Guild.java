package dev.dubhe.brace.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface Guild {
    /**
     * 获取频道列表
     *
     * @return 当前Guild的频道列表
     */
    @Nonnull
    List<Channel> getChannels();

    /**
     * 获取用户列表
     *
     * @return 当前Guild的用户列表
     */
    @Nonnull
    List<User> getUsers();

    /**
     * 获取Guild ID
     *
     * @return 当前Guild的ID
     */
    @Nonnull
    Long getGuildID();

    /**
     * 通过频道ID获取频道
     *
     * @param id 频道ID
     * @return 频道Channel实例，为null代表频道不存在
     */
    @Nullable
    default Channel getChannel(@Nonnull Long id) {
        for (Channel channel : getChannels()) {
            if (channel.getChannelID().equals(id)) return channel;
        }
        return null;
    }
}
