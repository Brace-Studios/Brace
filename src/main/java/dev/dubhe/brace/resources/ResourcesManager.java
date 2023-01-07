package dev.dubhe.brace.resources;

import com.mojang.logging.LogUtils;
import dev.dubhe.brace.BraceServer;
import dev.dubhe.brace.utils.chat.TranslatableComponent;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Vector;

public class ResourcesManager {
    private final Path RESOURCES_PACK = BraceServer.ROOT.resolve("resources_packs");
    public final Logger LOGGER = LogUtils.getLogger();
    public final List<ResourceFile> resourceFiles = new Vector<>();

    public void load() {
        LOGGER.info(new TranslatableComponent("brace.resources.packs.loading").getString());
        File resPack = RESOURCES_PACK.toFile();
        if (resPack.isDirectory()) resPack.mkdirs();
        File[] resPacks = resPack.listFiles((file) -> file.getName().endsWith(".zip"));
        if (null != resPacks) for (File file : resPacks) {
            this.load(file);
        }
    }

    public void load(File file) {
        try {
            ResourceFile resourceFile = new ResourceFile(file);
            if (resourceFile.isResource()) resourceFiles.add(resourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param language 语言
     * @param key      翻译键
     * @return 翻译内容
     */
    @Nonnull
    public String findLang(String language, String key) {
        for (ResourceFile file : this.resourceFiles) {
            String s = file.findLang(language, key);
            if (s != null) return s;
        }
        return key;
    }

    /**
     * @param location 资源定位器
     * @return 资源Byte数组
     */
    @Nullable
    public byte[] findVoice(ResourceLocation location) {
        for (ResourceFile file : this.resourceFiles) {
            byte[] s = file.findVoice(location);
            if (s != null) return s;
        }
        return null;
    }

    /**
     * @param location 资源定位器
     * @return 资源Byte数组
     */
    @Nullable
    public byte[] findTexture(ResourceLocation location) {
        for (ResourceFile file : this.resourceFiles) {
            byte[] s = file.findTexture(location);
            if (s != null) return s;
        }
        return null;
    }

    public void sort(ResourceFilesSorter sorter) {
        sorter.sort(this.resourceFiles);
    }

    @FunctionalInterface
    public interface ResourceFilesSorter {
        void sort(List<ResourceFile> resourceFiles);
    }
}
