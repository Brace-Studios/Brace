package dev.dubhe.brace.utils.chat;

import com.mojang.brigadier.Message;

public class ComponentUtils {
    public static Component fromMessage(Message message) {
        return (Component)(message instanceof Component ? (Component)message : new TextComponent(message.getString()));
    }
}
