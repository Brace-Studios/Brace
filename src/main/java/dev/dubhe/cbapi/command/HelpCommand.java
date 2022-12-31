package dev.dubhe.cbapi.command;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import dev.dubhe.cbapi.util.chat.TextComponent;
import dev.dubhe.cbapi.util.chat.TranslatableComponent;

import java.util.Map;

public class HelpCommand {
    private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslatableComponent("commands.help.failed"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("help").executes((commandContext) -> {
            Map<CommandNode<CommandSourceStack>, String> map = dispatcher.getSmartUsage(dispatcher.getRoot(), commandContext.getSource());
            for (String string : map.values()) {
                commandContext.getSource().sendSuccess(new TextComponent("/" + string), false);
            }

            return map.size();
        }).then(Commands.argument("command", StringArgumentType.greedyString()).executes((commandContext) -> {
            ParseResults<CommandSourceStack> parseResults = dispatcher.parse(StringArgumentType.getString(commandContext, "command"), commandContext.getSource());
            if (parseResults.getContext().getNodes().isEmpty()) {
                throw ERROR_FAILED.create();
            } else {
                Map<CommandNode<CommandSourceStack>, String> map = dispatcher.getSmartUsage(Iterables.getLast(parseResults.getContext().getNodes()).getNode(), commandContext.getSource());

                for (String string : map.values()) {
                    CommandSourceStack var10000 = commandContext.getSource();
                    String var10003 = parseResults.getReader().getString();
                    var10000.sendSuccess(new TextComponent("/" + var10003 + " " + string), false);
                }

                return map.size();
            }
        })));
    }
}
