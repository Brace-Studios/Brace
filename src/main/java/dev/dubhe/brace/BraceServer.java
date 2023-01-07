package dev.dubhe.brace;

import com.mojang.logging.LogUtils;
import dev.dubhe.brace.commands.Commands;
import dev.dubhe.brace.events.EventManager;
import dev.dubhe.brace.plugins.PluginManager;
import dev.dubhe.brace.resources.ResourcesManager;
import dev.dubhe.brace.utils.chat.TranslatableComponent;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Path;

public abstract class BraceServer {
    public static Path ROOT;
    private static BraceBot bot;
    private static ResourcesManager resourcesManager;
    private static Commands commands;
    private static PluginManager pluginManager;
    private static EventManager eventManager;
    private static BraceConsole console;
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void run(Path root, @Nonnull BraceBot bot) {
        BraceServer.ROOT = root;
        BraceServer.bot = bot;
        BraceServer.resourcesManager = new ResourcesManager();
        BraceServer.resourcesManager.load(new File(BraceServer.class.getProtectionDomain().getCodeSource().getLocation().getFile()));
        BraceServer.LOGGER.info(new TranslatableComponent("brace.bot.start").getString());
        BraceServer.resourcesManager.load();
        BraceServer.eventManager = new EventManager();
        BraceServer.commands = new Commands();
        BraceServer.pluginManager = new PluginManager();
        BraceServer.pluginManager.onInitialization();
        BraceServer.bot.onInitialization();
        BraceServer.commands.load();
        BraceServer.console = new BraceConsole();
        BraceServer.console.start();
        BraceServer.bot.onStart();
        BraceServer.LOGGER.info(new TranslatableComponent("brace.bot.started").getString());
    }

    public static ResourcesManager getResourcesManager() {
        return resourcesManager;
    }

    public static Commands getCommands() {
        return commands;
    }

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static PluginManager getPluginManager() {
        return pluginManager;
    }

    public static BraceConsole getConsole() {
        return console;
    }

    public static BraceBot getBraceBot() {
        return bot;
    }
}
