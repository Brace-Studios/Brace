package dev.dubhe.brace.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import dev.dubhe.brace.BraceConsole;
import dev.dubhe.brace.utils.chat.TextComponent;

import java.util.Map;

public class HelpCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("help").executes((commandContext) -> {
            Map<CommandNode<CommandSourceStack>, String> map = dispatcher.getSmartUsage(dispatcher.getRoot(), commandContext.getSource());
            StringBuilder stringBuilder = new StringBuilder();
            CommandSourceStack stack = commandContext.getSource();
            if (stack.commandSource instanceof BraceConsole) {
                for (String string : map.values()) {
                    stringBuilder.append("/").append(string).append("\n");
                    stack.sendSuccess(new TextComponent("/" + string), false);
                }
            } else {
                for (String string : map.values()) {
                    stringBuilder.append("/").append(string).append("\n");
                }
                stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
                stack.sendSuccess(new TextComponent(stringBuilder.toString()), false);
            }
            return map.size();
        }));
    }
}
