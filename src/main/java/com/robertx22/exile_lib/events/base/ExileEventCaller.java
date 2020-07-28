package com.robertx22.exile_lib.events.base;

import java.util.ArrayList;
import java.util.List;

public class ExileEventCaller<T extends ExileEvent> {

    List<EventConsumer<T>> events = new ArrayList<>();

    public ExileEventCaller() {
    }

    public T callEvents(T event) {
        events.forEach(x -> x.accept(event));
        return event;
    }

    public void register(EventConsumer<T> t) {
        this.events.add(t);
    }

}
