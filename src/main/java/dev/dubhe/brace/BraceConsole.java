package dev.dubhe.brace;

import com.mojang.logging.LogUtils;
import dev.dubhe.brace.base.*;
import dev.dubhe.brace.commands.CommandSource;
import dev.dubhe.brace.commands.CommandSourceStack;
import dev.dubhe.brace.utils.chat.Component;
import dev.dubhe.brace.utils.chat.TextComponent;
import dev.dubhe.brace.utils.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class BraceConsole extends Thread implements CommandSource, User {
    public static boolean flag = true;
    private static final Logger LOGGER = LogUtils.getLogger();

    public BraceConsole() {
        this.setDaemon(true);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\\s*\n");
        while (flag) {
            String command = scanner.next();
            BraceServer.getCommands().performCommand(new CommandSourceStack(BraceServer.getBraceBot(), this, new ConsoleMessage(command, this), 0), command);
        }
    }

    public String format(String command) {
        command = command + '\n';
        return command.startsWith("/") ? command.substring(1) : command;
    }

    @NotNull
    @Override
    public Long getUserID() {
        return 0L;
    }

    @NotNull
    @Override
    public TextChannel getPrivateChannel() {
        return new ConsoleChannel();
    }

    @Override
    public void sendMessage(Component msg) {
        LOGGER.info(msg.getString());
    }

    @Override
    public boolean acceptsSuccess() {
        return true;
    }

    @Override
    public boolean acceptsFailure() {
        return true;
    }

    @Override
    public boolean shouldInformAdmins() {
        return false;
    }

    @Override
    public boolean alwaysAccepts() {
        return true;
    }

    static class ConsoleMessage implements Message {
        private final TextComponent component;
        private final BraceConsole console;

        ConsoleMessage(String component, BraceConsole console) {
            this.component = new TextComponent(component);
            this.console = console;
        }

        @NotNull
        @Override
        public Component getMsg() {
            return this.component;
        }

        @NotNull
        @Override
        public TextChannel getChannel() {
            return new ConsoleChannel();
        }

        @NotNull
        @Override
        public User getSender() {
            return this.console;
        }

        @Override
        public void reply(@NotNull Component msg) {
            console.sendMessage(msg);
        }

        @Override
        public void reply(@NotNull String msg) {
            console.sendMessage(new TextComponent(msg));
        }
    }

    static class ConsoleChannel implements TextChannel {
        @NotNull
        @Override
        public List<User> getUsers() {
            return new Vector<>();
        }

        @NotNull
        @Override
        public Long getChannelID() {
            return 0L;
        }

        @NotNull
        @Override
        public Guild getGuild() {
            return new ConsoleGuild();
        }

        @Nullable
        @Override
        public User getUser(@NotNull Long id) {
            return null;
        }

        @Override
        public void sendMessage(@NotNull Component msg) {
            LOGGER.info(msg.getString());
        }

        @Override
        public void sendMessage(@NotNull String msg) {
            LOGGER.info(msg);
        }

        @Override
        public void sendFile(@NotNull File file) {
            LOGGER.info(new TranslatableComponent("brace.filetype.file").getString());
        }
    }

    static class ConsoleGuild implements Guild {
        @NotNull
        @Override
        public List<Channel> getChannels() {
            return new Vector<>();
        }

        @NotNull
        @Override
        public List<User> getUsers() {
            return new Vector<>();
        }

        @NotNull
        @Override
        public Long getGuildID() {
            return 0L;
        }

        @Nullable
        @Override
        public Channel getChannel(@NotNull Long id) {
            return null;
        }
    }
}
