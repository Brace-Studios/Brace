package dev.dubhe.cbapi.event;

import com.mojang.brigadier.CommandDispatcher;
import dev.dubhe.cbapi.base.TextMessage;
import dev.dubhe.cbapi.command.CommandSourceStack;

public class Events {
    public static final Event<TextMessage> NEW_MESSAGE = new Event<>();
    public static final Event<CommandDispatcher<CommandSourceStack>> ON_COMMAND_REGISTER = new Event<>();
}
