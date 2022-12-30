package dev.dubhe.brace.api.plugins;

import dev.dubhe.brace.plugins.PluginMeta;

public interface IPlugin {
    /**
     * 获取插件ID
     *
     * @return 插件ID
     */
    String getID();

    /**
     * 获取插件元数据
     *
     * @return 插件元数据
     */
    PluginMeta getMeta();

    /**
     * 初始化插件时执行
     */
    void initialization();

    /**
     * 卸载插件时执行
     */
    void uninstall();
}
