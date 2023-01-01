package dev.dubhe.brace.utils.chat;

import dev.dubhe.brace.BraceServer;
import dev.dubhe.brace.resources.Language;

import javax.annotation.Nonnull;

public class TranslatableComponent extends BaseComponent {
    private final String content;

    public TranslatableComponent(String key) {
        this.content = Language.getTrans(BraceServer.getBraceBot().getConfig().lang, key);
    }

    public TranslatableComponent(String key, Object... args) {
        this.content = Language.getTrans(BraceServer.getBraceBot().getConfig().lang, key).formatted(args);
    }

    @Nonnull
    @Override
    public String getString() {
        return this.content;
    }
}
