package dev.dubhe.cbapi;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Path;

public abstract class ChatServer {
    public static ChatBot bot;
    public static final Path ROOT = new File("").toPath();

    public static void run(@Nonnull ChatBot bot) {
        ChatServer.bot = bot;
        bot.onStart();
    }
}
