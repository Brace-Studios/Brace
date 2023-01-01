package dev.dubhe.brace.events.brace;

import com.mojang.brigadier.CommandDispatcher;
import dev.dubhe.brace.commands.CommandSourceStack;
import dev.dubhe.brace.events.Event;

public class CommandRegisterEvent extends Event {
    private final CommandDispatcher<CommandSourceStack> dispatcher;

    public CommandRegisterEvent(CommandDispatcher<CommandSourceStack> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void register(CommandRegister commandRegister) {
        commandRegister.register(this.dispatcher);
    }

    public interface CommandRegister {
        void register(CommandDispatcher<CommandSourceStack> dispatcher);
    }
}
