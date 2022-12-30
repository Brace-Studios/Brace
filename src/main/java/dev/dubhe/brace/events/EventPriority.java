package dev.dubhe.brace.events;

public enum EventPriority {
    HIGHEST(0),
    HIGH(1),
    NORMAL(2),
    LOW(3),
    LOWEST(4);
    public final int priority;

    EventPriority(int priority) {
        this.priority = priority;
    }
}
