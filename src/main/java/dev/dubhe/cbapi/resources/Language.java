package dev.dubhe.cbapi.resources;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Language {
    public static final Map<String, Map<String, String>> LANG_MAP = new HashMap<>();
    public static final Gson GSON = new Gson();

    public static String getTrans(String lang, String key) {
        Map<String, String> transMap = LANG_MAP.getOrDefault(lang, LANG_MAP.getOrDefault("zh_cn", new HashMap<>()));
        return transMap.getOrDefault(key, LANG_MAP.getOrDefault("zh_cn", new HashMap<>()).getOrDefault(key, key));
    }

    public static void load(String name, InputStream in) {
        try (InputStreamReader reader = new InputStreamReader(in)) {
            Map<String, String> trans_map = GSON.fromJson(reader, HashMap.class);
            if (LANG_MAP.containsKey(name)) {
                LANG_MAP.get(name).putAll(trans_map);
            } else LANG_MAP.put(name, trans_map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
