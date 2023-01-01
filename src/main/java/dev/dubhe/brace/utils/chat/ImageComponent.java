package dev.dubhe.brace.utils.chat;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public class ImageComponent extends BaseComponent {
    private final InputStream imageStream;

    public ImageComponent(InputStream imageStream) {
        this.imageStream = imageStream;
    }

    public InputStream getImageStream() {
        return this.imageStream;
    }

    @NotNull
    @Override
    public String getString() {
        return new TranslatableComponent("brace.filetype.image").getString();
    }
}
