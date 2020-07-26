package com.robertx22.exiled_lib.events.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ExileEventCaller<T extends ExileEvent, Data> {

    List<T> events = new ArrayList<>();

    public ExileEventCaller() {
    }

    public Data callEvents(Consumer<T> consumer, Data data) {
        events.forEach(consumer);
        return data;
    }

    public void register(T t) {
        this.events.add(t);
    }

}
