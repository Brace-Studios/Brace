package dev.dubhe.brace.events.guild;

import dev.dubhe.brace.base.Guild;
import dev.dubhe.brace.base.User;

public class GuildMemberLeaveEvent extends GuildMemberEvent {
    public GuildMemberLeaveEvent(Guild guild, User member) {
        super(guild, member);
    }
}
