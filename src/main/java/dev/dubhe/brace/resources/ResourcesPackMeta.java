package dev.dubhe.brace.resources;

import dev.dubhe.brace.Brace;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

public class ResourcesPackMeta {
    int version; // 资源包加载器版本
    String description; // 资源包描述

    @NotNull
    public static ResourcesPackMeta load(@NotNull Reader reader) {
        return Brace.GSON.fromJson(reader, ResourcesPackMeta.class);
    }
}
