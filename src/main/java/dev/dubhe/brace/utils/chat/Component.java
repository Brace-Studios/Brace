package dev.dubhe.brace.utils.chat;

import com.mojang.brigadier.Message;

import javax.annotation.Nonnull;

public interface Component extends Message {
    @Override
    @Nonnull
    String getString();
}
