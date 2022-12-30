package dev.dubhe.cbapi.util.chat;

import dev.dubhe.cbapi.ChatServer;
import dev.dubhe.cbapi.resources.Language;

import javax.annotation.Nonnull;

public class TranslationComponent implements Component {
    private final String content;

    public TranslationComponent(String key) {
        this.content = Language.getTrans(ChatServer.bot.getConfig().lang, key);
    }

    public TranslationComponent(String key, Object... args) {
        this.content = Language.getTrans(ChatServer.bot.getConfig().lang, key).formatted(args);
    }

    @Nonnull
    @Override
    public String getString() {
        return this.content;
    }
}
