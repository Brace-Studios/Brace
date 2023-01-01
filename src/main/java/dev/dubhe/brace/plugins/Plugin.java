package dev.dubhe.brace.plugins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.dubhe.brace.BraceServer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public abstract class Plugin {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected PluginMeta pluginInfo = null;
    private JsonObject defaultConfig = null;

    public abstract void onInitialization();

    public abstract void onUninstall();

    protected void load(@Nonnull PluginMeta pluginInfo, @Nullable JsonObject defaultConfig) {
        this.pluginInfo = pluginInfo;
        this.defaultConfig = defaultConfig;
    }

    public Path getDefaultConfigDir() {
        Path configDir = BraceServer.ROOT.resolve("configs").resolve(this.pluginInfo.id);
        if (!configDir.toFile().isDirectory()) configDir.toFile().mkdirs();
        return configDir;
    }

    public File getDefaultConfigFile() {
        return this.getDefaultConfigDir().resolve("config.json").toFile();
    }

    public boolean createDefaultConfig() {
        if (this.defaultConfig != null) {
            File configFile = this.getDefaultConfigDir().resolve("config.json").toFile();
            try (FileWriter writer = new FileWriter(configFile)) {
                GSON.toJson(this.defaultConfig, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
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
