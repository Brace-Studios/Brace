package dev.dubhe.brace.plugins;

import dev.dubhe.brace.BraceServer;
import dev.dubhe.brace.utils.chat.TranslatableComponent;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PluginManager {
    private static final Logger LOGGER = BraceServer.getBraceBot().getLogger();
    private final Path PLUGINS_PATH = BraceServer.ROOT.resolve("plugins");
    protected final Map<String, PluginFile> PLUGIN_FILES = new ConcurrentHashMap<>();

    public PluginManager() {
        LOGGER.info(new TranslatableComponent("brace.plugin.plugin_manager.load").getString());
        LOGGER.info(new TranslatableComponent("brace.plugin.plugins.loading").getString());
        File plugins = this.PLUGINS_PATH.toFile();
        if (!plugins.isDirectory()) {
            if (!plugins.mkdirs()) return;
        }
        File[] files = plugins.listFiles();
        if (null != files) for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                try {
                    PluginFile pluginFile = new PluginFile(file);
                    String pluginID = pluginFile.getMetaData().id;
                    if (!this.PLUGIN_FILES.containsKey(pluginID)) {
                        this.PLUGIN_FILES.put(pluginID, pluginFile);
                    } else {
                        LOGGER.warn(new TranslatableComponent("brace.plugin.load.warn.has_same_id_plugin", file.getName()).getString());
                    }
                } catch (IOException | InvocationTargetException | NoSuchMethodException |
                         IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    LOGGER.warn(new TranslatableComponent("brace.plugin.load.warn.dont_has_main_class", file.getName()).getString());
                }
            }
        }
    }

    public void onInitialization() {
        for (PluginFile pluginFile : PLUGIN_FILES.values()) {
            pluginFile.onInitialization();
        }
    }

    public void onUninstall() {
        for (PluginFile pluginFile : PLUGIN_FILES.values()) {
            pluginFile.onUninstall();
        }
    }
}
