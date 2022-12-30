package dev.dubhe.cbapi.util.chat;


import javax.annotation.Nonnull;

public class TextComponent implements Component {
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
