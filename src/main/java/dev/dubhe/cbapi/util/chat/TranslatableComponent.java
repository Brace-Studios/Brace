package dev.dubhe.cbapi.util.chat;

import dev.dubhe.cbapi.ChatServer;
import dev.dubhe.cbapi.resources.Language;

import javax.annotation.Nonnull;

public class TranslatableComponent extends BaseComponent {
    private final String content;

    public TranslatableComponent(String key) {
        this.content = Language.getTrans(ChatServer.getBot().getConfig().lang, key);
    }

    public TranslatableComponent(String key, Object... args) {
        this.content = Language.getTrans(ChatServer.getBot().getConfig().lang, key).formatted(args);
    }

    @Nonnull
    @Override
    public String getString() {
        return this.content;
    }
}
