package dev.dubhe.cbapi.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.dubhe.cbapi.ChatServer;
import dev.dubhe.cbapi.resources.Resources;
import dev.dubhe.cbapi.util.Pair;
import dev.dubhe.cbapi.util.chat.TranslatableComponent;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

public class PluginManager {
    public static final Path PLUGINS_PATH = ChatServer.ROOT.resolve("plugins");
    public static final Map<String, Pair<Plugin.PluginMeta, File>> PLUGIN_FILES = new ConcurrentHashMap<>();
    public static final Map<String, Plugin> JAVA_PLUGINS = new ConcurrentHashMap<>();
    public static final Gson GSON = new Gson();
    public final Logger logger = ChatServer.getBot().getLogger();

    boolean load() {
        File plugins = PluginManager.PLUGINS_PATH.toFile();
        if (!plugins.isDirectory()) {
            if (!plugins.mkdirs()) return false;
        }
        File[] files = plugins.listFiles();
        if (null != files) for (File file : files) {
            this.load(file);
        }
        this.loadPluginJar();
        return true;
    }

    void load(File file) {
        if ((!file.isFile()) || (!file.getName().endsWith(".jar"))) return;
        try (JarFile jarFile = new JarFile(file)) {
            for (Iterator<JarEntry> it = jarFile.entries().asIterator(); it.hasNext(); ) {
                JarEntry entry = it.next();
                if (entry.isDirectory()) continue;
                if (entry.getName().equals("cbapi.json")) {
                    Plugin.PluginMeta meta = checkMeta(jarFile, entry);
                    if (null != meta) {
                        PLUGIN_FILES.put(meta.id, new Pair<>(meta, file));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    Plugin.PluginMeta checkMeta(JarFile file, JarEntry entry) {
        try (
                InputStream in = file.getInputStream(entry);
                InputStreamReader reader = new InputStreamReader(in)
        ) {
            Plugin.PluginMeta meta = GSON.fromJson(reader, Plugin.PluginMeta.class);
            if (PLUGIN_FILES.containsKey(meta.id)) {
                logger.warn(
                        new TranslatableComponent(
                                "cbapi.plugin.load.warn.has_same_id_plugin",
                                file.getName(),
                                PLUGIN_FILES.get(meta.id).right().getName()
                        ).getString()
                );
                return null;
            }
            return GSON.fromJson(reader, Plugin.PluginMeta.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            logger.warn(new TranslatableComponent("cbapi.plugin.load.warn.meta_syntax_error", file.getName()).getString());
            return null;
        }
    }

    void loadPluginJar() {
        URL[] urls = (URL[]) PLUGIN_FILES.values().stream().map(pair -> {
            try {
                return pair.right().toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }).toArray();
        try (URLClassLoader classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader())) {
            for (Pair<Plugin.PluginMeta, File> pair : PLUGIN_FILES.values()) {
                Plugin.PluginMeta meta = pair.left();
                File file = pair.right();
                Class<?> clazz = classLoader.loadClass(meta.main_class);
                if (clazz.getDeclaredConstructor().newInstance() instanceof Plugin plugin) {
                    plugin.load(meta);
                    JAVA_PLUGINS.put(meta.id, plugin);
                    try (ZipFile zipFile = new ZipFile(file)) {
                        Resources.load(zipFile);
                    }
                } else {
                    logger.warn(new TranslatableComponent("插件 %s 的主类没有继承 dev.dubhe.cbapi.plugin.JavaPlugin", file.getName()).getString());
                }
            }
        } catch (InvocationTargetException | NoSuchMethodException | ClassNotFoundException | IllegalAccessException |
                 IOException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
