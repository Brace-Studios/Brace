package dev.dubhe.brace.plugins;

import com.google.gson.annotations.SerializedName;
import dev.dubhe.brace.Brace;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.List;
import java.util.Map;

public class PluginMeta {
    String id; // 插件ID
    String name; // 插件名称
    String version; // 插件版本
    String description; // 插件描述
    @SerializedName("main_class")
    String mainClass; // 插件主类
    List<String> author; // 插件作者
    String website; // 插件网站
    String issue; // 插件反馈
    String source; // 插件源码
    Map<String, String> depends; // 插件依赖

    @NotNull
    public static PluginMeta load(@NotNull Reader reader) {
        return Brace.GSON.fromJson(reader, PluginMeta.class);
    }
}
