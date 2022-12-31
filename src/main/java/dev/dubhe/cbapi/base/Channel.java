package dev.dubhe.cbapi.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface Channel {
    // 获取用户列表
    @Nonnull
    List<User> getUsers();

    // 获取频道ID
    @Nonnull
    Long getChannelID();

    // 获取服务器
    @Nonnull
    Guild getGuild();

    // 通过用户ID获取用户
    @Nullable
    default User getUser(@Nonnull Long id) {
        for (User user : getUsers()) {
            if (user.getUserID().equals(id)) return user;
        }
        return null;
    }
}
