package dev.dubhe.cbapi.util.chat;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Vector;

public class BaseComponent implements MutableComponent {
    protected final List<Component> siblings = new Vector<>();

    @NotNull
    @Override
    public String getString() {
        StringBuilder builder = new StringBuilder();
        for (Component component : siblings) {
            builder.append(component.getString());
        }
        return builder.toString();
    }

    @Override
    public MutableComponent append(Component sibling) {
        this.siblings.add(sibling);
        return this;
    }
}
