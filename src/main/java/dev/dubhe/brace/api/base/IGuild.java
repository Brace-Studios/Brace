package dev.dubhe.brace.api.base;

import dev.dubhe.brace.api.base.chat.IComponent;

import java.util.List;

public interface IGuild extends IContact {
    /**
     * 获取默认文字频道
     * @return 默认文字频道
     */
    ITextChannel getDefaultTextChannel();

    /**
     * 获取用户列表
     * @return 用户列表
     */
    List<IUser> getUsers();

    /**
     * 获取文字频道列表
     * @return 文字频道列表
     */
    List<ITextChannel> getTextChannels();

    @Override
    default void send(IComponent component) {
        this.getDefaultTextChannel().send(component);
    }
}
