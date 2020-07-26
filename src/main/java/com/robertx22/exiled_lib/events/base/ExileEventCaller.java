package com.robertx22.exiled_lib.events.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ExileEventCaller<T extends ExileEvent> {

    List<T> events = new ArrayList<>();

    public ExileEventCaller() {
    }

    public void callEvents(Consumer<T> consumer) {
        events.forEach(consumer);
    }

    public void register(T t) {
        this.events.add(t);
    }

}
