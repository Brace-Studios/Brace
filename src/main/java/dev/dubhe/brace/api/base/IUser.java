package dev.dubhe.brace.api.base;

import dev.dubhe.brace.api.base.chat.IComponent;
import dev.dubhe.brace.api.commands.ICommandSource;

public interface IUser extends IContact, ICommandSource {
    /**
     * 获取私有文字频道
     *
     * @return 私有文字频道
     */
    ITextChannel getPrivateTextChannel();

    @Override
    default void send(IComponent component) {
        this.getPrivateTextChannel().send(component);
    }

    @Override
    default boolean acceptsSuccess() {
        return true;
    }

    @Override
    default boolean acceptsFailure() {
        return true;
    }
}
