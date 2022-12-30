package dev.dubhe.cbapi.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.dubhe.cbapi.ChatServer;
import dev.dubhe.cbapi.util.chat.TranslationComponent;
import dev.dubhe.cbapi.util.tools.JarTools;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    public static final Path PLUGINS_PATH = ChatServer.ROOT.resolve("plugins");
    public static final Map<String, JavaPlugin> PLUGINS = new HashMap<>();
    public static final Gson GSON = new Gson();
    public final Logger logger = ChatServer.bot.getLogger();

    boolean load() {
        File plugins = PluginManager.PLUGINS_PATH.toFile();
        if (!plugins.isDirectory()) {
            if (!plugins.mkdirs()) return false;
        }
        for (File file : plugins.listFiles()) {
            this.load(file);
        }
        return true;
    }

    void load(File file) {
        if (!file.isFile()) return;
        if (!file.getName().endsWith(".jar")) return;
        try (JarFile jarFile = new JarFile(file)) {
            for (Iterator<JarEntry> it = jarFile.entries().asIterator(); it.hasNext(); ) {
                JarEntry entry = it.next();
                if (entry.isDirectory()) continue;
                if (entry.getName().equals("cbapi.json")) {
                    InputStream in = jarFile.getInputStream(entry);
                    InputStreamReader reader = new InputStreamReader(in);
                    try {
                        JavaPlugin.JavaPluginInfo pluginInfo = GSON.fromJson(reader, JavaPlugin.JavaPluginInfo.class);
                        if (PLUGINS.containsKey(pluginInfo.id)) {
                            logger.warn(
                                    new TranslationComponent(
                                            "cbapi.plugin.load.warn.has_same_id_plugin",
                                            pluginInfo.name,
                                            PLUGINS.get(pluginInfo.id).pluginInfo.name
                                    ).getString()
                            );
                            continue;
                        }
                        JarTools.loadJar(file);
                        try {
                            Class<JavaPlugin> pluginClass = (Class<JavaPlugin>) Class.forName(pluginInfo.main_class);
                            try {
                                JavaPlugin plugin = pluginClass.newInstance();
                                plugin.load(pluginInfo);
                                PLUGINS.put(pluginInfo.id, plugin);
                            } catch (InstantiationException e) {
                                logger.warn(new TranslationComponent("cbapi.plugin.load.warn.not_plugin", file.getName()).getString());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } catch (ClassNotFoundException e) {
                            logger.warn(new TranslationComponent("cbapi.plugin.load.warn.dont_has_main_class", pluginInfo.name).getString());
                        }
                    } catch (JsonSyntaxException e) {
                        logger.warn(new TranslationComponent("cbapi.plugin.load.warn.not_plugin", file.getName()).getString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
