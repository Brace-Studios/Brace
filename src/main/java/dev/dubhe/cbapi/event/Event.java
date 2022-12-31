package dev.dubhe.cbapi.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Event<T> {
    private final List<Inter<T>> inters = Collections.synchronizedList(new ArrayList<>());

    public void inter(Inter<T> inter) {
        this.inters.add(inter);
    }

    public void invoker(T t) {
        for (Inter<T> inter : inters) {
            inter.inter(t);
        }
    }

    @FunctionalInterface
    interface Inter<T> {
        void inter(T context);
    }
}
