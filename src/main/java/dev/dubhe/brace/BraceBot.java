package dev.dubhe.brace;

import dev.dubhe.brace.base.Guild;
import dev.dubhe.brace.base.User;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface BraceBot {
    void onInitialization();

    void onStart();

    void onStop();

    @Nonnull
    Logger getLogger();

    @Nonnull
    List<Guild> getGuilds();

    @Nonnull
    List<User> getFriends();

    @Nullable
    User getUser(Long id);

    @Nullable
    Guild getGuild(Long id);

    @Nonnull
    Config getConfig();

    class Config {
        public String lang = "zh_cn";
    }
}
