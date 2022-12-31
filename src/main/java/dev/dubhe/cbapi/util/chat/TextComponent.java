package dev.dubhe.cbapi.util.chat;


import javax.annotation.Nonnull;

public class TextComponent extends BaseComponent {
    private final String text;

    public TextComponent(String text) {
        this.text = text;
    }

    @Nonnull
    @Override
    public String getString() {
        return this.text;
    }
}
