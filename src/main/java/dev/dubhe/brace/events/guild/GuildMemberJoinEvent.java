package dev.dubhe.brace.events.guild;

import dev.dubhe.brace.base.Guild;
import dev.dubhe.brace.base.User;

public class GuildMemberJoinEvent extends GuildMemberEvent {
    public GuildMemberJoinEvent(Guild guild, User member) {
        super(guild, member);
    }
}
