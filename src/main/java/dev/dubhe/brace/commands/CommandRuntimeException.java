package dev.dubhe.brace.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.dubhe.brace.utils.chat.Component;

public class CommandRuntimeException extends RuntimeException {

    private final Component message;

    public CommandRuntimeException(Component component) {
        super(component.getString(), null, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES);
        this.message = component;
    }

    public Component getComponent() {
        return this.message;
    }
}
