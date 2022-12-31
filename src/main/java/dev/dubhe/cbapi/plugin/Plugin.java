package dev.dubhe.cbapi.plugin;

import java.util.List;

public abstract class Plugin {
    protected PluginMeta pluginInfo;

    public abstract void onInitialization();

    public abstract void onUninstall();

    protected void load(PluginMeta pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    public static class PluginMeta {
        public String id; // 插件ID
        public String name; // 插件名称
        public String version; // 插件版本
        public String info = ""; // 插件介绍
        public String main_class = ""; // 插件主类
        public List<String> author; // 插件作者
        public String website = ""; // 插件网站
        public String issue = ""; // 插件反馈途径
        public String source = ""; // 插件开源地址
    }
}
