package dev.dubhe.brace.api.resources;

import java.util.Map;

public interface IResourcesSubPack {
    /**
     * 获取资源包子包命名空间
     *
     * @return 命名空间
     */
    String getNamespace();

    /**
     * 获取翻译文本
     *
     * @return Key - 语言名称，Value - 翻译键值映射表
     */
    Map<String, Map<String, String>> getLanguages();

    /**
     * 获取贴图
     *
     * @return Key - 贴图名称，Value - 贴图数据
     */
    Map<String, byte[]> getTextures();

    /**
     * 获取语音
     *
     * @return Key - 语音名称，Value - 语音数据
     */
    Map<String, byte[]> getVoice();
}
