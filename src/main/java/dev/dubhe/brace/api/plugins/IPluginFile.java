package dev.dubhe.brace.api.plugins;

import dev.dubhe.brace.plugins.PluginMeta;

import java.util.List;

public interface IPluginFile {
    /**
     * 获取插件元数据
     *
     * @return 插件元数据
     */
    PluginMeta getPluginMeta();

    /**
     * 获取插件依赖
     *
     * @return 插件依赖列表
     */
    List<IPluginFile> getDepends();

    /**
     * 获取插件附属
     *
     * @return 插件附属列表
     */
    List<IPluginFile> getChildren();
}
