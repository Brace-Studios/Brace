package dev.dubhe.cbapi;

import dev.dubhe.cbapi.base.Server;
import dev.dubhe.cbapi.base.User;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface ChatBot {
    void onStart();

    void onStop();

    @Nonnull
    Logger getLogger();

    @Nonnull
    List<Server> getServers();

    @Nonnull
    List<User> getFriends();

    @Nullable
    User getUser(Long id);

    @Nullable
    Server getServer(Long id);

    @Nonnull
    Config getConfig();

    class Config {
        public String lang = "zh_cn";
    }
}
