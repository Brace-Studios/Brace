package dev.dubhe.brace.events.guild;

import dev.dubhe.brace.base.Guild;
import dev.dubhe.brace.events.Event;

public class GuildEvent extends Event {
    protected Guild guild;

    public GuildEvent(Guild guild) {
        this.guild = guild;
    }

    public Guild getGuild() {
        return guild;
    }
}
