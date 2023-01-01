package dev.dubhe.brace.events.guild;

import dev.dubhe.brace.base.Guild;
import dev.dubhe.brace.base.User;

public class GuildMemberEvent extends GuildEvent {
    protected User member;

    public GuildMemberEvent(Guild guild, User member) {
        super(guild);
        this.member = member;
    }

    public User getMember() {
        return member;
    }
}
