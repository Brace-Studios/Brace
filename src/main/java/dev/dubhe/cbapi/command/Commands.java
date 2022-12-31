package dev.dubhe.cbapi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import dev.dubhe.cbapi.event.Events;
import dev.dubhe.cbapi.util.Util;
import dev.dubhe.cbapi.util.chat.*;
import org.slf4j.Logger;

public class Commands {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final CommandDispatcher<CommandSourceStack> dispatcher = new CommandDispatcher<>();

    public Commands() {
        HelpCommand.register(this.dispatcher);
        Events.ON_COMMAND_REGISTER.invoker(this.dispatcher);
        this.dispatcher.findAmbiguities((commandNode, commandNode2, commandNode3, collection) -> LOGGER.warn("Ambiguity between arguments {} and {} with inputs: {}", this.dispatcher.getPath(commandNode2), this.dispatcher.getPath(commandNode3), collection));
        this.dispatcher.setConsumer((commandContext, bl, i) -> commandContext.getSource().onCommandComplete(commandContext, bl, i));
    }

    public int performCommand(CommandSourceStack source, String command) {
        StringReader stringReader = new StringReader(command);
        if (stringReader.canRead() && stringReader.peek() == '/') {
            stringReader.skip();
        }
        try {
            return this.dispatcher.execute(stringReader, source);
        } catch (CommandRuntimeException e) {
            source.sendFailure(e.getComponent());
            return 0;
        } catch (CommandSyntaxException e) {
            source.sendFailure(ComponentUtils.fromMessage(e.getRawMessage()));
            if (e.getInput() != null && e.getCursor() >= 0) {
                int i = Math.min(e.getInput().length(), e.getCursor());
                MutableComponent mutableComponent = (new TextComponent(""));
                if (i > 10) {
                    mutableComponent.append("...");
                }

                mutableComponent.append(e.getInput().substring(Math.max(0, i - 10), i));
                if (i < e.getInput().length()) {
                    Component component = (new TextComponent(e.getInput().substring(i)));
                    mutableComponent.append(component);
                }

                mutableComponent.append((new TranslatableComponent("command.context.here")));
                source.sendFailure(mutableComponent);
            }
        } catch (Exception e) {
            MutableComponent mutableComponent2 = new TextComponent(e.getMessage() == null ? e.getClass().getName() : e.getMessage());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("Command exception: {}", command, e);
                StackTraceElement[] stackTraceElements = e.getStackTrace();

                for (int j = 0; j < Math.min(stackTraceElements.length, 3); ++j) {
                    mutableComponent2.append("\n\n").append(stackTraceElements[j].getMethodName()).append("\n ").append(stackTraceElements[j].getFileName()).append(":").append(String.valueOf(stackTraceElements[j].getLineNumber()));
                }
            }

            source.sendFailure((new TranslatableComponent("command.failed")));
            source.sendFailure(new TextComponent(Util.describeError(e)));
            LOGGER.error("'{}' threw an exception", command, e);

            return (byte) 0;
        }
        return 0;
    }

    public static LiteralArgumentBuilder<CommandSourceStack> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    public static <T> RequiredArgumentBuilder<CommandSourceStack, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    public CommandDispatcher<CommandSourceStack> getDispatcher() {
        return dispatcher;
    }

    public enum Permission {
        OWNER(0),
        ADMIN(1),
        MASTER(2),
        MODERATOR(3);
        public final int level;

        Permission(int level) {
            this.level = level;
        }
    }
}
