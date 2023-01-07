package dev.dubhe.brace.utils.chat;

import dev.dubhe.brace.BraceServer;

import javax.annotation.Nonnull;

public class TranslatableComponent extends BaseComponent {
    private final String content;

    public TranslatableComponent(String key) {
        this.content = BraceServer.getResourcesManager().findLang(BraceServer.getBraceBot().getConfig().lang, key);
    }

    public TranslatableComponent(String key, Object... args) {
        this.content = BraceServer.getResourcesManager().findLang(BraceServer.getBraceBot().getConfig().lang, key).formatted(args);
    }

    @Nonnull
    @Override
    public String getString() {
        return this.content;
    }
}
