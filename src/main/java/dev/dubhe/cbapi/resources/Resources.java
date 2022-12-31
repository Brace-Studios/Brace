package dev.dubhe.cbapi.resources;

import com.google.gson.Gson;
import dev.dubhe.cbapi.ChatServer;
import dev.dubhe.cbapi.util.Pair;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class Resources {
    public static final Map<String, ResourcesIndexes> ASSETS = new ConcurrentHashMap<>();
    public static final Path RESOURCES_PACK = ChatServer.ROOT.resolve("resources_pack");
    private static final Gson GSON = new Gson();

    public static void load() {
        File resPack = RESOURCES_PACK.toFile();
        if (resPack.isDirectory()) resPack.mkdir();
        File[] resPacks = resPack.listFiles((file) -> file.getName().endsWith(".zip"));
        if (null != resPacks) for (File file : resPacks) {
            try (ZipFile zipFile = new ZipFile(file)) {
                Resources.load(zipFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void load(ZipFile file) {
        for (Iterator<? extends ZipEntry> it = file.entries().asIterator(); it.hasNext(); ) {
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
                if (path[2].equals("textures") && entry.getName().endsWith(".png")) type = ResourceType.TEXTURE;
                else if (path[2].equals("voices") && entry.getName().endsWith(".ogg")) type = ResourceType.VOICE;
                else if (path[2].equals("lang") && entry.getName().endsWith(".json")) type = ResourceType.LANGUAGE;
                if (type != null) {
                    location = new StringBuilder();
                    for (int i = 3; i < path.length; i++) {
                        location.append(path[i]);
                    }
                    resourcesIndexes.indexes.get(type).put(location.toString(), new Pair<>(file, entry));
                }
            }
        }
    }

    public enum ResourceType {
        LANGUAGE,
        TEXTURE,
        VOICE
    }

    private static <T> T findResource(ResourceType type, ResourceLocation location) {
        Pair<ZipFile, ZipEntry> pair = ASSETS.get(location.getNamespace()).indexes.get(type).get(location.getLocation());
        try (ZipFile file = pair.left()) {
            ZipEntry entry = pair.right();
            try (
                    InputStream in = file.getInputStream(entry);
                    InputStreamReader reader = new InputStreamReader(in);
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

    public static Map<String, String> findLanguageResource(ResourceLocation location) {
        return findResource(ResourceType.LANGUAGE, location);
    }

    public static byte[] findTextureResource(ResourceLocation location) {
        return findResource(ResourceType.TEXTURE, location);
    }
    public static byte[] findVoiceResource(ResourceLocation location) {
        return findResource(ResourceType.VOICE, location);
    }

    static class ResourcesIndexes {
        public final Map<ResourceType, Map<String, Pair<ZipFile, ZipEntry>>> indexes = new ConcurrentHashMap<>();

        ResourcesIndexes() {
            indexes.put(ResourceType.LANGUAGE, new ConcurrentHashMap<>());
            indexes.put(ResourceType.TEXTURE, new ConcurrentHashMap<>());
            indexes.put(ResourceType.VOICE, new ConcurrentHashMap<>());
        }
    }
}
