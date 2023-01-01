package dev.dubhe.cbapi;

import dev.dubhe.cbapi.command.Commands;
import dev.dubhe.cbapi.plugin.PluginManager;
import dev.dubhe.cbapi.resources.Resources;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Path;

public abstract class ChatServer {
    public static final Path ROOT = new File("").toPath();
    private static ChatBot bot;
    private static Commands commands;
    private static PluginManager pluginManager;

    public static void run(@Nonnull ChatBot bot) {
        ChatServer.bot = bot;
        Resources.load();
        ChatServer.pluginManager = new PluginManager();
        ChatServer.commands = new Commands();
        ChatServer.pluginManager.load();
        ChatServer.pluginManager.onInitialization();
        bot.onStart();
    }

    public static Commands getCommands() {
        return commands;
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }

    public static ChatBot getBot() {
        return bot;
    }
}
