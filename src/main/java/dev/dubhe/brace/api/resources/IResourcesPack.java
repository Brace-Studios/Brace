package dev.dubhe.brace.api.resources;

import java.util.Map;

public interface IResourcesPack {
    /**
     * 获取资源包名称
     *
     * @return 资源包名称
     */
    String getName();

    /**
     * 获取资源包加载器版本
     *
     * @return 资源包加载器版本
     */
    int getVersion();

    /**
     * 获取资源包描述
     *
     * @return 资源包描述
     */
    String getDescription();

    /**
     * 获取资源包子包
     *
     * @return Key - 命名空间，Value - 资源包子包
     */
    Map<String, IResourcesSubPack> getSubPacks();
}
