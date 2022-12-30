package dev.dubhe.brace.api.commands;

public interface ICommandSource {
    /**
     * @return 是否接收命令执行失败消息
     */
    boolean acceptsFailure();

    /**
     * @return 是否接收命令执行成功消息
     */
    boolean acceptsSuccess();
}
