package dev.dubhe.brace.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface Channel {
    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    @Nonnull
    List<User> getUsers();

    /**
     * 获取频道ID
     *
     * @return 频道ID
     */
    @Nonnull
    Long getChannelID();

    /**
     * 获取Guild
     *
     * @return 父级Guild
     */
    @Nonnull
    Guild getGuild();

    /**
     * 通过用户ID获取用户
     *
     * @param id 用户ID
     * @return 用户User实例，为null代表用户不存在
     */
    @Nullable
    default User getUser(@Nonnull Long id) {
        for (User user : getUsers()) {
            if (user.getUserID().equals(id)) return user;
        }
        return null;
    }
}
