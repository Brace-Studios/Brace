package dev.dubhe.brace.api.plugins;

import java.util.Collection;
import java.util.List;

public interface IPluginManager {
    /**
     * 加载插件
     *
     * @param file 插件文件实例
     * @return 插件对象
     */
    IPlugin loadPlugin(IPluginFile file);

    /**
     * 寻找插件
     *
     * @param id 插件ID
     * @return 插件对象
     */
    IPlugin findPlugin(String id);

    /**
     * 获取插件列表
     *
     * @return 插件列表
     */
    List<IPlugin> getPlugins();

    /**
     * 卸载插件
     *
     * @param id 要卸载的插件id
     * @return 被卸载的所有插件id
     */
    Collection<String> unloadPlugin(String id);
}
