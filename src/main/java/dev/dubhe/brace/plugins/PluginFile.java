package dev.dubhe.brace.plugins;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import dev.dubhe.brace.BraceServer;
import dev.dubhe.brace.utils.chat.TranslatableComponent;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginFile {
    private static final Gson GSON = new Gson();
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final PluginClassLoader classLoader = new PluginClassLoader(PluginFile.class.getClassLoader());
    private final File file;
    private final JarFile jarFile;
    private final Plugin.PluginMeta metaData;
    private final JsonObject defaultConfig;
    protected final Map<String, Class<?>> classMap = new ConcurrentHashMap<>();
    private final Map<String, JarEntry> entryMap = new ConcurrentHashMap<>();
    private Plugin main = null;

    public PluginFile(File file) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        this.file = file;
        this.jarFile = new JarFile(this.file);
        this.loadJarFile();
        this.metaData = this.loadMetaData();
        this.defaultConfig = this.loadDefaultConfig();
        if (null != this.metaData) {
            this.loadClasses();
            this.main = this.load();
            BraceServer.getResourcesManager().load(this.file);
        }
        jarFile.close();
    }

    private void loadJarFile() {
        for (Iterator<JarEntry> it = this.jarFile.entries().asIterator(); it.hasNext(); ) {
            JarEntry entry = it.next();
            this.entryMap.put(entry.getName(), entry);
        }
    }

    private JsonObject loadDefaultConfig() throws IOException {
        if (this.entryMap.containsKey("config.json")) {
            JarEntry metaEntry = this.entryMap.get("config.json");
            try (InputStream in = this.jarFile.getInputStream(metaEntry);
                 InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                return GSON.fromJson(reader, JsonObject.class);
            }
        }
        return null;
    }

    private Plugin.PluginMeta loadMetaData() throws IOException {
        if (this.entryMap.containsKey("brace.json")) {
            JarEntry metaEntry = this.entryMap.get("brace.json");
            try (InputStream in = this.jarFile.getInputStream(metaEntry);
                 InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                return GSON.fromJson(reader, Plugin.PluginMeta.class);
            } catch (JsonSyntaxException e) {
                LOGGER.warn(new TranslatableComponent("brace.plugin.load.warn.meta_syntax", this.file.getName()).getString());
            }
        } else {
            LOGGER.warn(new TranslatableComponent("brace.plugin.load.warn.not_plugin", this.file.getName()).getString());
        }
        return null;
    }

    private void loadClasses() throws IOException, ClassFormatError {
        for (Map.Entry<String, JarEntry> entry : this.entryMap.entrySet()) {
            if (!entry.getKey().endsWith(".class")) continue;
            String className = entry.getKey().replace("/", ".").substring(0, entry.getKey().length() - 6);
            byte[] buffer = new byte[1024];
            byte[] bytes = new byte[0];
            int len;
            try (InputStream in = this.jarFile.getInputStream(entry.getValue())) {
                while ((len = in.read(buffer)) != -1) {
                    byte[] newBytes = new byte[bytes.length + len];
                    System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
                    System.arraycopy(buffer, 0, newBytes, bytes.length, len);
                    bytes = newBytes;
                }
            }
            Class<?> clazz = classLoader.defineClazz(className, bytes, 0, bytes.length);
            this.classMap.put(className, clazz);
        }
    }

    private Plugin load() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String mainClassName = metaData.main_class;
        if (this.classMap.containsKey(mainClassName)) {
            Class<?> clazz = this.classMap.get(mainClassName);
            Object obj = clazz.getDeclaredConstructor().newInstance();
            if (obj instanceof Plugin plugin) {
                plugin.load(metaData, defaultConfig);
                return plugin;
            } else {
                LOGGER.warn(new TranslatableComponent("brace.plugin.load.warn.main_class", metaData.id).getString());
            }
        } else {
            LOGGER.warn(new TranslatableComponent("brace.plugin.load.warn.dont_has_main_class", file.getName()).getString());
        }
        return null;
    }

    public Plugin.PluginMeta getMetaData() {
        return metaData;
    }

    public void onInitialization() {
        LOGGER.info(new TranslatableComponent("brace.plugin.loading", this.metaData.id).getString());
        if (null != main) {
            main.onInitialization();
        }
    }

    public void onUninstall() {
        LOGGER.info(new TranslatableComponent("brace.plugin.unloading", this.metaData.id).getString());
        if (null != main) {
            main.onUninstall();
        }
    }
}
