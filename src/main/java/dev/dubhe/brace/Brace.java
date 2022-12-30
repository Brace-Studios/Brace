package dev.dubhe.brace;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Brace {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
}
