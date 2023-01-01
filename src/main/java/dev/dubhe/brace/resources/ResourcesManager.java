package dev.dubhe.brace.resources;

import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import dev.dubhe.brace.BraceServer;
import dev.dubhe.brace.utils.Pair;
import dev.dubhe.brace.utils.chat.TranslatableComponent;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResourcesManager {
    private final Map<String, ResourcesIndexes> ASSETS = new ConcurrentHashMap<>();
    private final Path RESOURCES_PACK = BraceServer.ROOT.resolve("resources_packs");
    private final Gson GSON = new Gson();
    public final Logger LOGGER = LogUtils.getLogger();

    public void load() {
        LOGGER.info(new TranslatableComponent("brace.resources.packs.loading").getString());
        File resPack = RESOURCES_PACK.toFile();
        if (resPack.isDirectory()) resPack.mkdir();
        File[] resPacks = resPack.listFiles((file) -> file.getName().endsWith(".zip"));
        if (null != resPacks) for (File file : resPacks) {
            this.load(file);
        }
        this.loadLanguage();
    }

    public void load(File file) {
        try {
            ZipFile zipFile = new ZipFile(file);
            for (Iterator<? extends ZipEntry> it = zipFile.entries().asIterator(); it.hasNext(); ) {
                ZipEntry entry = it.next();
                if (entry.isDirectory()) continue;
                String[] path = entry.getName().split("/");
                if (path[0].equals("assets")) {
                    String namespace = path[1];
                    ResourcesIndexes resourcesIndexes;
                    StringBuilder location;
                    ResourceType type = null;
                    if (ASSETS.containsKey(namespace)) resourcesIndexes = ASSETS.get(namespace);
                    else {
                        resourcesIndexes = new ResourcesIndexes();
                        ASSETS.put(namespace, resourcesIndexes);
                    }
                    if (path[2].equals("textures") && entry.getName().endsWith(".png")) {
                        type = ResourceType.TEXTURE;
                        String str = path[path.length - 1];
                        path[path.length - 1] = str.substring(0, str.length() - 4);
                    } else if (path[2].equals("voices") && entry.getName().endsWith(".ogg")) {
                        type = ResourceType.VOICE;
                        String str = path[path.length - 1];
                        path[path.length - 1] = str.substring(0, str.length() - 4);
                    } else if (path[2].equals("lang") && entry.getName().endsWith(".json")) {
                        type = ResourceType.LANGUAGE;
                        String str = path[path.length - 1];
                        path[path.length - 1] = str.substring(0, str.length() - 5);
                    }
                    if (type != null) {
                        location = new StringBuilder();
                        for (int i = 3; i < path.length; i++) {
                            location.append(path[i]);
                        }
                        resourcesIndexes.indexes.get(type).put(location.toString(), new Pair<>(file, entry.getName()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLanguage() {
        for (ResourcesIndexes indexes : ASSETS.values()) {
            if (!indexes.indexes.containsKey(ResourceType.LANGUAGE)) continue;
            for (Map.Entry<String, Pair<File, String>> entry : indexes.indexes.get(ResourceType.LANGUAGE).entrySet()) {
                String name = entry.getKey();
                try (ZipFile file = new ZipFile(entry.getValue().left());
                     InputStream in = file.getInputStream(file.getEntry(entry.getValue().right()))) {
                    Language.load(name, in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public enum ResourceType {
        LANGUAGE,
        TEXTURE,
        VOICE
    }

    private <T> T findResource(ResourceType type, ResourceLocation location) {
        Pair<File, String> pair = ASSETS.get(location.getNamespace()).indexes.get(type).get(location.getLocation());
        try (ZipFile file = new ZipFile(pair.left())) {
            ZipEntry entry = file.getEntry(pair.right());
            try (
                    InputStream in = file.getInputStream(entry);
                    InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)
            ) {
                if (type == ResourceType.TEXTURE || type == ResourceType.VOICE) {
                    List<Byte> buffer = new ArrayList<>();
                    while (reader.ready()) {
                        buffer.add((byte) reader.read());
                    }
                    return (T) buffer.toArray();
                } else if (type == ResourceType.LANGUAGE) {
                    return (T) GSON.fromJson(reader, ConcurrentHashMap.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] findTextureResource(ResourceLocation location) {
        return findResource(ResourceType.TEXTURE, location);
    }

    public byte[] findVoiceResource(ResourceLocation location) {
        return findResource(ResourceType.VOICE, location);
    }

    static class ResourcesIndexes {
        public final Map<ResourceType, Map<String, Pair<File, String>>> indexes = new ConcurrentHashMap<>();

        ResourcesIndexes() {
            indexes.put(ResourceType.LANGUAGE, new ConcurrentHashMap<>());
            indexes.put(ResourceType.TEXTURE, new ConcurrentHashMap<>());
            indexes.put(ResourceType.VOICE, new ConcurrentHashMap<>());
        }
    }
}
