package dev.dubhe.brace.events;

public interface Cancellable {
    boolean isCanceled();

    void cancel();
}
