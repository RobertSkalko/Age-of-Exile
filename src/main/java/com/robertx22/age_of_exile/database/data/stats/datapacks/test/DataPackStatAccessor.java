package com.robertx22.age_of_exile.database.data.stats.datapacks.test;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;

import java.util.HashMap;

public class DataPackStatAccessor<T> {

    private HashMap<T, String> map = new HashMap<>();

    public Stat get(T key) {
        return Database.Stats()
            .get(map.get(key));
    }

    public void add(T key, String id) {
        map.put(key, id);
    }
}
