package dev.dubhe.brace.resources;

import com.google.gson.Gson;
import dev.dubhe.brace.utils.tools.StreamTool;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResourceFile {
    private static final Gson GSON = new Gson();
    private final File file;
    private final ZipFile zipFile;
    private final ResourceMeta metaData;
    private boolean isResource = false;
    private final Map<String, ZipEntry> entryMap = new ConcurrentHashMap<>();
    private final Map<String, ResourceSpace> resourceMap = new ConcurrentHashMap<>();

    public ResourceFile(File file) throws IOException {
        this.file = file;
        this.zipFile = new ZipFile(this.file);
        this.loadZipFile();
        this.metaData = this.loadMetaData();
        if (null != this.metaData) {
            this.isResource = true;
            this.loadResources();
        }
        this.zipFile.close();
    }

    private void loadZipFile() {
        for (Iterator<? extends ZipEntry> it = this.zipFile.entries().asIterator(); it.hasNext(); ) {
            ZipEntry entry = it.next();
            this.entryMap.put(entry.getName(), entry);
        }
    }

    private ResourceMeta loadMetaData() throws IOException {
        if (this.entryMap.containsKey("pack.json")) {
            try (InputStream in = this.zipFile.getInputStream(this.entryMap.get("pack.json"));
                 InputStreamReader reader = new InputStreamReader(in)) {
                MetaJson metaJson = GSON.fromJson(reader, MetaJson.class);
                String name = this.file.getName();
                byte[] icon = null;
                if (this.entryMap.containsKey("icon.png"))
                    icon = StreamTool.readStream(this.zipFile.getInputStream(this.entryMap.get("icon.png")));
                return new ResourceMeta(name, icon, metaJson.version, metaJson.description);
            }
        }
        return null;
    }

    private void loadResources() throws IOException {
        for (Map.Entry<String, ZipEntry> entry : entryMap.entrySet()) {
            if (entry.getValue().isDirectory()) continue;

            String name = entry.getKey();
            if (!name.startsWith("assets")) continue;

            String[] path = name.split("/");
            if (path.length < 4) continue;

            String namespace = path[1];
            StringBuilder location = new StringBuilder();
            for (int i = 3; i < path.length; i++) {
                location.append(path[i]);
            }

            ResourceSpace space;
            if (!resourceMap.containsKey(namespace)) {
                space = new ResourceSpace();
                resourceMap.put(namespace, space);
            } else space = resourceMap.get(namespace);

            if ("textures".equals(path[2]) && name.endsWith(".png")) {
                this.loadTextureOrVoice(location, entry.getValue(), space.imageMap);
            } else if ("voices".equals(path[2]) && name.endsWith(".amr")) {
                this.loadTextureOrVoice(location, entry.getValue(), space.voiceMap);
            } else if ("lang".equals(path[2]) && name.endsWith(".json")) {
                this.loadLang(path[path.length - 1], entry.getValue(), space);
            }
        }
    }

    private void loadTextureOrVoice(StringBuilder location, ZipEntry entry, Map<String, byte[]> map) throws IOException {
        try (InputStream in = this.zipFile.getInputStream(entry)) {
            String locate = location.toString();
            locate = locate.substring(0, locate.length() - 4);
            byte[] image = StreamTool.readStream(in);
            map.put(locate, image);
        }
    }

    private void loadLang(String locate, ZipEntry entry, ResourceSpace space) throws IOException {
        try (InputStream in = this.zipFile.getInputStream(entry);
             InputStreamReader reader = new InputStreamReader(in)) {
            locate = locate.substring(0, locate.length() - 5);
            Map<String, String> lang = GSON.fromJson(reader, Map.class);
            space.langMap.put(locate, lang);
        }
    }

    public ResourceMeta getMetaData() {
        return metaData;
    }

    public boolean isResource() {
        return isResource;
    }

    /**
     * @param language 语言
     * @param key      翻译键
     * @return 翻译内容
     */
    @Nullable
    public String findLang(String language, String key) {
        for (ResourceSpace space : resourceMap.values()) {
            if (space.langMap.containsKey(language)) {
                Map<String, String> lang = space.langMap.get(language);
                if (lang.containsKey(key)) {
                    return lang.get(key);
                }
            }
        }
        return null;
    }

    /**
     * @param location 资源定位器
     * @return 资源Byte数组
     */
    @Nullable
    public byte[] findVoice(ResourceLocation location) {
        String namespace = location.getNamespace();
        if (resourceMap.containsKey(namespace)) {
            String locate = location.getLocation();
            ResourceSpace space = resourceMap.get(namespace);
            if (space.voiceMap.containsKey(locate)) return space.voiceMap.get(locate);
        }
        return null;
    }

    /**
     * @param location 资源定位器
     * @return 资源Byte数组
     */
    @Nullable
    public byte[] findTexture(ResourceLocation location) {
        String namespace = location.getNamespace();
        if (resourceMap.containsKey(namespace)) {
            String locate = location.getLocation();
            ResourceSpace space = resourceMap.get(namespace);
            if (space.imageMap.containsKey(locate)) return space.imageMap.get(locate);
        }
        return null;
    }

    public static class ResourceSpace {
        private final Map<String, Map<String, String>> langMap = new ConcurrentHashMap<>();
        private final Map<String, byte[]> imageMap = new ConcurrentHashMap<>();
        private final Map<String, byte[]> voiceMap = new ConcurrentHashMap<>();
    }

    public static class ResourceMeta {
        public String name;
        public byte[] icon;
        public String version;
        public String description;

        private ResourceMeta(String name, byte[] icon, String version, String description) {
            this.name = name;
            this.icon = icon;
            this.version = version;
            this.description = description;
        }
    }

    public static class MetaJson {
        public String version;
        public String description = "";
    }
}
